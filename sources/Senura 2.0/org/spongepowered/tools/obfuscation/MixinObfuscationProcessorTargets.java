/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.Set;
/*     */ import javax.annotation.processing.RoundEnvironment;
/*     */ import javax.annotation.processing.SupportedAnnotationTypes;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.Implements;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
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
/*     */ @SupportedAnnotationTypes({"org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements"})
/*     */ public class MixinObfuscationProcessorTargets
/*     */   extends MixinObfuscationProcessor
/*     */ {
/*     */   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
/*  66 */     if (roundEnv.processingOver()) {
/*  67 */       postProcess(roundEnv);
/*  68 */       return true;
/*     */     } 
/*     */     
/*  71 */     processMixins(roundEnv);
/*  72 */     processShadows(roundEnv);
/*  73 */     processOverwrites(roundEnv);
/*  74 */     processAccessors(roundEnv);
/*  75 */     processInvokers(roundEnv);
/*  76 */     processImplements(roundEnv);
/*  77 */     postProcess(roundEnv);
/*     */     
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postProcess(RoundEnvironment roundEnv) {
/*  84 */     super.postProcess(roundEnv);
/*     */     
/*     */     try {
/*  87 */       this.mixins.writeReferences();
/*  88 */       this.mixins.writeMappings();
/*  89 */     } catch (Exception ex) {
/*  90 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processShadows(RoundEnvironment roundEnv) {
/*  99 */     for (Element elem : roundEnv.getElementsAnnotatedWith((Class)Shadow.class)) {
/* 100 */       Element parent = elem.getEnclosingElement();
/* 101 */       if (!(parent instanceof TypeElement)) {
/* 102 */         this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(parent), elem);
/*     */         
/*     */         continue;
/*     */       } 
/* 106 */       AnnotationHandle shadow = AnnotationHandle.of(elem, Shadow.class);
/*     */       
/* 108 */       if (elem.getKind() == ElementKind.FIELD) {
/* 109 */         this.mixins.registerShadow((TypeElement)parent, (VariableElement)elem, shadow); continue;
/* 110 */       }  if (elem.getKind() == ElementKind.METHOD) {
/* 111 */         this.mixins.registerShadow((TypeElement)parent, (ExecutableElement)elem, shadow); continue;
/*     */       } 
/* 113 */       this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method or field", elem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processOverwrites(RoundEnvironment roundEnv) {
/* 123 */     for (Element elem : roundEnv.getElementsAnnotatedWith((Class)Overwrite.class)) {
/* 124 */       Element parent = elem.getEnclosingElement();
/* 125 */       if (!(parent instanceof TypeElement)) {
/* 126 */         this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(parent), elem);
/*     */         
/*     */         continue;
/*     */       } 
/* 130 */       if (elem.getKind() == ElementKind.METHOD) {
/* 131 */         this.mixins.registerOverwrite((TypeElement)parent, (ExecutableElement)elem); continue;
/*     */       } 
/* 133 */       this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", elem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processAccessors(RoundEnvironment roundEnv) {
/* 143 */     for (Element elem : roundEnv.getElementsAnnotatedWith((Class)Accessor.class)) {
/* 144 */       Element parent = elem.getEnclosingElement();
/* 145 */       if (!(parent instanceof TypeElement)) {
/* 146 */         this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(parent), elem);
/*     */         
/*     */         continue;
/*     */       } 
/* 150 */       if (elem.getKind() == ElementKind.METHOD) {
/* 151 */         this.mixins.registerAccessor((TypeElement)parent, (ExecutableElement)elem); continue;
/*     */       } 
/* 153 */       this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", elem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processInvokers(RoundEnvironment roundEnv) {
/* 163 */     for (Element elem : roundEnv.getElementsAnnotatedWith((Class)Invoker.class)) {
/* 164 */       Element parent = elem.getEnclosingElement();
/* 165 */       if (!(parent instanceof TypeElement)) {
/* 166 */         this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(parent), elem);
/*     */         
/*     */         continue;
/*     */       } 
/* 170 */       if (elem.getKind() == ElementKind.METHOD) {
/* 171 */         this.mixins.registerInvoker((TypeElement)parent, (ExecutableElement)elem); continue;
/*     */       } 
/* 173 */       this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", elem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processImplements(RoundEnvironment roundEnv) {
/* 183 */     for (Element elem : roundEnv.getElementsAnnotatedWith((Class)Implements.class)) {
/* 184 */       if (elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE) {
/* 185 */         AnnotationHandle implementsAnnotation = AnnotationHandle.of(elem, Implements.class);
/* 186 */         this.mixins.registerSoftImplements((TypeElement)elem, implementsAnnotation); continue;
/*     */       } 
/* 188 */       this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", elem);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\MixinObfuscationProcessorTargets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */