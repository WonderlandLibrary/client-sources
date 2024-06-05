package net.minecraft.network;

import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;

enum EnumConnectionState$3
{
    {
        this.registerPacket(EnumPacketDirection.SERVERBOUND, C00PacketServerQuery.class);
        this.registerPacket(EnumPacketDirection.CLIENTBOUND, S00PacketServerInfo.class);
        this.registerPacket(EnumPacketDirection.SERVERBOUND, C01PacketPing.class);
        this.registerPacket(EnumPacketDirection.CLIENTBOUND, S01PacketPong.class);
    }
}