package com.alan.clients.event.impl.render;

import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptRender3DEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Render3DEvent implements Event {

    private final float partialTicks;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptRender3DEvent(this);
    }
}
