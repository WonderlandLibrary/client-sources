package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptPreUpdateEvent extends ScriptEvent<PreUpdateEvent> {

    public ScriptPreUpdateEvent(final PreUpdateEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onPreUpdate";
    }
}
