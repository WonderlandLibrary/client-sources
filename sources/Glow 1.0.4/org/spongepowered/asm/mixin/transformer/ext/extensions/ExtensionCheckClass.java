package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.lib.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.throwables.*;

public class ExtensionCheckClass implements IExtension
{
    public ExtensionCheckClass() {
        super();
    }
    
    @Override
    public boolean checkActive(final MixinEnvironment mixinEnvironment) {
        return mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
    }
    
    @Override
    public void preApply(final ITargetClassContext targetClassContext) {
    }
    
    @Override
    public void postApply(final ITargetClassContext targetClassContext) {
        try {
            targetClassContext.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
        }
        catch (RuntimeException ex) {
            throw new ValidationFailedException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public void export(final MixinEnvironment mixinEnvironment, final String s, final boolean b, final byte[] array) {
    }
    
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
}
