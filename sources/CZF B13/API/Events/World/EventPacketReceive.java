package gq.vapu.czfclient.API.Events.World;

import gq.vapu.czfclient.API.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event {
    public static Packet packet;

    public EventPacketReceive(Packet packet) {
        EventPacketReceive.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        EventPacketReceive.packet = packet;
    }
}
