package com.srcfur.diaperpants.networking.packet;

import com.srcfur.diaperpants.block.entity.DiaperBagEntity;
import com.srcfur.diaperpants.server.BladderManager;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.logging.Logger;

public class DiaperBagSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(client.player == null || client.world == null){
            return;
        }
        int x, y, z = 0;
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        BlockEntity ent = client.world.getBlockEntity(new BlockPos(x,y,z));
        if(ent instanceof DiaperBagEntity){
            DiaperBagEntity diaperbag = (DiaperBagEntity) ent;
            diaperbag.ReadInventoryFromNetwork(buf);
            Logger.getAnonymousLogger().info("Synced diaper bag :3!");
        }
    }
}
