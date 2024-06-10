/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.Mixins;
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
/*     */ public class MixinPlatformManager
/*     */ {
/*     */   private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
/*     */   private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
/*  62 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final Map<URI, MixinContainer> containers = new LinkedHashMap<URI, MixinContainer>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MixinContainer primaryContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean prepared = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean injected;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  98 */     logger.debug("Initialising Mixin Platform Manager");
/*     */ 
/*     */     
/* 101 */     URI uri = null;
/*     */     try {
/* 103 */       uri = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
/* 104 */       if (uri != null) {
/* 105 */         logger.debug("Mixin platform: primary container is {}", new Object[] { uri });
/* 106 */         this.primaryContainer = addContainer(uri);
/*     */       } 
/* 108 */     } catch (URISyntaxException ex) {
/* 109 */       ex.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 113 */     scanClasspath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPhaseProviderClasses() {
/* 120 */     Collection<String> phaseProviders = this.primaryContainer.getPhaseProviders();
/* 121 */     if (phaseProviders != null) {
/* 122 */       return Collections.unmodifiableCollection(phaseProviders);
/*     */     }
/*     */     
/* 125 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final MixinContainer addContainer(URI uri) {
/* 136 */     MixinContainer existingContainer = this.containers.get(uri);
/* 137 */     if (existingContainer != null) {
/* 138 */       return existingContainer;
/*     */     }
/*     */     
/* 141 */     logger.debug("Adding mixin platform agents for container {}", new Object[] { uri });
/* 142 */     MixinContainer container = new MixinContainer(this, uri);
/* 143 */     this.containers.put(uri, container);
/*     */     
/* 145 */     if (this.prepared) {
/* 146 */       container.prepare();
/*     */     }
/* 148 */     return container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void prepare(List<String> args) {
/* 157 */     this.prepared = true;
/* 158 */     for (MixinContainer container : this.containers.values()) {
/* 159 */       container.prepare();
/*     */     }
/* 161 */     if (args != null) {
/* 162 */       parseArgs(args);
/*     */     } else {
/* 164 */       String argv = System.getProperty("sun.java.command");
/* 165 */       if (argv != null) {
/* 166 */         parseArgs(Arrays.asList(argv.split(" ")));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseArgs(List<String> args) {
/* 177 */     boolean captureNext = false;
/* 178 */     for (String arg : args) {
/* 179 */       if (captureNext) {
/* 180 */         addConfig(arg);
/*     */       }
/* 182 */       captureNext = "--mixin".equals(arg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inject() {
/* 190 */     if (this.injected) {
/*     */       return;
/*     */     }
/* 193 */     this.injected = true;
/*     */     
/* 195 */     if (this.primaryContainer != null) {
/* 196 */       this.primaryContainer.initPrimaryContainer();
/*     */     }
/*     */     
/* 199 */     scanClasspath();
/* 200 */     logger.debug("inject() running with {} agents", new Object[] { Integer.valueOf(this.containers.size()) });
/* 201 */     for (MixinContainer container : this.containers.values()) {
/*     */       try {
/* 203 */         container.inject();
/* 204 */       } catch (Exception ex) {
/* 205 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scanClasspath() {
/* 215 */     URL[] sources = MixinService.getService().getClassProvider().getClassPath();
/* 216 */     for (URL url : sources) {
/*     */       try {
/* 218 */         URI uri = url.toURI();
/* 219 */         if (!this.containers.containsKey(uri)) {
/*     */ 
/*     */           
/* 222 */           logger.debug("Scanning {} for mixin tweaker", new Object[] { uri });
/* 223 */           if ("file".equals(uri.getScheme()) && (new File(uri)).exists())
/*     */           
/*     */           { 
/* 226 */             MainAttributes attributes = MainAttributes.of(uri);
/* 227 */             String tweaker = attributes.get("TweakClass");
/* 228 */             if ("org.spongepowered.asm.launch.MixinTweaker".equals(tweaker))
/* 229 */             { logger.debug("{} contains a mixin tweaker, adding agents", new Object[] { uri });
/* 230 */               addContainer(uri); }  } 
/*     */         } 
/* 232 */       } catch (Exception ex) {
/* 233 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLaunchTarget() {
/* 243 */     for (MixinContainer container : this.containers.values()) {
/* 244 */       String mainClass = container.getLaunchTarget();
/* 245 */       if (mainClass != null) {
/* 246 */         return mainClass;
/*     */       }
/*     */     } 
/*     */     
/* 250 */     return "net.minecraft.client.main.Main";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void setCompatibilityLevel(String level) {
/*     */     try {
/* 262 */       MixinEnvironment.CompatibilityLevel value = MixinEnvironment.CompatibilityLevel.valueOf(level.toUpperCase());
/* 263 */       logger.debug("Setting mixin compatibility level: {}", new Object[] { value });
/* 264 */       MixinEnvironment.setCompatibilityLevel(value);
/* 265 */     } catch (IllegalArgumentException ex) {
/* 266 */       logger.warn("Invalid compatibility level specified: {}", new Object[] { level });
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
/*     */   final void addConfig(String config) {
/* 280 */     if (config.endsWith(".json")) {
/* 281 */       logger.debug("Registering mixin config: {}", new Object[] { config });
/* 282 */       Mixins.addConfiguration(config);
/* 283 */     } else if (config.contains(".json@")) {
/* 284 */       int pos = config.indexOf(".json@");
/* 285 */       String phaseName = config.substring(pos + 6);
/* 286 */       config = config.substring(0, pos + 5);
/* 287 */       MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(phaseName);
/* 288 */       if (phase != null) {
/* 289 */         logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[] { config });
/* 290 */         logger.debug("Registering mixin config: {}", new Object[] { config });
/* 291 */         MixinEnvironment.getEnvironment(phase).addConfiguration(config);
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
/*     */   final void addTokenProvider(String provider) {
/* 306 */     if (provider.contains("@")) {
/* 307 */       String[] parts = provider.split("@", 2);
/* 308 */       MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(parts[1]);
/* 309 */       if (phase != null) {
/* 310 */         logger.debug("Registering token provider class: {}", new Object[] { parts[0] });
/* 311 */         MixinEnvironment.getEnvironment(phase).registerTokenProviderClass(parts[0]);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 316 */     MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(provider);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\platform\MixinPlatformManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */