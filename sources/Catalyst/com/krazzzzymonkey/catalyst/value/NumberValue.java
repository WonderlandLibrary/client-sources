// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.value;

public class NumberValue extends Value<Double>
{
    protected /* synthetic */ Double max;
    protected /* synthetic */ Double min;
    
    public NumberValue(final String llIIlIlIllIllII, final Double llIIlIlIllIIllI, final Double llIIlIlIllIlIlI, final Double llIIlIlIllIIlII) {
        super(llIIlIlIllIllII, llIIlIlIllIIllI);
        this.min = llIIlIlIllIlIlI;
        this.max = llIIlIlIllIIlII;
    }
    
    public Double getMax() {
        return this.max;
    }
    
    public Double getMin() {
        return this.min;
    }
}
