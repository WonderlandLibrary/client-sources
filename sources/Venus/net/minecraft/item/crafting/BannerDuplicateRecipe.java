/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BannerDuplicateRecipe
extends SpecialRecipe {
    public BannerDuplicateRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        DyeColor dyeColor = null;
        ItemStack itemStack = null;
        ItemStack itemStack2 = null;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack3 = craftingInventory.getStackInSlot(i);
            Item item = itemStack3.getItem();
            if (!(item instanceof BannerItem)) continue;
            BannerItem bannerItem = (BannerItem)item;
            if (dyeColor == null) {
                dyeColor = bannerItem.getColor();
            } else if (dyeColor != bannerItem.getColor()) {
                return true;
            }
            int n = BannerTileEntity.getPatterns(itemStack3);
            if (n > 6) {
                return true;
            }
            if (n > 0) {
                if (itemStack != null) {
                    return true;
                }
                itemStack = itemStack3;
                continue;
            }
            if (itemStack2 != null) {
                return true;
            }
            itemStack2 = itemStack3;
        }
        return itemStack != null && itemStack2 != null;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            int n;
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty() || (n = BannerTileEntity.getPatterns(itemStack)) <= 0 || n > 6) continue;
            ItemStack itemStack2 = itemStack.copy();
            itemStack2.setCount(1);
            return itemStack2;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory craftingInventory) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(craftingInventory.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem().hasContainerItem()) {
                nonNullList.set(i, new ItemStack(itemStack.getItem().getContainerItem()));
                continue;
            }
            if (!itemStack.hasTag() || BannerTileEntity.getPatterns(itemStack) <= 0) continue;
            ItemStack itemStack2 = itemStack.copy();
            itemStack2.setCount(1);
            nonNullList.set(i, itemStack2);
        }
        return nonNullList;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_BANNERDUPLICATE;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public NonNullList getRemainingItems(IInventory iInventory) {
        return this.getRemainingItems((CraftingInventory)iInventory);
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

