package net;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

public class a8h {
   public static void b(Packet var0, PacketBuffer var1) {
      var0.writePacketData(var1);
   }

   public static void a(Packet var0, INetHandler var1) {
      var0.processPacket(var1);
   }

   public static void a(Packet var0, PacketBuffer var1) {
      var0.readPacketData(var1);
   }
}
