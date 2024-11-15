package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.Event;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.PlayerTickListener;

import java.util.ArrayList;

public class PlayerTickEvent extends Event {
    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof PlayerTickListener).map(listener -> (PlayerTickListener) listener).forEach(PlayerTickListener::onPlayerTick);
    }

    @Override
    public Class<?> getClazz() {
        return PlayerTickListener.class;
    }
}