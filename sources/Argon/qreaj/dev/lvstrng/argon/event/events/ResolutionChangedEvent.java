package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.Event;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.ResolutionChangeListener;
import net.minecraft.client.util.Window;

import java.util.ArrayList;

public class ResolutionChangedEvent extends Event {
    public Window window;

    public ResolutionChangedEvent(final Window window) {
        this.window = window;
    }

    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof ResolutionChangeListener).map(listener -> (ResolutionChangeListener) listener).forEach(listener -> listener.onResolutionChange(this));
    }

    @Override
    public Class<?> getClazz() {
        return ResolutionChangeListener.class;
    }
}