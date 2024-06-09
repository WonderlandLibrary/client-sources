package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.google.common.base.*;

public class CallbackInjectionInfo extends InjectionInfo
{
    protected CallbackInjectionInfo(final MixinTargetContext mixinTargetContext, final MethodNode methodNode, final AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode annotationNode) {
        return new CallbackInjector(this, Annotations.getValue(annotationNode, "cancellable", Boolean.FALSE), Annotations.getValue(annotationNode, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE), Annotations.getValue(annotationNode, "id", ""));
    }
    
    @Override
    public String getSliceId(final String s) {
        return Strings.nullToEmpty(s);
    }
}
