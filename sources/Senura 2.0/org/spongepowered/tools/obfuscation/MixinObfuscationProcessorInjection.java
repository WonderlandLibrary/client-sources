/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Set;
/*     */ import javax.annotation.processing.RoundEnvironment;
/*     */ import javax.annotation.processing.SupportedAnnotationTypes;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
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
/*     */ 
/*     */ @SupportedAnnotationTypes({"org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At"})
/*     */ public class MixinObfuscationProcessorInjection
/*     */   extends MixinObfuscationProcessor
/*     */ {
/*     */   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
/*  68 */     if (roundEnv.processingOver()) {
/*  69 */       postProcess(roundEnv);
/*  70 */       return true;
/*     */     } 
/*     */     
/*  73 */     processMixins(roundEnv);
/*  74 */     processInjectors(roundEnv, (Class)Inject.class);
/*  75 */     processInjectors(roundEnv, (Class)ModifyArg.class);
/*  76 */     processInjectors(roundEnv, (Class)ModifyArgs.class);
/*  77 */     processInjectors(roundEnv, (Class)Redirect.class);
/*  78 */     processInjectors(roundEnv, (Class)ModifyVariable.class);
/*  79 */     processInjectors(roundEnv, (Class)ModifyConstant.class);
/*  80 */     postProcess(roundEnv);
/*     */     
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postProcess(RoundEnvironment roundEnv) {
/*  87 */     super.postProcess(roundEnv);
/*     */     
/*     */     try {
/*  90 */       this.mixins.writeReferences();
/*  91 */     } catch (Exception ex) {
/*  92 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processInjectors(RoundEnvironment roundEnv, Class<? extends Annotation> injectorClass) {
/* 101 */     for (Element elem : roundEnv.getElementsAnnotatedWith(injectorClass)) {
/* 102 */       Element parent = elem.getEnclosingElement();
/* 103 */       if (!(parent instanceof TypeElement)) {
/* 104 */         throw new IllegalStateException("@" + injectorClass.getSimpleName() + " element has unexpected parent with type " + 
/* 105 */             TypeUtils.getElementType(parent));
/*     */       }
/*     */       
/* 108 */       AnnotationHandle inject = AnnotationHandle.of(elem, injectorClass);
/*     */       
/* 110 */       if (elem.getKind() == ElementKind.METHOD) {
/* 111 */         this.mixins.registerInjector((TypeElement)parent, (ExecutableElement)elem, inject); continue;
/*     */       } 
/* 113 */       this.mixins.printMessage(Diagnostic.Kind.WARNING, "Found an @" + injectorClass
/* 114 */           .getSimpleName() + " annotation on an element which is not a method: " + elem.toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\MixinObfuscationProcessorInjection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */