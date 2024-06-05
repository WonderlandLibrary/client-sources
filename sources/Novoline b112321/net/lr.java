package net;

import net.minecraft.network.play.client.C15PacketClientSettings;

public class lr {
   public static String a(C15PacketClientSettings var0) {
      return var0.getLang();
   }

   public static boolean c(C15PacketClientSettings var0) {
      return var0.isColorsEnabled();
   }

   public static int b(C15PacketClientSettings var0) {
      return var0.getModelPartFlags();
   }
}
