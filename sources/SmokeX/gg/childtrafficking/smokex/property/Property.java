// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.property;

import java.util.function.Supplier;

public abstract class Property<T>
{
    protected final Supplier<Boolean> dependency;
    protected final String identifier;
    protected final String displayName;
    protected T value;
    
    public Property(final String identifier, final String displayName, final T value, final Supplier<Boolean> dependency) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.value = value;
        this.dependency = dependency;
    }
    
    public Property(final String identifier, final String displayName, final T value) {
        this(identifier, displayName, value, () -> true);
    }
    
    public Property(final String displayName, final T value) {
        this(displayName.replace(" ", ""), displayName, value);
    }
    
    public Property() {
        this(null, null, null, () -> true);
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.value = value;
    }
    
    public abstract boolean setValueFromString(final String p0);
    
    public abstract String getValueAsString();
}
