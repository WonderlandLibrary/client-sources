package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class ShapedRecipes implements IRecipe
{
    private final int recipeWidth;
    private final ItemStack recipeOutput;
    private final ItemStack[] recipeItems;
    private boolean copyIngredientNBT;
    private final int recipeHeight;
    
    @Override
    public boolean matches(final InventoryCrafting inventoryCrafting, final World world) {
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i <= "   ".length() - this.recipeWidth) {
            int j = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j <= "   ".length() - this.recipeHeight) {
                if (this.checkMatch(inventoryCrafting, i, j, " ".length() != 0)) {
                    return " ".length() != 0;
                }
                if (this.checkMatch(inventoryCrafting, i, j, "".length() != 0)) {
                    return " ".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    private boolean checkMatch(final InventoryCrafting inventoryCrafting, final int n, final int n2, final boolean b) {
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (j < "   ".length()) {
                final int n3 = i - n;
                final int n4 = j - n2;
                ItemStack itemStack = null;
                if (n3 >= 0 && n4 >= 0 && n3 < this.recipeWidth && n4 < this.recipeHeight) {
                    if (b) {
                        itemStack = this.recipeItems[this.recipeWidth - n3 - " ".length() + n4 * this.recipeWidth];
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        itemStack = this.recipeItems[n3 + n4 * this.recipeWidth];
                    }
                }
                final ItemStack stackInRowAndColumn = inventoryCrafting.getStackInRowAndColumn(i, j);
                if (stackInRowAndColumn != null || itemStack != null) {
                    if ((stackInRowAndColumn == null && itemStack != null) || (stackInRowAndColumn != null && itemStack == null)) {
                        return "".length() != 0;
                    }
                    if (itemStack.getItem() != stackInRowAndColumn.getItem()) {
                        return "".length() != 0;
                    }
                    if (itemStack.getMetadata() != 1222 + 13871 + 12006 + 5668 && itemStack.getMetadata() != stackInRowAndColumn.getMetadata()) {
                        return "".length() != 0;
                    }
                }
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public ItemStack[] getRemainingItems(final InventoryCrafting inventoryCrafting) {
        final ItemStack[] array = new ItemStack[inventoryCrafting.getSizeInventory()];
        int i = "".length();
        "".length();
        if (1 >= 3) {
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
    
    public ShapedRecipes(final int recipeWidth, final int recipeHeight, final ItemStack[] recipeItems, final ItemStack recipeOutput) {
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting inventoryCrafting) {
        final ItemStack copy = this.getRecipeOutput().copy();
        if (this.copyIngredientNBT) {
            int i = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (i < inventoryCrafting.getSizeInventory()) {
                final ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (stackInSlot != null && stackInSlot.hasTagCompound()) {
                    copy.setTagCompound((NBTTagCompound)stackInSlot.getTagCompound().copy());
                }
                ++i;
            }
        }
        return copy;
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
