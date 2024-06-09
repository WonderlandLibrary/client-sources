package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.throwables.*;

public static class ValidationFailedException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public ValidationFailedException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ValidationFailedException(final String s) {
        super(s);
    }
    
    public ValidationFailedException(final Throwable t) {
        super(t);
    }
}
