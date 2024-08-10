package cc.slack.utils.network;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.utils.client.IMinecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.*;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;

import java.util.concurrent.CopyOnWriteArrayList;

public class PingSpoofUtil implements IMinecraft {


    public static boolean isEnabled = false;
    public static boolean DELAY_OUTBOUND = false;
    public static boolean DELAY_INBOUND = false;

    public static int outboundDelay = 0;
    public static int inboundDelay = 0;

    private static final CopyOnWriteArrayList<TimedPacket> clientPackets = new CopyOnWriteArrayList<>();
    private static final CopyOnWriteArrayList<TimedPacket> serverPackets = new CopyOnWriteArrayList<>();

    public static boolean isSpoofing() {
        return isEnabled;
    }

    public static void enableInbound(boolean inboundState, int delay) {
        DELAY_INBOUND = inboundState;
        inboundDelay = delay;
        isEnabled = true;
    }

    public static void enableOutbound(boolean outboundState, int delay) {
        DELAY_OUTBOUND = outboundState;
        outboundDelay = delay;
        isEnabled = true;
    }

    public static void disable() {
        disable(true, true);
    }

    public static void disable(boolean inbound, boolean outbound) {
        DELAY_INBOUND = !inbound && DELAY_INBOUND;
        DELAY_OUTBOUND = !outbound  && DELAY_OUTBOUND;
        isEnabled = DELAY_INBOUND || DELAY_OUTBOUND;
        releasePackets(inbound, false, outbound, false);
    }

    public static void releasePackets() {
        releasePackets(true, true, true, true);
    }

    public static void releasePackets(boolean releaseInbound, boolean inboundCheck, boolean releaseOutgoing, boolean outboundCheck) {
        if (releaseInbound) {
            serverPackets.forEach(timedPacket -> {
                if (!inboundCheck || timedPacket.elapsed(inboundDelay)) {
                    PacketUtil.receiveNoEvent(timedPacket.getPacket());
                    serverPackets.remove(timedPacket);
                }
            });
        }

        if (releaseOutgoing) {
            clientPackets.forEach(timedPacket -> {
                if (!outboundCheck || timedPacket.elapsed(outboundDelay)) {
                    PacketUtil.sendNoEvent(timedPacket.getPacket());
                    clientPackets.remove(timedPacket);
                }
            });
        }
    }

    public static boolean handlePacket(PacketEvent event) {
        final Packet packet = event.getPacket();

        if (mc.thePlayer == null || mc.theWorld == null || mc.thePlayer.ticksExisted < 4) return false;

        if (isSpoofing()) {
            if (event.getDirection() == PacketDirection.INCOMING && DELAY_INBOUND) {
                if (!(packet instanceof S00PacketDisconnect || packet instanceof S01PacketPong ||
                        packet instanceof S00PacketServerInfo || packet instanceof S3EPacketTeams ||
                        packet instanceof S19PacketEntityStatus || packet instanceof S02PacketChat ||
                        packet instanceof S3BPacketScoreboardObjective || packet instanceof S0CPacketSpawnPlayer)) {

                    serverPackets.add(new TimedPacket(packet));
                    return true;
                }
            } else if (event.getDirection() == PacketDirection.OUTGOING && DELAY_OUTBOUND && mc.thePlayer.ticksExisted > 3) {
                if (!(packet instanceof C00Handshake ||
                        packet instanceof C00PacketLoginStart)) {
                    clientPackets.add(new TimedPacket(packet));
                    return true;
                }
            }
        }
        return false;
    }
}
