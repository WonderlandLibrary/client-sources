package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipesTools
{
    private String[][] recipePatterns;
    private static final String[] I;
    private Object[][] recipeItems;
    
    public RecipesTools() {
        final String[][] recipePatterns = new String[0x66 ^ 0x62][];
        final int length = "".length();
        final String[] array = new String["   ".length()];
        array["".length()] = RecipesTools.I["".length()];
        array[" ".length()] = RecipesTools.I[" ".length()];
        array["  ".length()] = RecipesTools.I["  ".length()];
        recipePatterns[length] = array;
        final int length2 = " ".length();
        final String[] array2 = new String["   ".length()];
        array2["".length()] = RecipesTools.I["   ".length()];
        array2[" ".length()] = RecipesTools.I[0x25 ^ 0x21];
        array2["  ".length()] = RecipesTools.I[0x5E ^ 0x5B];
        recipePatterns[length2] = array2;
        final int length3 = "  ".length();
        final String[] array3 = new String["   ".length()];
        array3["".length()] = RecipesTools.I[0x62 ^ 0x64];
        array3[" ".length()] = RecipesTools.I[0x16 ^ 0x11];
        array3["  ".length()] = RecipesTools.I[0x63 ^ 0x6B];
        recipePatterns[length3] = array3;
        final int length4 = "   ".length();
        final String[] array4 = new String["   ".length()];
        array4["".length()] = RecipesTools.I[0x78 ^ 0x71];
        array4[" ".length()] = RecipesTools.I[0x7B ^ 0x71];
        array4["  ".length()] = RecipesTools.I[0x86 ^ 0x8D];
        recipePatterns[length4] = array4;
        this.recipePatterns = recipePatterns;
        final Object[][] recipeItems = new Object[0xB7 ^ 0xB2][];
        final int length5 = "".length();
        final Object[] array5 = new Object[0x5C ^ 0x59];
        array5["".length()] = Blocks.planks;
        array5[" ".length()] = Blocks.cobblestone;
        array5["  ".length()] = Items.iron_ingot;
        array5["   ".length()] = Items.diamond;
        array5[0x1F ^ 0x1B] = Items.gold_ingot;
        recipeItems[length5] = array5;
        final int length6 = " ".length();
        final Object[] array6 = new Object[0x34 ^ 0x31];
        array6["".length()] = Items.wooden_pickaxe;
        array6[" ".length()] = Items.stone_pickaxe;
        array6["  ".length()] = Items.iron_pickaxe;
        array6["   ".length()] = Items.diamond_pickaxe;
        array6[0x5C ^ 0x58] = Items.golden_pickaxe;
        recipeItems[length6] = array6;
        final int length7 = "  ".length();
        final Object[] array7 = new Object[0x56 ^ 0x53];
        array7["".length()] = Items.wooden_shovel;
        array7[" ".length()] = Items.stone_shovel;
        array7["  ".length()] = Items.iron_shovel;
        array7["   ".length()] = Items.diamond_shovel;
        array7[0x93 ^ 0x97] = Items.golden_shovel;
        recipeItems[length7] = array7;
        final int length8 = "   ".length();
        final Object[] array8 = new Object[0x6B ^ 0x6E];
        array8["".length()] = Items.wooden_axe;
        array8[" ".length()] = Items.stone_axe;
        array8["  ".length()] = Items.iron_axe;
        array8["   ".length()] = Items.diamond_axe;
        array8[0x5F ^ 0x5B] = Items.golden_axe;
        recipeItems[length8] = array8;
        final int n = 0x8C ^ 0x88;
        final Object[] array9 = new Object[0x30 ^ 0x35];
        array9["".length()] = Items.wooden_hoe;
        array9[" ".length()] = Items.stone_hoe;
        array9["  ".length()] = Items.iron_hoe;
        array9["   ".length()] = Items.diamond_hoe;
        array9[0xC4 ^ 0xC0] = Items.golden_hoe;
        recipeItems[n] = array9;
        this.recipeItems = recipeItems;
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.recipeItems["".length()].length) {
            final Object o = this.recipeItems["".length()][i];
            int j = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (j < this.recipeItems.length - " ".length()) {
                final ItemStack itemStack = new ItemStack((Item)this.recipeItems[j + " ".length()][i]);
                final Object[] array = new Object[0x2B ^ 0x2E];
                array["".length()] = this.recipePatterns[j];
                array[" ".length()] = (char)(0xB0 ^ 0x93);
                array["  ".length()] = Items.stick;
                array["   ".length()] = (char)(0x7C ^ 0x24);
                array[0xBA ^ 0xBE] = o;
                craftingManager.addRecipe(itemStack, array);
                ++j;
            }
            ++i;
        }
        final ItemStack itemStack2 = new ItemStack(Items.shears);
        final Object[] array2 = new Object[0x69 ^ 0x6D];
        array2["".length()] = RecipesTools.I[0x63 ^ 0x6F];
        array2[" ".length()] = RecipesTools.I[0xA9 ^ 0xA4];
        array2["  ".length()] = (char)(0x77 ^ 0x54);
        array2["   ".length()] = Items.iron_ingot;
        craftingManager.addRecipe(itemStack2, array2);
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x31])["".length()] = I("7\u000b\"", "oSzqC");
        RecipesTools.I[" ".length()] = I("nWu", "NtUgJ");
        RecipesTools.I["  ".length()] = I("qly", "QOYJp");
        RecipesTools.I["   ".length()] = I("\f", "TuYEX");
        RecipesTools.I[0x7C ^ 0x78] = I("E", "fuBiB");
        RecipesTools.I[0x16 ^ 0x13] = I("r", "QDFDs");
        RecipesTools.I[0x48 ^ 0x4E] = I("\u001a\u000f", "BWHoV");
        RecipesTools.I[0x3 ^ 0x4] = I("3G", "kdWLJ");
        RecipesTools.I[0x68 ^ 0x60] = I("ge", "GFcOb");
        RecipesTools.I[0xA8 ^ 0xA1] = I("\u001c\u001f", "DGBlK");
        RecipesTools.I[0xE ^ 0x4] = I("Dw", "dTOWL");
        RecipesTools.I[0x3C ^ 0x37] = I("VG", "vdUjW");
        RecipesTools.I[0x66 ^ 0x6A] = I("su", "SVvBO");
        RecipesTools.I[0x62 ^ 0x6F] = I("oA", "LaSrB");
    }
    
    static {
        I();
    }
}
