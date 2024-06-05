package net.minecraft.network;

import java.io.IOException;

public interface Packet {
   void readPacketData(PacketBuffer var1) throws IOException;

   void processPacket(INetHandler var1);

   void writePacketData(PacketBuffer var1) throws IOException;
}
