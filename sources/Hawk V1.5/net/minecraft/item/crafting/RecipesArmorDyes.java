package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipesArmorDyes implements IRecipe {
   private static final String __OBFID = "CL_00000079";

   public int getRecipeSize() {
      return 10;
   }

   public boolean matches(InventoryCrafting var1, World var2) {
      ItemStack var3 = null;
      ArrayList var4 = Lists.newArrayList();

      for(int var5 = 0; var5 < var1.getSizeInventory(); ++var5) {
         ItemStack var6 = var1.getStackInSlot(var5);
         if (var6 != null) {
            if (var6.getItem() instanceof ItemArmor) {
               ItemArmor var7 = (ItemArmor)var6.getItem();
               if (var7.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || var3 != null) {
                  return false;
               }

               var3 = var6;
            } else {
               if (var6.getItem() != Items.dye) {
                  return false;
               }

               var4.add(var6);
            }
         }
      }

      if (var3 != null && !var4.isEmpty()) {
         return true;
      } else {
         return false;
      }
   }

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

   public ItemStack getRecipeOutput() {
      return null;
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      ItemStack var2 = null;
      int[] var3 = new int[3];
      int var4 = 0;
      int var5 = 0;
      ItemArmor var6 = null;

      int var7;
      int var8;
      float var9;
      float var10;
      int var11;
      for(var7 = 0; var7 < var1.getSizeInventory(); ++var7) {
         ItemStack var12 = var1.getStackInSlot(var7);
         if (var12 != null) {
            if (var12.getItem() instanceof ItemArmor) {
               var6 = (ItemArmor)var12.getItem();
               if (var6.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || var2 != null) {
                  return null;
               }

               var2 = var12.copy();
               var2.stackSize = 1;
               if (var6.hasColor(var12)) {
                  var8 = var6.getColor(var2);
                  var9 = (float)(var8 >> 16 & 255) / 255.0F;
                  var10 = (float)(var8 >> 8 & 255) / 255.0F;
                  float var13 = (float)(var8 & 255) / 255.0F;
                  var4 = (int)((float)var4 + Math.max(var9, Math.max(var10, var13)) * 255.0F);
                  var3[0] = (int)((float)var3[0] + var9 * 255.0F);
                  var3[1] = (int)((float)var3[1] + var10 * 255.0F);
                  var3[2] = (int)((float)var3[2] + var13 * 255.0F);
                  ++var5;
               }
            } else {
               if (var12.getItem() != Items.dye) {
                  return null;
               }

               float[] var17 = EntitySheep.func_175513_a(EnumDyeColor.func_176766_a(var12.getMetadata()));
               int var14 = (int)(var17[0] * 255.0F);
               int var15 = (int)(var17[1] * 255.0F);
               var11 = (int)(var17[2] * 255.0F);
               var4 += Math.max(var14, Math.max(var15, var11));
               var3[0] += var14;
               var3[1] += var15;
               var3[2] += var11;
               ++var5;
            }
         }
      }

      if (var6 == null) {
         return null;
      } else {
         var7 = var3[0] / var5;
         int var16 = var3[1] / var5;
         var8 = var3[2] / var5;
         var9 = (float)var4 / (float)var5;
         var10 = (float)Math.max(var7, Math.max(var16, var8));
         var7 = (int)((float)var7 * var9 / var10);
         var16 = (int)((float)var16 * var9 / var10);
         var8 = (int)((float)var8 * var9 / var10);
         var11 = (var7 << 8) + var16;
         var11 = (var11 << 8) + var8;
         var6.func_82813_b(var2, var11);
         return var2;
      }
   }
}
