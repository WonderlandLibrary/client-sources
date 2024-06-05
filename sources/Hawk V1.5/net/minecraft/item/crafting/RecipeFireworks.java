package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecipeFireworks implements IRecipe {
   private ItemStack field_92102_a;
   private static final String __OBFID = "CL_00000083";

   public boolean matches(InventoryCrafting var1, World var2) {
      this.field_92102_a = null;
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;

      for(int var9 = 0; var9 < var1.getSizeInventory(); ++var9) {
         ItemStack var10 = var1.getStackInSlot(var9);
         if (var10 != null) {
            if (var10.getItem() == Items.gunpowder) {
               ++var4;
            } else if (var10.getItem() == Items.firework_charge) {
               ++var6;
            } else if (var10.getItem() == Items.dye) {
               ++var5;
            } else if (var10.getItem() == Items.paper) {
               ++var3;
            } else if (var10.getItem() == Items.glowstone_dust) {
               ++var7;
            } else if (var10.getItem() == Items.diamond) {
               ++var7;
            } else if (var10.getItem() == Items.fire_charge) {
               ++var8;
            } else if (var10.getItem() == Items.feather) {
               ++var8;
            } else if (var10.getItem() == Items.gold_nugget) {
               ++var8;
            } else {
               if (var10.getItem() != Items.skull) {
                  return false;
               }

               ++var8;
            }
         }
      }

      var7 += var5 + var8;
      if (var4 <= 3 && var3 <= 1) {
         int var12;
         ItemStack var13;
         NBTTagCompound var15;
         NBTTagCompound var16;
         if (var4 >= 1 && var3 == 1 && var7 == 0) {
            this.field_92102_a = new ItemStack(Items.fireworks);
            if (var6 > 0) {
               var15 = new NBTTagCompound();
               var16 = new NBTTagCompound();
               NBTTagList var19 = new NBTTagList();

               for(var12 = 0; var12 < var1.getSizeInventory(); ++var12) {
                  var13 = var1.getStackInSlot(var12);
                  if (var13 != null && var13.getItem() == Items.firework_charge && var13.hasTagCompound() && var13.getTagCompound().hasKey("Explosion", 10)) {
                     var19.appendTag(var13.getTagCompound().getCompoundTag("Explosion"));
                  }
               }

               var16.setTag("Explosions", var19);
               var16.setByte("Flight", (byte)var4);
               var15.setTag("Fireworks", var16);
               this.field_92102_a.setTagCompound(var15);
            }

            return true;
         } else {
            int var21;
            if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1) {
               this.field_92102_a = new ItemStack(Items.firework_charge);
               var15 = new NBTTagCompound();
               var16 = new NBTTagCompound();
               byte var17 = 0;
               ArrayList var20 = Lists.newArrayList();

               for(var21 = 0; var21 < var1.getSizeInventory(); ++var21) {
                  ItemStack var14 = var1.getStackInSlot(var21);
                  if (var14 != null) {
                     if (var14.getItem() == Items.dye) {
                        var20.add(ItemDye.dyeColors[var14.getMetadata() & 15]);
                     } else if (var14.getItem() == Items.glowstone_dust) {
                        var16.setBoolean("Flicker", true);
                     } else if (var14.getItem() == Items.diamond) {
                        var16.setBoolean("Trail", true);
                     } else if (var14.getItem() == Items.fire_charge) {
                        var17 = 1;
                     } else if (var14.getItem() == Items.feather) {
                        var17 = 4;
                     } else if (var14.getItem() == Items.gold_nugget) {
                        var17 = 2;
                     } else if (var14.getItem() == Items.skull) {
                        var17 = 3;
                     }
                  }
               }

               int[] var24 = new int[var20.size()];

               for(int var23 = 0; var23 < var24.length; ++var23) {
                  var24[var23] = (Integer)var20.get(var23);
               }

               var16.setIntArray("Colors", var24);
               var16.setByte("Type", var17);
               var15.setTag("Explosion", var16);
               this.field_92102_a.setTagCompound(var15);
               return true;
            } else if (var4 == 0 && var3 == 0 && var6 == 1 && var5 > 0 && var5 == var7) {
               ArrayList var11 = Lists.newArrayList();

               for(var12 = 0; var12 < var1.getSizeInventory(); ++var12) {
                  var13 = var1.getStackInSlot(var12);
                  if (var13 != null) {
                     if (var13.getItem() == Items.dye) {
                        var11.add(ItemDye.dyeColors[var13.getMetadata() & 15]);
                     } else if (var13.getItem() == Items.firework_charge) {
                        this.field_92102_a = var13.copy();
                        this.field_92102_a.stackSize = 1;
                     }
                  }
               }

               int[] var18 = new int[var11.size()];

               for(var21 = 0; var21 < var18.length; ++var21) {
                  var18[var21] = (Integer)var11.get(var21);
               }

               if (this.field_92102_a != null && this.field_92102_a.hasTagCompound()) {
                  NBTTagCompound var22 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
                  if (var22 == null) {
                     return false;
                  } else {
                     var22.setIntArray("FadeColors", var18);
                     return true;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
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

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      return this.field_92102_a.copy();
   }

   public ItemStack getRecipeOutput() {
      return this.field_92102_a;
   }

   public int getRecipeSize() {
      return 10;
   }
}
