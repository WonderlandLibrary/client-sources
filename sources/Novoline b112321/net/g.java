package net;

import cc.novoline.events.events.SneakEvent;

public class g {
   public static boolean a(SneakEvent var0) {
      return var0.isCancelled();
   }

   public static void a(SneakEvent var0, boolean var1) {
      var0.setCancelled(var1);
   }
}
