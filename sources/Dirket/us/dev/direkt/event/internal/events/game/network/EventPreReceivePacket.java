package us.dev.direkt.event.internal.events.game.network;

import net.minecraft.network.Packet;
import us.dev.direkt.event.CancellableEvent;

/**
 * @author Foundry
 */
public class EventPreReceivePacket extends CancellableEvent implements PacketEvent {
    private Packet<?> packet;

    public EventPreReceivePacket(Packet<?> packet){
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
