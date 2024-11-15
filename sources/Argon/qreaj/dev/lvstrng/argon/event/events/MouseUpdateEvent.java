package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.Event;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.MouseUpdateListener;

import java.util.ArrayList;

public class MouseUpdateEvent extends Event {
    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof MouseUpdateListener).map(listener -> (MouseUpdateListener) listener).forEach(MouseUpdateListener::onMouseUpdate);
    }

    @Override
    public Class<?> getClazz() {
        return MouseUpdateListener.class;
    }
}