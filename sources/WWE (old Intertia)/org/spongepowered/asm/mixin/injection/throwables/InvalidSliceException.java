package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.code.*;

public class InvalidSliceException extends InvalidInjectionException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidSliceException(final IMixinContext mixinContext, final String s) {
        super(mixinContext, s);
    }
    
    public InvalidSliceException(final ISliceContext sliceContext, final String s) {
        super(sliceContext.getContext(), s);
    }
    
    public InvalidSliceException(final IMixinContext mixinContext, final Throwable t) {
        super(mixinContext, t);
    }
    
    public InvalidSliceException(final ISliceContext sliceContext, final Throwable t) {
        super(sliceContext.getContext(), t);
    }
    
    public InvalidSliceException(final IMixinContext mixinContext, final String s, final Throwable t) {
        super(mixinContext, s, t);
    }
    
    public InvalidSliceException(final ISliceContext sliceContext, final String s, final Throwable t) {
        super(sliceContext.getContext(), s, t);
    }
}
