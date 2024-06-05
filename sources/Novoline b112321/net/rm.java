package net;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.KeyPressEvent;

public class rm {
   private static String[] b;

   @EventTarget
   public void a(KeyPressEvent var1) {
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
