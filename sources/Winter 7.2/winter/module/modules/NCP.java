/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;

public class NCP
extends Module {
    public NCP() {
        super("NCP", Module.Category.Other, -1);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onTick(TickEvent event) {
        this.visible(false);
    }
}

