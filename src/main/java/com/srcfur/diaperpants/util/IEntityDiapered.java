package com.srcfur.diaperpants.util;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import net.minecraft.util.Pair;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class IEntityDiapered {
    public static final String BLADDER_KEY = "bladder";
    public static final String CONTINENCE_KEY = "continence";
    public static final String POTTYTRAIN_KEY = "pottytraining";
    public static final String POTTYSPEEDRESET_KEY = "pottyreset";
    public static final String POTTYSPEED_KEY = "pottyspeed";
    public static final String BOWEL_KEY = "bowels";

    public static final int DEFAULT_CONTINENCE = 40;
    public static final int CONTINENCE_BOWEL_MULTIPLIER = 2;
    public static final int CONTINENCE_TO_BLADDER_LEVEL = 4;

    public static Optional<ItemStack> getDiaperFromPlayer(PlayerEntity ent){
        Optional<TrinketComponent> tcomp = TrinketsApi.getTrinketComponent((LivingEntity) ent);
        if(tcomp.isPresent()) {
            List<Pair<SlotReference, ItemStack>> items = tcomp.get().getAllEquipped();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getRight().getItem().getClass() == DiaperArmorItem.class) {
                    return Optional.of(items.get(i).getRight());
                }
            }
        }
        return Optional.empty();
    }

    public static boolean checkDiapered(PlayerEntity ent){
        Optional<ItemStack> diaper = getDiaperFromPlayer(ent);
        return diaper.isPresent();
    }

    private static int getOrDefault(PlayerEntity ent, String key, int def){
        IEntityDataSaver data = (IEntityDataSaver) ent;
        if(!data.getPersistentData().contains(key)){
            data.getPersistentData().putInt(key, def);
        }
        return data.getPersistentData().getInt(key);
    }

    public static boolean isItemADiaper(Item item){
        return item.getClass() == DiaperArmorItem.class || DiaperArmorItem.class.isAssignableFrom(item.getClass());
    }

    public static int getBladderLevel(PlayerEntity ent){
        return getOrDefault(ent, BLADDER_KEY, 0);
    }
    public static void setBladderLevel(PlayerEntity ent, int amount){
        ((IEntityDataSaver)ent).getPersistentData().putInt(BLADDER_KEY, Math.min(amount, getContinenceLevel(ent) + 1));
    }

    public static int getContinenceLevel(PlayerEntity ent){
        IEntityDataSaver data = (IEntityDataSaver) ent;
        if(!data.getPersistentData().contains(CONTINENCE_KEY)){
            data.getPersistentData().putInt(CONTINENCE_KEY, DEFAULT_CONTINENCE);
        }
        return data.getPersistentData().getInt(CONTINENCE_KEY);
    }
    public static void setContinenceLevel(PlayerEntity ent, int amount){
        ((IEntityDataSaver)ent).getPersistentData().putInt(CONTINENCE_KEY, amount);
    }

    public static int getPottySpeedReset(PlayerEntity ent){
        IEntityDataSaver data = (IEntityDataSaver) ent;
        if(!data.getPersistentData().contains(POTTYSPEEDRESET_KEY)){
            data.getPersistentData().putInt(POTTYSPEEDRESET_KEY, 0);
        }
        return data.getPersistentData().getInt(POTTYSPEEDRESET_KEY);
    }
    public static void setPottySpeedReset(PlayerEntity ent, int amount){
        ((IEntityDataSaver)ent).getPersistentData().putInt(POTTYSPEEDRESET_KEY, amount);
    }

    public static float getPottyGainSpeed(PlayerEntity ent){
        IEntityDataSaver data = (IEntityDataSaver) ent;
        if(!data.getPersistentData().contains(POTTYSPEED_KEY)){
            data.getPersistentData().putFloat(POTTYSPEED_KEY, 1);
        }
        return data.getPersistentData().getFloat(POTTYSPEED_KEY);
    }
    public static void setPottyGainSpeed(PlayerEntity ent, float amount){
        ((IEntityDataSaver)ent).getPersistentData().putFloat(POTTYSPEED_KEY, amount);
    }

    public static int getPottyTraining(PlayerEntity ent){
        IEntityDataSaver data = (IEntityDataSaver) ent;
        if(!data.getPersistentData().contains(POTTYTRAIN_KEY)){
            data.getPersistentData().putInt(POTTYTRAIN_KEY, 0);
        }
        return data.getPersistentData().getInt(POTTYTRAIN_KEY);
    }
    public static void setPottyTraining(PlayerEntity ent, int amount){
        ((IEntityDataSaver)ent).getPersistentData().putInt(POTTYTRAIN_KEY, amount);
    }

    public static int getBowelLevel(PlayerEntity ent){
        return getOrDefault(ent, BOWEL_KEY, 0);
    }
    public static void setBowelLevel(PlayerEntity ent, int amount){
        ((IEntityDataSaver)ent).getPersistentData().putInt(BOWEL_KEY, amount);
    }
}
