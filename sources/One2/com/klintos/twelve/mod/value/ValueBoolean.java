// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.value;

import com.klintos.twelve.utils.FileUtils;

public class ValueBoolean extends Value
{
    private boolean value;
    
    public ValueBoolean(final String name, final boolean value) {
        super(name);
        this.value = value;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public void setValue(final boolean value) {
        this.value = value;
        FileUtils.saveValues();
    }
}
