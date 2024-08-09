/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class StonecuttingRecipe
extends SingleItemRecipe {
    public StonecuttingRecipe(ResourceLocation resourceLocation, String string, Ingredient ingredient, ItemStack itemStack) {
        super(IRecipeType.STONECUTTING, IRecipeSerializer.STONECUTTING, resourceLocation, string, ingredient, itemStack);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.ingredient.test(iInventory.getStackInSlot(0));
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.STONECUTTER);
    }
}

