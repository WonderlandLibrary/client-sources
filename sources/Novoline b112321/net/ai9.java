package net;

import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;

public class ai9 {
   public static void a(IBorderListener var0, WorldBorder var1, double var2, double var4) {
      var0.onCenterChanged(var1, var2, var4);
   }

   public static void c(IBorderListener var0, WorldBorder var1, double var2) {
      var0.onSizeChanged(var1, var2);
   }

   public static void a(IBorderListener var0, WorldBorder var1, double var2, double var4, long var6) {
      var0.onTransitionStarted(var1, var2, var4, var6);
   }

   public static void a(IBorderListener var0, WorldBorder var1, double var2) {
      var0.onDamageBufferChanged(var1, var2);
   }

   public static void b(IBorderListener var0, WorldBorder var1, double var2) {
      var0.onDamageAmountChanged(var1, var2);
   }

   public static void b(IBorderListener var0, WorldBorder var1, int var2) {
      var0.onWarningTimeChanged(var1, var2);
   }

   public static void a(IBorderListener var0, WorldBorder var1, int var2) {
      var0.onWarningDistanceChanged(var1, var2);
   }
}
