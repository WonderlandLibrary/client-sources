package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

public interface IInjectionPointContext {
   IMixinContext getContext();

   MethodNode getMethod();

   AnnotationNode getAnnotation();
}
