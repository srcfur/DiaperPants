package com.srcfur.diaperpants.block.custom;

import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.block.entity.DiaperBagEntity;
import com.srcfur.diaperpants.client.blockstates.ModProperties;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.util.DiaperFamily;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class DiaperBag extends BlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<DiaperFamily> FAMILY = ModProperties.DIAPER_FAMILY;

    public DiaperBag(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        ItemStack usedStack = hand == Hand.MAIN_HAND ? player.getMainHandStack() : player.getOffHandStack();
        BlockEntity worldent = world.getBlockEntity(pos);
        if(worldent instanceof DiaperBagEntity){
            DiaperBagEntity _diaperbag = (DiaperBagEntity) worldent;
            if(usedStack.isEmpty()){
                //Try to receive diaper :3
                ItemStack received = _diaperbag.PopNextDiaper();
                if(!received.isEmpty()){
                    player.setStackInHand(hand, received);
                }
            }else{
                if(usedStack.getItem().getClass() == DiaperArmorItem.class){
                    //Try to insert diaper
                    if(_diaperbag.InsertDiaper(usedStack.copy())){
                        usedStack.setCount(0);
                    }
                }
            }

        }

        return ActionResult.CONSUME;
    }


    @javax.annotation.Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx){
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(FAMILY, DiaperFamily.NONE);
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockRenderView renderView, BlockPos pos, Direction side, @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
        BlockState basis = super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);

        return basis;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation){
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror){
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DiaperBagEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FAMILY);
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        DiaperBagEntity entity = (DiaperBagEntity) world.getBlockEntity(pos);
        ItemStack diaperbag = new ItemStack(ModItems.DIAPER_BAG_ITEM, 1);
        NbtCompound comp = new NbtCompound();
        entity.writeNbt(comp);
        diaperbag.getOrCreateNbt().put("BlockEntityTag", comp);
        ItemEntity itemdrop = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), diaperbag);
        world.spawnEntity(itemdrop);
        world.updateComparators(pos, this);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BagEntity, DiaperBagEntity::tick);
    }
}
