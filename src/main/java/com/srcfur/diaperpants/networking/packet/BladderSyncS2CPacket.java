package com.srcfur.diaperpants.networking.packet;

import com.srcfur.diaperpants.util.IEntityDiapered;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import com.srcfur.diaperpants.util.IEntityDataSaver;

public class BladderSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(client.player == null){
            return;
        }
        IEntityDiapered.setBladderLevel(client.player, buf.readInt());
        IEntityDiapered.setContinenceLevel(client.player, buf.readInt());
    }
}
