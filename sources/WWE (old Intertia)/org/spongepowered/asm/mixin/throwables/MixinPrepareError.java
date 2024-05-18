package org.spongepowered.asm.mixin.throwables;

public class MixinPrepareError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public MixinPrepareError(final String s) {
        super(s);
    }
    
    public MixinPrepareError(final Throwable t) {
        super(t);
    }
    
    public MixinPrepareError(final String s, final Throwable t) {
        super(s, t);
    }
}
