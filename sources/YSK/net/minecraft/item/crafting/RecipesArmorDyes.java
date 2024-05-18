package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;

public class RecipesArmorDyes implements IRecipe
{
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        ItemStack itemStack = null;
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() instanceof ItemArmor) {
                    if (((ItemArmor)stackInSlot.getItem()).getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || itemStack != null) {
                        return "".length() != 0;
                    }
                    itemStack = stackInSlot;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    if (stackInSlot.getItem() != Items.dye) {
                        return "".length() != 0;
                    }
                    arrayList.add(stackInSlot);
                }
            }
            ++i;
        }
        if (itemStack != null && !arrayList.isEmpty()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getRecipeSize() {
        return 0x23 ^ 0x29;
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        ItemStack copy = null;
        final int[] array = new int["   ".length()];
        int length = "".length();
        int length2 = "".length();
        ItemArmor itemArmor = null;
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < inventoryCrafting.getSizeInventory()) {
            final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() instanceof ItemArmor) {
                    itemArmor = (ItemArmor)stackInSlot.getItem();
                    if (itemArmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || copy != null) {
                        return null;
                    }
                    copy = stackInSlot.copy();
                    copy.stackSize = " ".length();
                    if (itemArmor.hasColor(stackInSlot)) {
                        final int color = itemArmor.getColor(copy);
                        final float n = (color >> (0x55 ^ 0x45) & 210 + 49 - 227 + 223) / 255.0f;
                        final float n2 = (color >> (0x42 ^ 0x4A) & 102 + 186 - 233 + 200) / 255.0f;
                        final float n3 = (color & 4 + 232 + 16 + 3) / 255.0f;
                        length += (int)(Math.max(n, Math.max(n2, n3)) * 255.0f);
                        array["".length()] += (int)(n * 255.0f);
                        array[" ".length()] += (int)(n2 * 255.0f);
                        array["  ".length()] += (int)(n3 * 255.0f);
                        ++length2;
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                }
                else {
                    if (stackInSlot.getItem() != Items.dye) {
                        return null;
                    }
                    final float[] func_175513_a = EntitySheep.func_175513_a(EnumDyeColor.byDyeDamage(stackInSlot.getMetadata()));
                    final int n4 = (int)(func_175513_a["".length()] * 255.0f);
                    final int n5 = (int)(func_175513_a[" ".length()] * 255.0f);
                    final int n6 = (int)(func_175513_a["  ".length()] * 255.0f);
                    length += Math.max(n4, Math.max(n5, n6));
                    final int[] array2 = array;
                    final int length3 = "".length();
                    array2[length3] += n4;
                    final int[] array3 = array;
                    final int length4 = " ".length();
                    array3[length4] += n5;
                    final int[] array4 = array;
                    final int length5 = "  ".length();
                    array4[length5] += n6;
                    ++length2;
                }
            }
            ++i;
        }
        if (itemArmor == null) {
            return null;
        }
        final int n7 = array["".length()] / length2;
        final int n8 = array[" ".length()] / length2;
        final int n9 = array["  ".length()] / length2;
        final float n10 = length / length2;
        final float n11 = Math.max(n7, Math.max(n8, n9));
        itemArmor.setColor(copy, (((int)(n7 * n10 / n11) << (0x2E ^ 0x26)) + (int)(n8 * n10 / n11) << (0xCC ^ 0xC4)) + (int)(n9 * n10 / n11));
        return copy;
    }
    
    @Override
    public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        int i = "".length();
        "".length();
        if (3 == 0) {
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
    public ItemStack getRecipeOutput() {
        return null;
    }
}
