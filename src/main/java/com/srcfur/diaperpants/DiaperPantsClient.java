package com.srcfur.diaperpants;

import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.block.ModBlocks;
import com.srcfur.diaperpants.block.custom.DiaperBag;
import com.srcfur.diaperpants.block.entity.DiaperBagEntity;
import com.srcfur.diaperpants.client.BladderHudOverlay;
import com.srcfur.diaperpants.client.renderers.DiaperAssemblerRenderer;
import com.srcfur.diaperpants.client.renderers.DiaperBagRenderer;
import com.srcfur.diaperpants.entity.client.DiaperTrinketRenderer;
import com.srcfur.diaperpants.entity.client.PacifierTrinketRenderer;
import com.srcfur.diaperpants.entity.client.armor.DiaperArmorRenderer;
import com.srcfur.diaperpants.entity.client.armor.PacifierArmorRenderer;
import com.srcfur.diaperpants.fluids.ModFluids;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.networking.ClientMessages;
import com.srcfur.diaperpants.networking.ModMessages;
import com.srcfur.diaperpants.screen.DiaperAssemblerScreen;
import com.srcfur.diaperpants.screen.ModScreenHandlers;
import dev.emi.trinkets.TrinketsClient;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.fabricmc.fabric.mixin.lookup.BlockEntityTypeAccessor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import software.bernie.example.client.renderer.tile.HabitatTileRenderer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class DiaperPantsClient implements ClientModInitializer {

    void registerDiaperTrinket(Item item){
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), item);
        TrinketRendererRegistry.registerRenderer(item, new DiaperTrinketRenderer());
    }
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new BladderHudOverlay());

        ClientMessages.registerC2SPackets();

        //Register custom block renderers
        BlockEntityRendererRegistry.register(ModBlockEntities.BagEntity, DiaperBagRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.DiaperAssembler, DiaperAssemblerRenderer::new);

        //Register all our diapers :3
        registerDiaperTrinket(ModItems.CHEAPDIAPER);
        registerDiaperTrinket(ModItems.MEDICALDIAPER);
        registerDiaperTrinket(ModItems.PULLUPDIAPER);
        registerDiaperTrinket(ModItems.MEGADIAPER);
        registerDiaperTrinket(ModItems.SUBSPACEDIAPER);
        registerDiaperTrinket(ModItems.BUNNYHOPPSDIAPER);

        GeoArmorRenderer.registerArmorRenderer(new PacifierArmorRenderer(), ModItems.PACIFIER);
        TrinketRendererRegistry.registerRenderer(ModItems.PACIFIER, new PacifierTrinketRenderer());

        HandledScreens.register(ModScreenHandlers.DIAPER_ASSEMBLER_SCREEN_HANDLER, DiaperAssemblerScreen::new);
    }
}
