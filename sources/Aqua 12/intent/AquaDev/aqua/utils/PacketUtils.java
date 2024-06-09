// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.network.Packet;
import net.minecraft.client.Minecraft;

public class PacketUtils
{
    static Minecraft mc;
    
    public static void sendPacket(final Packet<?> packet, final boolean silent) {
        if (PacketUtils.mc.thePlayer != null) {
            PacketUtils.mc.getNetHandler().getNetworkManager().sendPacket(packet);
        }
    }
    
    public static void sendPacketNoEvent(final Packet packet) {
        sendPacket(packet, true);
    }
    
    public static void sendPacket(final Packet packet) {
        sendPacket(packet, false);
    }
    
    static {
        PacketUtils.mc = Minecraft.getMinecraft();
    }
}
