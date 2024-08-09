/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BookCloningRecipe
extends SpecialRecipe {
    public BookCloningRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int n = 0;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() == Items.WRITTEN_BOOK) {
                if (!itemStack.isEmpty()) {
                    return true;
                }
                itemStack = itemStack2;
                continue;
            }
            if (itemStack2.getItem() != Items.WRITABLE_BOOK) {
                return true;
            }
            ++n;
        }
        return !itemStack.isEmpty() && itemStack.hasTag() && n > 0;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory craftingInventory) {
        Object object;
        int n = 0;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.getSizeInventory(); ++i) {
            object = craftingInventory.getStackInSlot(i);
            if (((ItemStack)object).isEmpty()) continue;
            if (((ItemStack)object).getItem() == Items.WRITTEN_BOOK) {
                if (!itemStack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                itemStack = object;
                continue;
            }
            if (((ItemStack)object).getItem() != Items.WRITABLE_BOOK) {
                return ItemStack.EMPTY;
            }
            ++n;
        }
        if (!itemStack.isEmpty() && itemStack.hasTag() && n >= 1 && WrittenBookItem.getGeneration(itemStack) < 2) {
            ItemStack itemStack2 = new ItemStack(Items.WRITTEN_BOOK, n);
            object = itemStack.getTag().copy();
            ((CompoundNBT)object).putInt("generation", WrittenBookItem.getGeneration(itemStack) + 1);
            itemStack2.setTag((CompoundNBT)object);
            return itemStack2;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory craftingInventory) {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(craftingInventory.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStackInSlot(i);
            if (itemStack.getItem().hasContainerItem()) {
                nonNullList.set(i, new ItemStack(itemStack.getItem().getContainerItem()));
                continue;
            }
            if (!(itemStack.getItem() instanceof WrittenBookItem)) continue;
            ItemStack itemStack2 = itemStack.copy();
            itemStack2.setCount(1);
            nonNullList.set(i, itemStack2);
            break;
        }
        return nonNullList;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.CRAFTING_SPECIAL_BOOKCLONING;
    }

    @Override
    public boolean canFit(int n, int n2) {
        return n >= 3 && n2 >= 3;
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

