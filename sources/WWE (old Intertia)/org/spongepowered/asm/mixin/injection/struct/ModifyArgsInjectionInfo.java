package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.invoke.*;

public class ModifyArgsInjectionInfo extends InjectionInfo
{
    public ModifyArgsInjectionInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode, final AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode annotationNode) {
        return new ModifyArgsInjector(this);
    }
    
    @Override
    protected String getDescription() {
        return "Multi-argument modifier method";
    }
}
