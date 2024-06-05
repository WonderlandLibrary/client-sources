package net;

import cc.novoline.utils.ScaleUtils;
import net.minecraft.client.Minecraft;

public class bgk {
   public static void a(Minecraft var0) {
      ScaleUtils.scale(var0);
   }

   public static int[] a(Minecraft var0, int var1, int var2) {
      return ScaleUtils.getScaledMouseCoordinates(var0, var1, var2);
   }

   public static int[] b(Minecraft var0, int var1, int var2) {
      return ScaleUtils.b(var0, var1, var2);
   }

   public static double[] a(Minecraft var0, double var1, double var3) {
      return ScaleUtils.a(var0, var1, var3);
   }
}
