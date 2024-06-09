package net.minecraft.client.triton.impl.modules.movement.phase;

import net.minecraft.client.triton.management.event.events.BoundingBoxEvent;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.management.option.types.BooleanOption;

public class PhaseMode extends BooleanOption
{
    public PhaseMode(final String name, final boolean value, final Module module) {
        super(name, name, value, module);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof PhaseMode) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof PhaseMode && option != this) {
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
    
    public boolean onUpdate(final UpdateEvent event) {
        return this.getValue();
    }
    
    public boolean onMove(final MoveEvent event) {
        return this.getValue();
    }
    
    public boolean onSetBoundingbox(final BoundingBoxEvent event) {
        return this.getValue();
    }
}
