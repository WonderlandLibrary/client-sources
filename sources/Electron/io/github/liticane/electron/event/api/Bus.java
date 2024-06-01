package io.github.liticane.electron.event.api;

import io.github.liticane.electron.event.Event;

public interface Bus {
    void subscribe(Object object);

    void unsubscribe(Object object);

    <U extends Event> void publish(U event);
}
