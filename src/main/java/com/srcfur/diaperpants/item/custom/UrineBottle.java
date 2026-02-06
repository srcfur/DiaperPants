package com.srcfur.diaperpants.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import com.srcfur.diaperpants.util.IEntityDiapered;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UrineBottle extends PotionItem {
    public UrineBottle(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        return new ItemStack(this);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        IEntityDataSaver ds = (IEntityDataSaver) user;
        if(ds.getPersistentData().getInt("PottyUseDelay") > 0){
            return TypedActionResult.fail(user.getInventory().getMainHandStack());
        }else {
            return super.use(world, user, hand);
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity;
        PlayerEntity playerEntity2 = playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }
        if (playerEntity != null) {
            if (!world.isClient) {
                IEntityDiapered.setBladderLevel(playerEntity, IEntityDiapered.getBladderLevel(playerEntity) + 3);
            }
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            ((IEntityDataSaver) playerEntity).getPersistentData().putInt("PottyUseDelay", 10);
            playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
        }

        world.emitGameEvent((Entity)user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
        return stack;
    }
}
