package Snipeware.api.bus;

import Snipeware.events.Event;

/**
 * @since 1.4.0
 */

public interface Bus<T extends Object> {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(T event);
}
