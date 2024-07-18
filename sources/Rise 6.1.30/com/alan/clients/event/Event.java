package com.alan.clients.event;

import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public interface Event {
    default ScriptEvent<? extends Event> getScriptEvent() {
        return null;
    }
}
