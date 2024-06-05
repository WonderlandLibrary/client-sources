package net;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class zf {
   public static String a(S3FPacketCustomPayload var0) {
      return var0.getChannelName();
   }

   public static PacketBuffer b(S3FPacketCustomPayload var0) {
      return var0.getBufferData();
   }
}
