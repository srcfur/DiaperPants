package com.srcfur.diaperpants.entity.client;

import com.srcfur.diaperpants.entity.client.armor.PacifierArmorRenderer;
import com.srcfur.diaperpants.item.custom.PacifierArmorItem;
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

public class PacifierTrinketRenderer implements TrinketRenderer {
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        PacifierArmorRenderer diaperRenderer = (PacifierArmorRenderer) GeoArmorRenderer.getRenderer(PacifierArmorItem.class);
        //Shouldn't cause issues... may...
        BipedEntityModel<LivingEntity> bipedModel = (BipedEntityModel<LivingEntity>) contextModel;
        diaperRenderer.render(matrices, vertexConsumers, stack, entity,
                EquipmentSlot.HEAD, light, bipedModel);
    }
}
