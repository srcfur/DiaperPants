package com.srcfur.diaperpants.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class DiaperAssemblerScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public DiaperAssemblerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4));
    }

    public DiaperAssemblerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory extInventory) {
        super(ModScreenHandlers.DIAPER_ASSEMBLER_SCREEN_HANDLER, syncId);
        checkSize(extInventory, 4);
        this.inventory = extInventory;
        inventory.onOpen(playerInventory.player);

        addSlot(new Slot(extInventory, 0, 44, 41));
        addSlot(new Slot(extInventory, 1, 62, 41));
        addSlot(new Slot(extInventory, 2, 80, 41));
        addSlot(new FurnaceOutputSlot(playerInventory.player, extInventory, 3, 116, 41));


        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot.hasStack()){
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(index < this.inventory.size()){
                if(!insertItem(originalStack, this.inventory.size(), this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)){
                return ItemStack.EMPTY;
            }
            if(originalStack.isEmpty()){
                slot.setStack(ItemStack.EMPTY);
            }else{
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    private void addPlayerInventory(PlayerInventory plInv){
        for(int i = 0; i < 3; i++){
            for(int l = 0; l < 9; l++){
                this.addSlot(new Slot(plInv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(PlayerInventory plinv){
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(plinv, i, 8 + i * 18, 142));
        }
    }
}
