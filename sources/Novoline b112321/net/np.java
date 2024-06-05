package net;

import paulscode.sound.Source;

public class np {
   private static int[] b;

   public static boolean a(Source var0) {
      return var0.playing();
   }

   public static boolean b(Source var0) {
      return var0.paused();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[4]);
      }

   }
}
