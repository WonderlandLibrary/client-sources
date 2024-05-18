package net.minecraft.item.crafting;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipesIngots
{
    private static final String[] I;
    private Object[][] recipeItems;
    
    private static void I() {
        (I = new String[0x9B ^ 0x93])["".length()] = I("plN", "SOmiE");
        RecipesIngots.I[" ".length()] = I("POE", "slfuT");
        RecipesIngots.I["  ".length()] = I("KQm", "hrNwT");
        RecipesIngots.I["   ".length()] = I("I", "jMZYL");
        RecipesIngots.I[0xB1 ^ 0xB5] = I("Kk[", "hHxFV");
        RecipesIngots.I[0x4B ^ 0x4E] = I("NJE", "mifyU");
        RecipesIngots.I[0x8E ^ 0x88] = I("NVh", "muKmK");
        RecipesIngots.I[0x6F ^ 0x68] = I("V", "usIcT");
    }
    
    public void addRecipes(final CraftingManager craftingManager) {
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < this.recipeItems.length) {
            final Block block = (Block)this.recipeItems[i]["".length()];
            final ItemStack itemStack = (ItemStack)this.recipeItems[i][" ".length()];
            final ItemStack itemStack2 = new ItemStack(block);
            final Object[] array = new Object[0x55 ^ 0x50];
            array["".length()] = RecipesIngots.I["".length()];
            array[" ".length()] = RecipesIngots.I[" ".length()];
            array["  ".length()] = RecipesIngots.I["  ".length()];
            array["   ".length()] = (char)(0x99 ^ 0xBA);
            array[0x44 ^ 0x40] = itemStack;
            craftingManager.addRecipe(itemStack2, array);
            final ItemStack itemStack3 = itemStack;
            final Object[] array2 = new Object["   ".length()];
            array2["".length()] = RecipesIngots.I["   ".length()];
            array2[" ".length()] = (char)(0x25 ^ 0x6);
            array2["  ".length()] = block;
            craftingManager.addRecipe(itemStack3, array2);
            ++i;
        }
        final ItemStack itemStack4 = new ItemStack(Items.gold_ingot);
        final Object[] array3 = new Object[0xBB ^ 0xBE];
        array3["".length()] = RecipesIngots.I[0x34 ^ 0x30];
        array3[" ".length()] = RecipesIngots.I[0x5D ^ 0x58];
        array3["  ".length()] = RecipesIngots.I[0x6D ^ 0x6B];
        array3["   ".length()] = (char)(0x3D ^ 0x1E);
        array3[0x3D ^ 0x39] = Items.gold_nugget;
        craftingManager.addRecipe(itemStack4, array3);
        final ItemStack itemStack5 = new ItemStack(Items.gold_nugget, 0xB9 ^ 0xB0);
        final Object[] array4 = new Object["   ".length()];
        array4["".length()] = RecipesIngots.I[0xE ^ 0x9];
        array4[" ".length()] = (char)(0xE4 ^ 0xC7);
        array4["  ".length()] = Items.gold_ingot;
        craftingManager.addRecipe(itemStack5, array4);
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RecipesIngots() {
        final Object[][] recipeItems = new Object[0x8B ^ 0x82][];
        final int length = "".length();
        final Object[] array = new Object["  ".length()];
        array["".length()] = Blocks.gold_block;
        array[" ".length()] = new ItemStack(Items.gold_ingot, 0x2D ^ 0x24);
        recipeItems[length] = array;
        final int length2 = " ".length();
        final Object[] array2 = new Object["  ".length()];
        array2["".length()] = Blocks.iron_block;
        array2[" ".length()] = new ItemStack(Items.iron_ingot, 0x7 ^ 0xE);
        recipeItems[length2] = array2;
        final int length3 = "  ".length();
        final Object[] array3 = new Object["  ".length()];
        array3["".length()] = Blocks.diamond_block;
        array3[" ".length()] = new ItemStack(Items.diamond, 0x56 ^ 0x5F);
        recipeItems[length3] = array3;
        final int length4 = "   ".length();
        final Object[] array4 = new Object["  ".length()];
        array4["".length()] = Blocks.emerald_block;
        array4[" ".length()] = new ItemStack(Items.emerald, 0x89 ^ 0x80);
        recipeItems[length4] = array4;
        final int n = 0xC7 ^ 0xC3;
        final Object[] array5 = new Object["  ".length()];
        array5["".length()] = Blocks.lapis_block;
        array5[" ".length()] = new ItemStack(Items.dye, 0x5E ^ 0x57, EnumDyeColor.BLUE.getDyeDamage());
        recipeItems[n] = array5;
        final int n2 = 0x0 ^ 0x5;
        final Object[] array6 = new Object["  ".length()];
        array6["".length()] = Blocks.redstone_block;
        array6[" ".length()] = new ItemStack(Items.redstone, 0x1A ^ 0x13);
        recipeItems[n2] = array6;
        final int n3 = 0x8F ^ 0x89;
        final Object[] array7 = new Object["  ".length()];
        array7["".length()] = Blocks.coal_block;
        array7[" ".length()] = new ItemStack(Items.coal, 0x48 ^ 0x41, "".length());
        recipeItems[n3] = array7;
        final int n4 = 0xA5 ^ 0xA2;
        final Object[] array8 = new Object["  ".length()];
        array8["".length()] = Blocks.hay_block;
        array8[" ".length()] = new ItemStack(Items.wheat, 0x59 ^ 0x50);
        recipeItems[n4] = array8;
        final int n5 = 0xB3 ^ 0xBB;
        final Object[] array9 = new Object["  ".length()];
        array9["".length()] = Blocks.slime_block;
        array9[" ".length()] = new ItemStack(Items.slime_ball, 0xB2 ^ 0xBB);
        recipeItems[n5] = array9;
        this.recipeItems = recipeItems;
    }
    
    static {
        I();
    }
}
