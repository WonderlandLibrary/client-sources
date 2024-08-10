package cc.slack.utils.network;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.utils.client.IMinecraft;
import cc.slack.utils.player.BlinkUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public final class PacketUtil implements IMinecraft {

    public static void send(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void send(Packet<?> packet, int iterations) {
        for (int i = 0; i < iterations; i++) send(packet);
    }

    public static void sendNoEvent(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }
    public static void sendNoEvent(Packet<?> packet, int iterations) {
        for (int i = 0; i < iterations; i++) sendNoEvent(packet);
    }

    public static void receive(Packet<?> packet) {
        PacketEvent packetEvent = new PacketEvent(packet, PacketDirection.INCOMING);
        if (packetEvent.call().isCanceled()) return;
        if (BlinkUtil.handlePacket(packetEvent)) return;

        packetEvent.getPacket().processPacket(mc.getNetHandler().getNetworkManager().packetListener);
    }

    public static void receiveNoEvent(Packet<?> packet) {
        PacketEvent packetEvent = new PacketEvent(packet, PacketDirection.INCOMING);
        packetEvent.getPacket().processPacket(mc.getNetHandler().getNetworkManager().packetListener);
    }

    public static void sendBlocking(boolean callEvent, boolean place) {
        C08PacketPlayerBlockPlacement packet;
        packet = place ? new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0.0f, 0.0f, 0.0f) : new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem());
        if (callEvent) {
            PacketUtil.send(packet);
        } else {
            PacketUtil.sendNoEvent(packet);
        }
    }

    public static void sendCriticalPacket(double yOffset, boolean ground) {
        PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + yOffset, mc.thePlayer.posZ, ground));
    }

    public static void releaseUseItem(boolean callEvent) {
        C07PacketPlayerDigging packet = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
        if (callEvent) {
            PacketUtil.send(packet);
        } else {
            PacketUtil.sendNoEvent(packet);
        }
    }
}
