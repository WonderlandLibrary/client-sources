package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.init.*;

public class RecipesArmor
{
    private static final String[] I;
    private Item[][] recipeItems;
    private String[][] recipePatterns;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < this.recipeItems["".length()].length) {
            final Item item = this.recipeItems["".length()][i];
            int j = "".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (j < this.recipeItems.length - " ".length()) {
                final ItemStack itemStack = new ItemStack(this.recipeItems[j + " ".length()][i]);
                final Object[] array = new Object["   ".length()];
                array["".length()] = this.recipePatterns[j];
                array[" ".length()] = (char)(0xF2 ^ 0xAA);
                array["  ".length()] = item;
                craftingManager.addRecipe(itemStack, array);
                ++j;
            }
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[0x4F ^ 0x45])["".length()] = I("\u001b/\u0010", "CwHnV");
        RecipesArmor.I[" ".length()] = I("\u001bO\u001a", "CoBak");
        RecipesArmor.I["  ".length()] = I("(C/", "pcwMZ");
        RecipesArmor.I["   ".length()] = I("\f\u001c\n", "TDRKt");
        RecipesArmor.I[0x1A ^ 0x1E] = I("(\n+", "pRsBn");
        RecipesArmor.I[0x50 ^ 0x55] = I("\u00009\u000f", "XaWLQ");
        RecipesArmor.I[0x14 ^ 0x12] = I("0S-", "hsubK");
        RecipesArmor.I[0x72 ^ 0x75] = I(".O:", "vobjE");
        RecipesArmor.I[0x5A ^ 0x52] = I(" E\u0015", "xeMtA");
        RecipesArmor.I[0x68 ^ 0x61] = I("\u0010G ", "HgxPw");
    }
    
    public RecipesArmor() {
        final String[][] recipePatterns = new String[0x6F ^ 0x6B][];
        final int length = "".length();
        final String[] array = new String["  ".length()];
        array["".length()] = RecipesArmor.I["".length()];
        array[" ".length()] = RecipesArmor.I[" ".length()];
        recipePatterns[length] = array;
        final int length2 = " ".length();
        final String[] array2 = new String["   ".length()];
        array2["".length()] = RecipesArmor.I["  ".length()];
        array2[" ".length()] = RecipesArmor.I["   ".length()];
        array2["  ".length()] = RecipesArmor.I[0x42 ^ 0x46];
        recipePatterns[length2] = array2;
        final int length3 = "  ".length();
        final String[] array3 = new String["   ".length()];
        array3["".length()] = RecipesArmor.I[0xB5 ^ 0xB0];
        array3[" ".length()] = RecipesArmor.I[0xC2 ^ 0xC4];
        array3["  ".length()] = RecipesArmor.I[0x2 ^ 0x5];
        recipePatterns[length3] = array3;
        final int length4 = "   ".length();
        final String[] array4 = new String["  ".length()];
        array4["".length()] = RecipesArmor.I[0x12 ^ 0x1A];
        array4[" ".length()] = RecipesArmor.I[0x66 ^ 0x6F];
        recipePatterns[length4] = array4;
        this.recipePatterns = recipePatterns;
        final Item[][] recipeItems = new Item[0x6D ^ 0x68][];
        final int length5 = "".length();
        final Item[] array5 = new Item[0x64 ^ 0x60];
        array5["".length()] = Items.leather;
        array5[" ".length()] = Items.iron_ingot;
        array5["  ".length()] = Items.diamond;
        array5["   ".length()] = Items.gold_ingot;
        recipeItems[length5] = array5;
        final int length6 = " ".length();
        final Item[] array6 = new Item[0x90 ^ 0x94];
        array6["".length()] = Items.leather_helmet;
        array6[" ".length()] = Items.iron_helmet;
        array6["  ".length()] = Items.diamond_helmet;
        array6["   ".length()] = Items.golden_helmet;
        recipeItems[length6] = array6;
        final int length7 = "  ".length();
        final Item[] array7 = new Item[0x75 ^ 0x71];
        array7["".length()] = Items.leather_chestplate;
        array7[" ".length()] = Items.iron_chestplate;
        array7["  ".length()] = Items.diamond_chestplate;
        array7["   ".length()] = Items.golden_chestplate;
        recipeItems[length7] = array7;
        final int length8 = "   ".length();
        final Item[] array8 = new Item[0x86 ^ 0x82];
        array8["".length()] = Items.leather_leggings;
        array8[" ".length()] = Items.iron_leggings;
        array8["  ".length()] = Items.diamond_leggings;
        array8["   ".length()] = Items.golden_leggings;
        recipeItems[length8] = array8;
        final int n = 0xA3 ^ 0xA7;
        final Item[] array9 = new Item[0xBA ^ 0xBE];
        array9["".length()] = Items.leather_boots;
        array9[" ".length()] = Items.iron_boots;
        array9["  ".length()] = Items.diamond_boots;
        array9["   ".length()] = Items.golden_boots;
        recipeItems[n] = array9;
        this.recipeItems = recipeItems;
    }
}
