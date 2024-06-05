package net;

import cc.novoline.modules.configurations.annotation.Property;

public class p {
   private static int[] b;

   public static String a(Property var0) {
      return var0.value();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[2]);
      }

   }
}
