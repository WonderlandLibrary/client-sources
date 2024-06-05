package net;

import cc.novoline.events.events.PacketDirection;
import net.Q5;
import net.apZ;

public class h6 {
   private static String[] b;

   public static Q5 b(apZ var0) {
      return var0.getPacket();
   }

   public static PacketDirection a(apZ var0) {
      return var0.getDirection();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[4]);
      }

   }
}
