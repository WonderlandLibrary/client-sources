package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class RecipeFireworks implements IRecipe {
   private ItemStack field_92102_a;

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
         if(var10.getItem() == Items.gunpowder) {
            ++var4;
         } else if(var10.getItem() == Items.firework_charge) {
            ++var6;
         } else if(var10.getItem() == Items.dye) {
            ++var5;
         } else if(var10.getItem() == Items.paper) {
            ++var3;
         } else if(var10.getItem() == Items.glowstone_dust) {
            ++var7;
         } else if(var10.getItem() == Items.diamond) {
            ++var7;
         } else if(var10.getItem() == Items.fire_charge) {
            ++var8;
         } else if(var10.getItem() == Items.feather) {
            ++var8;
         } else if(var10.getItem() == Items.gold_nugget) {
            ++var8;
         } else {
            if(var10.getItem() != Items.skull) {
               return false;
            }

            ++var8;
         }
      }

      var7 = var7 + var5 + var8;
      if(var4 <= 3 && var3 <= 1) {
         if(var4 >= 1 && var3 == 1) {
            this.field_92102_a = new ItemStack(Items.fireworks);
            NBTTagCompound var18 = new NBTTagCompound();
            NBTTagCompound var22 = new NBTTagCompound();
            NBTTagList var26 = new NBTTagList();

            for(int var27 = 0; var27 < var1.getSizeInventory(); ++var27) {
               ItemStack var29 = var1.getStackInSlot(var27);
               if(var29.getItem() == Items.firework_charge && var29.hasTagCompound() && var29.getTagCompound().hasKey("Explosion", 10)) {
                  var26.appendTag(var29.getTagCompound().getCompoundTag("Explosion"));
               }
            }

            var22.setTag("Explosions", var26);
            var22.setByte("Flight", (byte)var4);
            var18.setTag("Fireworks", var22);
            this.field_92102_a.setTagCompound(var18);
            return true;
         } else if(var4 == 1 && var8 <= 1) {
            this.field_92102_a = new ItemStack(Items.firework_charge);
            NBTTagCompound var17 = new NBTTagCompound();
            NBTTagCompound var21 = new NBTTagCompound();
            byte var25 = 0;
            ArrayList var12 = Lists.newArrayList();

            for(int var13 = 0; var13 < var1.getSizeInventory(); ++var13) {
               ItemStack var14 = var1.getStackInSlot(var13);
               if(var14.getItem() == Items.dye) {
                  var12.add(Integer.valueOf(ItemDye.dyeColors[var14.getMetadata() & 15]));
               } else if(var14.getItem() == Items.glowstone_dust) {
                  var21.setBoolean("Flicker", true);
               } else if(var14.getItem() == Items.diamond) {
                  var21.setBoolean("Trail", true);
               } else if(var14.getItem() == Items.fire_charge) {
                  var25 = 1;
               } else if(var14.getItem() == Items.feather) {
                  var25 = 4;
               } else if(var14.getItem() == Items.gold_nugget) {
                  var25 = 2;
               } else if(var14.getItem() == Items.skull) {
                  var25 = 3;
               }
            }

            int[] var28 = new int[var12.size()];

            for(int var30 = 0; var30 < var28.length; ++var30) {
               var28[var30] = ((Integer)var12.get(var30)).intValue();
            }

            var21.setIntArray("Colors", var28);
            var21.setByte("Type", var25);
            var17.setTag("Explosion", var21);
            this.field_92102_a.setTagCompound(var17);
            return true;
         } else if(var6 == 1 && var5 == var7) {
            ArrayList var16 = Lists.newArrayList();

            for(int var19 = 0; var19 < var1.getSizeInventory(); ++var19) {
               ItemStack var11 = var1.getStackInSlot(var19);
               if(var11.getItem() == Items.dye) {
                  var16.add(Integer.valueOf(ItemDye.dyeColors[var11.getMetadata() & 15]));
               } else if(var11.getItem() == Items.firework_charge) {
                  this.field_92102_a = var11.copy();
                  this.field_92102_a.stackSize = 1;
               }
            }

            int[] var20 = new int[var16.size()];

            for(int var23 = 0; var23 < var20.length; ++var23) {
               var20[var23] = ((Integer)var16.get(var23)).intValue();
            }

            if(this.field_92102_a != null && this.field_92102_a.hasTagCompound()) {
               NBTTagCompound var24 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
               return false;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public ItemStack getCraftingResult(InventoryCrafting var1) {
      return this.field_92102_a.copy();
   }

   public int getRecipeSize() {
      return 10;
   }

   public ItemStack getRecipeOutput() {
      return this.field_92102_a;
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
