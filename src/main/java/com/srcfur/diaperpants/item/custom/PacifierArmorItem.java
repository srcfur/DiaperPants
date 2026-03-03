package com.srcfur.diaperpants.item.custom;

import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacifierArmorItem extends ArmorItem implements IAnimatable, Trinket {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public PacifierArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        TrinketsApi.registerTrinket(this, this);
    }

    // Predicate runs every frame
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        // This is all the extradata this event carries. The livingentity is the entity
        // that's wearing the armor. The itemstack and equipmentslottype are
        // explanatory.
        LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);

        // Always loop the animation
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));

        // If the living entity is an armorstand just play the animation nonstop
        if (livingEntity instanceof ArmorStandEntity) {
            return PlayState.CONTINUE;
        }

        // Make sure the player is wearing all the armor. If they are, continue playing
        // the animation, otherwise stop
        Optional<TrinketComponent> comp = TrinketsApi.getTrinketComponent(livingEntity);
        if(comp.isPresent()){
            boolean isWearingAll = comp.get().isEquipped(this);
            return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
