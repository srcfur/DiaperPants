package com.srcfur.diaperpants.entity.client.armor;

import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DiaperArmorRenderer extends GeoArmorRenderer<DiaperArmorItem> {

    public DiaperArmorRenderer() {
        super(new DiaperArmorModel());
        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.leftArmBone = "armorLeftArm";
        this.leftBootBone = "armorLeftBoot";
        this.rightBootBone = "armorRightBoot";
    }
    public Identifier GetUseTextures(DiaperArmorItem instance, Integer useamount){
        return new Identifier(DiaperPants.MOD_ID, "textures/models/armor/diapers/" + instance.DiaperTexture + useamount.toString() + ".png");
    }
    @Override
    public GeoArmorRenderer<DiaperArmorItem> applySlot(EquipmentSlot slot) {
        super.applySlot(slot);
        this.setBoneVisibility(this.bodyBone, true);
        return this;
    }
    @Override
    public Identifier getTextureLocation(DiaperArmorItem instance) {
        int UseCount = 0;
        float health = (float)itemStack.getDamage() / itemStack.getMaxDamage();
        health = health;
        UseCount = (int)Math.floor(instance.MaxUses * health);
        UseCount = Math.min(UseCount, instance.MaxUses);

        //Sneakily do messing stuff here!
        //Start at 1 cause soiling0 is the default state
        for(int i = 1; i < 4; i++){
            this.setBoneVisibility("soiling" + i, instance.getPooped(itemStack) == i);
        }

        return GetUseTextures(instance, UseCount);
    }

}
