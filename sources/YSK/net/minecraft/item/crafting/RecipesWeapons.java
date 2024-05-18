package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.init.*;

public class RecipesWeapons
{
    private static final String[] I;
    private String[][] recipePatterns;
    private Object[][] recipeItems;
    
    static {
        I();
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < this.recipeItems["".length()].length) {
            final Object o = this.recipeItems["".length()][i];
            int j = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (j < this.recipeItems.length - " ".length()) {
                final ItemStack itemStack = new ItemStack((Item)this.recipeItems[j + " ".length()][i]);
                final Object[] array = new Object[0xC2 ^ 0xC7];
                array["".length()] = this.recipePatterns[j];
                array[" ".length()] = (char)(0x64 ^ 0x47);
                array["  ".length()] = Items.stick;
                array["   ".length()] = (char)(0xD ^ 0x55);
                array[0x33 ^ 0x37] = o;
                craftingManager.addRecipe(itemStack, array);
                ++j;
            }
            ++i;
        }
        final ItemStack itemStack2 = new ItemStack(Items.bow, " ".length());
        final Object[] array2 = new Object[0xC6 ^ 0xC1];
        array2["".length()] = RecipesWeapons.I["   ".length()];
        array2[" ".length()] = RecipesWeapons.I[0x16 ^ 0x12];
        array2["  ".length()] = RecipesWeapons.I[0x4B ^ 0x4E];
        array2["   ".length()] = (char)(0x5D ^ 0x5);
        array2[0x4D ^ 0x49] = Items.string;
        array2[0x39 ^ 0x3C] = (char)(0x9E ^ 0xBD);
        array2[0x4A ^ 0x4C] = Items.stick;
        craftingManager.addRecipe(itemStack2, array2);
        final ItemStack itemStack3 = new ItemStack(Items.arrow, 0x8C ^ 0x88);
        final Object[] array3 = new Object[0xBB ^ 0xB2];
        array3["".length()] = RecipesWeapons.I[0xAE ^ 0xA8];
        array3[" ".length()] = RecipesWeapons.I[0x14 ^ 0x13];
        array3["  ".length()] = RecipesWeapons.I[0xB5 ^ 0xBD];
        array3["   ".length()] = (char)(0x32 ^ 0x6B);
        array3[0x60 ^ 0x64] = Items.feather;
        array3[0xB7 ^ 0xB2] = (char)(0xDB ^ 0x83);
        array3[0x3C ^ 0x3A] = Items.flint;
        array3[0x69 ^ 0x6E] = (char)(0x75 ^ 0x56);
        array3[0xA4 ^ 0xAC] = Items.stick;
        craftingManager.addRecipe(itemStack3, array3);
    }
    
    private static void I() {
        (I = new String[0x2F ^ 0x26])["".length()] = I("-", "uybVQ");
        RecipesWeapons.I[" ".length()] = I("\u0010", "HvtLN");
        RecipesWeapons.I["  ".length()] = I("y", "ZYyTZ");
        RecipesWeapons.I["   ".length()] = I("or\u0019", "OQAMr");
        RecipesWeapons.I[0xB ^ 0xF] = I("wn*", "TNrUc");
        RecipesWeapons.I[0x5F ^ 0x5A] = I("Gs9", "gPaJx");
        RecipesWeapons.I[0x94 ^ 0x92] = I(",", "tnbbF");
        RecipesWeapons.I[0x92 ^ 0x95] = I("[", "xDwRU");
        RecipesWeapons.I[0x42 ^ 0x4A] = I("\u000b", "RDwQB");
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
    
    public RecipesWeapons() {
        final String[][] recipePatterns = new String[" ".length()][];
        final int length = "".length();
        final String[] array = new String["   ".length()];
        array["".length()] = RecipesWeapons.I["".length()];
        array[" ".length()] = RecipesWeapons.I[" ".length()];
        array["  ".length()] = RecipesWeapons.I["  ".length()];
        recipePatterns[length] = array;
        this.recipePatterns = recipePatterns;
        final Object[][] recipeItems = new Object["  ".length()][];
        final int length2 = "".length();
        final Object[] array2 = new Object[0x3A ^ 0x3F];
        array2["".length()] = Blocks.planks;
        array2[" ".length()] = Blocks.cobblestone;
        array2["  ".length()] = Items.iron_ingot;
        array2["   ".length()] = Items.diamond;
        array2[0x87 ^ 0x83] = Items.gold_ingot;
        recipeItems[length2] = array2;
        final int length3 = " ".length();
        final Object[] array3 = new Object[0xB7 ^ 0xB2];
        array3["".length()] = Items.wooden_sword;
        array3[" ".length()] = Items.stone_sword;
        array3["  ".length()] = Items.iron_sword;
        array3["   ".length()] = Items.diamond_sword;
        array3[0x38 ^ 0x3C] = Items.golden_sword;
        recipeItems[length3] = array3;
        this.recipeItems = recipeItems;
    }
}
