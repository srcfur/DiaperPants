package com.srcfur.diaperpants.block;


import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.block.custom.ToiletBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block PUPPY_BLUE = registerBlock("puppy_pastel_blue",
            new Block(FabricBlockSettings.of(Material.WOOL).strength(1).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block PUPPY_PINK = registerBlock("puppy_pastel_pink",
            new Block(FabricBlockSettings.of(Material.WOOL).strength(1).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block TOILET_BASIC = registerBlock("toilet_basic",
            new ToiletBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().drops(new Identifier("blocks/toilet_basic")).strength(4)), ItemGroup.MISC);
    public static final Block TOILET_COPPER = registerBlock("toilet_copper",
            new ToiletBlock(FabricBlockSettings.of(Material.METAL).nonOpaque().drops(new Identifier("block.diaperpants.toilet_copper")).strength(4)), ItemGroup.MISC);


    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(DiaperPants.MOD_ID, name), block);
    }
    public static Item registerBlockItem(String name, Block block, ItemGroup group){
        return Registry.register(Registry.ITEM, new Identifier(DiaperPants.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }
    public static void registerModBlocks() {
        DiaperPants.LOGGER.info("Registering modblocks for " + DiaperPants.MOD_ID);
    }
}
