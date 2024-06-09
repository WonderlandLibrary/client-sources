package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipe {
  boolean matches(InventoryCrafting paramInventoryCrafting, World paramWorld);
  
  ItemStack getCraftingResult(InventoryCrafting paramInventoryCrafting);
  
  int getRecipeSize();
  
  ItemStack getRecipeOutput();
  
  ItemStack[] getRemainingItems(InventoryCrafting paramInventoryCrafting);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\crafting\IRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */