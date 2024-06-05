package net;

import cc.novoline.gui.screen.config.GuiConfig;
import java.nio.file.Path;
import net.acE;

public class ajw {
   private static acE[] b;

   public static GuiConfig a(Path var0) {
      return GuiConfig.of(var0);
   }

   public static int e(GuiConfig var0) {
      return var0.getY();
   }

   public static int b(GuiConfig var0) {
      return var0.getOffset();
   }

   public static void a(GuiConfig var0, int var1) {
      var0.setOffset(var1);
   }

   public static void f(GuiConfig var0) {
      var0.update();
   }

   public static boolean d(GuiConfig var0) {
      return var0.isOutsideOfMenu();
   }

   public static void a(GuiConfig var0, boolean var1) {
      var0.setSelected(var1);
   }

   public static boolean c(GuiConfig var0) {
      return var0.isSelected();
   }

   public static String a(GuiConfig var0) {
      return var0.getName();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[3]);
      }

   }
}
