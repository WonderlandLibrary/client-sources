package net;

import cc.novoline.gui.screen.setting.Setting;
import java.util.List;
import net.HH;

public class Av {
   private static int[] b;

   public static Setting c(HH var0) {
      return var0.e();
   }

   public static void a(HH var0, int var1, int var2, List var3) {
      var0.a(var1, var2, var3);
   }

   public static boolean a(HH var0) {
      return var0.c();
   }

   public static boolean b(HH var0) {
      return var0.f();
   }

   public static boolean a(HH var0, int var1, int var2) {
      return var0.b(var1, var2);
   }

   public static void a(HH var0, int var1, int var2, int var3, List var4) {
      var0.a(var1, var2, var3, var4);
   }

   public static void a(HH var0, boolean var1) {
      var0.a(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
