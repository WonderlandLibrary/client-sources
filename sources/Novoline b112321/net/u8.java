package net;

import cc.novoline.gui.screen.dropdown.config.Config;
import java.util.List;
import net.a1I;
import net.acE;

public class u8 {
   private static acE[] b;

   public static float e(a1I var0) {
      return var0.getPosY();
   }

   public static Config d(a1I var0) {
      return var0.c();
   }

   public static List b(a1I var0) {
      return var0.a();
   }

   public static float c(a1I var0) {
      return var0.getPosX();
   }

   public static void a(a1I var0, Config var1) {
      var0.a(var1);
   }

   public static void a(a1I var0) {
      var0.b();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[4]);
      }

   }
}
