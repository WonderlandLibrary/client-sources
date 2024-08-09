/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ShieldRecipes
extends SpecialRecipe {
    public ShieldRecipes(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack3 = craftingInventory.getStackInSlot(i);
            if (itemStack3.isEmpty()) continue;
            if (itemStack3.getItem() instanceof BannerItem) {
                if (!itemStack2.isEmpty()) {
                    return true;
                }
                itemStack2 = itemStack3;
                continue;
            }
            if (itemStack3.getItem() != Items.SHIELD) {
                return true;
            }
            if (!itemStack.isEmpty()) {
                return true;
            }
            if (itemStack3.getChildTag("BlockEntityTag") != null) {
                return true;
            }
            itemStack = itemStack3;
        }
        return !itemStack.isEmpty() && !itemStack2.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        Object object;
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            object = craftingInventory.getStackInSlot(i);
            if (((ItemStack)object).isEmpty()) continue;
            if (((ItemStack)object).getItem() instanceof BannerItem) {
                itemStack = object;
                continue;
            }
            if (((ItemStack)object).getItem() != Items.SHIELD) continue;
            itemStack2 = ((ItemStack)object).copy();
        }
        if (itemStack2.isEmpty()) {
            return itemStack2;
        }
        CompoundNBT compoundNBT = itemStack.getChildTag("BlockEntityTag");
        object = compoundNBT == null ? new CompoundNBT() : compoundNBT.copy();
        ((CompoundNBT)object).putInt("Base", ((BannerItem)itemStack.getItem()).getColor().getId());
        itemStack2.setTagInfo("BlockEntityTag", (INBT)object);
        return itemStack2;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_SHIELD;
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

