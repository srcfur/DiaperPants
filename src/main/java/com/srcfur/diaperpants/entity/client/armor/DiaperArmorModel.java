package com.srcfur.diaperpants.entity.client.armor;

import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.util.Identifier;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;



public class DiaperArmorModel extends AnimatedGeoModel<DiaperArmorItem> {

    @Override
    public Identifier getModelLocation(DiaperArmorItem object) {
        return new Identifier(DiaperPants.MOD_ID, "geo/diaperarmor.json");
    }

    @Override
    public Identifier getTextureLocation(DiaperArmorItem object) {
        return null;
    }

    @Override
    public Identifier getAnimationFileLocation(DiaperArmorItem animatable) {
        return new Identifier(DiaperPants.MOD_ID, "animations/armor_animation.json");
    }
}
