package net;

import com.google.gson.reflect.TypeToken;

public class qH {
   private static boolean b;

   public static Class a(TypeToken var0) {
      return var0.getRawType();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
