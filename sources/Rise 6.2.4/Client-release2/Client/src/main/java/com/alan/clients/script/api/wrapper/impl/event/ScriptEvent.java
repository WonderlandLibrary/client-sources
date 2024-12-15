package com.alan.clients.script.api.wrapper.impl.event;

import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.ScriptWrapper;

public abstract class ScriptEvent<T extends Event> extends ScriptWrapper<T> {

    public ScriptEvent(final T wrappedEvent) {
        super(wrappedEvent);
    }

    public abstract String getHandlerName();
}
