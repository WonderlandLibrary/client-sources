/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.movement.nofall.Mineplex;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;

@Module.Mod
public class Nofall
extends Module {
    public Mineplex nc = new Mineplex("Mineplex", true, this);

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.nc);
        super.preInitialize();
    }

    @Override
    public void enable() {
        this.nc.enable();
        super.enable();
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.nc.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.nc.getValue()).booleanValue()) {
            this.setSuffix("Mineplex");
        }
    }
}

