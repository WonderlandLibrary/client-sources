/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
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
/*     */ public abstract class MixinValidator
/*     */   implements IMixinValidator
/*     */ {
/*     */   protected final ProcessingEnvironment processingEnv;
/*     */   protected final Messager messager;
/*     */   protected final IOptionProvider options;
/*     */   protected final IMixinValidator.ValidationPass pass;
/*     */   
/*     */   public MixinValidator(IMixinAnnotationProcessor ap, IMixinValidator.ValidationPass pass) {
/*  75 */     this.processingEnv = ap.getProcessingEnvironment();
/*  76 */     this.messager = (Messager)ap;
/*  77 */     this.options = (IOptionProvider)ap;
/*  78 */     this.pass = pass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean validate(IMixinValidator.ValidationPass pass, TypeElement mixin, AnnotationHandle annotation, Collection<TypeHandle> targets) {
/*  90 */     if (pass != this.pass) {
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     return validate(mixin, annotation, targets);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean validate(TypeElement paramTypeElement, AnnotationHandle paramAnnotationHandle, Collection<TypeHandle> paramCollection);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void note(String note, Element element) {
/* 106 */     this.messager.printMessage(Diagnostic.Kind.NOTE, note, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void warning(String warning, Element element) {
/* 116 */     this.messager.printMessage(Diagnostic.Kind.WARNING, warning, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void error(String error, Element element) {
/* 126 */     this.messager.printMessage(Diagnostic.Kind.ERROR, error, element);
/*     */   }
/*     */   
/*     */   protected final Collection<TypeMirror> getMixinsTargeting(TypeMirror targetType) {
/* 130 */     return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(targetType);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\MixinValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */