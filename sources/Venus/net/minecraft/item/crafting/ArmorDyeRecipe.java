/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ArmorDyeRecipe
extends SpecialRecipe {
    public ArmorDyeRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() instanceof IDyeableArmorItem) {
                if (!itemStack.isEmpty()) {
                    return true;
                }
                itemStack = itemStack2;
                continue;
            }
            if (!(itemStack2.getItem() instanceof DyeItem)) {
                return true;
            }
            arrayList.add(itemStack2);
        }
        return !itemStack.isEmpty() && !arrayList.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        ArrayList<DyeItem> arrayList = Lists.newArrayList();
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            Item item = itemStack2.getItem();
            if (item instanceof IDyeableArmorItem) {
                if (!itemStack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                itemStack = itemStack2.copy();
                continue;
            }
            if (!(item instanceof DyeItem)) {
                return ItemStack.EMPTY;
            }
            arrayList.add((DyeItem)item);
        }
        return !itemStack.isEmpty() && !arrayList.isEmpty() ? IDyeableArmorItem.dyeItem(itemStack, arrayList) : ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n * n2 >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_ARMORDYE;
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

