package net.minecraft.network;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;

enum EnumConnectionState$4 {
   EnumConnectionState$4(int var3) {
      this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketDisconnect.class);
      this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketEncryptionRequest.class);
      this.registerPacket(EnumPacketDirection.CLIENTBOUND, S02PacketLoginSuccess.class);
      this.registerPacket(EnumPacketDirection.CLIENTBOUND, S03PacketEnableCompression.class);
      this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketLoginStart.class);
      this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketEncryptionResponse.class);
   }
}
