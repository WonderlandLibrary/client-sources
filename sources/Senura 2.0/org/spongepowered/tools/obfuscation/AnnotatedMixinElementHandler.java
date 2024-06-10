/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.ConstraintParser;
/*     */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*     */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
/*     */ import org.spongepowered.tools.obfuscation.mirror.Visibility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AnnotatedMixinElementHandler
/*     */ {
/*     */   protected final AnnotatedMixin mixin;
/*     */   protected final String classRef;
/*     */   protected final IMixinAnnotationProcessor ap;
/*     */   protected final IObfuscationManager obf;
/*     */   private IMappingConsumer mappings;
/*     */   
/*     */   static abstract class AnnotatedElement<E extends Element>
/*     */   {
/*     */     protected final E element;
/*     */     protected final AnnotationHandle annotation;
/*     */     private final String desc;
/*     */     
/*     */     public AnnotatedElement(E element, AnnotationHandle annotation) {
/*  74 */       this.element = element;
/*  75 */       this.annotation = annotation;
/*  76 */       this.desc = TypeUtils.getDescriptor((Element)element);
/*     */     }
/*     */     
/*     */     public E getElement() {
/*  80 */       return this.element;
/*     */     }
/*     */     
/*     */     public AnnotationHandle getAnnotation() {
/*  84 */       return this.annotation;
/*     */     }
/*     */     
/*     */     public String getSimpleName() {
/*  88 */       return getElement().getSimpleName().toString();
/*     */     }
/*     */     
/*     */     public String getDesc() {
/*  92 */       return this.desc;
/*     */     }
/*     */     
/*     */     public final void printMessage(Messager messager, Diagnostic.Kind kind, CharSequence msg) {
/*  96 */       messager.printMessage(kind, msg, (Element)this.element, this.annotation.asMirror());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AliasedElementName
/*     */   {
/*     */     protected final String originalName;
/*     */ 
/*     */ 
/*     */     
/*     */     private final List<String> aliases;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean caseSensitive;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AliasedElementName(Element element, AnnotationHandle annotation) {
/* 119 */       this.originalName = element.getSimpleName().toString();
/* 120 */       this.aliases = annotation.getList("aliases");
/*     */     }
/*     */     
/*     */     public AliasedElementName setCaseSensitive(boolean caseSensitive) {
/* 124 */       this.caseSensitive = caseSensitive;
/* 125 */       return this;
/*     */     }
/*     */     
/*     */     public boolean isCaseSensitive() {
/* 129 */       return this.caseSensitive;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasAliases() {
/* 136 */       return (this.aliases.size() > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<String> getAliases() {
/* 143 */       return this.aliases;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String elementName() {
/* 150 */       return this.originalName;
/*     */     }
/*     */     
/*     */     public String baseName() {
/* 154 */       return this.originalName;
/*     */     }
/*     */     
/*     */     public boolean hasPrefix() {
/* 158 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ShadowElementName
/*     */     extends AliasedElementName
/*     */   {
/*     */     private final boolean hasPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String baseName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String obfuscated;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ShadowElementName(Element element, AnnotationHandle shadow) {
/* 191 */       super(element, shadow);
/*     */       
/* 193 */       this.prefix = (String)shadow.getValue("prefix", "shadow$");
/*     */       
/* 195 */       boolean hasPrefix = false;
/* 196 */       String name = this.originalName;
/* 197 */       if (name.startsWith(this.prefix)) {
/* 198 */         hasPrefix = true;
/* 199 */         name = name.substring(this.prefix.length());
/*     */       } 
/*     */       
/* 202 */       this.hasPrefix = hasPrefix;
/* 203 */       this.obfuscated = this.baseName = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 211 */       return this.baseName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String baseName() {
/* 216 */       return this.baseName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShadowElementName setObfuscatedName(IMapping<?> name) {
/* 226 */       this.obfuscated = name.getName();
/* 227 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShadowElementName setObfuscatedName(String name) {
/* 237 */       this.obfuscated = name;
/* 238 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasPrefix() {
/* 243 */       return this.hasPrefix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String prefix() {
/* 250 */       return this.hasPrefix ? this.prefix : "";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String name() {
/* 257 */       return prefix(this.baseName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String obfuscated() {
/* 264 */       return prefix(this.obfuscated);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String prefix(String name) {
/* 274 */       return this.hasPrefix ? (this.prefix + name) : name;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotatedMixinElementHandler(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 296 */     this.ap = ap;
/* 297 */     this.mixin = mixin;
/* 298 */     this.classRef = mixin.getClassRef();
/* 299 */     this.obf = ap.getObfuscationManager();
/*     */   }
/*     */   
/*     */   private IMappingConsumer getMappings() {
/* 303 */     if (this.mappings == null) {
/* 304 */       IMappingConsumer mappingConsumer = this.mixin.getMappings();
/* 305 */       if (mappingConsumer instanceof Mappings) {
/* 306 */         this.mappings = ((Mappings)mappingConsumer).asUnique();
/*     */       } else {
/* 308 */         this.mappings = mappingConsumer;
/*     */       } 
/*     */     } 
/* 311 */     return this.mappings;
/*     */   }
/*     */   
/*     */   protected final void addFieldMappings(String mcpName, String mcpSignature, ObfuscationData<MappingField> obfData) {
/* 315 */     for (ObfuscationType type : obfData) {
/* 316 */       MappingField obfField = obfData.get(type);
/* 317 */       addFieldMapping(type, mcpName, obfField.getSimpleName(), mcpSignature, obfField.getDesc());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addFieldMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
/* 325 */     addFieldMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addFieldMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
/* 332 */     MappingField from = new MappingField(this.classRef, mcpName, mcpSignature);
/* 333 */     MappingField to = new MappingField(this.classRef, obfName, obfSignature);
/* 334 */     getMappings().addFieldMapping(type, from, to);
/*     */   }
/*     */   
/*     */   protected final void addMethodMappings(String mcpName, String mcpSignature, ObfuscationData<MappingMethod> obfData) {
/* 338 */     for (ObfuscationType type : obfData) {
/* 339 */       MappingMethod obfMethod = obfData.get(type);
/* 340 */       addMethodMapping(type, mcpName, obfMethod.getSimpleName(), mcpSignature, obfMethod.getDesc());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addMethodMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
/* 348 */     addMethodMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void addMethodMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
/* 355 */     MappingMethod from = new MappingMethod(this.classRef, mcpName, mcpSignature);
/* 356 */     MappingMethod to = new MappingMethod(this.classRef, obfName, obfSignature);
/* 357 */     getMappings().addMethodMapping(type, from, to);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void checkConstraints(ExecutableElement method, AnnotationHandle annotation) {
/*     */     try {
/* 369 */       ConstraintParser.Constraint constraint = ConstraintParser.parse((String)annotation.getValue("constraints"));
/*     */       try {
/* 371 */         constraint.check(this.ap.getTokenProvider());
/* 372 */       } catch (ConstraintViolationException ex) {
/* 373 */         this.ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), method, annotation.asMirror());
/*     */       } 
/* 375 */     } catch (InvalidConstraintException ex) {
/* 376 */       this.ap.printMessage(Diagnostic.Kind.WARNING, ex.getMessage(), method, annotation.asMirror());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void validateTarget(Element element, AnnotationHandle annotation, AliasedElementName name, String type) {
/* 381 */     if (element instanceof ExecutableElement) {
/* 382 */       validateTargetMethod((ExecutableElement)element, annotation, name, type, false, false);
/* 383 */     } else if (element instanceof VariableElement) {
/* 384 */       validateTargetField((VariableElement)element, annotation, name, type);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void validateTargetMethod(ExecutableElement method, AnnotationHandle annotation, AliasedElementName name, String type, boolean overwrite, boolean merge) {
/* 394 */     String signature = TypeUtils.getJavaSignature(method);
/*     */     
/* 396 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 397 */       if (target.isImaginary()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 402 */       MethodHandle targetMethod = target.findMethod(method);
/*     */ 
/*     */       
/* 405 */       if (targetMethod == null && name.hasPrefix()) {
/* 406 */         targetMethod = target.findMethod(name.baseName(), signature);
/*     */       }
/*     */ 
/*     */       
/* 410 */       if (targetMethod == null && name.hasAliases()) {
/* 411 */         String alias; Iterator<String> iterator = name.getAliases().iterator(); do { alias = iterator.next(); } while (iterator.hasNext() && (
/* 412 */           targetMethod = target.findMethod(alias, signature)) == null);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 418 */       if (targetMethod != null) {
/* 419 */         if (overwrite)
/* 420 */           validateMethodVisibility(method, annotation, type, target, targetMethod);  continue;
/*     */       } 
/* 422 */       if (!merge) {
/* 423 */         printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " method in " + target, method, annotation);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void validateMethodVisibility(ExecutableElement method, AnnotationHandle annotation, String type, TypeHandle target, MethodHandle targetMethod) {
/* 430 */     Visibility visTarget = targetMethod.getVisibility();
/* 431 */     if (visTarget == null) {
/*     */       return;
/*     */     }
/*     */     
/* 435 */     Visibility visMethod = TypeUtils.getVisibility(method);
/* 436 */     String visibility = "visibility of " + visTarget + " method in " + target;
/* 437 */     if (visTarget.ordinal() > visMethod.ordinal()) {
/* 438 */       printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method cannot reduce " + visibility, method, annotation);
/* 439 */     } else if (visTarget == Visibility.PRIVATE && visMethod.ordinal() > visTarget.ordinal()) {
/* 440 */       printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method will upgrade " + visibility, method, annotation);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void validateTargetField(VariableElement field, AnnotationHandle annotation, AliasedElementName name, String type) {
/* 449 */     String fieldType = field.asType().toString();
/*     */     
/* 451 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 452 */       String alias; if (target.isImaginary()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 457 */       FieldHandle targetField = target.findField(field);
/* 458 */       if (targetField != null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 463 */       List<String> aliases = name.getAliases();
/* 464 */       Iterator<String> iterator = aliases.iterator(); do { alias = iterator.next(); } while (iterator.hasNext() && (
/* 465 */         targetField = target.findField(alias, fieldType)) == null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 470 */       if (targetField == null) {
/* 471 */         this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " field in " + target, field, annotation.asMirror());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void validateReferencedTarget(ExecutableElement method, AnnotationHandle inject, MemberInfo reference, String type) {
/* 481 */     String signature = reference.toDescriptor();
/*     */     
/* 483 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 484 */       if (target.isImaginary()) {
/*     */         continue;
/*     */       }
/*     */       
/* 488 */       MethodHandle targetMethod = target.findMethod(reference.name, signature);
/* 489 */       if (targetMethod == null) {
/* 490 */         this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + type + " in " + target, method, inject.asMirror());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void printMessage(Diagnostic.Kind kind, String msg, Element e, AnnotationHandle annotation) {
/* 496 */     if (annotation == null) {
/* 497 */       this.ap.printMessage(kind, msg, e);
/*     */     } else {
/* 499 */       this.ap.printMessage(kind, msg, e, annotation.asMirror());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> data) {
/* 504 */     ObfuscationData<T> stripped = new ObfuscationData<T>();
/* 505 */     for (ObfuscationType type : data) {
/* 506 */       IMapping iMapping = (IMapping)data.get(type);
/* 507 */       stripped.put(type, (T)iMapping.move(null));
/*     */     } 
/* 509 */     return stripped;
/*     */   }
/*     */   
/*     */   protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> data) {
/* 513 */     ObfuscationData<T> stripped = new ObfuscationData<T>();
/* 514 */     for (ObfuscationType type : data) {
/* 515 */       IMapping iMapping = (IMapping)data.get(type);
/* 516 */       stripped.put(type, (T)iMapping.transform(null));
/*     */     } 
/* 518 */     return stripped;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */