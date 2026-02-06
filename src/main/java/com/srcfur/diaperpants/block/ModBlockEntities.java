package com.srcfur.diaperpants.block;

import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.block.entity.PottyEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class ModBlockEntities {
    public static BlockEntityType<PottyEntity> PottyStation;

    public static void registerAllBlockEntities(){
        //This single handedly has been killing me :3
        PottyStation = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                RegistryKey.of(Registry.BLOCK_ENTITY_TYPE_KEY, new Identifier(DiaperPants.MOD_ID, "potty_station")),
                FabricBlockEntityTypeBuilder.create(PottyEntity::new,
                        ModBlocks.TOILET_BASIC).build(null));

    }
}
