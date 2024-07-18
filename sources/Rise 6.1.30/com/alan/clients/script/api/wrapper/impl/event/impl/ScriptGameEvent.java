package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.other.GameEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptGameEvent extends ScriptEvent<GameEvent> {

    public ScriptGameEvent(final GameEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onGame";
    }
}
