/*     */ package org.spongepowered.asm.mixin.transformer.ext;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Extensions
/*     */ {
/*     */   private final MixinTransformer transformer;
/*  52 */   private final List<IExtension> extensions = new ArrayList<IExtension>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private final Map<Class<? extends IExtension>, IExtension> extensionMap = new HashMap<Class<? extends IExtension>, IExtension>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final List<IClassGenerator> generators = new ArrayList<IClassGenerator>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private final List<IClassGenerator> generatorsView = Collections.unmodifiableList(this.generators);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap = new HashMap<Class<? extends IClassGenerator>, IClassGenerator>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private List<IExtension> activeExtensions = Collections.emptyList();
/*     */   
/*     */   public Extensions(MixinTransformer transformer) {
/*  84 */     this.transformer = transformer;
/*     */   }
/*     */   
/*     */   public MixinTransformer getTransformer() {
/*  88 */     return this.transformer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IExtension extension) {
/*  97 */     this.extensions.add(extension);
/*  98 */     this.extensionMap.put(extension.getClass(), extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IExtension> getExtensions() {
/* 105 */     return Collections.unmodifiableList(this.extensions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IExtension> getActiveExtensions() {
/* 112 */     return this.activeExtensions;
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
/*     */   public <T extends IExtension> T getExtension(Class<? extends IExtension> extensionClass) {
/* 124 */     return (T)lookup(extensionClass, this.extensionMap, this.extensions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void select(MixinEnvironment environment) {
/* 133 */     ImmutableList.Builder<IExtension> activeExtensions = ImmutableList.builder();
/*     */     
/* 135 */     for (IExtension extension : this.extensions) {
/* 136 */       if (extension.checkActive(environment)) {
/* 137 */         activeExtensions.add(extension);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     this.activeExtensions = (List<IExtension>)activeExtensions.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {
/* 150 */     for (IExtension extension : this.activeExtensions) {
/* 151 */       extension.preApply(context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {
/* 161 */     for (IExtension extension : this.activeExtensions) {
/* 162 */       extension.postApply(context);
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
/*     */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {
/* 176 */     for (IExtension extension : this.activeExtensions) {
/* 177 */       extension.export(env, name, force, bytes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IClassGenerator generator) {
/* 187 */     this.generators.add(generator);
/* 188 */     this.generatorMap.put(generator.getClass(), generator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IClassGenerator> getGenerators() {
/* 195 */     return this.generatorsView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends IClassGenerator> T getGenerator(Class<? extends IClassGenerator> generatorClass) {
/* 205 */     return (T)lookup(generatorClass, this.generatorMap, this.generators);
/*     */   }
/*     */   
/*     */   private static <T> T lookup(Class<? extends T> extensionClass, Map<Class<? extends T>, T> map, List<T> list) {
/* 209 */     T extension = map.get(extensionClass);
/* 210 */     if (extension == null) {
/* 211 */       for (T classGenerator : list) {
/* 212 */         if (extensionClass.isAssignableFrom(classGenerator.getClass())) {
/* 213 */           extension = classGenerator;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 218 */       if (extension == null) {
/* 219 */         throw new IllegalArgumentException("Extension for <" + extensionClass.getName() + "> could not be found");
/*     */       }
/*     */       
/* 222 */       map.put(extensionClass, extension);
/*     */     } 
/*     */     
/* 225 */     return extension;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\ext\Extensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */