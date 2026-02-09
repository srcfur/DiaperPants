package com.srcfur.diaperpants.block.entity;

import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.networking.ModMessages;
import com.srcfur.diaperpants.util.DiaperFamily;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.logging.Logger;

public class DiaperBagEntity extends BlockEntity {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(10, ItemStack.EMPTY);
    private int heldDiapers = 0;
    public boolean checkCanAddDiaper(ItemStack diaper){
        if(heldDiapers > 10){
            return false;
        }
        if(heldDiapers == 0){
            return true;
        }
        ItemStack compstack = inventory.get(heldDiapers - 1);

        DiaperArmorItem compDiaper = (DiaperArmorItem) compstack.getItem();
        DiaperArmorItem feedDiaper = (DiaperArmorItem) diaper.getItem();

        if(compDiaper.family != DiaperFamily.NONE){
            return compDiaper.family == feedDiaper.family;
        }else{
            return compDiaper == feedDiaper;
        }
    }
    public ItemStack PopNextDiaper(){
        if(heldDiapers <= 0) {return ItemStack.EMPTY;}
        heldDiapers--;
        ItemStack stack = inventory.get(heldDiapers);
        inventory.set(heldDiapers, ItemStack.EMPTY);
        Logger.getGlobal().info("Dispensed diaper :3!");
        SyncDiapers();
        return stack;
    }
    public boolean InsertDiaper(ItemStack diaper){
        if(checkCanAddDiaper(diaper)){
            inventory.set(heldDiapers, diaper);
            //Logger.getGlobal().info("Inserted diaper :3!");
            heldDiapers++;
            SyncDiapers();
            return true;
        }
        return false;
    }
    public String getDiaperBagName(){
        if(heldDiapers == 0){
            return "Empty";
        }
        DiaperArmorItem item = (DiaperArmorItem) inventory.get(heldDiapers - 1).getItem();
        if(item.family != DiaperFamily.NONE){
            return item.family.DiaperName;
        }else{
            return inventory.get(heldDiapers - 1).getName().getString();
        }
    }
    public List<ItemStack> getAllDiapers(){
        return inventory.subList(0, heldDiapers);
    }
    public void ReadInventoryFromNetwork(PacketByteBuf buffer){
        heldDiapers = buffer.readInt();
        for(int i = 0; i < heldDiapers; i++){
            inventory.set(i, buffer.readItemStack());
        }
    }
    public void WriteInventoryToNetwork(PacketByteBuf buffer){
        buffer.writeInt(heldDiapers);
        for(int i = 0;i < heldDiapers; i++){
            buffer.writeItemStack(inventory.get(i));
        }
    }
    private void SyncDiapers(){
        this.markDirty();
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(pos.getX());
        buffer.writeInt(pos.getY());
        buffer.writeInt(pos.getZ());
        WriteInventoryToNetwork(buffer);
        List<ServerPlayerEntity> players = (List<ServerPlayerEntity>)world.getPlayers();
        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i).squaredDistanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ())) < 24){
                ServerPlayNetworking.send(players.get(i), ModMessages.DIAPER_BAG_SYNC_ID, buffer);
            }
        }
    }
    public DiaperBagEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BagEntity, pos, state);
    }

    //HOLY SHIT THIS IS STARTING TO MAKE SENSE!!!
    //PASS AN NBT COMPOUND TO THESE FUNCTION TO SEND OR RECEIVE DATA FROM IT!

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        heldDiapers = nbt.getInt("DiapersHeld");
        NbtList inv = (NbtList) nbt.get("Inventory");
        if(inv != null){
            for(int i = 0; i < heldDiapers; i++){
                inventory.set(i, ItemStack.fromNbt((NbtCompound) inv.get(i)));
            }
        }else{
            Logger.getGlobal().info("Failed to get inventory :P");
        }
    }


    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("DiapersHeld", heldDiapers);
        NbtList inv = new NbtList();
        //NbtCompound air = new NbtCompound();
        //ItemStack.EMPTY.writeNbt(air);
        for(int i = 0; i < heldDiapers; i++){
            NbtCompound diaper = new NbtCompound();
            inventory.get(i).writeNbt(diaper);
            inv.add(diaper);
        }
        nbt.put("Inventory", inv);
    }
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound comp = new NbtCompound();
        writeNbt(comp);
        return comp;
    }
}
