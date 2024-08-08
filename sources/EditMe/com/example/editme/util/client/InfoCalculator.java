package com.example.editme.util.client;

import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class InfoCalculator {
   private static DecimalFormat formatter = new DecimalFormat("#.#");

   public static String tps() {
      return String.valueOf((new StringBuilder()).append("").append(Math.round(LagCompensator.INSTANCE.getTickRate())));
   }

   public static String speed(boolean var0, Minecraft var1, int var2) {
      float var3 = var1.field_71428_T.field_194149_e / 1000.0F;
      double var4 = 1.0D;
      if (var0) {
         var4 = 3.6D;
      }

      return String.valueOf((new StringBuilder()).append("").append(MathUtil.round((double)(MathHelper.func_76133_a(Math.pow(coordsDiff('x', var1), 2.0D) + Math.pow(coordsDiff('z', var1), 2.0D)) / var3) * var4, var2)));
   }

   public static String memory() {
      return String.valueOf((new StringBuilder()).append("").append(Runtime.getRuntime().freeMemory() / 1000000L));
   }

   public static String cardinalToAxis(char var0) {
      switch(var0) {
      case 'E':
         return "+X";
      case 'N':
         return "-Z";
      case 'S':
         return "+Z";
      case 'W':
         return "-X";
      default:
         return "invalid";
      }
   }

   private static double coordsDiff(char var0, Minecraft var1) {
      switch(var0) {
      case 'x':
         return var1.field_71439_g.field_70165_t - var1.field_71439_g.field_70169_q;
      case 'z':
         return var1.field_71439_g.field_70161_v - var1.field_71439_g.field_70166_s;
      default:
         return 0.0D;
      }
   }

   public static int ping(Minecraft var0) {
      if (var0.func_147114_u() == null) {
         return 1;
      } else if (var0.field_71439_g == null) {
         return -1;
      } else {
         try {
            return var0.func_147114_u().func_175102_a(var0.field_71439_g.func_110124_au()).func_178853_c();
         } catch (NullPointerException var2) {
            return -1;
         }
      }
   }

   public static int dura(Minecraft var0) {
      ItemStack var1 = var0.field_71439_g.func_184614_ca();
      return var1.func_77958_k() - var1.func_77952_i();
   }

   public static String playerDimension(Minecraft var0) {
      if (var0.field_71439_g == null) {
         return "No Dimension";
      } else {
         switch(var0.field_71439_g.field_71093_bK) {
         case -1:
            return "Nether";
         case 0:
            return "Overworld";
         case 1:
            return "End";
         default:
            return "No Dimension";
         }
      }
   }
}
