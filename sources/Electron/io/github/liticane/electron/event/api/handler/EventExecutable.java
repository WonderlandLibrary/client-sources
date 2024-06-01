package io.github.liticane.electron.event.api.handler;

import io.github.liticane.electron.event.Event;
import io.github.liticane.electron.event.EventPriority;
import io.github.liticane.electron.event.api.listener.Listener;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class EventExecutable {

    // Parental object
    private final Object parent;

    // MethodHandler instance for registered methods
    private MethodHandler method;

    // ListenerHandler instance for registered listeners
    private ListenerHandler<? extends Event> listener;

    private final int priority;

    public EventExecutable(final Method method, final Object parent, final EventPriority eventPriority) {
        this(method, null, parent, eventPriority);
    }

    public EventExecutable(final Field field, final Object parent, final EventPriority eventPriority) {
        this(null, field, parent, eventPriority);
    }

    public <U extends Event> EventExecutable(final Class<U> clazz, final Listener<U> listener, final Object parent, final EventPriority eventPriority) {
        this(null, null, parent, eventPriority.getPriority());
        this.listener = new ListenerHandler<>(clazz, listener);
    }

    public EventExecutable(final Method method, final Field field, final Object parent, final EventPriority eventPriority) {
        this(method, field,  parent, eventPriority.getPriority());
    }

    public EventExecutable(final Method method, final Field field, final Object parent, final int priority) {
        this.parent = parent;
        this.priority = priority;

        if (field != null) {
            try {
                field.setAccessible(true);
                this.listener = new ListenerHandler<>(field.getGenericType(), (Listener<?>) field.get(parent));
            } catch (Exception e) {
                this.listener = null;
                e.printStackTrace();
            }
        } else {
            this.listener = null;
        }

        if (method != null && method.getParameterCount() == 1)
            this.method = new MethodHandler(method, parent);
    }

    public MethodHandler getMethod() {
        return method;
    }

    public ListenerHandler<? extends Event> getListener() {
        return listener;
    }

    public Object getParent() {
        return parent;
    }

    public int getPriority() {
        return priority;
    }
}