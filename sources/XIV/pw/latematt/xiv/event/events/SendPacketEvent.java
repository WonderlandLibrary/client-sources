package pw.latematt.xiv.event.events;

import net.minecraft.network.Packet;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class SendPacketEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final Packet packet;

    public SendPacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
