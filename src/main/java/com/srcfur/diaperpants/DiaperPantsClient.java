package com.srcfur.diaperpants;

import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.block.ModBlocks;
import com.srcfur.diaperpants.client.BladderHudOverlay;
import com.srcfur.diaperpants.entity.client.DiaperTrinketRenderer;
import com.srcfur.diaperpants.entity.client.armor.DiaperArmorRenderer;
import com.srcfur.diaperpants.fluids.ModFluids;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.item.custom.PullupDiaper;
import com.srcfur.diaperpants.networking.ClientMessages;
import com.srcfur.diaperpants.networking.ModMessages;
import dev.emi.trinkets.TrinketsClient;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.item.Item;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DiaperPantsClient implements ClientModInitializer {

    void registerDiaperTrinket(Item item){
        GeoArmorRenderer.registerArmorRenderer(new DiaperArmorRenderer(), item);
        TrinketRendererRegistry.registerRenderer(item, new DiaperTrinketRenderer());
    }
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new BladderHudOverlay());

        ClientMessages.registerC2SPackets();

        //Register all our diapers :3
        registerDiaperTrinket(ModItems.CHEAPDIAPER);
        registerDiaperTrinket(ModItems.MEDICALDIAPER);
        registerDiaperTrinket(ModItems.PULLUPDIAPER);
        registerDiaperTrinket(ModItems.MEGADIAPER);
        registerDiaperTrinket(ModItems.SUBSPACEDIAPER);
        registerDiaperTrinket(ModItems.LILTYKESDIAPER);
    }
}
