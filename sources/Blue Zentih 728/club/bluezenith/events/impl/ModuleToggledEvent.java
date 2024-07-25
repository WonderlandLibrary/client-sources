package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import club.bluezenith.module.Module;

public class ModuleToggledEvent extends Event {
    public final Module module;

    public final boolean isEnabled;

    public ModuleToggledEvent(Module module, boolean isEnabled) {
        this.module = module;
        this.isEnabled = isEnabled;
    }
}
