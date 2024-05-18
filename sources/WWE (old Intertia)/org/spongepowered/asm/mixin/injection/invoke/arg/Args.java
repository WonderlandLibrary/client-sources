package org.spongepowered.asm.mixin.injection.invoke.arg;

public abstract class Args
{
    protected final Object[] values;
    
    protected Args(final Object[] values) {
        super();
        this.values = values;
    }
    
    public int size() {
        return this.values.length;
    }
    
    public <T> T get(final int n) {
        return (T)this.values[n];
    }
    
    public abstract <T> void set(final int p0, final T p1);
    
    public abstract void setAll(final Object... p0);
}
