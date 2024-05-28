package dev.vertic.util.player;

import dev.vertic.Utils;
import lombok.experimental.UtilityClass;
import net.minecraft.network.Packet;

@UtilityClass
public class PacketUtil implements Utils {

    public void sendPacket(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public void sendPacketWithoutEvent(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueueWithoutEvent(packet);
    }

    public void receivePacket(final Packet<?> packet) {
        mc.getNetHandler().addToReceiveQueue(packet);
    }

    public void receivePacketWithoutEvent(final Packet<?> packet) {
        mc.getNetHandler().addToReceiveQueueWithoutEvent(packet);
    }
}
