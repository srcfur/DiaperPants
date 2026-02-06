package com.srcfur.diaperpants.effects;

import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class ModEffects {
    public static StatusEffect FULL_DIAPER_EFFECT;

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(DiaperPants.MOD_ID, id), entry);
    }

    public static void registerEffects(){
        FULL_DIAPER_EFFECT = register("full", new FullEffect(StatusEffectCategory.HARMFUL, 0x339900).
                addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID.randomUUID().toString(), -0.35f, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }
}
