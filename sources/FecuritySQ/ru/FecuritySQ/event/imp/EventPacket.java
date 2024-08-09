package ru.FecuritySQ.event.imp;

import net.minecraft.network.IPacket;
import ru.FecuritySQ.event.Event;

public class EventPacket extends Event {
    public IPacket<?> packet;
    public EventPacket(IPacket<?> packet){
        this.packet = packet;
    }

}
