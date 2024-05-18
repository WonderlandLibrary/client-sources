package org.spongepowered.asm.util;

public final class Counter
{
    public int value;
    
    public Counter() {
        super();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o.getClass() == Counter.class && ((Counter)o).value == this.value;
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
}
