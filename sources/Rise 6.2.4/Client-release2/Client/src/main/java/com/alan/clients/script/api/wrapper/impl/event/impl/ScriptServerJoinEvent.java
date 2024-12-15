package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.other.ServerJoinEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptServerJoinEvent extends ScriptEvent<ServerJoinEvent> {

    public ScriptServerJoinEvent(final ServerJoinEvent wrappedEvent) {
        super(wrappedEvent);
    }

    public String getIp() {
        return wrapped.getIp();
    }

    public int getPort() {
        return wrapped.getPort();
    }

    @Override
    public String getHandlerName() {
        return "onServerJoin";
    }
}
