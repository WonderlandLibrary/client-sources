package org.spongepowered.asm.mixin.throwables;

public class ClassAlreadyLoadedException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public ClassAlreadyLoadedException(final String s) {
        super(s);
    }
    
    public ClassAlreadyLoadedException(final Throwable t) {
        super(t);
    }
    
    public ClassAlreadyLoadedException(final String s, final Throwable t) {
        super(s, t);
    }
}
