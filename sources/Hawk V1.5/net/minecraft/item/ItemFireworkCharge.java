package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.StatCollector;

public class ItemFireworkCharge extends Item {
   private static final String __OBFID = "CL_00000030";

   public static NBTBase func_150903_a(ItemStack var0, String var1) {
      if (var0.hasTagCompound()) {
         NBTTagCompound var2 = var0.getTagCompound().getCompoundTag("Explosion");
         if (var2 != null) {
            return var2.getTag(var1);
         }
      }

      return null;
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      if (var2 != 1) {
         return super.getColorFromItemStack(var1, var2);
      } else {
         NBTBase var3 = func_150903_a(var1, "Colors");
         if (!(var3 instanceof NBTTagIntArray)) {
            return 9079434;
         } else {
            NBTTagIntArray var4 = (NBTTagIntArray)var3;
            int[] var5 = var4.getIntArray();
            if (var5.length == 1) {
               return var5[0];
            } else {
               int var6 = 0;
               int var7 = 0;
               int var8 = 0;
               int[] var9 = var5;
               int var10 = var5.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  int var12 = var9[var11];
                  var6 += (var12 & 16711680) >> 16;
                  var7 += (var12 & '\uff00') >> 8;
                  var8 += (var12 & 255) >> 0;
               }

               var6 /= var5.length;
               var7 /= var5.length;
               var8 /= var5.length;
               return var6 << 16 | var7 << 8 | var8;
            }
         }
      }
   }

   public void addInformation(ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      if (var1.hasTagCompound()) {
         NBTTagCompound var5 = var1.getTagCompound().getCompoundTag("Explosion");
         if (var5 != null) {
            func_150902_a(var5, var3);
         }
      }

   }

   public static void func_150902_a(NBTTagCompound var0, List var1) {
      byte var2 = var0.getByte("Type");
      if (var2 >= 0 && var2 <= 4) {
         var1.add(StatCollector.translateToLocal(String.valueOf((new StringBuilder("item.fireworksCharge.type.")).append(var2))).trim());
      } else {
         var1.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
      }

      int[] var3 = var0.getIntArray("Colors");
      int var4;
      int var5;
      if (var3.length > 0) {
         boolean var6 = true;
         String var7 = "";
         int[] var8 = var3;
         int var9 = var3.length;

         for(var4 = 0; var4 < var9; ++var4) {
            var5 = var8[var4];
            if (!var6) {
               var7 = String.valueOf((new StringBuilder(String.valueOf(var7))).append(", "));
            }

            var6 = false;
            boolean var10 = false;

            for(int var11 = 0; var11 < ItemDye.dyeColors.length; ++var11) {
               if (var5 == ItemDye.dyeColors[var11]) {
                  var10 = true;
                  var7 = String.valueOf((new StringBuilder(String.valueOf(var7))).append(StatCollector.translateToLocal(String.valueOf((new StringBuilder("item.fireworksCharge.")).append(EnumDyeColor.func_176766_a(var11).func_176762_d())))));
                  break;
               }
            }

            if (!var10) {
               var7 = String.valueOf((new StringBuilder(String.valueOf(var7))).append(StatCollector.translateToLocal("item.fireworksCharge.customColor")));
            }
         }

         var1.add(var7);
      }

      int[] var13 = var0.getIntArray("FadeColors");
      boolean var15;
      if (var13.length > 0) {
         var15 = true;
         String var14 = String.valueOf((new StringBuilder(String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")))).append(" "));
         int[] var16 = var13;
         var4 = var13.length;

         for(var5 = 0; var5 < var4; ++var5) {
            int var18 = var16[var5];
            if (!var15) {
               var14 = String.valueOf((new StringBuilder(String.valueOf(var14))).append(", "));
            }

            var15 = false;
            boolean var19 = false;

            for(int var12 = 0; var12 < 16; ++var12) {
               if (var18 == ItemDye.dyeColors[var12]) {
                  var19 = true;
                  var14 = String.valueOf((new StringBuilder(String.valueOf(var14))).append(StatCollector.translateToLocal(String.valueOf((new StringBuilder("item.fireworksCharge.")).append(EnumDyeColor.func_176766_a(var12).func_176762_d())))));
                  break;
               }
            }

            if (!var19) {
               var14 = String.valueOf((new StringBuilder(String.valueOf(var14))).append(StatCollector.translateToLocal("item.fireworksCharge.customColor")));
            }
         }

         var1.add(var14);
      }

      var15 = var0.getBoolean("Trail");
      if (var15) {
         var1.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
      }

      boolean var17 = var0.getBoolean("Flicker");
      if (var17) {
         var1.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
      }

   }
}
