package net;

import java.util.List;
import net.Ul;

public class K9 {
   private static String[] b;

   public static List a(Ul var0) {
      return var0.a();
   }

   public static boolean b(Ul var0) {
      return var0.c();
   }

   public static void b(Ul var0, int var1, int var2) {
      var0.c(var1, var2);
   }

   public static void a(Ul var0, int var1, int var2) {
      var0.b(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[5]);
      }

   }
}
