package net;

import net.minecraft.network.login.server.S03PacketEnableCompression;

public class Be {
   private static String[] b;

   public static int a(S03PacketEnableCompression var0) {
      return var0.getCompressionTreshold();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[2]);
      }

   }
}
