package com.srcfur.diaperpants.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.server.network.ServerPlayerEntity;

public class PullupDiaper extends DiaperArmorItem {
    public PullupDiaper(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public PullupDiaper(ArmorMaterial material, EquipmentSlot slot, Settings settings, String texturename, Integer maxUse) {
        super(material, slot, settings, texturename, maxUse);
    }

    @Override
    public void OnWearerEffect(ServerPlayerEntity player) {
        if(!player.hasStatusEffect(StatusEffects.HASTE)){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1, 0));
        }
        super.OnWearerEffect(player);
    }
}
