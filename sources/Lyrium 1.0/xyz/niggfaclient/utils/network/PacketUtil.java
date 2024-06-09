// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.network;

import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.Utils;

public class PacketUtil extends Utils
{
    public static void sendPacket(final Packet<?> packet, final boolean silent) {
        if (PacketUtil.mc.thePlayer != null) {
            PacketUtil.mc.getNetHandler().getNetworkManager().sendPacket(packet, silent);
        }
    }
    
    public static void sendPacketNoEvent(final Packet packet) {
        sendPacket(packet, true);
    }
    
    public static void sendPacket(final Packet packet) {
        sendPacket(packet, false);
    }
}
