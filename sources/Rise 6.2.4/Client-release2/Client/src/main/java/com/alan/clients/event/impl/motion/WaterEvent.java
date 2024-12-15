package com.alan.clients.event.impl.motion;

import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptWaterEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WaterEvent implements Event {
    private boolean water;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptWaterEvent(this);
    }
}
