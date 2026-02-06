package com.srcfur.diaperpants.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.networking.ModMessages;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import com.srcfur.diaperpants.util.IEntityDiapered;

import java.util.Random;

public class BladderManager implements ServerTickEvents.StartWorldTick {
    public static final int TicksBetweenBladderGainPossibilities = 80;
    public static void syncBladder(ServerPlayerEntity player){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(((IEntityDataSaver)player).getPersistentData().getInt("bladder"));
        buffer.writeInt(((IEntityDataSaver)player).getPersistentData().getInt("continence"));
        ServerPlayNetworking.send(player, ModMessages.BLADDER_SYNC_ID, buffer);
    }

    @Override
    public void onStartTick(ServerWorld world) {
        for (int i = 0; i < world.getPlayers().size(); i++) {
            IEntityDataSaver playerdata = (IEntityDataSaver) world.getPlayers().get(i);
            if(world.getPlayers().get(i).isCreative()) {
                continue;
            }
            IEntityDiapered.setPottySpeedReset(world.getPlayers().get(i), IEntityDiapered.getPottySpeedReset(world.getPlayers().get(i)) - 1);
            if(IEntityDiapered.getPottySpeedReset(world.getPlayers().get(i)) <= 0){
                IEntityDiapered.setPottyGainSpeed(world.getPlayers().get(i), 1); //Resets potty gain speed
            }
            int PottyTimer = playerdata.getPersistentData().getInt("PottyUseDelay");
            PottyTimer--;
            if(IEntityDiapered.getBladderLevel(world.getPlayers().get(i)) > playerdata.getPersistentData().getInt("continence")){
                PottyAccident(world.getPlayers().get(i), world.getRandom());
            }
            syncBladder(world.getPlayers().get(i));
            //playerdata.getPersistentData().putInt("TTNBladderGain", TicksTillNextBladderGainPossibility);
            playerdata.getPersistentData().putInt("PottyUseDelay", PottyTimer);
        }
    }

    public static void PottyAccident(ServerPlayerEntity player, Random rng){
        boolean accidentCaughtByDiaper = false;
        ItemStack leggings =  player.getInventory().getArmorStack(1);
        int currentBladder = ((IEntityDataSaver)player).getPersistentData().getInt("bladder");
        if(!leggings.isEmpty()){
            leggings.damage(currentBladder, rng, null);
            if(IEntityDiapered.checkDiapered(player)){
                DiaperArmorItem daiData = (DiaperArmorItem) leggings.getItem();
                int UsedAmount = leggings.getOrCreateNbt().getInt("Used") + 1;
                leggings.getOrCreateNbt().putInt("Used", UsedAmount);
            }

            accidentCaughtByDiaper = leggings.getDamage() < leggings.getMaxDamage();
        }
        currentBladder = 0;
        /*
        if(!accidentCaughtByDiaper){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,
                    20 * 5, 2, true, false, true), null);
        }
         */
        ((IEntityDataSaver)player).getPersistentData().putInt("continence", Math.max(12, IEntityDiapered.getContinenceLevel(player) - 1));
        ((IEntityDataSaver)player).getPersistentData().putInt("bladder", currentBladder);
        syncBladder(player);
    }
}
