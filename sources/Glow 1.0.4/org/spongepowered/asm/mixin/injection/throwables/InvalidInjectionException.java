package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.refmap.*;

public class InvalidInjectionException extends InvalidMixinException
{
    private static final long serialVersionUID = 2L;
    private final InjectionInfo info;
    
    public InvalidInjectionException(final IMixinContext mixinContext, final String s) {
        super(mixinContext, s);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo info, final String s) {
        super(info.getContext(), s);
        this.info = info;
    }
    
    public InvalidInjectionException(final IMixinContext mixinContext, final Throwable t) {
        super(mixinContext, t);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo info, final Throwable t) {
        super(info.getContext(), t);
        this.info = info;
    }
    
    public InvalidInjectionException(final IMixinContext mixinContext, final String s, final Throwable t) {
        super(mixinContext, s, t);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo info, final String s, final Throwable t) {
        super(info.getContext(), s, t);
        this.info = info;
    }
    
    public InjectionInfo getInjectionInfo() {
        return this.info;
    }
}
