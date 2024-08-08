package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public abstract class SpecialMethodInfo implements IInjectionPointContext {
   protected final AnnotationNode annotation;
   protected final ClassNode classNode;
   protected final MethodNode method;
   protected final MixinTargetContext mixin;

   public SpecialMethodInfo(MixinTargetContext var1, MethodNode var2, AnnotationNode var3) {
      this.mixin = var1;
      this.method = var2;
      this.annotation = var3;
      this.classNode = var1.getTargetClassNode();
   }

   public final IMixinContext getContext() {
      return this.mixin;
   }

   public final AnnotationNode getAnnotation() {
      return this.annotation;
   }

   public final ClassNode getClassNode() {
      return this.classNode;
   }

   public final MethodNode getMethod() {
      return this.method;
   }
}
