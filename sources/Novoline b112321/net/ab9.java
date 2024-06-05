package net;

import cc.novoline.events.events.SlowdownEvent;

public class ab9 {
   public static void a(SlowdownEvent var0, boolean var1) {
      var0.setCancelled(var1);
   }

   public static boolean a(SlowdownEvent var0) {
      return var0.isCancelled();
   }
}
