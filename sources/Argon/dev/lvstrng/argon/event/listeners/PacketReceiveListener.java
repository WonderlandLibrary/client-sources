package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.PacketReceiveEvent;

public interface PacketReceiveListener extends EventListener {
    void onPacketReceive(final PacketReceiveEvent event);
}
