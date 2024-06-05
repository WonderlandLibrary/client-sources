package net;

import net.minecraft.network.play.server.S48PacketResourcePackSend;

public class q4 {
   public static String b(S48PacketResourcePackSend var0) {
      return var0.getURL();
   }

   public static String a(S48PacketResourcePackSend var0) {
      return var0.getHash();
   }
}
