package com.srcfur.diaperpants.fluids;

import com.srcfur.diaperpants.DiaperPants;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.srcfur.diaperpants.item.custom.UrineBottle;

public class ModFluids {
    public static FlowableFluid STILL_URINE;
    public static FlowableFluid FLOWING_URINE;
    public static Item URINE_BUCKET;
    public static Item URINE_BOTTLE;
    public static Block URINE_BLOCK;

    public static void registerFluids() {
        STILL_URINE = Registry.register(Registry.FLUID, new Identifier(DiaperPants.MOD_ID, "urine"), new UrineFluid.Still());
        FLOWING_URINE = Registry.register(Registry.FLUID, new Identifier(DiaperPants.MOD_ID, "flowing_urine"), new UrineFluid.Flowing());
        URINE_BUCKET = Registry.register(Registry.ITEM, new Identifier(DiaperPants.MOD_ID, "urine_bucket"),
                new BucketItem(STILL_URINE, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        URINE_BOTTLE = Registry.register(Registry.ITEM, new Identifier(DiaperPants.MOD_ID, "urine_bottle"),
                new UrineBottle(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(1)));
        URINE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(DiaperPants.MOD_ID, "urine"), new FluidBlock(STILL_URINE, FabricBlockSettings.copy(Blocks.WATER)));

    }
}
