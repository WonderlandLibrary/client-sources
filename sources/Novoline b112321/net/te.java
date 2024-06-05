package net;

import kotlin.Pair;
import kotlin.TuplesKt;

public class te {
   private static String[] b;

   public static Pair a(Object var0, Object var1) {
      return TuplesKt.to(var0, var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[5]);
      }

   }
}
