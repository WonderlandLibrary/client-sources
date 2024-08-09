/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.utils.paste;

import net.minecraft.network.IPacket;
import net.minecraftforge.eventbus.api.Event;

public class EventPacket
extends Event {
    private IPacket packet;
    private final PacketType packetType;

    public EventPacket(IPacket packet, PacketType packetType) {
        this.packet = packet;
        this.packetType = packetType;
    }

    public IPacket getPacket() {
        return this.packet;
    }

    public void setPacket(IPacket packet) {
        this.packet = packet;
    }

    public boolean isReceivePacket() {
        return this.packetType == PacketType.RECEIVE;
    }

    public boolean isSendPacket() {
        return this.packetType == PacketType.SEND;
    }

    public static enum PacketType {
        SEND,
        RECEIVE;

    }
}

