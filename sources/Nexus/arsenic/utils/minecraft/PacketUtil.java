package arsenic.utils.minecraft;

import arsenic.utils.java.UtilityClass;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.*;

public class PacketUtil extends UtilityClass {
    public static void send(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void receive(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    private static boolean isServerPacket(final Packet<?> packet) {
        return packet.toString().toCharArray()[34] == 'S';
    }

    private static boolean isClientPacket(final Packet<?> packet) {
        return packet.toString().toCharArray()[34] == 'C';
    }

    public static void handlePacket(Packet<INetHandlerPlayClient> packet) {
        INetHandlerPlayClient netHandler = mc.getNetHandler();
        if (packet instanceof S00PacketKeepAlive) {
            netHandler.handleKeepAlive((S00PacketKeepAlive) packet);
        } else if (packet instanceof S03PacketTimeUpdate) {
            netHandler.handleTimeUpdate((S03PacketTimeUpdate) packet);
        }else if (packet instanceof S06PacketUpdateHealth) {
            netHandler.handleUpdateHealth((S06PacketUpdateHealth) packet);
        } else if (packet instanceof S12PacketEntityVelocity) {
            netHandler.handleEntityVelocity((S12PacketEntityVelocity) packet);
        } else if (packet instanceof S14PacketEntity) {
            netHandler.handleEntityMovement((S14PacketEntity) packet);
        } else if (packet instanceof S18PacketEntityTeleport) {
            netHandler.handleEntityTeleport((S18PacketEntityTeleport) packet);
        } else if (packet instanceof S27PacketExplosion) {
            netHandler.handleExplosion((S27PacketExplosion) packet);
        }
    }


    public static class TimedPacket {
        private final Packet<?> packet;
        private final long time;

        public TimedPacket(final Packet<?> packet, final long time) {
            this.packet = packet;
            this.time = time;
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public long getTime() {
            return time;
        }
    }
}
