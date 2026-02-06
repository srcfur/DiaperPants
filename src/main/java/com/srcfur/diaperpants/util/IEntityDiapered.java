package com.srcfur.diaperpants.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;

public class IEntityDiapered {
    public static final String BLADDER_KEY = "bladder";
    public static final String CONTINENCE_KEY = "continence";
    public static final String POTTYTRAIN_KEY = "pottytraining";
    public static final String POTTYSPEEDRESET_KEY = "pottyreset";
    public static final String POTTYSPEED_KEY = "pottyspeed";

    public static final int DEFAULT_CONTINENCE = 40;

    public static boolean checkDiapered(PlayerEntity ent){
        ItemStack leggings = ent.getInventory().getArmorStack(1);
        if(leggings.isEmpty()){
            return false;
        }
        return leggings.getItem().getClass() == DiaperArmorItem.class || DiaperArmorItem.class.isAssignableFrom(leggings.getItem().getClass());
    }

    public static int getBladderLevel(PlayerEntity ent){
        IEntityDataSaver data = (IEntityDataSaver) ent;
        if(!data.getPersistentData().contains(BLADDER_KEY)){
            data.getPersistentData().putInt(BLADDER_KEY, 0);
        }
        return data.getPersistentData().getInt(BLADDER_KEY);
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
}
