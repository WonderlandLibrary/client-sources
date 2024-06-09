package me.finz0.osiris.event.events;

import me.finz0.osiris.event.OsirisEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends OsirisEvent {

    private final Packet packet;

    public PacketEvent(Packet packet) {
        super();
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet packet) {
            super(packet);
        }
    }
    public static class Send extends PacketEvent {
        public Send(Packet packet) {
            super(packet);
        }
    }

}
