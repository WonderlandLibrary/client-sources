package net;

import net.minecraft.network.status.client.C01PacketPing;

public class adV {
   private static int[] b;

   public static long a(C01PacketPing var0) {
      return var0.getClientTime();
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
