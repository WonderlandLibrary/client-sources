package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

public class MixinReloadException extends MixinException
{
    private static final long serialVersionUID = 2L;
    private final IMixinInfo mixinInfo;
    
    public MixinReloadException(final IMixinInfo mixinInfo, final String s) {
        super(s);
        this.mixinInfo = mixinInfo;
    }
    
    public IMixinInfo getMixinInfo() {
        return this.mixinInfo;
    }
}
