package xyz.vergoclient.util.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtil {

    // Implementing this for ease-of-access in the future.
    // Use this > Minecraft built-in since its way easier.

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendPacketNoEvent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacket(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

}
