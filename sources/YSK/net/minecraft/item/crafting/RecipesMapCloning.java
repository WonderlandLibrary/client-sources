package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class RecipesMapCloning implements IRecipe
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < array.length) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null && stackInSlot.getItem().hasContainerItem()) {
                array[i] = new ItemStack(stackInSlot.getItem().getContainerItem());
            }
            ++i;
        }
        return array;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        int length = "".length();
        ItemStack itemStack = null;
        int i = "".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.filled_map) {
                    if (itemStack != null) {
                        return null;
                    }
                    itemStack = stackInSlot;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    if (stackInSlot.getItem() != Items.map) {
                        return null;
                    }
                    ++length;
                }
            }
            ++i;
        }
        if (itemStack != null && length >= " ".length()) {
            final ItemStack itemStack2 = new ItemStack(Items.filled_map, length + " ".length(), itemStack.getMetadata());
            if (itemStack.hasDisplayName()) {
                itemStack2.setStackDisplayName(itemStack.getDisplayName());
            }
            return itemStack2;
        }
        return null;
    }
    
    @Override
    public int getRecipeSize() {
        return 0x2A ^ 0x23;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        int length = "".length();
        ItemStack itemStack = null;
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.filled_map) {
                    if (itemStack != null) {
                        return "".length() != 0;
                    }
                    itemStack = stackInSlot;
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else {
                    if (stackInSlot.getItem() != Items.map) {
                        return "".length() != 0;
                    }
                    ++length;
                }
            }
            ++i;
        }
        if (itemStack != null && length > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
