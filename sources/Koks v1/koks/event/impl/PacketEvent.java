package koks.event.impl;

import koks.event.Event;
import net.minecraft.network.Packet;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:49
 */
public class PacketEvent extends Event {

    private Type type;
    private Packet packet;

    public PacketEvent(Type type, Packet packet) {
        this.type = type;
        this.packet = packet;
    }

    public Type getType() {
        return type;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public enum Type {
        RECIVE, SEND;
    }
}
