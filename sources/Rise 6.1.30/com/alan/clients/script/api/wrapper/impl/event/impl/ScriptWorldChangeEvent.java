package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptWorldChangeEvent extends ScriptEvent<WorldChangeEvent> {

    public ScriptWorldChangeEvent(final WorldChangeEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onWorldChange";
    }
}
