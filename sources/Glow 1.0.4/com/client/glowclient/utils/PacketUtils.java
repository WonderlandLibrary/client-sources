package com.client.glowclient.utils;

import net.minecraft.network.*;

public final class PacketUtils
{
    public static void sendPacket(final Packet packet) {
        MinecraftHelper.getConnection().sendPacket(packet);
    }
    
    public static void sendPacket2(final Packet packet) {
        MinecraftHelper.getConnection().sendPacket(packet);
    }
    
    public PacketUtils() {
        super();
    }
}
