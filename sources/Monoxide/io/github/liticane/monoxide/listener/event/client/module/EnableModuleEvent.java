package io.github.liticane.monoxide.listener.event.client.module;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.listener.event.Event;

public class EnableModuleEvent extends Event {
    private final Module module;
    final EnableModuleEvent.Type type;

    public enum Type {
        PRE, POST
    }

    public EnableModuleEvent(Module module, Type type) {
        this.module = module;
        this.type = type;
    }

    public Module getModule() {
        return module;
    }

    public Type getType() {
        return type;
    }
}
