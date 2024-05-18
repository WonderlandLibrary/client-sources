package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;

static class Accessor extends SubType
{
    private final Collection<String> interfaces;
    
    Accessor(final MixinInfo mixinInfo) {
        super(mixinInfo, "@Mixin", false);
        (this.interfaces = new ArrayList<String>()).add(mixinInfo.getClassRef());
    }
    
    @Override
    boolean isLoadable() {
        return true;
    }
    
    @Override
    Collection<String> getInterfaces() {
        return this.interfaces;
    }
    
    @Override
    void validateTarget(final String s, final ClassInfo classInfo) {
        if (classInfo.isInterface() && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
            throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
        }
    }
    
    @Override
    void validate(final State state, final List<ClassInfo> list) {
        final MixinClassNode classNode = state.getClassNode();
        if (!"java/lang/Object".equals(classNode.superName)) {
            throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + classNode.superName.replace('/', '.'));
        }
    }
    
    @Override
    MixinPreProcessorStandard createPreProcessor(final MixinClassNode mixinClassNode) {
        return new MixinPreProcessorAccessor(this.mixin, mixinClassNode);
    }
}
