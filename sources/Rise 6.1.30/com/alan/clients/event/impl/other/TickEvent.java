package com.alan.clients.event.impl.other;

import com.alan.clients.event.CancellableEvent;
import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptTickEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class TickEvent extends CancellableEvent {
    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptTickEvent(this);
    }
}
