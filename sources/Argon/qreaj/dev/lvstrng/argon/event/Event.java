package dev.lvstrng.argon.event;

import java.util.ArrayList;

public abstract class Event {
    public abstract void callListeners(final ArrayList<EventListener> listeners);

    public abstract Class<?> getClazz();
}
