package com.srcfur.diaperpants.mixin;

import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.util.IEntityDiapered;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @Inject(method = "update", at = @At("HEAD"))
    void puppyPantsUpdate(PlayerEntity player, CallbackInfo info){
        if(player.getHungerManager().getExhaustion() > 4.0f && !player.world.isClient){
            IEntityDiapered.setBladderLevel(player, IEntityDiapered.getBladderLevel(player) + 1);
            if(player.getHungerManager().getSaturationLevel() <= 0){
                IEntityDiapered.setBowelLevel(player, IEntityDiapered.getBowelLevel(player) + 1);
            }
        }
    }
}
