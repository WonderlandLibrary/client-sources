package com.alan.clients.event.impl.motion;

import com.alan.clients.event.CancellableEvent;
import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptJumpEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JumpEvent extends CancellableEvent {
    private float jumpMotion;
    private float yaw;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptJumpEvent(this);
    }
}
