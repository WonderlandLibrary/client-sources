/*     */ package org.spongepowered.tools.obfuscation.validation;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import org.spongepowered.asm.mixin.gen.Accessor;
/*     */ import org.spongepowered.asm.mixin.gen.Invoker;
/*     */ import org.spongepowered.tools.obfuscation.MixinValidator;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ public class TargetValidator
/*     */   extends MixinValidator
/*     */ {
/*     */   public TargetValidator(IMixinAnnotationProcessor ap) {
/*  56 */     super(ap, IMixinValidator.ValidationPass.LATE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate(TypeElement mixin, AnnotationHandle annotation, Collection<TypeHandle> targets) {
/*  67 */     if ("true".equalsIgnoreCase(this.options.getOption("disableTargetValidator"))) {
/*  68 */       return true;
/*     */     }
/*     */     
/*  71 */     if (mixin.getKind() == ElementKind.INTERFACE) {
/*  72 */       validateInterfaceMixin(mixin, targets);
/*     */     } else {
/*  74 */       validateClassMixin(mixin, targets);
/*     */     } 
/*     */     
/*  77 */     return true;
/*     */   }
/*     */   private void validateInterfaceMixin(TypeElement mixin, Collection<TypeHandle> targets) {
/*     */     int i;
/*  81 */     boolean containsNonAccessorMethod = false;
/*  82 */     for (Element element : mixin.getEnclosedElements()) {
/*  83 */       if (element.getKind() == ElementKind.METHOD) {
/*  84 */         boolean isAccessor = AnnotationHandle.of(element, Accessor.class).exists();
/*  85 */         boolean isInvoker = AnnotationHandle.of(element, Invoker.class).exists();
/*  86 */         i = containsNonAccessorMethod | ((!isAccessor && !isInvoker) ? 1 : 0);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     if (i == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     for (TypeHandle target : targets) {
/*  95 */       TypeElement targetType = target.getElement();
/*  96 */       if (targetType != null && targetType.getKind() != ElementKind.INTERFACE) {
/*  97 */         error("Targetted type '" + target + " of " + mixin + " is not an interface", mixin);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void validateClassMixin(TypeElement mixin, Collection<TypeHandle> targets) {
/* 103 */     TypeMirror superClass = mixin.getSuperclass();
/*     */     
/* 105 */     for (TypeHandle target : targets) {
/* 106 */       TypeMirror targetType = target.getType();
/* 107 */       if (targetType != null && !validateSuperClass(targetType, superClass)) {
/* 108 */         error("Superclass " + superClass + " of " + mixin + " was not found in the hierarchy of target class " + targetType, mixin);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean validateSuperClass(TypeMirror targetType, TypeMirror superClass) {
/* 114 */     if (TypeUtils.isAssignable(this.processingEnv, targetType, superClass)) {
/* 115 */       return true;
/*     */     }
/*     */     
/* 118 */     return validateSuperClassRecursive(targetType, superClass);
/*     */   }
/*     */   
/*     */   private boolean validateSuperClassRecursive(TypeMirror targetType, TypeMirror superClass) {
/* 122 */     if (!(targetType instanceof DeclaredType)) {
/* 123 */       return false;
/*     */     }
/*     */     
/* 126 */     if (TypeUtils.isAssignable(this.processingEnv, targetType, superClass)) {
/* 127 */       return true;
/*     */     }
/*     */     
/* 130 */     TypeElement targetElement = (TypeElement)((DeclaredType)targetType).asElement();
/* 131 */     TypeMirror targetSuper = targetElement.getSuperclass();
/* 132 */     if (targetSuper.getKind() == TypeKind.NONE) {
/* 133 */       return false;
/*     */     }
/*     */     
/* 136 */     if (checkMixinsFor(targetSuper, superClass)) {
/* 137 */       return true;
/*     */     }
/*     */     
/* 140 */     return validateSuperClassRecursive(targetSuper, superClass);
/*     */   }
/*     */   
/*     */   private boolean checkMixinsFor(TypeMirror targetType, TypeMirror superClass) {
/* 144 */     for (TypeMirror mixinType : getMixinsTargeting(targetType)) {
/* 145 */       if (TypeUtils.isAssignable(this.processingEnv, mixinType, superClass)) {
/* 146 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\validation\TargetValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */