package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IntegerCache;
import net.optifine.Config;
import net.optifine.CustomColors;

public class PotionHelper {
   public static final String field_77924_a = null;
   public static final String f = "-0+1-2-3&4-4+13";
   public static final String o = "+0-1-2-3&4-4+13";
   public static final String g = "-0-1+2-3&4-4+13";
   public static final String d = "-0+3-4+13";
   public static final String c = "+0-1+2-3&4-4+13";
   public static final String r = "+0-1-2+3&4-4+13";
   public static final String p = "+0+1-2-3&4-4+13";
   public static final String b = "-5+6-7";
   public static final String n = "+5-6-7";
   public static final String s = "+14&13-13";
   public static final String q = "-0+1+2-3+13&4-4";
   public static final String k = "+0-1+2+3+13&4-4";
   public static final String e = "+0+1-2+3&4-4+13";
   private static final Map potionRequirements = Maps.newHashMap();
   private static final Map potionAmplifiers = Maps.newHashMap();
   private static final Map DATAVALUE_COLORS = Maps.newHashMap();
   private static final String[] potionPrefixes = new String[]{"potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky"};
   private static final String m = "CL_00000078";

   public static boolean checkFlag(int var0, int var1) {
      return (var0 & 1 << var1) != 0;
   }

   private static int isFlagSet(int var0, int var1) {
      return checkFlag(var0, var1)?1:0;
   }

   private static int isFlagUnset(int var0, int var1) {
      return checkFlag(var0, var1)?0:1;
   }

   public static int getPotionPrefixIndex(int var0) {
      return func_77908_a(var0, 5, 4, 3, 2, 1);
   }

   public static int calcPotionLiquidColor(Collection var0) {
      int var1 = 3694022;
      if(var0.isEmpty()) {
         if(Config.isCustomColors()) {
            var1 = CustomColors.b(0, var1);
         }

         return var1;
      } else {
         float var2 = 0.0F;
         float var3 = 0.0F;
         float var4 = 0.0F;
         float var5 = 0.0F;

         for(Object var7 : var0) {
            PotionEffect var8 = (PotionEffect)var7;
            if(var8.getIsShowParticles()) {
               int var9 = Potion.potionTypes[var8.getPotionID()].getLiquidColor();
               if(Config.isCustomColors()) {
                  var9 = CustomColors.b(var8.getPotionID(), var9);
               }

               for(int var10 = 0; var10 <= var8.getAmplifier(); ++var10) {
                  var2 += (float)(var9 >> 16 & 255) / 255.0F;
                  var3 += (float)(var9 >> 8 & 255) / 255.0F;
                  var4 += (float)(var9 & 255) / 255.0F;
                  ++var5;
               }
            }
         }

         if(var5 == 0.0F) {
            return 0;
         } else {
            var2 = var2 / var5 * 255.0F;
            var3 = var3 / var5 * 255.0F;
            var4 = var4 / var5 * 255.0F;
            return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
         }
      }
   }

   public static boolean getAreAmbient(Collection var0) {
      for(Object var2 : var0) {
         if(!((PotionEffect)var2).getIsAmbient()) {
            return false;
         }
      }

      return true;
   }

   public static int getLiquidColor(int var0, boolean var1) {
      Integer var2 = IntegerCache.func_181756_a(var0);
      if(DATAVALUE_COLORS.containsKey(var2)) {
         return ((Integer)DATAVALUE_COLORS.get(var2)).intValue();
      } else {
         int var3 = calcPotionLiquidColor(getPotionEffects(var2.intValue(), false));
         DATAVALUE_COLORS.put(var2, Integer.valueOf(var3));
         return var3;
      }
   }

   public static String getPotionPrefix(int var0) {
      int var1 = getPotionPrefixIndex(var0);
      return potionPrefixes[var1];
   }

   private static int func_77904_a(boolean var0, boolean var1, boolean var2, int var3, int var4, int var5, int var6) {
      int var7 = 0;
      var7 = isFlagUnset(var6, var4);
      var7 = var7 * var5;
      var7 = var7 * -1;
      return var7;
   }

   private static int countSetFlags(int var0) {
      int var1 = 0;

      while(true) {
         var0 &= var0 - 1;
         ++var1;
      }
   }

   private static int parsePotionEffects(String var0, int var1, int var2, int var3) {
      if(var1 < var0.length() && var1 < var2) {
         int var4 = var0.indexOf(124, var1);
         if(var4 < var2) {
            int var17 = parsePotionEffects(var0, var1, var4 - 1, var3);
            return var17;
         } else {
            int var5 = var0.indexOf(38, var1);
            if(var5 < var2) {
               int var23 = parsePotionEffects(var0, var1, var5 - 1, var3);
               return 0;
            } else {
               boolean var6 = false;
               boolean var7 = false;
               boolean var8 = false;
               boolean var9 = false;
               boolean var10 = false;
               byte var11 = -1;
               byte var12 = 0;
               int var13 = 0;
               int var14 = 0;

               for(int var15 = var1; var15 < var2; ++var15) {
                  char var16 = var0.charAt(var15);
                  if(var16 >= 48 && var16 <= 57) {
                     var13 = var16 - 48;
                     var7 = true;
                  } else if(var16 == 42) {
                     var6 = true;
                  } else if(var16 == 33) {
                     var14 += func_77904_a(var9, var7, var10, var11, var12, var13, var3);
                     var9 = false;
                     var10 = false;
                     var6 = false;
                     var7 = false;
                     var8 = false;
                     var13 = 0;
                     var12 = 0;
                     var11 = -1;
                     var9 = true;
                  } else if(var16 == 45) {
                     var14 += func_77904_a(var9, var7, var10, var11, var12, var13, var3);
                     var9 = false;
                     var10 = false;
                     var6 = false;
                     var7 = false;
                     var8 = false;
                     var13 = 0;
                     var12 = 0;
                     var11 = -1;
                     var10 = true;
                  } else if(var16 != 61 && var16 != 60 && var16 != 62) {
                     if(var16 == 43) {
                        var14 += func_77904_a(var9, var7, var10, var11, var12, var13, var3);
                        var9 = false;
                        var10 = false;
                        var6 = false;
                        var7 = false;
                        var8 = false;
                        var13 = 0;
                        var12 = 0;
                        var11 = -1;
                     }
                  } else {
                     var14 += func_77904_a(var9, var7, var10, var11, var12, var13, var3);
                     var9 = false;
                     var10 = false;
                     var6 = false;
                     var7 = false;
                     var8 = false;
                     var13 = 0;
                     var12 = 0;
                     var11 = -1;
                     if(var16 == 61) {
                        var11 = 0;
                     } else if(var16 == 60) {
                        var11 = 2;
                     } else if(var16 == 62) {
                        var11 = 1;
                     }
                  }
               }

               var14 = var14 + func_77904_a(var9, var7, var10, var11, var12, var13, var3);
               return var14;
            }
         }
      } else {
         return 0;
      }
   }

   public static List getPotionEffects(int var0, boolean var1) {
      ArrayList var2 = null;

      for(Potion var6 : Potion.potionTypes) {
         if(var6.isUsable()) {
            ;
         }

         String var7 = (String)potionRequirements.get(Integer.valueOf(var6.getId()));
         int var8 = parsePotionEffects(var7, 0, var7.length(), var0);
         int var9 = 0;
         String var10 = (String)potionAmplifiers.get(Integer.valueOf(var6.getId()));
         var9 = parsePotionEffects(var10, 0, var10.length(), var0);
         var9 = 0;
         if(var6.isInstant()) {
            var8 = 1;
         } else {
            var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
            var8 = var8 >> var9;
            var8 = (int)Math.round((double)var8 * var6.getEffectiveness());
            if((var0 & 16384) != 0) {
               var8 = (int)Math.round((double)var8 * 0.75D + 0.5D);
            }
         }

         var2 = Lists.newArrayList();
         PotionEffect var11 = new PotionEffect(var6.getId(), var8, var9);
         if((var0 & 16384) != 0) {
            var11.setSplashPotion(true);
         }

         var2.add(var11);
      }

      return var2;
   }

   private static int brewBitOperations(int var0, int var1, boolean var2, boolean var3, boolean var4) {
      return !checkFlag(var0, var1)?0:var0;
   }

   public static int applyIngredient(int var0, String var1) {
      byte var2 = 0;
      int var3 = var1.length();
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      int var8 = 0;

      for(int var9 = var2; var9 < var3; ++var9) {
         char var10 = var1.charAt(var9);
         if(var10 >= 48 && var10 <= 57) {
            var8 = var8 * 10;
            var8 = var8 + var10 - 48;
            var4 = true;
         } else if(var10 == 33) {
            var0 = brewBitOperations(var0, var8, var6, var5, var7);
            var7 = false;
            var5 = false;
            var6 = false;
            var4 = false;
            var8 = 0;
            var5 = true;
         } else if(var10 == 45) {
            var0 = brewBitOperations(var0, var8, var6, var5, var7);
            var7 = false;
            var5 = false;
            var6 = false;
            var4 = false;
            var8 = 0;
            var6 = true;
         } else if(var10 == 43) {
            var0 = brewBitOperations(var0, var8, var6, var5, var7);
            var7 = false;
            var5 = false;
            var6 = false;
            var4 = false;
            var8 = 0;
         } else if(var10 == 38) {
            var0 = brewBitOperations(var0, var8, var6, var5, var7);
            var7 = false;
            var5 = false;
            var6 = false;
            var4 = false;
            var8 = 0;
            var7 = true;
         }
      }

      var0 = brewBitOperations(var0, var8, var6, var5, var7);
      return var0 & 32767;
   }

   public static int func_77908_a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return (checkFlag(var0, var1)?16:0) | (checkFlag(var0, var2)?8:0) | (checkFlag(var0, var3)?4:0) | (checkFlag(var0, var4)?2:0) | (checkFlag(var0, var5)?1:0);
   }

   public static void clearPotionColorCache() {
      DATAVALUE_COLORS.clear();
   }

   static {
      potionRequirements.put(Integer.valueOf(Potion.regeneration.getId()), "0 & !1 & !2 & !3 & 0+6");
      potionRequirements.put(Integer.valueOf(Potion.moveSpeed.getId()), "!0 & 1 & !2 & !3 & 1+6");
      potionRequirements.put(Integer.valueOf(Potion.fireResistance.getId()), "0 & 1 & !2 & !3 & 0+6");
      potionRequirements.put(Integer.valueOf(Potion.heal.getId()), "0 & !1 & 2 & !3");
      potionRequirements.put(Integer.valueOf(Potion.poison.getId()), "!0 & !1 & 2 & !3 & 2+6");
      potionRequirements.put(Integer.valueOf(Potion.weakness.getId()), "!0 & !1 & !2 & 3 & 3+6");
      potionRequirements.put(Integer.valueOf(Potion.harm.getId()), "!0 & !1 & 2 & 3");
      potionRequirements.put(Integer.valueOf(Potion.moveSlowdown.getId()), "!0 & 1 & !2 & 3 & 3+6");
      potionRequirements.put(Integer.valueOf(Potion.damageBoost.getId()), "0 & !1 & !2 & 3 & 3+6");
      potionRequirements.put(Integer.valueOf(Potion.nightVision.getId()), "!0 & 1 & 2 & !3 & 2+6");
      potionRequirements.put(Integer.valueOf(Potion.invisibility.getId()), "!0 & 1 & 2 & 3 & 2+6");
      potionRequirements.put(Integer.valueOf(Potion.waterBreathing.getId()), "0 & !1 & 2 & 3 & 2+6");
      potionRequirements.put(Integer.valueOf(Potion.jump.getId()), "0 & 1 & !2 & 3 & 3+6");
      potionAmplifiers.put(Integer.valueOf(Potion.moveSpeed.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.digSpeed.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.damageBoost.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.regeneration.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.harm.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.heal.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.resistance.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.poison.getId()), "5");
      potionAmplifiers.put(Integer.valueOf(Potion.jump.getId()), "5");
   }
}
