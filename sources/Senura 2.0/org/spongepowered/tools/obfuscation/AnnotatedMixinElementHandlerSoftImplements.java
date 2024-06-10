/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.Interface;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
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
/*     */ public class AnnotatedMixinElementHandlerSoftImplements
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/*  48 */     super(ap, mixin);
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
/*     */   public void process(AnnotationHandle implementsAnnotation) {
/*  62 */     if (!this.mixin.remap()) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     List<AnnotationHandle> interfaces = implementsAnnotation.getAnnotationList("value");
/*     */ 
/*     */     
/*  69 */     if (interfaces.size() < 1) {
/*  70 */       this.ap.printMessage(Diagnostic.Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), implementsAnnotation.asMirror());
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     for (AnnotationHandle interfaceAnnotation : interfaces) {
/*  75 */       Interface.Remap remap = (Interface.Remap)interfaceAnnotation.getValue("remap", Interface.Remap.ALL);
/*  76 */       if (remap == Interface.Remap.NONE) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/*  81 */         TypeHandle iface = new TypeHandle((DeclaredType)interfaceAnnotation.getValue("iface"));
/*  82 */         String prefix = (String)interfaceAnnotation.getValue("prefix");
/*  83 */         processSoftImplements(remap, iface, prefix);
/*  84 */       } catch (Exception ex) {
/*  85 */         this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected error: " + ex.getClass().getName() + ": " + ex.getMessage(), this.mixin.getMixin(), interfaceAnnotation
/*  86 */             .asMirror());
/*     */       } 
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
/*     */   private void processSoftImplements(Interface.Remap remap, TypeHandle iface, String prefix) {
/* 100 */     for (ExecutableElement method : iface.getEnclosedElements(new ElementKind[] { ElementKind.METHOD })) {
/* 101 */       processMethod(remap, iface, prefix, method);
/*     */     }
/*     */     
/* 104 */     for (TypeHandle superInterface : iface.getInterfaces()) {
/* 105 */       processSoftImplements(remap, superInterface, prefix);
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
/*     */   private void processMethod(Interface.Remap remap, TypeHandle iface, String prefix, ExecutableElement method) {
/* 120 */     String name = method.getSimpleName().toString();
/* 121 */     String sig = TypeUtils.getJavaSignature(method);
/* 122 */     String desc = TypeUtils.getDescriptor(method);
/*     */     
/* 124 */     if (remap != Interface.Remap.ONLY_PREFIXED) {
/* 125 */       MethodHandle mixinMethod = this.mixin.getHandle().findMethod(name, sig);
/* 126 */       if (mixinMethod != null) {
/* 127 */         addInterfaceMethodMapping(remap, iface, (String)null, mixinMethod, name, desc);
/*     */       }
/*     */     } 
/*     */     
/* 131 */     if (prefix != null) {
/* 132 */       MethodHandle prefixedMixinMethod = this.mixin.getHandle().findMethod(prefix + name, sig);
/* 133 */       if (prefixedMixinMethod != null) {
/* 134 */         addInterfaceMethodMapping(remap, iface, prefix, prefixedMixinMethod, name, desc);
/*     */       }
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
/*     */   private void addInterfaceMethodMapping(Interface.Remap remap, TypeHandle iface, String prefix, MethodHandle method, String name, String desc) {
/* 152 */     MappingMethod mapping = new MappingMethod(iface.getName(), name, desc);
/* 153 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(mapping);
/* 154 */     if (obfData.isEmpty()) {
/* 155 */       if (remap.forceRemap()) {
/* 156 */         this.ap.printMessage(Diagnostic.Kind.ERROR, "No obfuscation mapping for soft-implementing method", method.getElement());
/*     */       }
/*     */       return;
/*     */     } 
/* 160 */     addMethodMappings(method.getName(), desc, applyPrefix(obfData, prefix));
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
/*     */   private ObfuscationData<MappingMethod> applyPrefix(ObfuscationData<MappingMethod> data, String prefix) {
/* 172 */     if (prefix == null) {
/* 173 */       return data;
/*     */     }
/*     */     
/* 176 */     ObfuscationData<MappingMethod> prefixed = new ObfuscationData<MappingMethod>();
/* 177 */     for (ObfuscationType type : data) {
/* 178 */       MappingMethod mapping = data.get(type);
/* 179 */       prefixed.put(type, mapping.addPrefix(prefix));
/*     */     } 
/* 181 */     return prefixed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerSoftImplements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */