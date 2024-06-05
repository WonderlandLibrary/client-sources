package net.minecraft.network;

import net.minecraft.network.login.server.*;
import net.minecraft.network.login.client.*;

enum EnumConnectionState$4
{
    {
        this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketDisconnect.class);
        this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketEncryptionRequest.class);
        this.registerPacket(EnumPacketDirection.CLIENTBOUND, S02PacketLoginSuccess.class);
        this.registerPacket(EnumPacketDirection.CLIENTBOUND, S03PacketEnableCompression.class);
        this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketLoginStart.class);
        this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketEncryptionResponse.class);
    }
}