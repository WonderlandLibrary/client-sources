// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.value;

import com.klintos.twelve.utils.FileUtils;

public class ValueDouble extends Value
{
    private String name;
    private double value;
    private double min;
    private double max;
    private int round;
    
    public ValueDouble(final String name, final double value, final double min, final double max, final int round) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
        this.round = round;
    }
    
    public ValueDouble(final String name, final double value, final double min, final double max) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
        this.round = 1;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(final double value) {
        this.value = value;
        FileUtils.saveValues();
    }
    
    public int getRound() {
        return this.round;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
}
