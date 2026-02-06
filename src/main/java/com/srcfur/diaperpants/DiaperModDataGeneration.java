package com.srcfur.diaperpants;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;

import java.util.List;
import java.util.function.Consumer;

public class DiaperModDataGeneration implements DataGeneratorEntrypoint {
    private static class DiaperRecipeGenerator extends FabricRecipeProvider {
        private DiaperRecipeGenerator(FabricDataGenerator generator){
            super(generator);
        }
        @Override
        protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

        }
    }
    public static class DiaperModelGenerator extends FabricModelProvider {
        public DiaperModelGenerator(FabricDataGenerator output) {
            super(output);
        }


        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            // ...
        }


        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            List<Item> diapers = List.of(ModItems.CHEAPDIAPER, ModItems.MEDICALDIAPER, ModItems.SUBSPACEDIAPER);
            for(int i = 0; i < diapers.size(); i++){
                DiaperArmorItem cdiaper = (DiaperArmorItem) diapers.get(i);
                itemModelGenerator.register(cdiaper, cdiaper.DiaperTexture, Models.GENERATED);
            }
        }
    }
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(DiaperRecipeGenerator::new);
        generator.addProvider(DiaperModelGenerator::new);

    }
}
