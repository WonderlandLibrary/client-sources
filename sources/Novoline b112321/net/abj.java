package net;

import viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;

public class abj {
   private static int[] b;

   public static GameMode a(int var0) {
      return GameMode.getById(var0);
   }

   public static String a(GameMode var0) {
      return var0.getText();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[2]);
      }

   }
}
