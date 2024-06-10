/*     */ package org.spongepowered.asm.mixin.refmap;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReferenceMapper
/*     */   implements IReferenceMapper, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
/*  67 */   public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final Map<String, Map<String, String>> mappings = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private final Map<String, Map<String, Map<String, String>>> data = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient boolean readOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private transient String context = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient String resource;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceMapper() {
/* 102 */     this(false, "mixin.refmap.json");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ReferenceMapper(boolean readOnly, String resource) {
/* 111 */     this.readOnly = readOnly;
/* 112 */     this.resource = resource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 120 */     return this.readOnly;
/*     */   }
/*     */   
/*     */   private void setResourceName(String resource) {
/* 124 */     if (!this.readOnly) {
/* 125 */       this.resource = (resource != null) ? resource : "<unknown resource>";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourceName() {
/* 135 */     return this.resource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/* 143 */     return isDefault() ? "No refMap loaded." : ("Using refmap " + getResourceName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContext() {
/* 151 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContext(String context) {
/* 160 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remap(String className, String reference) {
/* 169 */     return remapWithContext(this.context, className, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String remapWithContext(String context, String className, String reference) {
/* 179 */     Map<String, Map<String, String>> mappings = this.mappings;
/* 180 */     if (context != null) {
/* 181 */       mappings = this.data.get(context);
/* 182 */       if (mappings == null) {
/* 183 */         mappings = this.mappings;
/*     */       }
/*     */     } 
/* 186 */     return remap(mappings, className, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String remap(Map<String, Map<String, String>> mappings, String className, String reference) {
/* 193 */     if (className == null) {
/* 194 */       for (Map<String, String> mapping : mappings.values()) {
/* 195 */         if (mapping.containsKey(reference)) {
/* 196 */           return mapping.get(reference);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 201 */     Map<String, String> classMappings = mappings.get(className);
/* 202 */     if (classMappings == null) {
/* 203 */       return reference;
/*     */     }
/* 205 */     String remappedReference = classMappings.get(reference);
/* 206 */     return (remappedReference != null) ? remappedReference : reference;
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
/*     */   public String addMapping(String context, String className, String reference, String newReference) {
/* 219 */     if (this.readOnly || reference == null || newReference == null || reference.equals(newReference)) {
/* 220 */       return null;
/*     */     }
/* 222 */     Map<String, Map<String, String>> mappings = this.mappings;
/* 223 */     if (context != null) {
/* 224 */       mappings = this.data.get(context);
/* 225 */       if (mappings == null) {
/* 226 */         mappings = Maps.newHashMap();
/* 227 */         this.data.put(context, mappings);
/*     */       } 
/*     */     } 
/* 230 */     Map<String, String> classMappings = mappings.get(className);
/* 231 */     if (classMappings == null) {
/* 232 */       classMappings = new HashMap<String, String>();
/* 233 */       mappings.put(className, classMappings);
/*     */     } 
/* 235 */     return classMappings.put(reference, newReference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(Appendable writer) {
/* 244 */     (new GsonBuilder()).setPrettyPrinting().create().toJson(this, writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceMapper read(String resourcePath) {
/* 254 */     Logger logger = LogManager.getLogger("mixin");
/* 255 */     Reader reader = null;
/*     */     try {
/* 257 */       IMixinService service = MixinService.getService();
/* 258 */       InputStream resource = service.getResourceAsStream(resourcePath);
/* 259 */       if (resource != null) {
/* 260 */         reader = new InputStreamReader(resource);
/* 261 */         ReferenceMapper mapper = readJson(reader);
/* 262 */         mapper.setResourceName(resourcePath);
/* 263 */         return mapper;
/*     */       } 
/* 265 */     } catch (JsonParseException ex) {
/* 266 */       logger.error("Invalid REFMAP JSON in " + resourcePath + ": " + ex.getClass().getName() + " " + ex.getMessage());
/* 267 */     } catch (Exception ex) {
/* 268 */       logger.error("Failed reading REFMAP JSON from " + resourcePath + ": " + ex.getClass().getName() + " " + ex.getMessage());
/*     */     } finally {
/* 270 */       IOUtils.closeQuietly(reader);
/*     */     } 
/*     */     
/* 273 */     return DEFAULT_MAPPER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceMapper read(Reader reader, String name) {
/*     */     try {
/* 285 */       ReferenceMapper mapper = readJson(reader);
/* 286 */       mapper.setResourceName(name);
/* 287 */       return mapper;
/* 288 */     } catch (Exception ex) {
/* 289 */       return DEFAULT_MAPPER;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ReferenceMapper readJson(Reader reader) {
/* 294 */     return (ReferenceMapper)(new Gson()).fromJson(reader, ReferenceMapper.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\refmap\ReferenceMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */