package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import optifine.Config;
import optifine.CustomColors;

public class PotionHelper {
   public static final String field_77924_a = null;
   public static final String redstoneEffect;
   private static final Map field_179540_p = Maps.newHashMap();
   public static final String magmaCreamEffect;
   public static final String glowstoneEffect;
   private static final Map field_179539_o = Maps.newHashMap();
   public static final String spiderEyeEffect;
   public static final String blazePowderEffect;
   public static final String goldenCarrotEffect;
   private static final String[] potionPrefixes;
   public static final String field_151423_m;
   public static final String field_179538_n;
   public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
   public static final String speckledMelonEffect;
   private static final Map field_77925_n;
   public static final String sugarEffect;
   public static final String gunpowderEffect;
   public static final String fermentedSpiderEyeEffect;

   public static boolean checkFlag(int var0, int var1) {
      return (var0 & 1 << var1) != 0;
   }

   private static int countSetFlags(int var0) {
      int var1;
      for(var1 = 0; var0 > 0; ++var1) {
         var0 &= var0 - 1;
      }

      return var1;
   }

   public static int func_77908_a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return (checkFlag(var0, var1) ? 16 : 0) | (checkFlag(var0, var2) ? 8 : 0) | (checkFlag(var0, var3) ? 4 : 0) | (checkFlag(var0, var4) ? 2 : 0) | (checkFlag(var0, var5) ? 1 : 0);
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
         if (var10 >= '0' && var10 <= '9') {
            var8 *= 10;
            var8 += var10 - 48;
            var4 = true;
         } else if (var10 == '!') {
            if (var4) {
               var0 = brewBitOperations(var0, var8, var6, var5, var7);
               var7 = false;
               var5 = false;
               var6 = false;
               var4 = false;
               var8 = 0;
            }

            var5 = true;
         } else if (var10 == '-') {
            if (var4) {
               var0 = brewBitOperations(var0, var8, var6, var5, var7);
               var7 = false;
               var5 = false;
               var6 = false;
               var4 = false;
               var8 = 0;
            }

            var6 = true;
         } else if (var10 == '+') {
            if (var4) {
               var0 = brewBitOperations(var0, var8, var6, var5, var7);
               var7 = false;
               var5 = false;
               var6 = false;
               var4 = false;
               var8 = 0;
            }
         } else if (var10 == '&') {
            if (var4) {
               var0 = brewBitOperations(var0, var8, var6, var5, var7);
               var7 = false;
               var5 = false;
               var6 = false;
               var4 = false;
               var8 = 0;
            }

            var7 = true;
         }
      }

      if (var4) {
         var0 = brewBitOperations(var0, var8, var6, var5, var7);
      }

      return var0 & 32767;
   }

   public static int calcPotionLiquidColor(Collection var0) {
      int var1 = 3694022;
      if (var0 != null && !var0.isEmpty()) {
         float var2 = 0.0F;
         float var3 = 0.0F;
         float var4 = 0.0F;
         float var5 = 0.0F;
         Iterator var6 = var0.iterator();

         while(true) {
            PotionEffect var7;
            do {
               if (!var6.hasNext()) {
                  if (var5 == 0.0F) {
                     return 0;
                  }

                  var2 = var2 / var5 * 255.0F;
                  var3 = var3 / var5 * 255.0F;
                  var4 = var4 / var5 * 255.0F;
                  return (int)var2 << 16 | (int)var3 << 8 | (int)var4;
               }

               var7 = (PotionEffect)var6.next();
            } while(!var7.func_180154_f());

            int var8 = Potion.potionTypes[var7.getPotionID()].getLiquidColor();
            if (Config.isCustomColors()) {
               var8 = CustomColors.getPotionColor(var7.getPotionID(), var8);
            }

            for(int var9 = 0; var9 <= var7.getAmplifier(); ++var9) {
               var2 += (float)(var8 >> 16 & 255) / 255.0F;
               var3 += (float)(var8 >> 8 & 255) / 255.0F;
               var4 += (float)(var8 >> 0 & 255) / 255.0F;
               ++var5;
            }
         }
      } else {
         if (Config.isCustomColors()) {
            var1 = CustomColors.getPotionColor(0, var1);
         }

         return var1;
      }
   }

   public static boolean func_82817_b(Collection var0) {
      Iterator var1 = var0.iterator();

      while(var1.hasNext()) {
         PotionEffect var2 = (PotionEffect)var1.next();
         if (!var2.getIsAmbient()) {
            return false;
         }
      }

      return true;
   }

   public static int func_77909_a(int var0) {
      return func_77908_a(var0, 5, 4, 3, 2, 1);
   }

   public static List getPotionEffects(int var0, boolean var1) {
      ArrayList var2 = null;
      Potion[] var3 = Potion.potionTypes;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Potion var6 = var3[var5];
         if (var6 != null && (!var6.isUsable() || var1)) {
            String var7 = (String)field_179539_o.get(var6.getId());
            if (var7 != null) {
               int var8 = parsePotionEffects(var7, 0, var7.length(), var0);
               if (var8 > 0) {
                  int var9 = 0;
                  String var10 = (String)field_179540_p.get(var6.getId());
                  if (var10 != null) {
                     var9 = parsePotionEffects(var10, 0, var10.length(), var0);
                     if (var9 < 0) {
                        var9 = 0;
                     }
                  }

                  if (var6.isInstant()) {
                     var8 = 1;
                  } else {
                     var8 = 1200 * (var8 * 3 + (var8 - 1) * 2);
                     var8 >>= var9;
                     var8 = (int)Math.round((double)var8 * var6.getEffectiveness());
                     if ((var0 & 16384) != 0) {
                        var8 = (int)Math.round((double)var8 * 0.75D + 0.5D);
                     }
                  }

                  if (var2 == null) {
                     var2 = Lists.newArrayList();
                  }

                  PotionEffect var11 = new PotionEffect(var6.getId(), var8, var9);
                  if ((var0 & 16384) != 0) {
                     var11.setSplashPotion(true);
                  }

                  var2.add(var11);
               }
            }
         }
      }

      return var2;
   }

   public static int func_77915_a(int var0, boolean var1) {
      if (!var1) {
         if (field_77925_n.containsKey(var0)) {
            return (Integer)field_77925_n.get(var0);
         } else {
            int var2 = calcPotionLiquidColor(getPotionEffects(var0, false));
            field_77925_n.put(var0, var2);
            return var2;
         }
      } else {
         return calcPotionLiquidColor(getPotionEffects(var0, true));
      }
   }

   private static int func_77904_a(boolean var0, boolean var1, boolean var2, int var3, int var4, int var5, int var6) {
      int var7 = 0;
      if (var0) {
         var7 = isFlagUnset(var6, var4);
      } else if (var3 != -1) {
         if (var3 == 0 && countSetFlags(var6) == var4) {
            var7 = 1;
         } else if (var3 == 1 && countSetFlags(var6) > var4) {
            var7 = 1;
         } else if (var3 == 2 && countSetFlags(var6) < var4) {
            var7 = 1;
         }
      } else {
         var7 = isFlagSet(var6, var4);
      }

      if (var1) {
         var7 *= var5;
      }

      if (var2) {
         var7 *= -1;
      }

      return var7;
   }

   private static int parsePotionEffects(String var0, int var1, int var2, int var3) {
      if (var1 < var0.length() && var2 >= 0 && var1 < var2) {
         int var4 = var0.indexOf(124, var1);
         int var5;
         int var6;
         if (var4 >= 0 && var4 < var2) {
            var5 = parsePotionEffects(var0, var1, var4 - 1, var3);
            if (var5 > 0) {
               return var5;
            } else {
               var6 = parsePotionEffects(var0, var4 + 1, var2, var3);
               return var6 > 0 ? var6 : 0;
            }
         } else {
            var5 = var0.indexOf(38, var1);
            if (var5 >= 0 && var5 < var2) {
               var6 = parsePotionEffects(var0, var1, var5 - 1, var3);
               if (var6 <= 0) {
                  return 0;
               } else {
                  int var18 = parsePotionEffects(var0, var5 + 1, var2, var3);
                  return var18 <= 0 ? 0 : (var6 > var18 ? var6 : var18);
               }
            } else {
               boolean var7 = false;
               boolean var8 = false;
               boolean var9 = false;
               boolean var10 = false;
               boolean var11 = false;
               byte var12 = -1;
               int var13 = 0;
               int var14 = 0;
               int var15 = 0;

               for(int var16 = var1; var16 < var2; ++var16) {
                  char var17 = var0.charAt(var16);
                  if (var17 >= '0' && var17 <= '9') {
                     if (var7) {
                        var14 = var17 - 48;
                        var8 = true;
                     } else {
                        var13 *= 10;
                        var13 += var17 - 48;
                        var9 = true;
                     }
                  } else if (var17 == '*') {
                     var7 = true;
                  } else if (var17 == '!') {
                     if (var9) {
                        var15 += func_77904_a(var10, var8, var11, var12, var13, var14, var3);
                        var10 = false;
                        var11 = false;
                        var7 = false;
                        var8 = false;
                        var9 = false;
                        var14 = 0;
                        var13 = 0;
                        var12 = -1;
                     }

                     var10 = true;
                  } else if (var17 == '-') {
                     if (var9) {
                        var15 += func_77904_a(var10, var8, var11, var12, var13, var14, var3);
                        var10 = false;
                        var11 = false;
                        var7 = false;
                        var8 = false;
                        var9 = false;
                        var14 = 0;
                        var13 = 0;
                        var12 = -1;
                     }

                     var11 = true;
                  } else if (var17 != '=' && var17 != '<' && var17 != '>') {
                     if (var17 == '+' && var9) {
                        var15 += func_77904_a(var10, var8, var11, var12, var13, var14, var3);
                        var10 = false;
                        var11 = false;
                        var7 = false;
                        var8 = false;
                        var9 = false;
                        var14 = 0;
                        var13 = 0;
                        var12 = -1;
                     }
                  } else {
                     if (var9) {
                        var15 += func_77904_a(var10, var8, var11, var12, var13, var14, var3);
                        var10 = false;
                        var11 = false;
                        var7 = false;
                        var8 = false;
                        var9 = false;
                        var14 = 0;
                        var13 = 0;
                        var12 = -1;
                     }

                     if (var17 == '=') {
                        var12 = 0;
                     } else if (var17 == '<') {
                        var12 = 2;
                     } else if (var17 == '>') {
                        var12 = 1;
                     }
                  }
               }

               if (var9) {
                  var15 += func_77904_a(var10, var8, var11, var12, var13, var14, var3);
               }

               return var15;
            }
         }
      } else {
         return 0;
      }
   }

   private static int isFlagSet(int var0, int var1) {
      return checkFlag(var0, var1) ? 1 : 0;
   }

   private static int brewBitOperations(int var0, int var1, boolean var2, boolean var3, boolean var4) {
      if (var4) {
         if (!checkFlag(var0, var1)) {
            return 0;
         }
      } else if (var2) {
         var0 &= ~(1 << var1);
      } else if (var3) {
         if ((var0 & 1 << var1) == 0) {
            var0 |= 1 << var1;
         } else {
            var0 &= ~(1 << var1);
         }
      } else {
         var0 |= 1 << var1;
      }

      return var0;
   }

   public static void clearPotionColorCache() {
      field_77925_n.clear();
   }

   private static int isFlagUnset(int var0, int var1) {
      return checkFlag(var0, var1) ? 0 : 1;
   }

   static {
      field_179539_o.put(Potion.regeneration.getId(), "0 & !1 & !2 & !3 & 0+6");
      sugarEffect = "-0+1-2-3&4-4+13";
      field_179539_o.put(Potion.moveSpeed.getId(), "!0 & 1 & !2 & !3 & 1+6");
      magmaCreamEffect = "+0+1-2-3&4-4+13";
      field_179539_o.put(Potion.fireResistance.getId(), "0 & 1 & !2 & !3 & 0+6");
      speckledMelonEffect = "+0-1+2-3&4-4+13";
      field_179539_o.put(Potion.heal.getId(), "0 & !1 & 2 & !3");
      spiderEyeEffect = "-0-1+2-3&4-4+13";
      field_179539_o.put(Potion.poison.getId(), "!0 & !1 & 2 & !3 & 2+6");
      fermentedSpiderEyeEffect = "-0+3-4+13";
      field_179539_o.put(Potion.weakness.getId(), "!0 & !1 & !2 & 3 & 3+6");
      field_179539_o.put(Potion.harm.getId(), "!0 & !1 & 2 & 3");
      field_179539_o.put(Potion.moveSlowdown.getId(), "!0 & 1 & !2 & 3 & 3+6");
      blazePowderEffect = "+0-1-2+3&4-4+13";
      field_179539_o.put(Potion.damageBoost.getId(), "0 & !1 & !2 & 3 & 3+6");
      goldenCarrotEffect = "-0+1+2-3+13&4-4";
      field_179539_o.put(Potion.nightVision.getId(), "!0 & 1 & 2 & !3 & 2+6");
      field_179539_o.put(Potion.invisibility.getId(), "!0 & 1 & 2 & 3 & 2+6");
      field_151423_m = "+0-1+2+3+13&4-4";
      field_179539_o.put(Potion.waterBreathing.getId(), "0 & !1 & 2 & 3 & 2+6");
      field_179538_n = "+0+1-2+3&4-4+13";
      field_179539_o.put(Potion.jump.getId(), "0 & 1 & !2 & 3");
      glowstoneEffect = "+5-6-7";
      field_179540_p.put(Potion.moveSpeed.getId(), "5");
      field_179540_p.put(Potion.digSpeed.getId(), "5");
      field_179540_p.put(Potion.damageBoost.getId(), "5");
      field_179540_p.put(Potion.regeneration.getId(), "5");
      field_179540_p.put(Potion.harm.getId(), "5");
      field_179540_p.put(Potion.heal.getId(), "5");
      field_179540_p.put(Potion.resistance.getId(), "5");
      field_179540_p.put(Potion.poison.getId(), "5");
      field_179540_p.put(Potion.jump.getId(), "5");
      redstoneEffect = "-5+6-7";
      gunpowderEffect = "+14&13-13";
      field_77925_n = Maps.newHashMap();
      potionPrefixes = new String[]{"potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky"};
   }

   public static String func_77905_c(int var0) {
      int var1 = func_77909_a(var0);
      return potionPrefixes[var1];
   }
}
