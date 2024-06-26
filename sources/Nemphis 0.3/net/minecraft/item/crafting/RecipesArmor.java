/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

public class RecipesArmor {
    private String[][] recipePatterns = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Item[][] recipeItems = new Item[][]{{Items.leather, Items.iron_ingot, Items.diamond, Items.gold_ingot}, {Items.leather_helmet, Items.iron_helmet, Items.diamond_helmet, Items.golden_helmet}, {Items.leather_chestplate, Items.iron_chestplate, Items.diamond_chestplate, Items.golden_chestplate}, {Items.leather_leggings, Items.iron_leggings, Items.diamond_leggings, Items.golden_leggings}, {Items.leather_boots, Items.iron_boots, Items.diamond_boots, Items.golden_boots}};
    private static final String __OBFID = "CL_00000080";

    public void addRecipes(CraftingManager p_77609_1_) {
        int var2 = 0;
        while (var2 < this.recipeItems[0].length) {
            Item var3 = this.recipeItems[0][var2];
            int var4 = 0;
            while (var4 < this.recipeItems.length - 1) {
                Item var5 = this.recipeItems[var4 + 1][var2];
                p_77609_1_.addRecipe(new ItemStack(var5), this.recipePatterns[var4], Character.valueOf('X'), var3);
                ++var4;
            }
            ++var2;
        }
    }
}

