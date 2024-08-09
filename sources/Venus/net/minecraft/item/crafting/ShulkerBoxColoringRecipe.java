/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShulkerBoxColoringRecipe
extends SpecialRecipe {
    public ShulkerBoxColoringRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock) {
                ++n;
            } else {
                if (!(itemStack.getItem() instanceof DyeItem)) {
                    return true;
                }
                ++n2;
            }
            if (n2 <= 1 && n <= 1) continue;
            return true;
        }
        return n == 1 && n2 == 1;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeItem dyeItem = (DyeItem)Items.WHITE_DYE;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            Item item = itemStack2.getItem();
            if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
                itemStack = itemStack2;
                continue;
            }
            if (!(item instanceof DyeItem)) continue;
            dyeItem = (DyeItem)item;
        }
        ItemStack itemStack3 = ShulkerBoxBlock.getColoredItemStack(dyeItem.getDyeColor());
        if (itemStack.hasTag()) {
            itemStack3.setTag(itemStack.getTag().copy());
        }
        return itemStack3;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_SHULKERBOXCOLORING;
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

