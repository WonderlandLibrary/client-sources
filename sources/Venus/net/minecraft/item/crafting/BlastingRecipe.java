/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class BlastingRecipe
extends AbstractCookingRecipe {
    public BlastingRecipe(ResourceLocation resourceLocation, String string, Ingredient ingredient, ItemStack itemStack, float f, int n) {
        super(IRecipeType.BLASTING, resourceLocation, string, ingredient, itemStack, f, n);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.BLAST_FURNACE);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.BLASTING;
    }
}

