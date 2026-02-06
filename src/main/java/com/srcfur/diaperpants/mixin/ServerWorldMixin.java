package com.srcfur.diaperpants.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(ServerWorld.class)
public interface ServerWorldMixin {
    @Invoker("getPlayers")
    public List<ServerPlayerEntity> worldPlayers();

}
