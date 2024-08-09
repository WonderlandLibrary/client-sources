/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MapCloningRecipe
extends SpecialRecipe {
    public MapCloningRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int n = 0;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() == Items.FILLED_MAP) {
                if (!itemStack.isEmpty()) {
                    return true;
                }
                itemStack = itemStack2;
                continue;
            }
            if (itemStack2.getItem() != Items.MAP) {
                return true;
            }
            ++n;
        }
        return !itemStack.isEmpty() && n > 0;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        int n = 0;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() == Items.FILLED_MAP) {
                if (!itemStack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                itemStack = itemStack2;
                continue;
            }
            if (itemStack2.getItem() != Items.MAP) {
                return ItemStack.EMPTY;
            }
            ++n;
        }
        if (!itemStack.isEmpty() && n >= 1) {
            ItemStack itemStack3 = itemStack.copy();
            itemStack3.setCount(n + 1);
            return itemStack3;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n >= 3 && n2 >= 3;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_MAPCLONING;
    }

    @Override
    public ItemStack getCraftingResult(IInventory iInventory) {
        return this.getCraftingResult((CraftingInventory)iInventory);
    }

    @Override
    public boolean matches(IInventory iInventory, World world) {
        return this.matches((CraftingInventory)iInventory, world);
    }
}

