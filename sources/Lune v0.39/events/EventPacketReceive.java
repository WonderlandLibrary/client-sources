package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import net.minecraft.network.Packet;

/**
 * @description: Receive Packet Event
 * @author: Qian_Xia
 * @create: 2020-08-23 20:35
 **/
public class EventPacketReceive extends Event {
    private boolean cancelled = false;
    private Packet packet;

    public EventPacketReceive(Packet packet) {
        this.packet = packet;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
