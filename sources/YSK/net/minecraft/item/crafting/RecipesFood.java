package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;

public class RecipesFood
{
    private static final String[] I;
    
    public void addRecipes(final CraftingManager craftingManager) {
        final ItemStack itemStack = new ItemStack(Items.mushroom_stew);
        final Object[] array = new Object["   ".length()];
        array["".length()] = Blocks.brown_mushroom;
        array[" ".length()] = Blocks.red_mushroom;
        array["  ".length()] = Items.bowl;
        craftingManager.addShapelessRecipe(itemStack, array);
        final ItemStack itemStack2 = new ItemStack(Items.cookie, 0x54 ^ 0x5C);
        final Object[] array2 = new Object[0x6E ^ 0x6B];
        array2["".length()] = RecipesFood.I["".length()];
        array2[" ".length()] = (char)(0xD1 ^ 0x89);
        array2["  ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BROWN.getDyeDamage());
        array2["   ".length()] = (char)(0x5A ^ 0x79);
        array2[0x4B ^ 0x4F] = Items.wheat;
        craftingManager.addRecipe(itemStack2, array2);
        final ItemStack itemStack3 = new ItemStack(Items.rabbit_stew);
        final Object[] array3 = new Object[0x88 ^ 0x85];
        array3["".length()] = RecipesFood.I[" ".length()];
        array3[" ".length()] = RecipesFood.I["  ".length()];
        array3["  ".length()] = RecipesFood.I["   ".length()];
        array3["   ".length()] = (char)(0x31 ^ 0x63);
        array3[0x6E ^ 0x6A] = new ItemStack(Items.cooked_rabbit);
        array3[0x8 ^ 0xD] = (char)(0xFD ^ 0xBE);
        array3[0x3E ^ 0x38] = Items.carrot;
        array3[0x9F ^ 0x98] = (char)(0xD9 ^ 0x89);
        array3[0xA ^ 0x2] = Items.baked_potato;
        array3[0x4A ^ 0x43] = (char)(0xE3 ^ 0xAE);
        array3[0x9D ^ 0x97] = Blocks.brown_mushroom;
        array3[0x8D ^ 0x86] = (char)(0x7A ^ 0x38);
        array3[0x4A ^ 0x46] = Items.bowl;
        craftingManager.addRecipe(itemStack3, array3);
        final ItemStack itemStack4 = new ItemStack(Items.rabbit_stew);
        final Object[] array4 = new Object[0x7 ^ 0xA];
        array4["".length()] = RecipesFood.I[0xAB ^ 0xAF];
        array4[" ".length()] = RecipesFood.I[0x51 ^ 0x54];
        array4["  ".length()] = RecipesFood.I[0x32 ^ 0x34];
        array4["   ".length()] = (char)(0x25 ^ 0x77);
        array4[0x2F ^ 0x2B] = new ItemStack(Items.cooked_rabbit);
        array4[0x14 ^ 0x11] = (char)(0x2D ^ 0x6E);
        array4[0x4F ^ 0x49] = Items.carrot;
        array4[0x4B ^ 0x4C] = (char)(0x7D ^ 0x2D);
        array4[0x98 ^ 0x90] = Items.baked_potato;
        array4[0x2B ^ 0x22] = (char)(0x71 ^ 0x35);
        array4[0x74 ^ 0x7E] = Blocks.red_mushroom;
        array4[0x8E ^ 0x85] = (char)(0xF0 ^ 0xB2);
        array4[0x89 ^ 0x85] = Items.bowl;
        craftingManager.addRecipe(itemStack4, array4);
        final ItemStack itemStack5 = new ItemStack(Blocks.melon_block);
        final Object[] array5 = new Object[0x1 ^ 0x4];
        array5["".length()] = RecipesFood.I[0x70 ^ 0x77];
        array5[" ".length()] = RecipesFood.I[0x37 ^ 0x3F];
        array5["  ".length()] = RecipesFood.I[0x40 ^ 0x49];
        array5["   ".length()] = (char)(0x77 ^ 0x3A);
        array5[0x5B ^ 0x5F] = Items.melon;
        craftingManager.addRecipe(itemStack5, array5);
        final ItemStack itemStack6 = new ItemStack(Items.melon_seeds);
        final Object[] array6 = new Object["   ".length()];
        array6["".length()] = RecipesFood.I[0x98 ^ 0x92];
        array6[" ".length()] = (char)(0xFB ^ 0xB6);
        array6["  ".length()] = Items.melon;
        craftingManager.addRecipe(itemStack6, array6);
        final ItemStack itemStack7 = new ItemStack(Items.pumpkin_seeds, 0xBF ^ 0xBB);
        final Object[] array7 = new Object["   ".length()];
        array7["".length()] = RecipesFood.I[0xB3 ^ 0xB8];
        array7[" ".length()] = (char)(0x4 ^ 0x49);
        array7["  ".length()] = Blocks.pumpkin;
        craftingManager.addRecipe(itemStack7, array7);
        final ItemStack itemStack8 = new ItemStack(Items.pumpkin_pie);
        final Object[] array8 = new Object["   ".length()];
        array8["".length()] = Blocks.pumpkin;
        array8[" ".length()] = Items.sugar;
        array8["  ".length()] = Items.egg;
        craftingManager.addShapelessRecipe(itemStack8, array8);
        final ItemStack itemStack9 = new ItemStack(Items.fermented_spider_eye);
        final Object[] array9 = new Object["   ".length()];
        array9["".length()] = Items.spider_eye;
        array9[" ".length()] = Blocks.brown_mushroom;
        array9["  ".length()] = Items.sugar;
        craftingManager.addShapelessRecipe(itemStack9, array9);
        final ItemStack itemStack10 = new ItemStack(Items.blaze_powder, "  ".length());
        final Object[] array10 = new Object[" ".length()];
        array10["".length()] = Items.blaze_rod;
        craftingManager.addShapelessRecipe(itemStack10, array10);
        final ItemStack itemStack11 = new ItemStack(Items.magma_cream);
        final Object[] array11 = new Object["  ".length()];
        array11["".length()] = Items.blaze_powder;
        array11[" ".length()] = Items.slime_ball;
        craftingManager.addShapelessRecipe(itemStack11, array11);
    }
    
    private static void I() {
        (I = new String[0x10 ^ 0x1C])["".length()] = I("S\"N", "pzmPV");
        RecipesFood.I[" ".length()] = I("W\u0000u", "wRUKa");
        RecipesFood.I["  ".length()] = I("\u00144\u001b", "WdVmm");
        RecipesFood.I["   ".length()] = I("U\u0017S", "uUsfu");
        RecipesFood.I[0x61 ^ 0x65] = I("W\u0005M", "wWmOt");
        RecipesFood.I[0x3B ^ 0x3E] = I("\u000f$\u000e", "LtJpw");
        RecipesFood.I[0x99 ^ 0x9F] = I("P$t", "pfTKb");
        RecipesFood.I[0x91 ^ 0x96] = I("5\u001e'", "xSjKW");
        RecipesFood.I[0x6D ^ 0x65] = I("'+\u000e", "jfCsk");
        RecipesFood.I[0x35 ^ 0x3C] = I("/\b?", "bErNg");
        RecipesFood.I[0x86 ^ 0x8C] = I(";", "vjVih");
        RecipesFood.I[0x9E ^ 0x95] = I("\u0003", "NGhOj");
    }
    
    static {
        I();
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
