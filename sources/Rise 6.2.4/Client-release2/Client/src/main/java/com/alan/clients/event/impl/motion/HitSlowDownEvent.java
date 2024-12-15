package com.alan.clients.event.impl.motion;

import com.alan.clients.event.CancellableEvent;
import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptHitSlowDownEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class HitSlowDownEvent extends CancellableEvent {
    public double slowDown;
    public boolean sprint;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptHitSlowDownEvent(this);
    }
}
