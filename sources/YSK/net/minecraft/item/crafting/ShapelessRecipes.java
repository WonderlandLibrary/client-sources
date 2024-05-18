package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;

public class ShapelessRecipes implements IRecipe
{
    private final ItemStack recipeOutput;
    private final List<ItemStack> recipeItems;
    
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
            if (4 <= 1) {
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
        if (-1 == 3) {
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
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.recipeItems);
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < inventoryCrafting.getHeight()) {
            int j = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
            while (j < inventoryCrafting.getWidth()) {
                final ItemStack stackInRowAndColumn = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (stackInRowAndColumn != null) {
                    int n = "".length();
                    final Iterator<ItemStack> iterator = (Iterator<ItemStack>)arrayList.iterator();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final ItemStack itemStack = iterator.next();
                        if (stackInRowAndColumn.getItem() == itemStack.getItem() && (itemStack.getMetadata() == 31961 + 11097 - 18827 + 8536 || stackInRowAndColumn.getMetadata() == itemStack.getMetadata())) {
                            n = " ".length();
                            arrayList.remove(itemStack);
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                            break;
                        }
                    }
                    if (n == 0) {
                        return "".length() != 0;
                    }
                }
                ++j;
            }
            ++i;
        }
        return arrayList.isEmpty();
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    public ShapelessRecipes(final ItemStack recipeOutput, final List<ItemStack> recipeItems) {
        this.recipeOutput = recipeOutput;
        this.recipeItems = recipeItems;
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        return this.recipeOutput.copy();
    }
}
