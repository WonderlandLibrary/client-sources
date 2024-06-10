package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IRecipe
{
  public abstract boolean matches(InventoryCrafting paramInventoryCrafting, World paramWorld);
  
  public abstract ItemStack getCraftingResult(InventoryCrafting paramInventoryCrafting);
  
  public abstract int getRecipeSize();
  
  public abstract ItemStack getRecipeOutput();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.crafting.IRecipe
 * JD-Core Version:    0.7.0.1
 */