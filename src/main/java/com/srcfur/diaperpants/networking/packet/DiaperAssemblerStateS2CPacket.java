package com.srcfur.diaperpants.networking.packet;

import com.srcfur.diaperpants.block.entity.DiaperAssemblerBlockEntity;
import com.srcfur.diaperpants.block.entity.DiaperBagEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.logging.Logger;

public class DiaperAssemblerStateS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(client.player == null || client.world == null){
            return;
        }
        BlockEntity ent = client.world.getBlockEntity(buf.readBlockPos());
        if(ent instanceof DiaperAssemblerBlockEntity){
            DiaperAssemblerBlockEntity assembler = (DiaperAssemblerBlockEntity) ent;
            assembler.setProgressingAnimationState(buf.readBoolean());
        }
    }
}
