package com.srcfur.diaperpants.networking;

import com.srcfur.diaperpants.networking.packet.BladderFirstSyncRequestC2SPacket;
import com.srcfur.diaperpants.networking.packet.BladderSyncS2CPacket;
import com.srcfur.diaperpants.networking.packet.DiaperAssemblerStateS2CPacket;
import com.srcfur.diaperpants.networking.packet.DiaperBagSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ClientMessages {
    public static void registerC2SPackets(){
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.BLADDER_SYNC_ID, BladderSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.DIAPER_BAG_SYNC_ID, DiaperBagSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ModMessages.DIAPER_ASSEMBLER_SYNC_ID, DiaperAssemblerStateS2CPacket::receive);
    }
}
