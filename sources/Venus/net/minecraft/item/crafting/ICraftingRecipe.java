/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface ICraftingRecipe
extends IRecipe<CraftingInventory> {
    @Override
    default public IRecipeType<?> getType() {
        return IRecipeType.CRAFTING;
    }
}

