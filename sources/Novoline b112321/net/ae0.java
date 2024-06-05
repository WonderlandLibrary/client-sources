package net;

import net.minecraft.world.World;
import net.minecraft.world.demo.DemoWorldServer;

public class ae0 {
   private static boolean b;

   public static World a(DemoWorldServer var0) {
      return var0.init();
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
