package com.srcfur.diaperpants.item;

import com.srcfur.diaperpants.DiaperPants;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import com.srcfur.diaperpants.item.custom.PullupDiaper;
import com.srcfur.diaperpants.item.custom.SubspaceDiaper;
import com.srcfur.diaperpants.item.custom.TykableDiaper;

import java.util.List;

public class ModItems {
    //Diapers
    public static final Item CHEAPDIAPER = registerDiaper("cheap", 1);

    public static final Item MEDICALDIAPER = registerDiaper("medical", 3);
    public static final Item PULLUPDIAPER = registerItem("pullupdiaper", new PullupDiaper(ModArmorMaterials.Diaper, EquipmentSlot.LEGS,
            new FabricItemSettings().group(ItemGroup.MISC).maxDamage(20), "pullup", 5));
    public static final Item MEGADIAPER = registerDiaper("mega", 5);
    public static final Item SUBSPACEDIAPER = registerItem("subspacediaper", new SubspaceDiaper(ModArmorMaterials.Diaper, EquipmentSlot.LEGS,
            new FabricItemSettings().group(ItemGroup.MISC).maxDamage(50), "subspace", 5));
    public static final Item LILTYKESDIAPER = registerItem("liltykediaper", new TykableDiaper(ModArmorMaterials.Diaper, EquipmentSlot.LEGS,
            new FabricItemSettings().group(ItemGroup.MISC).maxDamage(80), "liltyke", 5));


    public static final Item DIAPERTRASH = registerItem("balled_diaper", new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16)));
    public static final Item WOODPULP = registerItem("woodpulp", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CHEAPDIAPERSAP = registerItem("cheapdiapersap", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item DIAPERSAP = registerItem("diapersap", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item DiaperCore = registerItem("diapercore", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LightDiaperCore = registerItem("cheapdiapercore", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item TRIPLEDIAPERCORE = registerItem("threediapercore", new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final FoodComponent QUESADILLACOMPONENT = new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).alwaysEdible().build();
    public static final Item QUESADILLA = registerItem("quesadilla", new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(QUESADILLACOMPONENT)));

    public static List<Item> alldiapers = List.of();

    private static Item registerDiaper(String diapername, int MaxUse){
        Item diaper = registerItem(diapername + "diaper",
                new DiaperArmorItem(ModArmorMaterials.Diaper, EquipmentSlot.LEGS,
                        new FabricItemSettings().group(ItemGroup.MISC).maxDamage(MaxUse * 10), diapername, MaxUse));
        return diaper;
    }
    private static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier(DiaperPants.MOD_ID, name), item);
    }
    public static void registerModItems() {
        DiaperPants.LOGGER.info("Registering mod items for " + DiaperPants.MOD_ID + " :3");
    }
}
