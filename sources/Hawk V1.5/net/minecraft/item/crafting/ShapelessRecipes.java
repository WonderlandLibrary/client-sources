package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShapelessRecipes implements IRecipe {
   private static final String __OBFID = "CL_00000094";
   private final List recipeItems;
   private final ItemStack recipeOutput;

   public ItemStack[] func_179532_b(InventoryCrafting var1) {
      ItemStack[] var2 = new ItemStack[var1.getSizeInventory()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ItemStack var4 = var1.getStackInSlot(var3);
         if (var4 != null && var4.getItem().hasContainerItem()) {
            var2[var3] = new ItemStack(var4.getItem().getContainerItem());
         }
      }

      return var2;
   }

   public boolean matches(InventoryCrafting var1, World var2) {
      ArrayList var3 = Lists.newArrayList(this.recipeItems);

      for(int var4 = 0; var4 < var1.func_174923_h(); ++var4) {
         for(int var5 = 0; var5 < var1.func_174922_i(); ++var5) {
            ItemStack var6 = var1.getStackInRowAndColumn(var5, var4);
            if (var6 != null) {
               boolean var7 = false;
               Iterator var8 = var3.iterator();

               while(var8.hasNext()) {
                  ItemStack var9 = (ItemStack)var8.next();
                  if (var6.getItem() == var9.getItem() && (var9.getMetadata() == 32767 || var6.getMetadata() == var9.getMetadata())) {
                     var7 = true;
                     var3.remove(var9);
                     break;
                  }
               }

               if (!var7) {
                  return false;
               }
            }
         }
      }

      return var3.isEmpty();
   }

   public ShapelessRecipes(ItemStack var1, List var2) {
      this.recipeOutput = var1;
      this.recipeItems = var2;
   }

   public int getRecipeSize() {
      return this.recipeItems.size();
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      return this.recipeOutput.copy();
   }

   public ItemStack getRecipeOutput() {
      return this.recipeOutput;
   }
}
