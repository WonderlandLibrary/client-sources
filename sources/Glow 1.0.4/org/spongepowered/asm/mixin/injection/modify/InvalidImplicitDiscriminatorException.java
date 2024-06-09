package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.*;

public class InvalidImplicitDiscriminatorException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidImplicitDiscriminatorException(final String s) {
        super(s);
    }
    
    public InvalidImplicitDiscriminatorException(final String s, final Throwable t) {
        super(s, t);
    }
}
