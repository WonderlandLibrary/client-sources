package com.polarware.event.impl.other;


import com.polarware.module.Module;
import com.polarware.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ModuleToggleEvent implements Event {
    private Module module;
}