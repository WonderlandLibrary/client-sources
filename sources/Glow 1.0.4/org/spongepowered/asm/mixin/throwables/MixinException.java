package org.spongepowered.asm.mixin.throwables;

public class MixinException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public MixinException() {
        super();
    }
    
    public MixinException(final String s) {
        super(s);
    }
    
    public MixinException(final Throwable t) {
        super(t);
    }
    
    public MixinException(final String s, final Throwable t) {
        super(s, t);
    }
}
