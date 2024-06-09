package us.dev.direkt.event.internal.events.game.network;

import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import us.dev.direkt.event.CancellableEvent;

/**
 * @author Foundry
 */
public class EventSendPacket extends CancellableEvent implements PacketEvent<INetHandlerPlayServer> {
    private Packet<INetHandlerPlayServer> packet;

    public EventSendPacket(Packet<INetHandlerPlayServer> packet){
        this.packet = packet;
    }

    public Packet<INetHandlerPlayServer> getPacket() {
        return this.packet;
    }

    public void setPacket(Packet<INetHandlerPlayServer> packet) {
        this.packet = packet;
    }
}
