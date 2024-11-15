package dev.lvstrng.argon.event.listeners;

import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.events.PacketSendEvent;

public interface PacketSendListener extends EventListener {
    void onPacketSend(final PacketSendEvent event);
}
