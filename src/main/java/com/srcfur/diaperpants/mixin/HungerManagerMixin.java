package com.srcfur.diaperpants.mixin;

import com.srcfur.diaperpants.util.IEntityDiapered;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Inject(method = "update", at = @At("HEAD"))
    void puppyPantsUpdate(PlayerEntity player, CallbackInfo info){
        if(player.getHungerManager().getExhaustion() > 4.0f && !player.world.isClient){
            IEntityDiapered.setBladderLevel(player, IEntityDiapered.getBladderLevel(player) + 1);
        }
    }
}
