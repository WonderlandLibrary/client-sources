package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeBookCloning implements IRecipe {
   public boolean matches(InventoryCrafting var1, World var2) {
      int var3 = 0;
      Object var4 = null;

      for(int var5 = 0; var5 < var1.getSizeInventory(); ++var5) {
         ItemStack var6 = var1.getStackInSlot(var5);
         if(var6.getItem() == Items.written_book) {
            return false;
         }

         if(var6.getItem() != Items.writable_book) {
            return false;
         }

         ++var3;
      }

      return true;
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      int var2 = 0;
      Object var3 = null;

      for(int var4 = 0; var4 < var1.getSizeInventory(); ++var4) {
         ItemStack var5 = var1.getStackInSlot(var4);
         if(var5.getItem() == Items.written_book) {
            return null;
         }

         if(var5.getItem() != Items.writable_book) {
            return null;
         }

         ++var2;
      }

      if(var2 >= 1 && ItemEditableBook.getGeneration((ItemStack)var3) < 2) {
         ItemStack var6 = new ItemStack(Items.written_book, var2);
         var6.setTagCompound((NBTTagCompound)((ItemStack)var3).getTagCompound().copy());
         var6.getTagCompound().setInteger("generation", ItemEditableBook.getGeneration((ItemStack)var3) + 1);
         if(((ItemStack)var3).hasDisplayName()) {
            var6.setStackDisplayName(((ItemStack)var3).getDisplayName());
         }

         return var6;
      } else {
         return null;
      }
   }

   public int getRecipeSize() {
      return 9;
   }

   public ItemStack getRecipeOutput() {
      return null;
   }

   public ItemStack[] getRemainingItems(InventoryCrafting var1) {
      ItemStack[] var2 = new ItemStack[var1.getSizeInventory()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ItemStack var4 = var1.getStackInSlot(var3);
         if(var4.getItem() instanceof ItemEditableBook) {
            var2[var3] = var4;
            break;
         }
      }

      return var2;
   }
}
