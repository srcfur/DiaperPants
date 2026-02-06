package com.srcfur.diaperpants.block.entity;

import com.srcfur.diaperpants.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;


public class PottyEntity extends BlockEntity {
    public PottyEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PottyStation, pos, state);
    }
}
