package net.minecraft.item.crafting;

import net.a_j;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

class RecipesBanners$RecipeDuplicatePattern implements IRecipe {
   private RecipesBanners$RecipeDuplicatePattern() {
   }

   public boolean matches(InventoryCrafting var1, World var2) {
      Object var3 = null;
      Object var4 = null;
      byte var5 = 0;
      if(var5 < var1.getSizeInventory()) {
         ItemStack var6 = var1.getStackInSlot(var5);
         return var6.getItem() != Items.banner?false:false;
      } else {
         return true;
      }
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      for(int var2 = 0; var2 < var1.getSizeInventory(); ++var2) {
         ItemStack var3 = var1.getStackInSlot(var2);
         if(TileEntityBanner.getPatterns(var3) > 0) {
            ItemStack var4 = var3.copy();
            var4.stackSize = 1;
            return var4;
         }
      }

      return null;
   }

   public int getRecipeSize() {
      return 2;
   }

   public ItemStack getRecipeOutput() {
      return null;
   }

   public ItemStack[] getRemainingItems(InventoryCrafting var1) {
      ItemStack[] var2 = new ItemStack[var1.getSizeInventory()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ItemStack var4 = var1.getStackInSlot(var3);
         if(var4.getItem().hasContainerItem()) {
            var2[var3] = new ItemStack(var4.getItem().getContainerItem());
         } else if(var4.hasTagCompound() && TileEntityBanner.getPatterns(var4) > 0) {
            var2[var3] = var4.copy();
            var2[var3].stackSize = 1;
         }
      }

      return var2;
   }

   RecipesBanners$RecipeDuplicatePattern(a_j var1) {
      this();
   }
}
