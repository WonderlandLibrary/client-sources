package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

static class Interface extends SubType
{
    Interface(final MixinInfo mixinInfo) {
        super(mixinInfo, "@Mixin", true);
    }
    
    @Override
    void validate(final State state, final List<ClassInfo> list) {
        if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
            throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
        }
        final MixinClassNode classNode = state.getClassNode();
        if (!"java/lang/Object".equals(classNode.superName)) {
            throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName.replace('/', '.'));
        }
    }
    
    @Override
    MixinPreProcessorStandard createPreProcessor(final MixinClassNode mixinClassNode) {
        return new MixinPreProcessorInterface(this.mixin, mixinClassNode);
    }
}
