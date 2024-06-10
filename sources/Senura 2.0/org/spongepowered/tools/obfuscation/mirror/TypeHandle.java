/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.Modifier;
/*     */ import javax.lang.model.element.PackageElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeHandle
/*     */ {
/*     */   private final String name;
/*     */   private final PackageElement pkg;
/*     */   private final TypeElement element;
/*     */   private TypeReference reference;
/*     */   
/*     */   public TypeHandle(PackageElement pkg, String name) {
/*  85 */     this.name = name.replace('.', '/');
/*  86 */     this.pkg = pkg;
/*  87 */     this.element = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle(TypeElement element) {
/*  96 */     this.pkg = TypeUtils.getPackage(element);
/*  97 */     this.name = TypeUtils.getInternalName(element);
/*  98 */     this.element = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle(DeclaredType type) {
/* 107 */     this((TypeElement)type.asElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 115 */     return this.name.replace('/', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 122 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PackageElement getPackage() {
/* 129 */     return this.pkg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TypeElement getElement() {
/* 136 */     return this.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TypeElement getTargetElement() {
/* 144 */     return this.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationHandle getAnnotation(Class<? extends Annotation> annotationClass) {
/* 155 */     return AnnotationHandle.of(getTargetElement(), annotationClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<? extends Element> getEnclosedElements() {
/* 162 */     return getEnclosedElements(getTargetElement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Element> List<T> getEnclosedElements(ElementKind... kind) {
/* 172 */     return getEnclosedElements(getTargetElement(), kind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeMirror getType() {
/* 180 */     return (getTargetElement() != null) ? getTargetElement().asType() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getSuperclass() {
/* 188 */     TypeElement targetElement = getTargetElement();
/* 189 */     if (targetElement == null) {
/* 190 */       return null;
/*     */     }
/*     */     
/* 193 */     TypeMirror superClass = targetElement.getSuperclass();
/* 194 */     if (superClass == null || superClass.getKind() == TypeKind.NONE) {
/* 195 */       return null;
/*     */     }
/*     */     
/* 198 */     return new TypeHandle((DeclaredType)superClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TypeHandle> getInterfaces() {
/* 205 */     if (getTargetElement() == null) {
/* 206 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 209 */     ImmutableList.Builder<TypeHandle> list = ImmutableList.builder();
/* 210 */     for (TypeMirror iface : getTargetElement().getInterfaces()) {
/* 211 */       list.add(new TypeHandle((DeclaredType)iface));
/*     */     }
/*     */     
/* 214 */     return (List<TypeHandle>)list.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPublic() {
/* 221 */     return (getTargetElement() != null && getTargetElement().getModifiers().contains(Modifier.PUBLIC));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImaginary() {
/* 228 */     return (getTargetElement() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSimulated() {
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TypeReference getReference() {
/* 242 */     if (this.reference == null) {
/* 243 */       this.reference = new TypeReference(this);
/*     */     }
/* 245 */     return this.reference;
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
/*     */   
/*     */   public MappingMethod getMappingMethod(String name, String desc) {
/* 258 */     return (MappingMethod)new ResolvableMappingMethod(this, name, desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String findDescriptor(MemberInfo memberInfo) {
/* 268 */     String desc = memberInfo.desc;
/* 269 */     if (desc == null) {
/* 270 */       for (ExecutableElement method : getEnclosedElements(new ElementKind[] { ElementKind.METHOD })) {
/* 271 */         if (method.getSimpleName().toString().equals(memberInfo.name)) {
/* 272 */           desc = TypeUtils.getDescriptor(method);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 277 */     return desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final FieldHandle findField(VariableElement element) {
/* 288 */     return findField(element, true);
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
/*     */   public final FieldHandle findField(VariableElement element, boolean caseSensitive) {
/* 300 */     return findField(element.getSimpleName().toString(), TypeUtils.getTypeName(element.asType()), caseSensitive);
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
/*     */   public final FieldHandle findField(String name, String type) {
/* 312 */     return findField(name, type, true);
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
/*     */   
/*     */   public FieldHandle findField(String name, String type, boolean caseSensitive) {
/* 325 */     String rawType = TypeUtils.stripGenerics(type);
/*     */     
/* 327 */     for (VariableElement field : getEnclosedElements(new ElementKind[] { ElementKind.FIELD })) {
/* 328 */       if (compareElement(field, name, type, caseSensitive))
/* 329 */         return new FieldHandle(getTargetElement(), field); 
/* 330 */       if (compareElement(field, name, rawType, caseSensitive)) {
/* 331 */         return new FieldHandle(getTargetElement(), field, true);
/*     */       }
/*     */     } 
/*     */     
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MethodHandle findMethod(ExecutableElement element) {
/* 346 */     return findMethod(element, true);
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
/*     */   public final MethodHandle findMethod(ExecutableElement element, boolean caseSensitive) {
/* 358 */     return findMethod(element.getSimpleName().toString(), TypeUtils.getJavaSignature(element), caseSensitive);
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
/*     */   public final MethodHandle findMethod(String name, String signature) {
/* 370 */     return findMethod(name, signature, true);
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
/*     */   
/*     */   public MethodHandle findMethod(String name, String signature, boolean matchCase) {
/* 383 */     String rawSignature = TypeUtils.stripGenerics(signature);
/* 384 */     return findMethod(this, name, signature, rawSignature, matchCase);
/*     */   }
/*     */   
/*     */   protected static MethodHandle findMethod(TypeHandle target, String name, String signature, String rawSignature, boolean matchCase) {
/* 388 */     for (ExecutableElement method : getEnclosedElements(target.getTargetElement(), new ElementKind[] { ElementKind.CONSTRUCTOR, ElementKind.METHOD })) {
/*     */       
/* 390 */       if (compareElement(method, name, signature, matchCase) || compareElement(method, name, rawSignature, matchCase)) {
/* 391 */         return new MethodHandle(target, method);
/*     */       }
/*     */     } 
/* 394 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean compareElement(Element elem, String name, String type, boolean matchCase) {
/*     */     try {
/* 399 */       String elementName = elem.getSimpleName().toString();
/* 400 */       String elementType = TypeUtils.getJavaSignature(elem);
/* 401 */       String rawElementType = TypeUtils.stripGenerics(elementType);
/* 402 */       boolean compared = matchCase ? name.equals(elementName) : name.equalsIgnoreCase(elementName);
/* 403 */       return (compared && (type.length() == 0 || type.equals(elementType) || type.equals(rawElementType)));
/* 404 */     } catch (NullPointerException ex) {
/* 405 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T extends Element> List<T> getEnclosedElements(TypeElement targetElement, ElementKind... kind) {
/* 411 */     if (kind == null || kind.length < 1) {
/* 412 */       return (List)getEnclosedElements(targetElement);
/*     */     }
/*     */     
/* 415 */     if (targetElement == null) {
/* 416 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 419 */     ImmutableList.Builder<T> list = ImmutableList.builder();
/* 420 */     for (Element elem : targetElement.getEnclosedElements()) {
/* 421 */       for (ElementKind ek : kind) {
/* 422 */         if (elem.getKind() == ek) {
/* 423 */           list.add(elem);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 429 */     return (List<T>)list.build();
/*     */   }
/*     */   
/*     */   protected static List<? extends Element> getEnclosedElements(TypeElement targetElement) {
/* 433 */     return (targetElement != null) ? targetElement.getEnclosedElements() : Collections.<Element>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mirror\TypeHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */