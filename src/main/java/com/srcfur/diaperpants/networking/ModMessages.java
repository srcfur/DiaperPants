package com.srcfur.diaperpants.networking;

import com.srcfur.diaperpants.DiaperPants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import com.srcfur.diaperpants.networking.packet.BladderFirstSyncRequestC2SPacket;
import com.srcfur.diaperpants.networking.packet.BladderSyncS2CPacket;

public class ModMessages {
    public static final Identifier BLADDER_SYNC_ID = new Identifier(DiaperPants.MOD_ID, "bladder_sync");
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(BLADDER_SYNC_ID, BladderFirstSyncRequestC2SPacket::receive);
    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(BLADDER_SYNC_ID, BladderSyncS2CPacket::receive);
    }
}
