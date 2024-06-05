package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipe {
   ItemStack[] func_179532_b(InventoryCrafting var1);

   boolean matches(InventoryCrafting var1, World var2);

   int getRecipeSize();

   ItemStack getRecipeOutput();

   ItemStack getCraftingResult(InventoryCrafting var1);
}
