package com.srcfur.diaperpants;

import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.block.ModBlocks;
import com.srcfur.diaperpants.client.BladderHudOverlay;
import com.srcfur.diaperpants.entity.client.DiaperTrinketRenderer;
import com.srcfur.diaperpants.entity.client.armor.DiaperArmorRenderer;
import com.srcfur.diaperpants.fluids.ModFluids;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.item.custom.PullupDiaper;
import com.srcfur.diaperpants.networking.ModMessages;
import dev.emi.trinkets.TrinketsClient;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DiaperPantsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new BladderHudOverlay());

        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), ModItems.CHEAPDIAPER);
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), ModItems.MEDICALDIAPER);
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), ModItems.PULLUPDIAPER);
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), ModItems.MEGADIAPER);
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), ModItems.SUBSPACEDIAPER);
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), ModItems.LILTYKESDIAPER);

        TrinketRendererRegistry.registerRenderer(ModItems.CHEAPDIAPER, new DiaperTrinketRenderer());
    }
}
