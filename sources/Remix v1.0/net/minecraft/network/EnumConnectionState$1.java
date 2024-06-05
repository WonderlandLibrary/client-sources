package net.minecraft.network;

import net.minecraft.network.handshake.client.*;

enum EnumConnectionState$1
{
    {
        this.registerPacket(EnumPacketDirection.SERVERBOUND, C00Handshake.class);
    }
}