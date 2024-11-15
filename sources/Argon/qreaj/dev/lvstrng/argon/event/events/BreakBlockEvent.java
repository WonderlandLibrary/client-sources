// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.CancellableEvent;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.BreakBlockListener;

import java.util.ArrayList;

public class BreakBlockEvent extends CancellableEvent {
    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof BreakBlockListener).map(listener -> (BreakBlockListener) listener).forEach(listener -> listener.onBlockBreak(this));
    }

    @Override
    public Class<?> getClazz() {
        return BreakBlockListener.class;
    }
}
