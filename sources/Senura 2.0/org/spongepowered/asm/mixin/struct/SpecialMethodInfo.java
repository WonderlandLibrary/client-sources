/*     */ package org.spongepowered.asm.mixin.struct;
/*     */ 
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpecialMethodInfo
/*     */   implements IInjectionPointContext
/*     */ {
/*     */   protected final AnnotationNode annotation;
/*     */   protected final ClassNode classNode;
/*     */   protected final MethodNode method;
/*     */   protected final MixinTargetContext mixin;
/*     */   
/*     */   public SpecialMethodInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*  60 */     this.mixin = mixin;
/*  61 */     this.method = method;
/*  62 */     this.annotation = annotation;
/*  63 */     this.classNode = mixin.getTargetClassNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final IMixinContext getContext() {
/*  73 */     return (IMixinContext)this.mixin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AnnotationNode getAnnotation() {
/*  83 */     return this.annotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ClassNode getClassNode() {
/*  92 */     return this.classNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodNode getMethod() {
/* 102 */     return this.method;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\struct\SpecialMethodInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */