package com.srcfur.diaperpants.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import com.srcfur.diaperpants.server.BladderManager;

public class BladderFirstSyncRequestC2SPacket {
    public static void receive(MinecraftServer minecraftServer, ServerPlayerEntity serverPlayerEntity, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        if(serverPlayerEntity == null){
            return;
        }
        BladderManager.syncBladder(serverPlayerEntity);
    }
}
