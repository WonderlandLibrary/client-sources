// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.combat.aura;

import java.util.Iterator;

import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.management.option.types.BooleanOption;

public class AuraMode extends BooleanOption
{
    public AuraMode(final String name, final boolean value, final Module module) {
        super(name, name, value, module);
    }
    
    @Override
    public void setValue(final Boolean value) {
        if (value) {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof AuraMode) {
                    ((BooleanOption)option).setValueHard(false);
                }
            }
        }
        else {
            for (final Option option : OptionManager.getOptionList()) {
                if (option.getModule().equals(this.getModule()) && option instanceof AuraMode && option != this) {
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
    
    public boolean disable()
    {
      return this.getValue();
    }
    
    public boolean onUpdate(final UpdateEvent event) {
        return this.getValue();
    }
}
