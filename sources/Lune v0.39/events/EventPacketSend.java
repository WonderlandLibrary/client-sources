package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import net.minecraft.network.Packet;

/**
 * @description: Send packet event
 * @author: Qian_Xia
 * @create: 2020-08-23 19:38
 **/
public class EventPacketSend extends Event {
    private boolean cancelled = false;
    private Packet packet;

    public EventPacketSend(Packet packet) {
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
