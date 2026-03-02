package com.srcfur.diaperpants.networking;

import com.srcfur.diaperpants.DiaperPants;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import com.srcfur.diaperpants.networking.packet.BladderFirstSyncRequestC2SPacket;
import com.srcfur.diaperpants.networking.packet.BladderSyncS2CPacket;

public class ModMessages {
    public static final Identifier BLADDER_SYNC_ID = new Identifier(DiaperPants.MOD_ID, "bladder_sync");
    public static final Identifier DIAPER_BAG_SYNC_ID = new Identifier(DiaperPants.MOD_ID, "diaper_bag_sync");
    public static final Identifier DIAPER_ASSEMBLER_SYNC_ID = new Identifier(DiaperPants.MOD_ID, "diaper_assembler_sync");
    public static void registerS2CPackets(){
        ServerPlayNetworking.registerGlobalReceiver(ModMessages.BLADDER_SYNC_ID, BladderFirstSyncRequestC2SPacket::receive);
    }
}
