/*    */ package org.spongepowered.tools.obfuscation;
/*    */ 
/*    */ import java.util.Set;
/*    */ import javax.annotation.processing.AbstractProcessor;
/*    */ import javax.annotation.processing.ProcessingEnvironment;
/*    */ import javax.annotation.processing.RoundEnvironment;
/*    */ import javax.lang.model.SourceVersion;
/*    */ import javax.lang.model.element.Element;
/*    */ import javax.lang.model.element.ElementKind;
/*    */ import javax.lang.model.element.TypeElement;
/*    */ import javax.tools.Diagnostic;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MixinObfuscationProcessor
/*    */   extends AbstractProcessor
/*    */ {
/*    */   protected AnnotatedMixins mixins;
/*    */   
/*    */   public synchronized void init(ProcessingEnvironment processingEnv) {
/* 56 */     super.init(processingEnv);
/* 57 */     this.mixins = AnnotatedMixins.getMixinsForEnvironment(processingEnv);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processMixins(RoundEnvironment roundEnv) {
/* 66 */     this.mixins.onPassStarted();
/*    */     
/* 68 */     for (Element elem : roundEnv.getElementsAnnotatedWith((Class)Mixin.class)) {
/* 69 */       if (elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE) {
/* 70 */         this.mixins.registerMixin((TypeElement)elem); continue;
/*    */       } 
/* 72 */       this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Mixin annotation on an element which is not a class or interface", elem);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void postProcess(RoundEnvironment roundEnv) {
/* 78 */     this.mixins.onPassCompleted(roundEnv);
/*    */   }
/*    */ 
/*    */   
/*    */   public SourceVersion getSupportedSourceVersion() {
/*    */     try {
/* 84 */       return SourceVersion.valueOf("RELEASE_8");
/* 85 */     } catch (IllegalArgumentException illegalArgumentException) {
/*    */ 
/*    */ 
/*    */       
/* 89 */       return super.getSupportedSourceVersion();
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set<String> getSupportedOptions() {
/* 94 */     return SupportedOptions.getAllOptions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\MixinObfuscationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */