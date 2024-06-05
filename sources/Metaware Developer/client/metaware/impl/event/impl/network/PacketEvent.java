package client.metaware.impl.event.impl.network;

import client.metaware.impl.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {

    private Packet packet;
    private Type type;

    public PacketEvent(Packet packet, Type type){
        this.packet = packet;
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> T getPacket() {
        return (T)packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public enum Type {
        RECEIVING,
        SENDING;
    }
}
