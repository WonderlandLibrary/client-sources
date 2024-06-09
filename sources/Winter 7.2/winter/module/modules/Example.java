/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class Example
extends Module {
    public Example() {
        super("Example", Module.Category.Other, -1);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
    }
}

