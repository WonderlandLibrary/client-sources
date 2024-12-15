package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptTickEvent extends ScriptEvent<TickEvent> {

    public ScriptTickEvent(final TickEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onTick";
    }
}
