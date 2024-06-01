package best.actinium.util.io;

import best.actinium.util.IAccess;
import net.minecraft.network.Packet;

public class PacketUtil implements IAccess {
    public static void send(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
    public static void sendSilent(Packet packet) {
        mc.thePlayer.sendQueue.addToSendQueueSilent(packet);
    }
    public static void receiveNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToReceiveQueueUnregistered(packet);
    }

    //thx alan boob <3 i mean polarware trust trust
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
