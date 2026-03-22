package com.srcfur.diaperpants.mixin;

import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.statistics.ModStatistics;
import com.srcfur.diaperpants.util.IEntityDiapered;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import com.srcfur.diaperpants.server.BladderManager;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
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
            int bedwet = rng.nextInt(0, IEntityDiapered.getContinenceLevel(spe));
            if(IEntityDiapered.getBladderLevel(spe) > IEntityDiapered.getContinenceLevel(spe) * 0.3){
                if(bedwet < IEntityDiapered.getBladderLevel(spe)){
                    Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(spe);
                    boolean stopAccident = false;
                    if(trinketComponent.isPresent()){
                        stopAccident = trinketComponent.get().isEquipped(ModItems.PACIFIER);
                    }
                    if(!stopAccident){
                        BladderManager.PottyAccident(spe, rng, false);
                        spe.incrementStat(ModStatistics.BEDWETTING_STAT);
                    }
                }
            }
            i += 1;
        }
    }
}
