package net.minecraft.item.crafting;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class RecipesDyes
{
    private static final String[] I;
    
    public void addRecipes(final CraftingManager craftingManager) {
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < (0x2E ^ 0x3E)) {
            final ItemStack itemStack = new ItemStack(Blocks.wool, " ".length(), i);
            final Object[] array = new Object["  ".length()];
            array["".length()] = new ItemStack(Items.dye, " ".length(), (0x7A ^ 0x75) - i);
            array[" ".length()] = new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), "".length());
            craftingManager.addShapelessRecipe(itemStack, array);
            final ItemStack itemStack2 = new ItemStack(Blocks.stained_hardened_clay, 0x30 ^ 0x38, (0xB7 ^ 0xB8) - i);
            final Object[] array2 = new Object[0x4A ^ 0x4D];
            array2["".length()] = RecipesDyes.I["".length()];
            array2[" ".length()] = RecipesDyes.I[" ".length()];
            array2["  ".length()] = RecipesDyes.I["  ".length()];
            array2["   ".length()] = (char)(0x38 ^ 0x1B);
            array2[0x16 ^ 0x12] = new ItemStack(Blocks.hardened_clay);
            array2[0xC2 ^ 0xC7] = (char)(0x53 ^ 0xB);
            array2[0x65 ^ 0x63] = new ItemStack(Items.dye, " ".length(), i);
            craftingManager.addRecipe(itemStack2, array2);
            final ItemStack itemStack3 = new ItemStack(Blocks.stained_glass, 0x90 ^ 0x98, (0x74 ^ 0x7B) - i);
            final Object[] array3 = new Object[0x2A ^ 0x2D];
            array3["".length()] = RecipesDyes.I["   ".length()];
            array3[" ".length()] = RecipesDyes.I[0x92 ^ 0x96];
            array3["  ".length()] = RecipesDyes.I[0x7B ^ 0x7E];
            array3["   ".length()] = (char)(0x78 ^ 0x5B);
            array3[0x7D ^ 0x79] = new ItemStack(Blocks.glass);
            array3[0xA ^ 0xF] = (char)(0x14 ^ 0x4C);
            array3[0xC0 ^ 0xC6] = new ItemStack(Items.dye, " ".length(), i);
            craftingManager.addRecipe(itemStack3, array3);
            final ItemStack itemStack4 = new ItemStack(Blocks.stained_glass_pane, 0xA9 ^ 0xB9, i);
            final Object[] array4 = new Object[0x98 ^ 0x9C];
            array4["".length()] = RecipesDyes.I[0x25 ^ 0x23];
            array4[" ".length()] = RecipesDyes.I[0x57 ^ 0x50];
            array4["  ".length()] = (char)(0x4A ^ 0x69);
            array4["   ".length()] = new ItemStack(Blocks.stained_glass, " ".length(), i);
            craftingManager.addRecipe(itemStack4, array4);
            ++i;
        }
        final ItemStack itemStack5 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.YELLOW.getDyeDamage());
        final Object[] array5 = new Object[" ".length()];
        array5["".length()] = new ItemStack(Blocks.yellow_flower, " ".length(), BlockFlower.EnumFlowerType.DANDELION.getMeta());
        craftingManager.addShapelessRecipe(itemStack5, array5);
        final ItemStack itemStack6 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        final Object[] array6 = new Object[" ".length()];
        array6["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.POPPY.getMeta());
        craftingManager.addShapelessRecipe(itemStack6, array6);
        final ItemStack itemStack7 = new ItemStack(Items.dye, "   ".length(), EnumDyeColor.WHITE.getDyeDamage());
        final Object[] array7 = new Object[" ".length()];
        array7["".length()] = Items.bone;
        craftingManager.addShapelessRecipe(itemStack7, array7);
        final ItemStack itemStack8 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.PINK.getDyeDamage());
        final Object[] array8 = new Object["  ".length()];
        array8["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        array8[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack8, array8);
        final ItemStack itemStack9 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.ORANGE.getDyeDamage());
        final Object[] array9 = new Object["  ".length()];
        array9["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        array9[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.YELLOW.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack9, array9);
        final ItemStack itemStack10 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.LIME.getDyeDamage());
        final Object[] array10 = new Object["  ".length()];
        array10["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.GREEN.getDyeDamage());
        array10[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack10, array10);
        final ItemStack itemStack11 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.GRAY.getDyeDamage());
        final Object[] array11 = new Object["  ".length()];
        array11["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLACK.getDyeDamage());
        array11[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack11, array11);
        final ItemStack itemStack12 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.SILVER.getDyeDamage());
        final Object[] array12 = new Object["  ".length()];
        array12["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.GRAY.getDyeDamage());
        array12[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack12, array12);
        final ItemStack itemStack13 = new ItemStack(Items.dye, "   ".length(), EnumDyeColor.SILVER.getDyeDamage());
        final Object[] array13 = new Object["   ".length()];
        array13["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLACK.getDyeDamage());
        array13[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        array13["  ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack13, array13);
        final ItemStack itemStack14 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.LIGHT_BLUE.getDyeDamage());
        final Object[] array14 = new Object["  ".length()];
        array14["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage());
        array14[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack14, array14);
        final ItemStack itemStack15 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.CYAN.getDyeDamage());
        final Object[] array15 = new Object["  ".length()];
        array15["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage());
        array15[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.GREEN.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack15, array15);
        final ItemStack itemStack16 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.PURPLE.getDyeDamage());
        final Object[] array16 = new Object["  ".length()];
        array16["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage());
        array16[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack16, array16);
        final ItemStack itemStack17 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.MAGENTA.getDyeDamage());
        final Object[] array17 = new Object["  ".length()];
        array17["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.PURPLE.getDyeDamage());
        array17[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.PINK.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack17, array17);
        final ItemStack itemStack18 = new ItemStack(Items.dye, "   ".length(), EnumDyeColor.MAGENTA.getDyeDamage());
        final Object[] array18 = new Object["   ".length()];
        array18["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage());
        array18[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        array18["  ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.PINK.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack18, array18);
        final ItemStack itemStack19 = new ItemStack(Items.dye, 0x58 ^ 0x5C, EnumDyeColor.MAGENTA.getDyeDamage());
        final Object[] array19 = new Object[0x3 ^ 0x7];
        array19["".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage());
        array19[" ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        array19["  ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        array19["   ".length()] = new ItemStack(Items.dye, " ".length(), EnumDyeColor.WHITE.getDyeDamage());
        craftingManager.addShapelessRecipe(itemStack19, array19);
        final ItemStack itemStack20 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.LIGHT_BLUE.getDyeDamage());
        final Object[] array20 = new Object[" ".length()];
        array20["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta());
        craftingManager.addShapelessRecipe(itemStack20, array20);
        final ItemStack itemStack21 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.MAGENTA.getDyeDamage());
        final Object[] array21 = new Object[" ".length()];
        array21["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.ALLIUM.getMeta());
        craftingManager.addShapelessRecipe(itemStack21, array21);
        final ItemStack itemStack22 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.SILVER.getDyeDamage());
        final Object[] array22 = new Object[" ".length()];
        array22["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.HOUSTONIA.getMeta());
        craftingManager.addShapelessRecipe(itemStack22, array22);
        final ItemStack itemStack23 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.RED.getDyeDamage());
        final Object[] array23 = new Object[" ".length()];
        array23["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.RED_TULIP.getMeta());
        craftingManager.addShapelessRecipe(itemStack23, array23);
        final ItemStack itemStack24 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.ORANGE.getDyeDamage());
        final Object[] array24 = new Object[" ".length()];
        array24["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta());
        craftingManager.addShapelessRecipe(itemStack24, array24);
        final ItemStack itemStack25 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.SILVER.getDyeDamage());
        final Object[] array25 = new Object[" ".length()];
        array25["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta());
        craftingManager.addShapelessRecipe(itemStack25, array25);
        final ItemStack itemStack26 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.PINK.getDyeDamage());
        final Object[] array26 = new Object[" ".length()];
        array26["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.PINK_TULIP.getMeta());
        craftingManager.addShapelessRecipe(itemStack26, array26);
        final ItemStack itemStack27 = new ItemStack(Items.dye, " ".length(), EnumDyeColor.SILVER.getDyeDamage());
        final Object[] array27 = new Object[" ".length()];
        array27["".length()] = new ItemStack(Blocks.red_flower, " ".length(), BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta());
        craftingManager.addShapelessRecipe(itemStack27, array27);
        final ItemStack itemStack28 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.YELLOW.getDyeDamage());
        final Object[] array28 = new Object[" ".length()];
        array28["".length()] = new ItemStack(Blocks.double_plant, " ".length(), BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta());
        craftingManager.addShapelessRecipe(itemStack28, array28);
        final ItemStack itemStack29 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.MAGENTA.getDyeDamage());
        final Object[] array29 = new Object[" ".length()];
        array29["".length()] = new ItemStack(Blocks.double_plant, " ".length(), BlockDoublePlant.EnumPlantType.SYRINGA.getMeta());
        craftingManager.addShapelessRecipe(itemStack29, array29);
        final ItemStack itemStack30 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.RED.getDyeDamage());
        final Object[] array30 = new Object[" ".length()];
        array30["".length()] = new ItemStack(Blocks.double_plant, " ".length(), BlockDoublePlant.EnumPlantType.ROSE.getMeta());
        craftingManager.addShapelessRecipe(itemStack30, array30);
        final ItemStack itemStack31 = new ItemStack(Items.dye, "  ".length(), EnumDyeColor.PINK.getDyeDamage());
        final Object[] array31 = new Object[" ".length()];
        array31["".length()] = new ItemStack(Blocks.double_plant, " ".length(), BlockDoublePlant.EnumPlantType.PAEONIA.getMeta());
        craftingManager.addShapelessRecipe(itemStack31, array31);
        int j = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (j < (0x6C ^ 0x7C)) {
            final ItemStack itemStack32 = new ItemStack(Blocks.carpet, "   ".length(), j);
            final Object[] array32 = new Object["   ".length()];
            array32["".length()] = RecipesDyes.I[0xB8 ^ 0xB0];
            array32[" ".length()] = (char)(0x88 ^ 0xAB);
            array32["  ".length()] = new ItemStack(Blocks.wool, " ".length(), j);
            craftingManager.addRecipe(itemStack32, array32);
            ++j;
        }
    }
    
    private static void I() {
        (I = new String[0x9D ^ 0x94])["".length()] = I("uBZ", "Vaywg");
        RecipesDyes.I[" ".length()] = I("t;S", "Wcpgn");
        RecipesDyes.I["  ".length()] = I("r[P", "QxsfW");
        RecipesDyes.I["   ".length()] = I("OIt", "ljWsS");
        RecipesDyes.I[0x55 ^ 0x51] = I("[\"r", "xzQCV");
        RecipesDyes.I[0x16 ^ 0x13] = I("Ghj", "dKIbQ");
        RecipesDyes.I[0x71 ^ 0x77] = I("fhm", "EKNVy");
        RecipesDyes.I[0x7B ^ 0x7C] = I("OlM", "lOnok");
        RecipesDyes.I[0x7D ^ 0x75] = I("YH", "zkPuy");
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
    
    static {
        I();
    }
}
