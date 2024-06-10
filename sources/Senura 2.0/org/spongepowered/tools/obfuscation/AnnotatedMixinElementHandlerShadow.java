/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
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
/*     */ class AnnotatedMixinElementHandlerShadow
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static abstract class AnnotatedElementShadow<E extends Element, M extends IMapping<M>>
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<E>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     private final AnnotatedMixinElementHandler.ShadowElementName name;
/*     */     private final IMapping.Type type;
/*     */     
/*     */     protected AnnotatedElementShadow(E element, AnnotationHandle annotation, boolean shouldRemap, IMapping.Type type) {
/*  62 */       super(element, annotation);
/*  63 */       this.shouldRemap = shouldRemap;
/*  64 */       this.name = new AnnotatedMixinElementHandler.ShadowElementName((Element)element, annotation);
/*  65 */       this.type = type;
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  69 */       return this.shouldRemap;
/*     */     }
/*     */     
/*     */     public AnnotatedMixinElementHandler.ShadowElementName getName() {
/*  73 */       return this.name;
/*     */     }
/*     */     
/*     */     public IMapping.Type getElementType() {
/*  77 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  82 */       return getElementType().name().toLowerCase();
/*     */     }
/*     */     
/*     */     public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> name) {
/*  86 */       return setObfuscatedName(name.getSimpleName());
/*     */     }
/*     */     
/*     */     public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String name) {
/*  90 */       return getName().setObfuscatedName(name);
/*     */     }
/*     */     
/*     */     public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider provider, TypeHandle owner) {
/*  94 */       return provider.getObfEntry((IMapping)getMapping(owner, getName().toString(), getDesc()));
/*     */     }
/*     */ 
/*     */     
/*     */     public abstract M getMapping(TypeHandle param1TypeHandle, String param1String1, String param1String2);
/*     */ 
/*     */     
/*     */     public abstract void addMapping(ObfuscationType param1ObfuscationType, IMapping<?> param1IMapping);
/*     */   }
/*     */ 
/*     */   
/*     */   class AnnotatedElementShadowField
/*     */     extends AnnotatedElementShadow<VariableElement, MappingField>
/*     */   {
/*     */     public AnnotatedElementShadowField(VariableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 109 */       super(element, annotation, shouldRemap, IMapping.Type.FIELD);
/*     */     }
/*     */ 
/*     */     
/*     */     public MappingField getMapping(TypeHandle owner, String name, String desc) {
/* 114 */       return new MappingField(owner.getName(), name, desc);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMapping(ObfuscationType type, IMapping<?> remapped) {
/* 119 */       AnnotatedMixinElementHandlerShadow.this.addFieldMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class AnnotatedElementShadowMethod
/*     */     extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
/*     */   {
/*     */     public AnnotatedElementShadowMethod(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 130 */       super(element, annotation, shouldRemap, IMapping.Type.METHOD);
/*     */     }
/*     */ 
/*     */     
/*     */     public MappingMethod getMapping(TypeHandle owner, String name, String desc) {
/* 135 */       return owner.getMappingMethod(name, desc);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMapping(ObfuscationType type, IMapping<?> remapped) {
/* 140 */       AnnotatedMixinElementHandlerShadow.this.addMethodMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 146 */     super(ap, mixin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(AnnotatedElementShadow<?, ?> elem) {
/* 153 */     validateTarget((Element)elem.getElement(), elem.getAnnotation(), elem.getName(), "@Shadow");
/*     */     
/* 155 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 159 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 160 */       registerShadowForTarget(elem, target);
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerShadowForTarget(AnnotatedElementShadow<?, ?> elem, TypeHandle target) {
/* 165 */     ObfuscationData<? extends IMapping<?>> obfData = (ObfuscationData)elem.getObfuscationData(this.obf.getDataProvider(), target);
/*     */     
/* 167 */     if (obfData.isEmpty()) {
/* 168 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 169 */       if (target.isSimulated()) {
/* 170 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
/*     */       } else {
/* 172 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     for (ObfuscationType type : obfData) {
/*     */       try {
/* 179 */         elem.addMapping(type, obfData.get(type));
/* 180 */       } catch (MappingConflictException ex) {
/* 181 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + elem + ": " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex
/* 182 */             .getOld().getSimpleName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerShadow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */