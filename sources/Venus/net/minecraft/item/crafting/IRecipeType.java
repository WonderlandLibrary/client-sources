/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import java.util.Optional;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface IRecipeType<T extends IRecipe<?>> {
    public static final IRecipeType<ICraftingRecipe> CRAFTING = IRecipeType.register("crafting");
    public static final IRecipeType<FurnaceRecipe> SMELTING = IRecipeType.register("smelting");
    public static final IRecipeType<BlastingRecipe> BLASTING = IRecipeType.register("blasting");
    public static final IRecipeType<SmokingRecipe> SMOKING = IRecipeType.register("smoking");
    public static final IRecipeType<CampfireCookingRecipe> CAMPFIRE_COOKING = IRecipeType.register("campfire_cooking");
    public static final IRecipeType<StonecuttingRecipe> STONECUTTING = IRecipeType.register("stonecutting");
    public static final IRecipeType<SmithingRecipe> SMITHING = IRecipeType.register("smithing");

    public static <T extends IRecipe<?>> IRecipeType<T> register(String string) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(string), new IRecipeType<T>(string){
            final String val$key;
            {
                this.val$key = string;
            }

            public String toString() {
                return this.val$key;
            }
        });
    }

    default public <C extends IInventory> Optional<T> matches(IRecipe<C> iRecipe, World world, C c) {
        return iRecipe.matches(c, world) ? Optional.of(iRecipe) : Optional.empty();
    }
}

