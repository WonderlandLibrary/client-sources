package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.input.ClickEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptClickEvent extends ScriptEvent<ClickEvent> {

    public ScriptClickEvent(final ClickEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onClick";
    }
}
