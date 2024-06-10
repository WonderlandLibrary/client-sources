/*     */ package org.spongepowered.asm.mixin.refmap;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.io.LineProcessor;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RemappingReferenceMapper
/*     */   implements IReferenceMapper
/*     */ {
/*     */   private static final String DEFAULT_RESOURCE_PATH_PROPERTY = "net.minecraftforge.gradle.GradleStart.srg.srg-mcp";
/*     */   private static final String DEFAULT_MAPPING_ENV = "searge";
/*  87 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static final Map<String, Map<String, String>> srgs = new HashMap<String, Map<String, String>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IReferenceMapper refMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<String, String> mappings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private final Map<String, Map<String, String>> cache = new HashMap<String, Map<String, String>>();
/*     */   
/*     */   private RemappingReferenceMapper(MixinEnvironment env, IReferenceMapper refMap) {
/* 112 */     this.refMap = refMap;
/* 113 */     this.refMap.setContext(getMappingEnv(env));
/*     */     
/* 115 */     String resource = getResource(env);
/* 116 */     this.mappings = loadSrgs(resource);
/*     */     
/* 118 */     logger.info("Remapping refMap {} using {}", new Object[] { refMap.getResourceName(), resource });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 126 */     return this.refMap.isDefault();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourceName() {
/* 135 */     return this.refMap.getResourceName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/* 143 */     return this.refMap.getStatus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContext() {
/* 151 */     return this.refMap.getContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContext(String context) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remap(String className, String reference) {
/* 169 */     Map<String, String> classCache = getCache(className);
/* 170 */     String remapped = classCache.get(reference);
/* 171 */     if (remapped == null) {
/* 172 */       remapped = this.refMap.remap(className, reference);
/* 173 */       for (Map.Entry<String, String> entry : this.mappings.entrySet()) {
/* 174 */         remapped = remapped.replace(entry.getKey(), entry.getValue());
/*     */       }
/* 176 */       classCache.put(reference, remapped);
/*     */     } 
/* 178 */     return remapped;
/*     */   }
/*     */   
/*     */   private Map<String, String> getCache(String className) {
/* 182 */     Map<String, String> classCache = this.cache.get(className);
/* 183 */     if (classCache == null) {
/* 184 */       classCache = new HashMap<String, String>();
/* 185 */       this.cache.put(className, classCache);
/*     */     } 
/* 187 */     return classCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remapWithContext(String context, String className, String reference) {
/* 197 */     return this.refMap.remapWithContext(context, className, reference);
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
/*     */   private static Map<String, String> loadSrgs(String fileName) {
/* 209 */     if (srgs.containsKey(fileName)) {
/* 210 */       return srgs.get(fileName);
/*     */     }
/*     */     
/* 213 */     final Map<String, String> map = new HashMap<String, String>();
/* 214 */     srgs.put(fileName, map);
/*     */     
/* 216 */     File file = new File(fileName);
/* 217 */     if (!file.isFile()) {
/* 218 */       return map;
/*     */     }
/*     */     
/*     */     try {
/* 222 */       Files.readLines(file, Charsets.UTF_8, new LineProcessor<Object>()
/*     */           {
/*     */             public Object getResult()
/*     */             {
/* 226 */               return null;
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean processLine(String line) throws IOException {
/* 231 */               if (Strings.isNullOrEmpty(line) || line.startsWith("#")) {
/* 232 */                 return true;
/*     */               }
/* 234 */               int fromPos = 0, toPos = 0;
/* 235 */               if ((toPos = line.startsWith("MD: ") ? 2 : (line.startsWith("FD: ") ? 1 : 0)) > 0) {
/* 236 */                 String[] entries = line.substring(4).split(" ", 4);
/* 237 */                 map.put(entries[fromPos]
/* 238 */                     .substring(entries[fromPos].lastIndexOf('/') + 1), entries[toPos]
/* 239 */                     .substring(entries[toPos].lastIndexOf('/') + 1));
/*     */               } 
/*     */               
/* 242 */               return true;
/*     */             }
/*     */           });
/* 245 */     } catch (IOException ex) {
/* 246 */       logger.warn("Could not read input SRG file: {}", new Object[] { fileName });
/* 247 */       logger.catching(ex);
/*     */     } 
/*     */     
/* 250 */     return map;
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
/*     */   public static IReferenceMapper of(MixinEnvironment env, IReferenceMapper refMap) {
/* 262 */     if (!refMap.isDefault() && hasData(env)) {
/* 263 */       return new RemappingReferenceMapper(env, refMap);
/*     */     }
/* 265 */     return refMap;
/*     */   }
/*     */   
/*     */   private static boolean hasData(MixinEnvironment env) {
/* 269 */     String fileName = getResource(env);
/* 270 */     return (fileName != null && (new File(fileName)).exists());
/*     */   }
/*     */   
/*     */   private static String getResource(MixinEnvironment env) {
/* 274 */     String resource = env.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_RESOURCE);
/* 275 */     return Strings.isNullOrEmpty(resource) ? System.getProperty("net.minecraftforge.gradle.GradleStart.srg.srg-mcp") : resource;
/*     */   }
/*     */   
/*     */   private static String getMappingEnv(MixinEnvironment env) {
/* 279 */     String resource = env.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_SOURCE_ENV);
/* 280 */     return Strings.isNullOrEmpty(resource) ? "searge" : resource;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\refmap\RemappingReferenceMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */