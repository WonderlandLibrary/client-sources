// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.value;

public class Value<T>
{
    private /* synthetic */ T defaultValue;
    private /* synthetic */ String name;
    public /* synthetic */ T value;
    
    public void setValue(final T lllIIllIIlllIll) {
        this.value = lllIIllIIlllIll;
    }
    
    public Value(final String lllIIllIlIIlIll, final T lllIIllIlIIlIlI) {
        this.name = lllIIllIlIIlIll;
        this.defaultValue = lllIIllIlIIlIlI;
        this.value = lllIIllIlIIlIlI;
    }
    
    public T getDefaultValue() {
        return this.defaultValue;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public String getName() {
        return this.name;
    }
}
