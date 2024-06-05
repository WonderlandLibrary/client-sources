package net.minecraft.network;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.handshake.client.C00Handshake;

enum EnumConnectionState$1 {
   EnumConnectionState$1(int var3) {
      this.registerPacket(EnumPacketDirection.SERVERBOUND, C00Handshake.class);
   }
}
