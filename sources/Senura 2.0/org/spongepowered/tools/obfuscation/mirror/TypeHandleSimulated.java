/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
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
/*     */ public class TypeHandleSimulated
/*     */   extends TypeHandle
/*     */ {
/*     */   private final TypeElement simulatedType;
/*     */   
/*     */   public TypeHandleSimulated(String name, TypeMirror type) {
/*  52 */     this(TypeUtils.getPackage(type), name, type);
/*     */   }
/*     */   
/*     */   public TypeHandleSimulated(PackageElement pkg, String name, TypeMirror type) {
/*  56 */     super(pkg, name);
/*  57 */     this.simulatedType = (TypeElement)((DeclaredType)type).asElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeElement getTargetElement() {
/*  66 */     return this.simulatedType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPublic() {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImaginary() {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSimulated() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationHandle getAnnotation(Class<? extends Annotation> annotationClass) {
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getSuperclass() {
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findDescriptor(MemberInfo memberInfo) {
/* 122 */     return (memberInfo != null) ? memberInfo.desc : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldHandle findField(String name, String type, boolean caseSensitive) {
/* 131 */     return new FieldHandle(null, name, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodHandle findMethod(String name, String desc, boolean caseSensitive) {
/* 141 */     return new MethodHandle(null, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getMappingMethod(String name, String desc) {
/* 151 */     String signature = (new SignaturePrinter(name, desc)).setFullyQualified(true).toDescriptor();
/* 152 */     String rawSignature = TypeUtils.stripGenerics(signature);
/*     */ 
/*     */     
/* 155 */     MethodHandle method = findMethodRecursive(this, name, signature, rawSignature, true);
/*     */ 
/*     */     
/* 158 */     return (method != null) ? method.asMapping(true) : super.getMappingMethod(name, desc);
/*     */   }
/*     */ 
/*     */   
/*     */   private static MethodHandle findMethodRecursive(TypeHandle target, String name, String signature, String rawSignature, boolean matchCase) {
/* 163 */     TypeElement elem = target.getTargetElement();
/* 164 */     if (elem == null) {
/* 165 */       return null;
/*     */     }
/*     */     
/* 168 */     MethodHandle method = TypeHandle.findMethod(target, name, signature, rawSignature, matchCase);
/* 169 */     if (method != null) {
/* 170 */       return method;
/*     */     }
/*     */     
/* 173 */     for (TypeMirror iface : elem.getInterfaces()) {
/* 174 */       method = findMethodRecursive(iface, name, signature, rawSignature, matchCase);
/* 175 */       if (method != null) {
/* 176 */         return method;
/*     */       }
/*     */     } 
/*     */     
/* 180 */     TypeMirror superClass = elem.getSuperclass();
/* 181 */     if (superClass == null || superClass.getKind() == TypeKind.NONE) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     return findMethodRecursive(superClass, name, signature, rawSignature, matchCase);
/*     */   }
/*     */   
/*     */   private static MethodHandle findMethodRecursive(TypeMirror target, String name, String signature, String rawSignature, boolean matchCase) {
/* 189 */     if (!(target instanceof DeclaredType)) {
/* 190 */       return null;
/*     */     }
/* 192 */     TypeElement element = (TypeElement)((DeclaredType)target).asElement();
/* 193 */     return findMethodRecursive(new TypeHandle(element), name, signature, rawSignature, matchCase);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\TypeHandleSimulated.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */