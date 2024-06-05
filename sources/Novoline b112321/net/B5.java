package net;

import java.util.List;
import net.acE;
import net.ad_;

public class B5 {
   private static acE[] b;

   public static boolean b(ad_ var0) {
      return var0.c();
   }

   public static void b(ad_ var0, int var1, int var2) {
      var0.a(var1, var2);
   }

   public static void a(ad_ var0, int var1, int var2) {
      var0.b(var1, var2);
   }

   public static List a(ad_ var0) {
      return var0.d();
   }

   public static void c(ad_ var0) {
      var0.b();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[4]);
      }

   }
}
