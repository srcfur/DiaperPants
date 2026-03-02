package com.srcfur.diaperpants.block.entity;

import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.block.ModBlocks;
import com.srcfur.diaperpants.item.inventory.ImplementedInventory;
import com.srcfur.diaperpants.networking.ModMessages;
import com.srcfur.diaperpants.recipes.DiaperAssemblerRecipe;
import com.srcfur.diaperpants.screen.DiaperAssemblerScreen;
import com.srcfur.diaperpants.screen.DiaperAssemblerScreenHandler;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DiaperAssemblerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory, IAnimatable {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(4, ItemStack.EMPTY);
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxprogress = 30;
    private boolean isProgressingAnimationState = false;
    public enum DiaperAssemblyAnimationState {
        idle,
        pressing
    }
    private DiaperAssemblyAnimationState animation_state = DiaperAssemblyAnimationState.idle;

    public DiaperAssemblerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DiaperAssembler, pos, state);
        propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch(index){
                    case 0: return progress;
                    case 1: return maxprogress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch(index){
                    case 0: progress = value; break;
                    case 1: maxprogress = value; break;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public void setProgressingAnimationState(boolean value){
        isProgressingAnimationState = value;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public DefaultedList<ItemStack> getInventory() {
        DefaultedList<ItemStack> outputinventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        outputinventory.set(0, inventory.get(3));
        return outputinventory;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return slot == 3 && stack != ItemStack.EMPTY;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("diaperpants.diaperassembler.storagename");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DiaperAssemblerScreenHandler(syncId, inv, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("diaper_progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("diaper_progress");
    }

    public static void tick(World world, BlockPos pos, BlockState state, DiaperAssemblerBlockEntity entity) {
        if(!world.isClient){
            if(buildProgress(entity)) {
                entity.progress++;
                if(entity.progress >= entity.maxprogress){
                    craftItem(entity);
                }
            }
            boolean calcShouldProg = entity.progress > 0;
            if(calcShouldProg != entity.isProgressingAnimationState){
                entity.setProgressingAnimationState(calcShouldProg);
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeBlockPos(pos);
                buffer.writeBoolean(calcShouldProg);
                for(PlayerEntity plr : world.getPlayers()){
                    ServerPlayNetworking.send((ServerPlayerEntity) plr, ModMessages.DIAPER_ASSEMBLER_SYNC_ID, buffer);
                }
            }
        }
    }

    private static void craftItem(DiaperAssemblerBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for(int i = 0; i < inventory.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }
        Optional<DiaperAssemblerRecipe> match = world.getRecipeManager()
                .getFirstMatch(DiaperAssemblerRecipe.Type.INSTANCE, inventory, world);
        if(match.isPresent()){
            entity.removeStack(0, 1);
            entity.removeStack(1, 1);
            entity.removeStack(2, 1);
            entity.setStack(3, new ItemStack(match.get().getOutput().getItem(), 1));
            entity.progress = 0;
        }
    }

    private static boolean hasRecipe(DiaperAssemblerBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for(int i = 0; i < inventory.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }
        Optional<DiaperAssemblerRecipe> match = world.getRecipeManager()
                .getFirstMatch(DiaperAssemblerRecipe.Type.INSTANCE, inventory, world);
        return match.isPresent() && inventory.getStack(3).isEmpty();
    }

    private static boolean hasNotReachedStackLimit(DiaperAssemblerBlockEntity entity) {
        return entity.getStack(3).isEmpty();
    }

    private static boolean buildProgress(DiaperAssemblerBlockEntity entity){
        return hasRecipe(entity) && hasNotReachedStackLimit(entity);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event){
        if(animation_state == DiaperAssemblyAnimationState.idle && isProgressingAnimationState){
            event.getController().setAnimation(new AnimationBuilder().playAndHold("pressdiaper"));
            animation_state = DiaperAssemblyAnimationState.pressing;
        }
        if(animation_state != DiaperAssemblyAnimationState.idle && !isProgressingAnimationState){
            event.getController().setAnimation(new AnimationBuilder().playAndHold("idle"));
            animation_state = DiaperAssemblyAnimationState.idle;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<DiaperAssemblerBlockEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
