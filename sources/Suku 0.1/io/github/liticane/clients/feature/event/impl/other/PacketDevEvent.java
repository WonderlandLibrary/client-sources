package io.github.liticane.clients.feature.event.impl.other;

import io.github.liticane.clients.feature.event.Event;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

public class PacketDevEvent extends Event {
    private Packet<?> packet;
    private EnumPacketDirection direction;
    private final INetHandler netHandler;
    public PacketDevEvent(Packet<?> packet,EnumPacketDirection directio,INetHandler handler) {
        this.packet = packet;
        this.direction = directio;
        this.netHandler = handler;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public INetHandler getNetHandler() {
        return netHandler;
    }

    public EnumPacketDirection getDirection() {
        return direction;
    }
}
