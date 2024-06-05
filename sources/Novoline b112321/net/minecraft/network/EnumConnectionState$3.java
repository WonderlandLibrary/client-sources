package net.minecraft.network;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;

enum EnumConnectionState$3 {
   EnumConnectionState$3(int var3) {
      this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketServerQuery.class);
      this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketServerInfo.class);
      this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketPing.class);
      this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketPong.class);
   }
}
