// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.value;

import com.klintos.twelve.utils.FileUtils;

public class ValueString extends Value
{
    private String value;
    
    public ValueString(final String name, final String value) {
        super(name);
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
        FileUtils.saveValues();
    }
}
