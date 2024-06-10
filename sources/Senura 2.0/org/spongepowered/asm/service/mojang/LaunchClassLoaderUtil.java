/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LaunchClassLoaderUtil
/*     */ {
/*     */   private static final String CACHED_CLASSES_FIELD = "cachedClasses";
/*     */   private static final String INVALID_CLASSES_FIELD = "invalidClasses";
/*     */   private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
/*     */   private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
/*     */   private final LaunchClassLoader classLoader;
/*     */   private final Map<String, Class<?>> cachedClasses;
/*     */   private final Set<String> invalidClasses;
/*     */   private final Set<String> classLoaderExceptions;
/*     */   private final Set<String> transformerExceptions;
/*     */   
/*     */   LaunchClassLoaderUtil(LaunchClassLoader classLoader) {
/*  64 */     this.classLoader = classLoader;
/*  65 */     this.cachedClasses = getField(classLoader, "cachedClasses");
/*  66 */     this.invalidClasses = getField(classLoader, "invalidClasses");
/*  67 */     this.classLoaderExceptions = getField(classLoader, "classLoaderExceptions");
/*  68 */     this.transformerExceptions = getField(classLoader, "transformerExceptions");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LaunchClassLoader getClassLoader() {
/*  75 */     return this.classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isClassLoaded(String name) {
/*  86 */     return this.cachedClasses.containsKey(name);
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
/*     */   boolean isClassExcluded(String name, String transformedName) {
/*  98 */     return (isClassClassLoaderExcluded(name, transformedName) || isClassTransformerExcluded(name, transformedName));
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
/*     */   boolean isClassClassLoaderExcluded(String name, String transformedName) {
/* 111 */     for (String exception : getClassLoaderExceptions()) {
/* 112 */       if ((transformedName != null && transformedName.startsWith(exception)) || name.startsWith(exception)) {
/* 113 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return false;
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
/*     */   boolean isClassTransformerExcluded(String name, String transformedName) {
/* 130 */     for (String exception : getTransformerExceptions()) {
/* 131 */       if ((transformedName != null && transformedName.startsWith(exception)) || name.startsWith(exception)) {
/* 132 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerInvalidClass(String name) {
/* 147 */     if (this.invalidClasses != null) {
/* 148 */       this.invalidClasses.add(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getClassLoaderExceptions() {
/* 156 */     if (this.classLoaderExceptions != null) {
/* 157 */       return this.classLoaderExceptions;
/*     */     }
/* 159 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getTransformerExceptions() {
/* 166 */     if (this.transformerExceptions != null) {
/* 167 */       return this.transformerExceptions;
/*     */     }
/* 169 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getField(LaunchClassLoader classLoader, String fieldName) {
/*     */     try {
/* 175 */       Field field = LaunchClassLoader.class.getDeclaredField(fieldName);
/* 176 */       field.setAccessible(true);
/* 177 */       return (T)field.get(classLoader);
/* 178 */     } catch (Exception ex) {
/* 179 */       ex.printStackTrace();
/*     */       
/* 181 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\service\mojang\LaunchClassLoaderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */