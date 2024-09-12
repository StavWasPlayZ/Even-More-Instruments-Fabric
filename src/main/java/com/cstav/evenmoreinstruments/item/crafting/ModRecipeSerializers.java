package com.cstav.evenmoreinstruments.item.crafting;

import com.cstav.evenmoreinstruments.EMIMain;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class ModRecipeSerializers {
    public static void load() {}

    public static final RecipeSerializer<RecordCloningRecipe> RECORD_CLONING = register(
        "crafting_special_recordcloning",
        new SimpleCraftingRecipeSerializer<>(RecordCloningRecipe::new))
    ;

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(final String name, final S value) {
        return Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            EMIMain.loc(name),
            value
        );
    }

}