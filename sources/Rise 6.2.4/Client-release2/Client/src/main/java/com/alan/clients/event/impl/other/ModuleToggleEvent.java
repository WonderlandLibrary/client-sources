package com.alan.clients.event.impl.other;

import com.alan.clients.event.Event;
import com.alan.clients.module.Module;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptModuleToggleEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ModuleToggleEvent implements Event {
    private Module module;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptModuleToggleEvent(this);
    }
}