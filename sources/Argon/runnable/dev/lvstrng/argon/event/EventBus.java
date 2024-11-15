package dev.lvstrng.argon.event;

import dev.lvstrng.argon.Argon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public final class EventBus {
    private final HashMap<Class<?>, ArrayList<PriorityListener>> listeners;

    public EventBus() {
        this.listeners = new HashMap<>();
    }

    public static void postEvent(final Event event) {
        final EventBus eventBus = Argon.INSTANCE.getEventBus();
        if (eventBus != null) eventBus.callListeners(event);
    }

    private void callListeners(final Event event) {
        final ArrayList<PriorityListener> listeners = this.listeners.getOrDefault(event.getClazz(), new ArrayList<>());
        if (listeners.isEmpty()) return;

        final ArrayList<PriorityListener> list = new ArrayList<>(listeners);
        list.removeIf(Objects::isNull);
        list.sort(Comparator.comparingInt(PriorityListener::getPriority));
        final ArrayList<EventListener> eventListeners = new ArrayList<>();
        for (PriorityListener pl : list) {
            eventListeners.add(pl.getListener());
        }
        event.callListeners(eventListeners);
    }

    public void registerPriorityListener(final Class<?> type, final EventListener listener) {
        this.register(type, listener, 0);
    }

    public void register(final Class<?> type, final EventListener listener, final int priority) {
        final ArrayList<PriorityListener> list = this.listeners.computeIfAbsent(type, k -> new ArrayList<>());
        list.add(new PriorityListener(listener, priority));
    }

    public void unregister(final Class<?> type, final EventListener listener) {
        final ArrayList<PriorityListener> list = this.listeners.get(type);
        if (list != null) list.removeIf(l -> l.getListener() == listener);
    }
}