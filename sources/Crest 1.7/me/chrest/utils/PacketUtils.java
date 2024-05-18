// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public final class PacketUtils
{
    public static void sendPacket(final Packet packet) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }
    
    public static int getPing() {
        return 0;
    }
    
    public static int getPlayerPing(final String pname) {
        return 0;
    }
}
