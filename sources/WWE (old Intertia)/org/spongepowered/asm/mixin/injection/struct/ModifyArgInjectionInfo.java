package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.invoke.*;

public class ModifyArgInjectionInfo extends InjectionInfo
{
    public ModifyArgInjectionInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode, final AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode annotationNode) {
        return new ModifyArgInjector(this, Annotations.getValue(annotationNode, "index", -1));
    }
    
    @Override
    protected String getDescription() {
        return "Argument modifier method";
    }
}
