package io.github.liticane.electron.event.api.listener;

import io.github.liticane.electron.event.Event;

/**
 * @author Solastis
 * DATE:19.12.21
 */
public interface Listener<T extends Event> {

    /**
     * Method for calling an event
     *
     * @param event event that should be called
     */
    void call(final T event);
}
