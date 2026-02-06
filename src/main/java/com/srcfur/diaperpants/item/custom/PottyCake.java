package com.srcfur.diaperpants.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import com.srcfur.diaperpants.util.IEntityDiapered;

public class PottyCake extends Item {

    public PottyCake(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.eatFood(world, stack);
        if(user.isPlayer()){
            IEntityDiapered.setPottyGainSpeed((PlayerEntity) user, 3.5f);
            IEntityDiapered.setPottySpeedReset((PlayerEntity) user, 1200);
        }
        return stack;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_CAT_EAT;
    }
}
