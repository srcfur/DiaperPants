package com.srcfur.diaperpants.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import com.srcfur.diaperpants.server.BladderManager;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ServerWorld.class)
public abstract class BedWetterMixin implements ServerWorldMixin {
    private Random rng = new Random();
    @Inject(method = "wakeSleepingPlayers", at = @At("HEAD"))
    private void wakeup(CallbackInfo info) {
        int i = 0;
        while(i < worldPlayers().size()){
            ServerPlayerEntity spe = worldPlayers().get(i);
            IEntityDataSaver data = (IEntityDataSaver)spe;
            int bedwet = rng.nextInt(0, 10);
            //System.out.println(spe.getDaisplayName().getString() + " has woken up with " + data.getPersistentData().getInt("bladder") + " urine! (Rolled: " + bedwet + ")");
            if(data.getPersistentData().getInt("bladder") > 3){
                if(bedwet < data.getPersistentData().getInt("bladder")){
                    //spe.sendMessage(Text.of("Another wet morning..."), true);
                    BladderManager.PottyAccident(spe, rng);
                }
            }
            i += 1;
        }
    }
}
