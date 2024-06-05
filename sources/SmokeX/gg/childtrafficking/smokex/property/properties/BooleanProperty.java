// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.property.properties;

import java.util.function.Supplier;
import gg.childtrafficking.smokex.property.Property;

public final class BooleanProperty extends Property<Boolean>
{
    public BooleanProperty(final String identifier, final String displayName, final Boolean value, final Supplier<Boolean> supplier) {
        super(identifier, displayName, value, supplier);
    }
    
    public BooleanProperty(final String identifier, final String displayName, final Boolean value) {
        this(identifier, displayName, value, () -> true);
    }
    
    public BooleanProperty(final String displayName, final Boolean value) {
        super(displayName, value);
    }
    
    @Override
    public boolean setValueFromString(final String value) {
        try {
            this.setValue(Boolean.parseBoolean(value));
            return true;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String getValueAsString() {
        return this.getValue().toString();
    }
}
