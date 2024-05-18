package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;

class MixinPreProcessorInterface extends MixinPreProcessorStandard
{
    MixinPreProcessorInterface(final MixinInfo mixinInfo, final MixinInfo.MixinClassNode mixinClassNode) {
        super(mixinInfo, mixinClassNode);
    }
    
    @Override
    protected void prepareMethod(final MixinInfo.MixinMethodNode mixinMethodNode, final ClassInfo.Method method) {
        if (!Bytecode.hasFlag(mixinMethodNode, 1) && !Bytecode.hasFlag(mixinMethodNode, 4096)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + method + " in " + this.mixin);
        }
        super.prepareMethod(mixinMethodNode, method);
    }
    
    @Override
    protected boolean validateField(final MixinTargetContext mixinTargetContext, final FieldNode fieldNode, final AnnotationNode annotationNode) {
        if (!Bytecode.hasFlag(fieldNode, 8)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + fieldNode.name + " in " + this.mixin);
        }
        return super.validateField(mixinTargetContext, fieldNode, annotationNode);
    }
}
