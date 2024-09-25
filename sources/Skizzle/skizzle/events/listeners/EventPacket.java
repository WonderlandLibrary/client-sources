/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import net.minecraft.network.Packet;
import skizzle.events.Event;

public class EventPacket<T>
extends Event<EventPacket> {
    public Packet packet;
    public boolean cancelled;

    public Packet getPacket() {
        EventPacket Nigga;
        return Nigga.packet;
    }

    @Override
    public void setCancelled(boolean Nigga) {
        Nigga.cancelled = Nigga;
    }

    public EventPacket(Packet Nigga) {
        EventPacket Nigga2;
        Nigga2.packet = Nigga;
    }

    @Override
    public boolean isCancelled() {
        EventPacket Nigga;
        return Nigga.cancelled;
    }

    public static {
        throw throwable;
    }

    public void setPacket(Packet Nigga) {
        Nigga.packet = Nigga;
    }
}

