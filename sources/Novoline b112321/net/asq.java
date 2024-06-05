package net;

import net.minecraft.client.gui.ScaledResolution;
import net.optifine.Lagometer;

public class asq {
   public static void a(ScaledResolution var0) {
      Lagometer.showLagometer(var0);
   }

   public static void a() {
      Lagometer.updateLagometer();
   }

   public static boolean b() {
      return Lagometer.isActive();
   }
}
