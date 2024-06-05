/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendPacket(Packet packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacketPlayer(Packet packet) {
        Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public static void sendPacketPlayerNoEvent(Packet packet) {
        Minecraft.thePlayer.sendQueue.getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacketNoEvent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacketNoEventSilent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void sendPacketSilent(Packet packet) {
        Minecraft.thePlayer.sendQueue.addToSendQueueSilent(packet);
    }

    public static void sendC04(double x, double y, double z, boolean ground, boolean silent) {
        if (silent) {
            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
            return;
        }
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
    }
}

