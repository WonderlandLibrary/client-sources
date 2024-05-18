// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement.speed;

import me.chrest.event.events.UpdateEvent;
import me.chrest.event.events.MoveEvent;
import java.util.Iterator;
import me.chrest.client.option.Option;
import me.chrest.client.option.OptionManager;
import me.chrest.client.module.Module;
import me.chrest.client.option.types.BooleanOption;

public class SpeedMode extends BooleanOption
{
    public SpeedMode(final String name, final boolean value, final Module module) {
        super(name, name, value, module);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof SpeedMode) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof SpeedMode && option != this) {
                    ((BooleanOption)option).setValueHard(true);
                    break;
                }
            }
        }
        super.setValue(value);
    }
    
    public boolean enable() {
        return this.getValue();
    }
    
    public boolean onMove(final MoveEvent event) {
        return this.getValue();
    }
    
    public boolean onUpdate(final UpdateEvent event) {
        return this.getValue();
    }
}
