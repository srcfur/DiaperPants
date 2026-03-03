package com.srcfur.diaperpants.entity.client.armor;

import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.item.custom.PacifierArmorItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PacifierArmorModel extends AnimatedGeoModel<PacifierArmorItem> {

    @Override
    public Identifier getModelLocation(PacifierArmorItem object) {
        return new Identifier(DiaperPants.MOD_ID, "geo/pacifier.json");
    }

    @Override
    public Identifier getTextureLocation(PacifierArmorItem object) {
        return new Identifier(DiaperPants.MOD_ID, "textures/models/pacifier.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PacifierArmorItem animatable) {
        return new Identifier(DiaperPants.MOD_ID, "animations/pacifier.animation.json");
    }
}