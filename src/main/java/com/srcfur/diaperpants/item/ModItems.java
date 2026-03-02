package com.srcfur.diaperpants.item;

import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.block.ModBlocks;
import com.srcfur.diaperpants.item.custom.DiaperBagBlockItem;
import com.srcfur.diaperpants.util.DiaperFamily;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;

import java.util.List;

public class ModItems {

    //Diapers
    //Low tier
    public static final Item CHEAPDIAPER = registerDiaper("cheap", 6, DiaperFamily.GROCERY);
    //Mid tier
    public static final Item MEDICALDIAPER = registerDiaper("medical", 16, DiaperFamily.MEDICAL);
    public static final Item PULLUPDIAPER = registerDiaper("pullup", 16, DiaperFamily.PULLUPS);
    //High tier
    public static final Item MEGADIAPER = registerDiaper("mega", 30, DiaperFamily.MEGACAPACITY);
    public static final Item SUBSPACEDIAPER = registerDiaper("subspace", 30, DiaperFamily.PHIGHTING);
    public static final Item BUNNYHOPPSDIAPER = registerDiaper("bunnyhopps", 30, DiaperFamily.ABU0);

    public static final Item DIAPERTRASH = registerItem("balled_diaper", new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16)));
    public static final Item WOODPULP = registerItem("woodpulp", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CHEAPDIAPERSAP = registerItem("cheapdiapersap", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item DIAPERSAP = registerItem("diapersap", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item DiaperCore = registerItem("diapercore", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LightDiaperCore = registerItem("cheapdiapercore", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TRIPLEDIAPERCORE = registerItem("threediapercore", new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final FoodComponent QUESADILLACOMPONENT = new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).alwaysEdible().build();
    public static final Item QUESADILLA = registerItem("quesadilla", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(QUESADILLACOMPONENT)));

    public static final Item DIAPER_BAG_ITEM = registerItem("diaper_bag", new DiaperBagBlockItem(ModBlocks.DIAPER_BAG,
            new FabricItemSettings().group(ItemGroup.MISC).maxCount(1)));

    public static List<Item> alldiapers = List.of();

    private static Item registerDiaper(String diapername, int health){
        return registerDiaper(diapername, health, DiaperFamily.NONE);
    }
    private static Item registerDiaper(String diapername, int health, DiaperFamily family){
        Item diaper = registerItem(diapername + "diaper",
                new DiaperArmorItem(ModArmorMaterials.Diaper, EquipmentSlot.MAINHAND,
                        new FabricItemSettings().group(ItemGroup.MISC).maxDamage(health * 10), diapername, 5));
        ((DiaperArmorItem)diaper).family = family;
        return diaper;
    }
    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(DiaperPants.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DiaperPants.LOGGER.info("Registering mod items for " + DiaperPants.MOD_ID + " :3");
    }
}
