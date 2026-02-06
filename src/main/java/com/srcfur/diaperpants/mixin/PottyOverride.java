package com.srcfur.diaperpants.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import com.srcfur.diaperpants.util.IEntityDiapered;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class PottyOverride implements IFoodMixin {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void update(PlayerEntity player, CallbackInfo info) {
        Difficulty difficulty = player.world.getDifficulty();
        if (this.getdpexhaustion() > 4.0f) {
            if (this.getdpsaturationLevel() == 0.0f) {
                if (difficulty != Difficulty.PEACEFUL) {
                    if (!player.world.isClient()){
                        IEntityDiapered.setBladderLevel(player, IEntityDiapered.getBladderLevel(player) + 1);
                    }
                }
            }
        }
    }
}
