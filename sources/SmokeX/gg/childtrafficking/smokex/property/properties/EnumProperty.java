// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.property.properties;

import java.util.function.Supplier;
import gg.childtrafficking.smokex.property.Property;

public final class EnumProperty<T extends Enum<?>> extends Property<T>
{
    private final T[] values;
    
    public EnumProperty(final String identifier, final String displayName, final T value, final Supplier<Boolean> dependency) {
        super(identifier, displayName, value, dependency);
        this.values = this.getEnumConstants();
    }
    
    public EnumProperty(final String identifier, final String displayName, final T value) {
        super(identifier, displayName, value, () -> true);
        this.values = null;
    }
    
    public EnumProperty(final String displayName, final T value) {
        super(displayName, displayName, value, () -> true);
        this.values = null;
    }
    
    @Override
    public boolean setValueFromString(final String value) {
        for (final Enum enumConstant : (Enum[])this.getValue().getClass().getEnumConstants()) {
            if (enumConstant.name().equalsIgnoreCase(value)) {
                try {
                    this.setValue((T)enumConstant);
                    return true;
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public T[] getValues() {
        return this.values;
    }
    
    public void setValue(final int index) {
        this.setValue(this.values[index]);
    }
    
    public T[] getEnumConstants() {
        return (T[])this.value.getClass().getEnumConstants();
    }
    
    @Override
    public String getValueAsString() {
        return this.value.toString();
    }
}
