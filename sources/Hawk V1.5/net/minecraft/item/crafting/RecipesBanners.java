package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

public class RecipesBanners {
   private static final String __OBFID = "CL_00002160";

   void func_179534_a(CraftingManager var1) {
      EnumDyeColor[] var2 = EnumDyeColor.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnumDyeColor var5 = var2[var4];
         var1.addRecipe(new ItemStack(Items.banner, 1, var5.getDyeColorDamage()), "###", "###", " | ", '#', new ItemStack(Blocks.wool, 1, var5.func_176765_a()), '|', Items.stick);
      }

      var1.func_180302_a(new RecipesBanners.RecipeDuplicatePattern((Object)null));
      var1.func_180302_a(new RecipesBanners.RecipeAddPattern((Object)null));
   }

   static class RecipeAddPattern implements IRecipe {
      private static final String __OBFID = "CL_00002158";

      private RecipeAddPattern() {
      }

      public ItemStack getCraftingResult(InventoryCrafting var1) {
         ItemStack var2 = null;

         for(int var3 = 0; var3 < var1.getSizeInventory(); ++var3) {
            ItemStack var4 = var1.getStackInSlot(var3);
            if (var4 != null && var4.getItem() == Items.banner) {
               var2 = var4.copy();
               var2.stackSize = 1;
               break;
            }
         }

         TileEntityBanner.EnumBannerPattern var9 = this.func_179533_c(var1);
         if (var9 != null) {
            int var10 = 0;

            ItemStack var5;
            for(int var6 = 0; var6 < var1.getSizeInventory(); ++var6) {
               var5 = var1.getStackInSlot(var6);
               if (var5 != null && var5.getItem() == Items.dye) {
                  var10 = var5.getMetadata();
                  break;
               }
            }

            NBTTagCompound var11 = var2.getSubCompound("BlockEntityTag", true);
            var5 = null;
            NBTTagList var7;
            if (var11.hasKey("Patterns", 9)) {
               var7 = var11.getTagList("Patterns", 10);
            } else {
               var7 = new NBTTagList();
               var11.setTag("Patterns", var7);
            }

            NBTTagCompound var8 = new NBTTagCompound();
            var8.setString("Pattern", var9.func_177273_b());
            var8.setInteger("Color", var10);
            var7.appendTag(var8);
         }

         return var2;
      }

      public ItemStack getRecipeOutput() {
         return null;
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

      private TileEntityBanner.EnumBannerPattern func_179533_c(InventoryCrafting var1) {
         TileEntityBanner.EnumBannerPattern[] var2 = TileEntityBanner.EnumBannerPattern.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TileEntityBanner.EnumBannerPattern var5 = var2[var4];
            if (var5.func_177270_d()) {
               boolean var6 = true;
               int var7;
               if (var5.func_177269_e()) {
                  boolean var8 = false;
                  boolean var9 = false;

                  for(var7 = 0; var7 < var1.getSizeInventory() && var6; ++var7) {
                     ItemStack var10 = var1.getStackInSlot(var7);
                     if (var10 != null && var10.getItem() != Items.banner) {
                        if (var10.getItem() == Items.dye) {
                           if (var9) {
                              var6 = false;
                              break;
                           }

                           var9 = true;
                        } else {
                           if (var8 || !var10.isItemEqual(var5.func_177272_f())) {
                              var6 = false;
                              break;
                           }

                           var8 = true;
                        }
                     }
                  }

                  if (!var8) {
                     var6 = false;
                  }
               } else if (var1.getSizeInventory() != var5.func_177267_c().length * var5.func_177267_c()[0].length()) {
                  var6 = false;
               } else {
                  int var12 = -1;

                  for(int var13 = 0; var13 < var1.getSizeInventory() && var6; ++var13) {
                     var7 = var13 / 3;
                     int var14 = var13 % 3;
                     ItemStack var11 = var1.getStackInSlot(var13);
                     if (var11 != null && var11.getItem() != Items.banner) {
                        if (var11.getItem() != Items.dye) {
                           var6 = false;
                           break;
                        }

                        if (var12 != -1 && var12 != var11.getMetadata()) {
                           var6 = false;
                           break;
                        }

                        if (var5.func_177267_c()[var7].charAt(var14) == ' ') {
                           var6 = false;
                           break;
                        }

                        var12 = var11.getMetadata();
                     } else if (var5.func_177267_c()[var7].charAt(var14) != ' ') {
                        var6 = false;
                        break;
                     }
                  }
               }

               if (var6) {
                  return var5;
               }
            }
         }

         return null;
      }

      RecipeAddPattern(Object var1) {
         this();
      }

      public int getRecipeSize() {
         return 10;
      }

      public boolean matches(InventoryCrafting var1, World var2) {
         boolean var3 = false;

         for(int var4 = 0; var4 < var1.getSizeInventory(); ++var4) {
            ItemStack var5 = var1.getStackInSlot(var4);
            if (var5 != null && var5.getItem() == Items.banner) {
               if (var3) {
                  return false;
               }

               if (TileEntityBanner.func_175113_c(var5) >= 6) {
                  return false;
               }

               var3 = true;
            }
         }

         if (!var3) {
            return false;
         } else if (this.func_179533_c(var1) != null) {
            return true;
         } else {
            return false;
         }
      }
   }

   static class RecipeDuplicatePattern implements IRecipe {
      private static final String __OBFID = "CL_00002157";

      public boolean matches(InventoryCrafting var1, World var2) {
         ItemStack var3 = null;
         ItemStack var4 = null;

         for(int var5 = 0; var5 < var1.getSizeInventory(); ++var5) {
            ItemStack var6 = var1.getStackInSlot(var5);
            if (var6 != null) {
               if (var6.getItem() != Items.banner) {
                  return false;
               }

               if (var3 != null && var4 != null) {
                  return false;
               }

               int var7 = TileEntityBanner.getBaseColor(var6);
               boolean var8 = TileEntityBanner.func_175113_c(var6) > 0;
               if (var3 != null) {
                  if (var8) {
                     return false;
                  }

                  if (var7 != TileEntityBanner.getBaseColor(var3)) {
                     return false;
                  }

                  var4 = var6;
               } else if (var4 != null) {
                  if (!var8) {
                     return false;
                  }

                  if (var7 != TileEntityBanner.getBaseColor(var4)) {
                     return false;
                  }

                  var3 = var6;
               } else if (var8) {
                  var3 = var6;
               } else {
                  var4 = var6;
               }
            }
         }

         if (var3 != null && var4 != null) {
            return true;
         } else {
            return false;
         }
      }

      RecipeDuplicatePattern(Object var1) {
         this();
      }

      public int getRecipeSize() {
         return 2;
      }

      public ItemStack getCraftingResult(InventoryCrafting var1) {
         for(int var2 = 0; var2 < var1.getSizeInventory(); ++var2) {
            ItemStack var3 = var1.getStackInSlot(var2);
            if (var3 != null && TileEntityBanner.func_175113_c(var3) > 0) {
               ItemStack var4 = var3.copy();
               var4.stackSize = 1;
               return var4;
            }
         }

         return null;
      }

      private RecipeDuplicatePattern() {
      }

      public ItemStack getRecipeOutput() {
         return null;
      }

      public ItemStack[] func_179532_b(InventoryCrafting var1) {
         ItemStack[] var2 = new ItemStack[var1.getSizeInventory()];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            ItemStack var4 = var1.getStackInSlot(var3);
            if (var4 != null) {
               if (var4.getItem().hasContainerItem()) {
                  var2[var3] = new ItemStack(var4.getItem().getContainerItem());
               } else if (var4.hasTagCompound() && TileEntityBanner.func_175113_c(var4) > 0) {
                  var2[var3] = var4.copy();
                  var2[var3].stackSize = 1;
               }
            }
         }

         return var2;
      }
   }
}
