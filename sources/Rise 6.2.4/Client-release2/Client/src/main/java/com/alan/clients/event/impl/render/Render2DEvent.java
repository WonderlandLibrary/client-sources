package com.alan.clients.event.impl.render;

import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptRender2DEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public final class Render2DEvent implements Event {

    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptRender2DEvent(this);
    }
}
