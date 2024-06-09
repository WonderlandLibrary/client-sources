package us.dev.direkt.event.internal.events.game.network;

import net.minecraft.network.Packet;

/**
 * @author Foundry
 */
public class EventPostReceivePacket implements PacketEvent {
    private Packet<?> packet;

    public EventPostReceivePacket(Packet<?> packet){
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}
