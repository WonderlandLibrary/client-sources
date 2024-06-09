/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpBypass2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.client.option.types.BooleanOption;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;

public class LongJumpBypassMode
extends BooleanOption {
    public static Minecraft mc = Minecraft.getMinecraft();

    public LongJumpBypassMode(String name, boolean value, Module module) {
        super(name, name, value, module, true);
    }

    @Override
    public void setValue(Boolean value) {
        if (value.booleanValue()) {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof LongJumpBypassMode)) continue;
                ((BooleanOption)option).setValueHard(false);
            }
        } else {
            for (Option option : OptionManager.getOptionList()) {
                if (!option.getModule().equals(this.getModule()) || !(option instanceof LongJumpBypassMode) || option == this) continue;
                ((BooleanOption)option).setValueHard(true);
                break;
            }
        }
        super.setValue(value);
    }

    public boolean enable() {
        return (Boolean)this.getValue();
    }

    public boolean onTick(TickEvent e) {
        return (Boolean)this.getValue();
    }

    public boolean onMove(MoveEvent event) {
        return (Boolean)this.getValue();
    }

    public boolean onUpdate(UpdateEvent event) {
        return (Boolean)this.getValue();
    }

    public boolean disable() {
        return (Boolean)this.getValue();
    }
}

