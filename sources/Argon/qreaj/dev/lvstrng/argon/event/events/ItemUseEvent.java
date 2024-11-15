package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.CancellableEvent;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.ItemUseListener;

import java.util.ArrayList;

public class ItemUseEvent extends CancellableEvent {
    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof ItemUseListener).map(listener -> (ItemUseListener) listener).forEach(listener -> listener.onItemUse(this));
    }

    @Override
    public Class<?> getClazz() {
        return ItemUseListener.class;
    }
}
