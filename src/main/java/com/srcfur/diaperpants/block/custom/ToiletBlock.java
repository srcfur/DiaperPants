package com.srcfur.diaperpants.block.custom;

import com.srcfur.diaperpants.block.entity.PottyEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.srcfur.diaperpants.fluids.ModFluids;
import com.srcfur.diaperpants.server.BladderManager;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import com.srcfur.diaperpants.util.IEntityDiapered;

import javax.annotation.Nullable;

public class ToiletBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public ToiletBlock(Settings settings) {
        super(settings);
    }

    @Nullable
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state){
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient){
            return ActionResult.PASS;
        }
        //Come back to this at some point idk...
        if (player.getInventory().getMainHandStack().getItem() == Items.BUCKET && false){
            player.getInventory().getMainHandStack().setCount(0);
            ItemStack urinebucket = new ItemStack(ModFluids.URINE_BUCKET);
            urinebucket.setCount(1);
            player.getInventory().setStack(player.getInventory().selectedSlot, urinebucket);
            return ActionResult.success(true);
        }else{
            IEntityDataSaver playerdata = (IEntityDataSaver)player;
            if(playerdata.getPersistentData().getInt("PottyUseDelay") > 0){
                return ActionResult.PASS;
            }
            if(playerdata.getPersistentData().getInt("bladder") > 3){
                if(!IEntityDiapered.checkDiapered(player)){
                    int newBladder = Math.max(playerdata.getPersistentData().getInt("bladder") - 4, 0);
                    playerdata.getPersistentData().putInt("bladder", newBladder);
                    BladderManager.syncBladder((ServerPlayerEntity)player);
                    playerdata.getPersistentData().putInt("PottyUseDelay", 10);
                    IEntityDiapered.setBowelLevel(player, 0);
                    int pottytraining = IEntityDiapered.getPottyTraining(player) + 1;
                    if(pottytraining >= 10){
                        pottytraining = 0;
                        IEntityDiapered.setContinenceLevel(player, Math.min(IEntityDiapered.DEFAULT_CONTINENCE, IEntityDiapered.getContinenceLevel(player) + 1));
                    }
                    IEntityDiapered.setPottyTraining(player, pottytraining);
                    return ActionResult.success(false);
                }
            }
        }
        return ActionResult.FAIL;
    }
    @Override
    public @org.jetbrains.annotations.Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PottyEntity(pos, state);
    }
}
