package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge extends Item {
   public int getColorFromItemStack(ItemStack var1, int var2) {
      if(var2 != 1) {
         return super.getColorFromItemStack(var1, var2);
      } else {
         NBTBase var3 = getExplosionTag(var1, "Colors");
         if(!(var3 instanceof NBTTagIntArray)) {
            return 9079434;
         } else {
            NBTTagIntArray var4 = (NBTTagIntArray)var3;
            int[] var5 = var4.getIntArray();
            if(var5.length == 1) {
               return var5[0];
            } else {
               int var6 = 0;
               int var7 = 0;
               int var8 = 0;

               for(int var12 : var5) {
                  var6 += (var12 & 16711680) >> 16;
                  var7 += (var12 & '\uff00') >> 8;
                  var8 += var12 & 255;
               }

               var6 = var6 / var5.length;
               var7 = var7 / var5.length;
               var8 = var8 / var5.length;
               return var6 << 16 | var7 << 8 | var8;
            }
         }
      }
   }

   public static NBTBase getExplosionTag(ItemStack var0, String var1) {
      if(var0.hasTagCompound()) {
         NBTTagCompound var2 = var0.getTagCompound().getCompoundTag("Explosion");
         return var2.getTag(var1);
      } else {
         return null;
      }
   }

   public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      if(var1.hasTagCompound()) {
         NBTTagCompound var5 = var1.getTagCompound().getCompoundTag("Explosion");
         addExplosionInfo(var5, var3);
      }

   }

   public static void addExplosionInfo(NBTTagCompound var0, List var1) {
      byte var2 = var0.getByte("Type");
      if(var2 <= 4) {
         var1.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
      } else {
         var1.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
      }

      int[] var3 = var0.getIntArray("Colors");
      if(var3.length > 0) {
         boolean var4 = true;
         String var5 = "";

         for(int var9 : var3) {
            var5 = var5 + ", ";
            var4 = false;
            boolean var10 = false;

            for(int var11 = 0; var11 < ItemDye.dyeColors.length; ++var11) {
               if(var9 == ItemDye.dyeColors[var11]) {
                  var10 = true;
                  var5 = var5 + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(var11).getUnlocalizedName());
                  break;
               }
            }

            var5 = var5 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
         }

         var1.add(var5);
      }

      int[] var14 = var0.getIntArray("FadeColors");
      if(var14.length > 0) {
         boolean var16 = true;
         String var19 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";

         for(int var26 : var14) {
            var19 = var19 + ", ";
            var16 = false;
            boolean var27 = false;

            for(int var12 = 0; var12 < 16; ++var12) {
               if(var26 == ItemDye.dyeColors[var12]) {
                  var27 = true;
                  var19 = var19 + StatCollector.translateToLocal("item.fireworksCharge." + EnumDyeColor.byDyeDamage(var12).getUnlocalizedName());
                  break;
               }
            }

            var19 = var19 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
         }

         var1.add(var19);
      }

      boolean var18 = var0.getBoolean("Trail");
      var1.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
      boolean var21 = var0.getBoolean("Flicker");
      var1.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
   }
}
