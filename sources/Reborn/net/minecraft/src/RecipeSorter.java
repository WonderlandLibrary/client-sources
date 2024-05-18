package net.minecraft.src;

import java.util.*;

class RecipeSorter implements Comparator
{
    final CraftingManager craftingManager;
    
    RecipeSorter(final CraftingManager par1CraftingManager) {
        this.craftingManager = par1CraftingManager;
    }
    
    public int compareRecipes(final IRecipe par1IRecipe, final IRecipe par2IRecipe) {
        return (par1IRecipe instanceof ShapelessRecipes && par2IRecipe instanceof ShapedRecipes) ? 1 : ((par2IRecipe instanceof ShapelessRecipes && par1IRecipe instanceof ShapedRecipes) ? -1 : ((par2IRecipe.getRecipeSize() < par1IRecipe.getRecipeSize()) ? -1 : ((par2IRecipe.getRecipeSize() > par1IRecipe.getRecipeSize()) ? 1 : 0)));
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.compareRecipes((IRecipe)par1Obj, (IRecipe)par2Obj);
    }
}
