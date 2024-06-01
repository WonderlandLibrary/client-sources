package io.github.liticane.electron.event.api;

import io.github.liticane.electron.event.Event;
import io.github.liticane.electron.event.api.handler.EventExecutable;
import io.github.liticane.electron.event.api.listener.EventHandler;
import io.github.liticane.electron.event.api.listener.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unused")
public final class EventBus implements Bus {

    /**
     * List of all executables in the event system
     */
    private final CopyOnWriteArrayList<EventExecutable> executables = new CopyOnWriteArrayList<>();

    /**
     * Register an object in the event system
     *
     * @param object the object to register
     */
    @Override
    public void subscribe(final Object object) {
        if (subscribed(object))
            return;

        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)
                    || method.getParameterCount() <= 0) continue;
            executables.add(new EventExecutable(method, object, method.getDeclaredAnnotation(EventHandler.class).value()));
        }

        for (final Field field : object.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(EventHandler.class)
                    || !field.getType().isAssignableFrom(Listener.class)) continue;

            executables.add(new EventExecutable(field, object, field.getDeclaredAnnotation(EventHandler.class).value()));
        }

        executables.sort(Comparator.comparingInt(EventExecutable::getPriority));
    }

    /**
     * Unregister an object from the event system
     *
     * @param object the object to unregister
     */
    @Override
    public void unsubscribe(final Object object) {
        if (!subscribed(object)) return;
        executables.removeIf(e -> e.getParent().equals(object));
    }

    /**
     * Method used for calling events
     *
     * @param event the event that should be called
     */
    @Override
    public <U extends Event> void publish(final U event) {
        for (EventExecutable eventExecutable : executables) {
            if (eventExecutable.getListener() != null)
                eventExecutable.getListener().call(event);
            if (eventExecutable.getMethod() != null)
                eventExecutable.getMethod().call(event);
        }
    }

    /**
     * Method used to check whether an object is registered in the event system
     *
     * @param object the object to check
     */
    public boolean subscribed(final Object object) {
        return executables.stream()
                .anyMatch(e -> e.getParent().equals(object));
    }

}