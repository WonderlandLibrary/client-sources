package me.tojatta.api.utilities.value.impl;

import me.tojatta.api.utilities.value.*;
import me.tojatta.api.utilities.value.interfaces.*;
import java.lang.reflect.*;

public class TypeBoolean extends Value<Boolean> implements Toggleable
{
    public TypeBoolean(final String label, final Object object, final Field field) {
        super(label, object, field);
    }
    
    @Override
    public boolean isEnabled() {
        return this.getValue();
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        this.setValue(enabled);
        if (enabled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        this.onToggle();
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onToggle() {
    }
    
    @Override
    public void toggle() {
        this.setEnabled(!this.isEnabled());
    }
}
