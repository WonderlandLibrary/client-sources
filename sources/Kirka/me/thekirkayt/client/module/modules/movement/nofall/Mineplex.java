/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement.nofall;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.movement.nofall.NofallMode;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;

@Module.Mod(displayName="Mineplex")
public class Mineplex
extends NofallMode {
    public Mineplex(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @EventTarget
    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (!event.shouldAlwaysSend()) {
            event.setGround(true);
        }
        return true;
    }
}

