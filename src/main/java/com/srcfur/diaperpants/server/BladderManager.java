package com.srcfur.diaperpants.server;

import com.srcfur.diaperpants.sounds.ModSounds;
import com.srcfur.diaperpants.statistics.ModStatistics;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.Random;

public class BladderManager implements ServerTickEvents.StartWorldTick {
    public static final int TicksBetweenBladderGainPossibilities = 80;
    public static void syncBladder(ServerPlayerEntity player){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(IEntityDiapered.getBladderLevel(player));
        buffer.writeInt(IEntityDiapered.getContinenceLevel(player));
        buffer.writeInt(IEntityDiapered.getBowelLevel(player));
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
            if(IEntityDiapered.getBowelLevel(world.getPlayers().get(i)) > playerdata.getPersistentData().getInt("continence") * IEntityDiapered.CONTINENCE_BOWEL_MULTIPLIER){
                PottyAccident(world.getPlayers().get(i), world.getRandom(), true);
            }else{
                if(IEntityDiapered.getBladderLevel(world.getPlayers().get(i)) > playerdata.getPersistentData().getInt("continence")){
                    PottyAccident(world.getPlayers().get(i), world.getRandom(), false);
                }
            }
            syncBladder(world.getPlayers().get(i));
            //playerdata.getPersistentData().putInt("TTNBladderGain", TicksTillNextBladderGainPossibility);
            playerdata.getPersistentData().putInt("PottyUseDelay", PottyTimer);
        }
    }

    public static void PottyAccident(ServerPlayerEntity player, Random rng, boolean bowels){
        ItemStack leggings =  player.getInventory().getArmorStack(1);
        int damage = IEntityDiapered.getBladderLevel(player);
        if(bowels){
            damage += IEntityDiapered.getBowelLevel(player);
        }

        Optional<ItemStack> diaper = IEntityDiapered.getDiaperFromPlayer(player);
        if(diaper.isPresent()){
            diaper.get().damage(damage, rng, null);
            if(bowels){
                ((DiaperArmorItem)diaper.get().getItem()).poopInDiaper(diaper.get());
            }
        }else{
            if(!leggings.isEmpty()){
                leggings.damage(damage, rng, null);
            }
        }
        IEntityDiapered.setContinenceLevel(player, Math.max(12, IEntityDiapered.getContinenceLevel(player) - 1));
        IEntityDiapered.setBladderLevel(player, 0);
        if(bowels){
            IEntityDiapered.setBowelLevel(player, 0);
            player.incrementStat(ModStatistics.POOPED_PANTS_STAT);
            player.world.playSoundFromEntity(null, player, ModSounds.soiling_sound, SoundCategory.PLAYERS, 1, 1);
        }else{
            player.incrementStat(ModStatistics.BLADDER_FAILIURE_STAT);
        }
        syncBladder(player);
    }
}
