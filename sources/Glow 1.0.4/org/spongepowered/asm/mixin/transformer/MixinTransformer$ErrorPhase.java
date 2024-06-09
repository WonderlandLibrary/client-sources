package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;

enum ErrorPhase
{
    PREPARE {
        MixinTransformer$ErrorPhase$1(final String s, final int n) {
        }
        
        @Override
        IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler mixinErrorHandler, final String s, final InvalidMixinException ex, final IMixinInfo mixinInfo, final IMixinErrorHandler.ErrorAction errorAction) {
            try {
                return mixinErrorHandler.onPrepareError(mixinInfo.getConfig(), ex, mixinInfo, errorAction);
            }
            catch (AbstractMethodError abstractMethodError) {
                return errorAction;
            }
        }
        
        @Override
        protected String getContext(final IMixinInfo mixinInfo, final String s) {
            return String.format("preparing %s in %s", mixinInfo.getName(), s);
        }
    }, 
    APPLY {
        MixinTransformer$ErrorPhase$2(final String s, final int n) {
        }
        
        @Override
        IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler mixinErrorHandler, final String s, final InvalidMixinException ex, final IMixinInfo mixinInfo, final IMixinErrorHandler.ErrorAction errorAction) {
            try {
                return mixinErrorHandler.onApplyError(s, ex, mixinInfo, errorAction);
            }
            catch (AbstractMethodError abstractMethodError) {
                return errorAction;
            }
        }
        
        @Override
        protected String getContext(final IMixinInfo mixinInfo, final String s) {
            return String.format("%s -> %s", mixinInfo, s);
        }
    };
    
    private final String text;
    private static final ErrorPhase[] $VALUES;
    
    public static ErrorPhase[] values() {
        return ErrorPhase.$VALUES.clone();
    }
    
    public static ErrorPhase valueOf(final String s) {
        return Enum.valueOf(ErrorPhase.class, s);
    }
    
    private ErrorPhase() {
        this.text = this.name().toLowerCase();
    }
    
    abstract IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler p0, final String p1, final InvalidMixinException p2, final IMixinInfo p3, final IMixinErrorHandler.ErrorAction p4);
    
    protected abstract String getContext(final IMixinInfo p0, final String p1);
    
    public String getLogMessage(final String s, final InvalidMixinException ex, final IMixinInfo mixinInfo) {
        return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(mixinInfo, s), ex.getClass().getName(), ex.getMessage());
    }
    
    public String getErrorMessage(final IMixinInfo mixinInfo, final IMixinConfig mixinConfig, final MixinEnvironment.Phase phase) {
        return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", mixinInfo, phase, mixinConfig, this.name());
    }
    
    ErrorPhase(final String s, final int n, final MixinTransformer$1 listener) {
        this();
    }
    
    static {
        $VALUES = new ErrorPhase[] { ErrorPhase.PREPARE, ErrorPhase.APPLY };
    }
}
