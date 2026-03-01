package com.srcfur.diaperpants.recipes;

import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void RegisterRecipe(){
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(DiaperPants.MOD_ID, DiaperAssemblerRecipe.Serializer.ID),
                DiaperAssemblerRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(DiaperPants.MOD_ID, DiaperAssemblerRecipe.Type.ID),
                DiaperAssemblerRecipe.Type.INSTANCE);
    }
}
