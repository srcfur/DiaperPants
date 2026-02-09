package com.srcfur.diaperpants.block.custom;

import com.srcfur.diaperpants.block.entity.DiaperBagEntity;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DiaperBag extends BlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

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
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
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
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }
}
