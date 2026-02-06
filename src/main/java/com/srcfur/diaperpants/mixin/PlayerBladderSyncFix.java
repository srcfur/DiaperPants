package com.srcfur.diaperpants.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import com.srcfur.diaperpants.server.BladderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class PlayerBladderSyncFix implements ServerWorldMixin {
    @Inject(method = "onPlayerConnected", at = @At("TAIL"))
    private void playeradded(ServerPlayerEntity player, CallbackInfo ci){
        BladderManager.syncBladder(player);
    }
}
