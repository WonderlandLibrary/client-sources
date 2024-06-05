package net;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.Event;

public class ap3 {
   private static boolean b;

   public static Event a(Event var0) {
      return EventManager.call(var0);
   }

   public static void b(Object var0) {
      EventManager.register(var0);
   }

   public static void a(Object var0) {
      EventManager.unregister(var0);
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
      if(b()) {
         b(true);
      }

   }
}
