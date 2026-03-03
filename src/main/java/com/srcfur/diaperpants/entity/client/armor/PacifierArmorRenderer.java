package com.srcfur.diaperpants.entity.client.armor;

import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.item.custom.PacifierArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PacifierArmorRenderer extends GeoArmorRenderer<PacifierArmorItem> {

    public PacifierArmorRenderer() {
        super(new PacifierArmorModel());
        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.leftArmBone = "armorLeftArm";
        this.leftBootBone = "armorLeftBoot";
        this.rightBootBone = "armorRightBoot";
    }
    @Override
    public GeoArmorRenderer<PacifierArmorItem> applySlot(EquipmentSlot slot) {
        super.applySlot(slot);
        this.setBoneVisibility(this.headBone, true);
        return this;
    }
}
