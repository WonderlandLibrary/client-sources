/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ class AnnotatedMixinElementHandlerOverwrite
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static class AnnotatedElementOverwrite
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     
/*     */     public AnnotatedElementOverwrite(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/*  51 */       super(element, annotation);
/*  52 */       this.shouldRemap = shouldRemap;
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  56 */       return this.shouldRemap;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/*  62 */     super(ap, mixin);
/*     */   }
/*     */   
/*     */   public void registerMerge(ExecutableElement method) {
/*  66 */     validateTargetMethod(method, null, new AnnotatedMixinElementHandler.AliasedElementName(method, AnnotationHandle.MISSING), "overwrite", true, true);
/*     */   }
/*     */   
/*     */   public void registerOverwrite(AnnotatedElementOverwrite elem) {
/*  70 */     AnnotatedMixinElementHandler.AliasedElementName name = new AnnotatedMixinElementHandler.AliasedElementName(elem.getElement(), elem.getAnnotation());
/*  71 */     validateTargetMethod(elem.getElement(), elem.getAnnotation(), name, "@Overwrite", true, false);
/*  72 */     checkConstraints(elem.getElement(), elem.getAnnotation());
/*     */     
/*  74 */     if (elem.shouldRemap()) {
/*  75 */       for (TypeHandle target : this.mixin.getTargets()) {
/*  76 */         if (!registerOverwriteForTarget(elem, target)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  82 */     if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
/*  83 */       Diagnostic.Kind overwriteErrorKind = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
/*     */ 
/*     */       
/*  86 */       String javadoc = this.ap.getJavadocProvider().getJavadoc(elem.getElement());
/*  87 */       if (javadoc == null) {
/*  88 */         this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing javadoc comment", elem.getElement());
/*     */         
/*     */         return;
/*     */       } 
/*  92 */       if (!javadoc.toLowerCase().contains("@author")) {
/*  93 */         this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @author tag", elem.getElement());
/*     */       }
/*     */       
/*  96 */       if (!javadoc.toLowerCase().contains("@reason")) {
/*  97 */         this.ap.printMessage(overwriteErrorKind, "@Overwrite is missing an @reason tag", elem.getElement());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean registerOverwriteForTarget(AnnotatedElementOverwrite elem, TypeHandle target) {
/* 103 */     MappingMethod targetMethod = target.getMappingMethod(elem.getSimpleName(), elem.getDesc());
/* 104 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
/*     */     
/* 106 */     if (obfData.isEmpty()) {
/* 107 */       Diagnostic.Kind error = Diagnostic.Kind.ERROR;
/*     */ 
/*     */       
/*     */       try {
/* 111 */         Method md = elem.getElement().getClass().getMethod("isStatic", new Class[0]);
/* 112 */         if (((Boolean)md.invoke(elem.getElement(), new Object[0])).booleanValue()) {
/* 113 */           error = Diagnostic.Kind.WARNING;
/*     */         }
/* 115 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 119 */       this.ap.printMessage(error, "No obfuscation mapping for @Overwrite method", elem.getElement());
/* 120 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 124 */       addMethodMappings(elem.getSimpleName(), elem.getDesc(), obfData);
/* 125 */     } catch (MappingConflictException ex) {
/* 126 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex
/* 127 */           .getOld().getSimpleName());
/* 128 */       return false;
/*     */     } 
/* 130 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerOverwrite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */