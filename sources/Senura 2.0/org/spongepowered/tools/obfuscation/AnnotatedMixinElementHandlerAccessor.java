/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
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
/*     */ public class AnnotatedMixinElementHandlerAccessor
/*     */   extends AnnotatedMixinElementHandler
/*     */   implements IMixinContext
/*     */ {
/*     */   static class AnnotatedElementAccessor
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     private final TypeMirror returnType;
/*     */     private String targetName;
/*     */     
/*     */     public AnnotatedElementAccessor(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/*  71 */       super(element, annotation);
/*  72 */       this.shouldRemap = shouldRemap;
/*  73 */       this.returnType = getElement().getReturnType();
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  77 */       return this.shouldRemap;
/*     */     }
/*     */     
/*     */     public String getAnnotationValue() {
/*  81 */       return (String)getAnnotation().getValue();
/*     */     }
/*     */     
/*     */     public TypeMirror getTargetType() {
/*  85 */       switch (getAccessorType()) {
/*     */         case FIELD_GETTER:
/*  87 */           return this.returnType;
/*     */         case FIELD_SETTER:
/*  89 */           return ((VariableElement)getElement().getParameters().get(0)).asType();
/*     */       } 
/*  91 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTargetTypeName() {
/*  96 */       return TypeUtils.getTypeName(getTargetType());
/*     */     }
/*     */     
/*     */     public String getAccessorDesc() {
/* 100 */       return TypeUtils.getInternalName(getTargetType());
/*     */     }
/*     */     
/*     */     public MemberInfo getContext() {
/* 104 */       return new MemberInfo(getTargetName(), null, getAccessorDesc());
/*     */     }
/*     */     
/*     */     public AccessorInfo.AccessorType getAccessorType() {
/* 108 */       return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
/*     */     }
/*     */     
/*     */     public void setTargetName(String targetName) {
/* 112 */       this.targetName = targetName;
/*     */     }
/*     */     
/*     */     public String getTargetName() {
/* 116 */       return this.targetName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 121 */       return (this.targetName != null) ? this.targetName : "<invalid>";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class AnnotatedElementInvoker
/*     */     extends AnnotatedElementAccessor
/*     */   {
/*     */     public AnnotatedElementInvoker(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 131 */       super(element, annotation, shouldRemap);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAccessorDesc() {
/* 136 */       return TypeUtils.getDescriptor(getElement());
/*     */     }
/*     */ 
/*     */     
/*     */     public AccessorInfo.AccessorType getAccessorType() {
/* 141 */       return AccessorInfo.AccessorType.METHOD_PROXY;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTargetTypeName() {
/* 146 */       return TypeUtils.getJavaSignature(getElement());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 152 */     super(ap, mixin);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReferenceMapper getReferenceMapper() {
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 162 */     return this.mixin.getClassRef().replace('/', '.');
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClassRef() {
/* 167 */     return this.mixin.getClassRef();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTargetClassRef() {
/* 172 */     throw new UnsupportedOperationException("Target class not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public IMixinInfo getMixin() {
/* 177 */     throw new UnsupportedOperationException("MixinInfo not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public Extensions getExtensions() {
/* 182 */     throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOption(MixinEnvironment.Option option) {
/* 187 */     throw new UnsupportedOperationException("Options not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 192 */     throw new UnsupportedOperationException("Priority not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public Target getTargetMethod(MethodNode into) {
/* 197 */     throw new UnsupportedOperationException("Target not available at compile time");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccessor(AnnotatedElementAccessor elem) {
/* 206 */     if (elem.getAccessorType() == null) {
/* 207 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unsupported accessor type");
/*     */       
/*     */       return;
/*     */     } 
/* 211 */     String targetName = getAccessorTargetName(elem);
/* 212 */     if (targetName == null) {
/* 213 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Cannot inflect accessor target name");
/*     */       return;
/*     */     } 
/* 216 */     elem.setTargetName(targetName);
/*     */     
/* 218 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 219 */       if (elem.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
/* 220 */         registerInvokerForTarget((AnnotatedElementInvoker)elem, target); continue;
/*     */       } 
/* 222 */       registerAccessorForTarget(elem, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAccessorForTarget(AnnotatedElementAccessor elem, TypeHandle target) {
/* 228 */     FieldHandle targetField = target.findField(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 229 */     if (targetField == null) {
/* 230 */       if (!target.isImaginary()) {
/* 231 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Accessor target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 235 */       targetField = new FieldHandle(target.getName(), elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 238 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 242 */     ObfuscationData<MappingField> obfData = this.obf.getDataProvider().getObfField(targetField.asMapping(false).move(target.getName()));
/* 243 */     if (obfData.isEmpty()) {
/* 244 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 245 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 249 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 252 */       this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 253 */     } catch (ReferenceConflictException ex) {
/* 254 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Accessor target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 255 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerInvokerForTarget(AnnotatedElementInvoker elem, TypeHandle target) {
/* 260 */     MethodHandle targetMethod = target.findMethod(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 261 */     if (targetMethod == null) {
/* 262 */       if (!target.isImaginary()) {
/* 263 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Invoker target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 267 */       targetMethod = new MethodHandle(target, elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 270 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 274 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod.asMapping(false).move(target.getName()));
/* 275 */     if (obfData.isEmpty()) {
/* 276 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 277 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 281 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 284 */       this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 285 */     } catch (ReferenceConflictException ex) {
/* 286 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Invoker target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 287 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getAccessorTargetName(AnnotatedElementAccessor elem) {
/* 292 */     String value = elem.getAnnotationValue();
/* 293 */     if (Strings.isNullOrEmpty(value)) {
/* 294 */       return inflectAccessorTarget(elem);
/*     */     }
/* 296 */     return value;
/*     */   }
/*     */   
/*     */   private String inflectAccessorTarget(AnnotatedElementAccessor elem) {
/* 300 */     return AccessorInfo.inflectTarget(elem.getSimpleName(), elem.getAccessorType(), "", this, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */