package com.srcfur.diaperpants.networking.packet;

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
        ((IEntityDataSaver)client.player).getPersistentData().putInt("bladder", buf.readInt());
        ((IEntityDataSaver)client.player).getPersistentData().putInt("continence", buf.readInt());
    }
}
