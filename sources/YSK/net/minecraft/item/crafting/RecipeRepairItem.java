package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;

public class RecipeRepairItem implements IRecipe
{
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                arrayList.add(stackInSlot);
                if (arrayList.size() > " ".length()) {
                    final ItemStack itemStack = arrayList.get("".length());
                    if (stackInSlot.getItem() != itemStack.getItem() || itemStack.stackSize != " ".length() || stackInSlot.stackSize != " ".length() || !itemStack.getItem().isDamageable()) {
                        return "".length() != 0;
                    }
                }
            }
            ++i;
        }
        if (arrayList.size() == "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getRecipeSize() {
        return 0x41 ^ 0x45;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                arrayList.add(stackInSlot);
                if (arrayList.size() > " ".length()) {
                    final ItemStack itemStack = arrayList.get("".length());
                    if (stackInSlot.getItem() != itemStack.getItem() || itemStack.stackSize != " ".length() || stackInSlot.stackSize != " ".length() || !itemStack.getItem().isDamageable()) {
                        return null;
                    }
                }
            }
            ++i;
        }
        if (arrayList.size() == "  ".length()) {
            final ItemStack itemStack2 = arrayList.get("".length());
            final ItemStack itemStack3 = arrayList.get(" ".length());
            if (itemStack2.getItem() == itemStack3.getItem() && itemStack2.stackSize == " ".length() && itemStack3.stackSize == " ".length() && itemStack2.getItem().isDamageable()) {
                final Item item = itemStack2.getItem();
                int length = item.getMaxDamage() - (item.getMaxDamage() - itemStack2.getItemDamage() + (item.getMaxDamage() - itemStack3.getItemDamage()) + item.getMaxDamage() * (0xB8 ^ 0xBD) / (0x63 ^ 0x7));
                if (length < 0) {
                    length = "".length();
                }
                return new ItemStack(itemStack2.getItem(), " ".length(), length);
            }
        }
        return null;
    }
    
    @Override
    public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        int i = "".length();
        "".length();
        if (4 <= 1) {
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
