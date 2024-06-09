/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpApple2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;

public class LongJumpAppleMode
extends BooleanOption {
    public LongJumpAppleMode(String name, boolean value, Module module) {
        super(name, name, value, module, true);
    }

    @Override
    public void setValue(Boolean value) {
        if (value.booleanValue()) {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof LongJumpAppleMode)) continue;
                ((BooleanOption)option).setValueHard(false);
            }
        } else {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof LongJumpAppleMode) || option == this) continue;
                ((BooleanOption)option).setValueHard(true);
                break;
            }
        }
        super.setValue(value);
    }

    public boolean enable() {
        return (Boolean)this.getValue();
    }

    public boolean disable() {
        return (Boolean)this.getValue();
    }

    public boolean onMove(MoveEvent event) {
        return (Boolean)this.getValue();
    }

    public boolean onUpdate(UpdateEvent event) {
        return (Boolean)this.getValue();
    }
}

