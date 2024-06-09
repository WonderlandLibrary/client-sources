package org.spongepowered.asm.mixin.injection.throwables;

public class InjectionError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public InjectionError() {
        super();
    }
    
    public InjectionError(final String s) {
        super(s);
    }
    
    public InjectionError(final Throwable t) {
        super(t);
    }
    
    public InjectionError(final String s, final Throwable t) {
        super(s, t);
    }
}
