/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
/*     */ import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObfuscationType
/*     */ {
/*  48 */   private static final Map<String, ObfuscationType> types = new LinkedHashMap<String, ObfuscationType>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String key;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ObfuscationTypeDescriptor descriptor;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinAnnotationProcessor ap;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IOptionProvider options;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObfuscationType(ObfuscationTypeDescriptor descriptor, IMixinAnnotationProcessor ap) {
/*  71 */     this.key = descriptor.getKey();
/*  72 */     this.descriptor = descriptor;
/*  73 */     this.ap = ap;
/*  74 */     this.options = (IOptionProvider)ap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObfuscationEnvironment createEnvironment() {
/*     */     try {
/*  82 */       Class<? extends ObfuscationEnvironment> cls = this.descriptor.getEnvironmentType();
/*  83 */       Constructor<? extends ObfuscationEnvironment> ctor = cls.getDeclaredConstructor(new Class[] { ObfuscationType.class });
/*  84 */       ctor.setAccessible(true);
/*  85 */       return ctor.newInstance(new Object[] { this });
/*  86 */     } catch (Exception ex) {
/*  87 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  98 */     return this.key;
/*     */   }
/*     */   
/*     */   public ObfuscationTypeDescriptor getConfig() {
/* 102 */     return this.descriptor;
/*     */   }
/*     */   
/*     */   public IMixinAnnotationProcessor getAnnotationProcessor() {
/* 106 */     return this.ap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 113 */     String defaultEnv = this.options.getOption("defaultObfuscationEnv");
/* 114 */     return ((defaultEnv == null && this.key.equals("searge")) || (defaultEnv != null && this.key
/* 115 */       .equals(defaultEnv.toLowerCase())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported() {
/* 122 */     return (getInputFileNames().size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getInputFileNames() {
/* 129 */     ImmutableList.Builder<String> builder = ImmutableList.builder();
/*     */     
/* 131 */     String inputFile = this.options.getOption(this.descriptor.getInputFileOption());
/* 132 */     if (inputFile != null) {
/* 133 */       builder.add(inputFile);
/*     */     }
/*     */     
/* 136 */     String extraInputFiles = this.options.getOption(this.descriptor.getExtraInputFilesOption());
/* 137 */     if (extraInputFiles != null) {
/* 138 */       for (String extraInputFile : extraInputFiles.split(";")) {
/* 139 */         builder.add(extraInputFile.trim());
/*     */       }
/*     */     }
/*     */     
/* 143 */     return (List<String>)builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOutputFileName() {
/* 150 */     return this.options.getOption(this.descriptor.getOutputFileOption());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterable<ObfuscationType> types() {
/* 157 */     return types.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationType create(ObfuscationTypeDescriptor descriptor, IMixinAnnotationProcessor ap) {
/* 168 */     String key = descriptor.getKey();
/* 169 */     if (types.containsKey(key)) {
/* 170 */       throw new IllegalArgumentException("Obfuscation type with key " + key + " was already registered");
/*     */     }
/* 172 */     ObfuscationType type = new ObfuscationType(descriptor, ap);
/* 173 */     types.put(key, type);
/* 174 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationType get(String key) {
/* 185 */     ObfuscationType type = types.get(key);
/* 186 */     if (type == null) {
/* 187 */       throw new IllegalArgumentException("Obfuscation type with key " + key + " was not registered");
/*     */     }
/* 189 */     return type;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\ObfuscationType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */