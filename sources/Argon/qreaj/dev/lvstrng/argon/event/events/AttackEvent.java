package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.CancellableEvent;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.AttackListener;

import java.util.ArrayList;

public class AttackEvent extends CancellableEvent {
    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof AttackListener).map(listener -> (AttackListener) listener).forEach(listener -> listener.onAttack(this));
    }

    @Override
    public Class<?> getClazz() {
        return AttackListener.class;
    }
}