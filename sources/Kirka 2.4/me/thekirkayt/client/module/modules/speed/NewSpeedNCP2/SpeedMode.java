/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.NewSpeedNCP2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;

public class SpeedMode
extends BooleanOption {
    public SpeedMode(String name, boolean value, Module module) {
        super(name, name, value, module, true);
    }

    @Override
    public void setValue(Boolean value) {
        if (value.booleanValue()) {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof SpeedMode)) continue;
                ((BooleanOption)option).setValueHard(false);
            }
        } else {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof SpeedMode) || option == this) continue;
                ((BooleanOption)option).setValueHard(true);
                break;
            }
        }
        super.setValue(value);
    }

    public boolean enable() {
        return (Boolean)this.getValue();
    }

    public boolean onTick(TickEvent event) {
        return (Boolean)this.getValue();
    }

    public boolean onMove(MoveEvent event) {
        return (Boolean)this.getValue();
    }

    public boolean onUpdate(UpdateEvent event) {
        return (Boolean)this.getValue();
    }
}

