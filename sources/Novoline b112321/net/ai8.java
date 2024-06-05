package net;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.realms.RealmsButton;

public class ai8 {
   public static void a(RealmsButton var0, int var1, int var2) {
      var0.render(var1, var2);
   }

   public static int a(RealmsButton var0) {
      return var0.id();
   }

   public static void c(RealmsButton var0, int var1, int var2) {
      var0.clicked(var1, var2);
   }

   public static void d(RealmsButton var0, int var1, int var2) {
      var0.released(var1, var2);
   }

   public static void b(RealmsButton var0, int var1, int var2) {
      var0.renderBg(var1, var2);
   }

   public static int a(RealmsButton var0, boolean var1) {
      return var0.getYImage(var1);
   }

   public static GuiButton b(RealmsButton var0) {
      return var0.getProxy();
   }
}
