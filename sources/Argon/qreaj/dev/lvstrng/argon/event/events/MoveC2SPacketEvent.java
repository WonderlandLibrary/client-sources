package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.Event;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.MoveC2SPacketListener;

import java.util.ArrayList;

public class MoveC2SPacketEvent extends Event {
    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof MoveC2SPacketListener).map(listener -> (MoveC2SPacketListener) listener).forEach(MoveC2SPacketListener::onMoveC2SPacket);
    }

    @Override
    public Class<?> getClazz() {
        return MoveC2SPacketListener.class;
    }
}
