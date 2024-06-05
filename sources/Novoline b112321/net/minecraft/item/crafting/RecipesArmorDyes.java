package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor$ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipesArmorDyes implements IRecipe {
   public boolean matches(InventoryCrafting var1, World var2) {
      Object var3 = null;
      ArrayList var4 = Lists.newArrayList();

      for(int var5 = 0; var5 < var1.getSizeInventory(); ++var5) {
         ItemStack var6 = var1.getStackInSlot(var5);
         if(var6.getItem() instanceof ItemArmor) {
            ItemArmor var7 = (ItemArmor)var6.getItem();
            if(var7.getArmorMaterial() == ItemArmor$ArmorMaterial.LEATHER) {
               ;
            }

            return false;
         }

         if(var6.getItem() != Items.dye) {
            return false;
         }

         var4.add(var6);
      }

      return !var4.isEmpty();
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      Object var2 = null;
      int[] var3 = new int[3];
      int var4 = 0;
      int var5 = 0;
      ItemArmor var6 = null;

      for(int var7 = 0; var7 < var1.getSizeInventory(); ++var7) {
         ItemStack var8 = var1.getStackInSlot(var7);
         if(var8.getItem() instanceof ItemArmor) {
            var6 = (ItemArmor)var8.getItem();
            if(var6.getArmorMaterial() == ItemArmor$ArmorMaterial.LEATHER) {
               ;
            }

            return null;
         }

         if(var8.getItem() != Items.dye) {
            return null;
         }

         float[] var9 = EntitySheep.func_175513_a(EnumDyeColor.byDyeDamage(var8.getMetadata()));
         int var10 = (int)(var9[0] * 255.0F);
         int var11 = (int)(var9[1] * 255.0F);
         int var12 = (int)(var9[2] * 255.0F);
         var4 += Math.max(var10, Math.max(var11, var12));
         var3[0] += var10;
         var3[1] += var11;
         var3[2] += var12;
         ++var5;
      }

      return null;
   }

   public int getRecipeSize() {
      return 10;
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
         }
      }

      return var2;
   }
}
