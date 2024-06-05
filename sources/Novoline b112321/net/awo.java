package net;

import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import net.minecraft.network.Packet;

public class awo {
   private static String[] b;

   public static PacketDirection a(PacketEvent var0) {
      return var0.getDirection();
   }

   public static Packet c(PacketEvent var0) {
      return var0.getPacket();
   }

   public static void a(PacketEvent var0, boolean var1) {
      var0.setCancelled(var1);
   }

   public static boolean b(PacketEvent var0) {
      return var0.isCancelled();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[5]);
      }

   }
}
