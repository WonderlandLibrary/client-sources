package net.minecraft.item.crafting;

import java.util.*;

class CraftingManager$1 implements Comparator {
    public int compare(final IRecipe p_compare_1_, final IRecipe p_compare_2_) {
        return (p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes) ? 1 : ((p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes) ? -1 : ((p_compare_2_.getRecipeSize() < p_compare_1_.getRecipeSize()) ? -1 : ((p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize()) ? 1 : 0)));
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((IRecipe)p_compare_1_, (IRecipe)p_compare_2_);
    }
}