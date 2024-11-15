package dev.lvstrng.argon.event;

import dev.lvstrng.argon.Argon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public final class EventBus {
    private final HashMap<Class<?>, ArrayList<EventListener>> listeners;

    public EventBus() {
        this.listeners = new HashMap<>();
    }

    public static void postEvent(final Event event) {
        final EventBus eventBus = Argon.INSTANCE.getEventBus();
        if (eventBus != null) eventBus.callListeners(event);
    }

    private void callListeners(final Event event) {
        final ArrayList<EventListener> listeners = this.listeners.get(event.getClazz());
        if (listeners == null || listeners.isEmpty()) return;

        final ArrayList<EventListener> list = new ArrayList<>(listeners);
        list.removeIf(Objects::isNull);
        list.sort(Comparator.comparingInt(listener -> ((PriorityListener) listener).getPriority()));

        event.callListeners(list);
    }

    public void registerPriorityListener(final Class<?> type, final EventListener listener) {
        this.register(type, listener, 0);
    }

    public void register(final Class<?> type, final EventListener listener, final int priority) {
        final ArrayList<EventListener> list = this.listeners.computeIfAbsent(type, k -> new ArrayList<>());
        list.add(new PriorityListener(listener, priority));
    }

    public void unregister(final Class<?> type, final EventListener listener) {
        final ArrayList<EventListener> list = this.listeners.get(type);
        if (list != null) list.remove(listener);
    }
}