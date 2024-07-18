package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.script.api.wrapper.impl.event.CancellableScriptEvent;
public class ScriptPacketSendEvent extends CancellableScriptEvent<PacketSendEvent> {

    public ScriptPacketSendEvent(final PacketSendEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onPacketSend";
    }
}
