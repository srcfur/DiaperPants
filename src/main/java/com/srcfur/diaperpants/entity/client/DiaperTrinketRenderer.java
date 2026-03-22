package com.srcfur.diaperpants.entity.client;

import com.srcfur.diaperpants.entity.client.armor.DiaperArmorRenderer;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.concurrent.atomic.AtomicBoolean;

public class DiaperTrinketRenderer implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        DiaperArmorRenderer diaperRenderer = (DiaperArmorRenderer) GeoArmorRenderer.getRenderer(DiaperArmorItem.class);
        //Shouldn't cause issues... may...
        BipedEntityModel<LivingEntity> bipedModel = (BipedEntityModel<LivingEntity>) contextModel;
        AtomicBoolean blockRenderingUsingPants = new AtomicBoolean(false);
        entity.getArmorItems().forEach(armoritem ->  {
            if(armoritem.getItem().getClass() == ArmorItem.class || ArmorItem.class.isAssignableFrom(armoritem.getItem().getClass())){
                blockRenderingUsingPants.set(blockRenderingUsingPants.get() || ((ArmorItem)armoritem.getItem()).getSlotType() == EquipmentSlot.LEGS);
            }
        });

        if(!blockRenderingUsingPants.get()){
            diaperRenderer.render(matrices, vertexConsumers, stack, entity,
                    EquipmentSlot.CHEST, light, bipedModel);
            diaperRenderer.render(matrices, vertexConsumers, stack, entity,
                    EquipmentSlot.LEGS, light, bipedModel);
        }
    }
}
