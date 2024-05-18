package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;

public interface IRecipe
{
    ItemStack getRecipeOutput();
    
    boolean matches(final InventoryCrafting p0, final World p1);
    
    ItemStack getCraftingResult(final InventoryCrafting p0);
    
    int getRecipeSize();
    
    ItemStack[] getRemainingItems(final InventoryCrafting p0);
}
