package net;

import cc.novoline.modules.player.Freecam;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class vn {
   private static boolean b;

   public static EntityOtherPlayerMP b(Freecam var0) {
      return var0.getFreecamEntity();
   }

   public static boolean a(Freecam var0) {
      return var0.isEnabled();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
