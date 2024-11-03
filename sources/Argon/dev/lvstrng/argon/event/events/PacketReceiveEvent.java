package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.CancellableEvent;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.PacketReceiveListener;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;

public class PacketReceiveEvent extends CancellableEvent {
    public Packet<?> packet;

    public PacketReceiveEvent(final Packet<?> packet) {
        this.packet = packet;
    }

    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof PacketReceiveListener).map(listener -> (PacketReceiveListener) listener).forEach(listener -> listener.onPacketReceive(this));
    }

    @Override
    public Class<?> getClazz() {
        return PacketReceiveListener.class;
    }
}