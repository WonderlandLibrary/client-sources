package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.refmap.*;

public class InvalidAccessorException extends InvalidMixinException
{
    private static final long serialVersionUID = 2L;
    private final AccessorInfo info;
    
    public InvalidAccessorException(final IMixinContext mixinContext, final String s) {
        super(mixinContext, s);
        this.info = null;
    }
    
    public InvalidAccessorException(final AccessorInfo info, final String s) {
        super(info.getContext(), s);
        this.info = info;
    }
    
    public InvalidAccessorException(final IMixinContext mixinContext, final Throwable t) {
        super(mixinContext, t);
        this.info = null;
    }
    
    public InvalidAccessorException(final AccessorInfo info, final Throwable t) {
        super(info.getContext(), t);
        this.info = info;
    }
    
    public InvalidAccessorException(final IMixinContext mixinContext, final String s, final Throwable t) {
        super(mixinContext, s, t);
        this.info = null;
    }
    
    public InvalidAccessorException(final AccessorInfo info, final String s, final Throwable t) {
        super(info.getContext(), s, t);
        this.info = info;
    }
    
    public AccessorInfo getAccessorInfo() {
        return this.info;
    }
}
