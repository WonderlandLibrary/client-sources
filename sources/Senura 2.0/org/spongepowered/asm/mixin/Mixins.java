/*     */ package org.spongepowered.asm.mixin;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.mixin.transformer.Config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Mixins
/*     */ {
/*  46 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CONFIGS_KEY = "mixin.configs.queue";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final Set<String> errorHandlers = new LinkedHashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addConfigurations(String... configFiles) {
/*  66 */     MixinEnvironment fallback = MixinEnvironment.getDefaultEnvironment();
/*  67 */     for (String configFile : configFiles) {
/*  68 */       createConfiguration(configFile, fallback);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addConfiguration(String configFile) {
/*  78 */     createConfiguration(configFile, MixinEnvironment.getDefaultEnvironment());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   static void addConfiguration(String configFile, MixinEnvironment fallback) {
/*  83 */     createConfiguration(configFile, fallback);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void createConfiguration(String configFile, MixinEnvironment fallback) {
/*  88 */     Config config = null;
/*     */     
/*     */     try {
/*  91 */       config = Config.create(configFile, fallback);
/*  92 */     } catch (Exception ex) {
/*  93 */       logger.error("Error encountered reading mixin config " + configFile + ": " + ex.getClass().getName() + " " + ex.getMessage(), ex);
/*     */     } 
/*     */     
/*  96 */     registerConfiguration(config);
/*     */   }
/*     */   
/*     */   private static void registerConfiguration(Config config) {
/* 100 */     if (config == null) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     MixinEnvironment env = config.getEnvironment();
/* 105 */     if (env != null) {
/* 106 */       env.registerConfig(config.getName());
/*     */     }
/* 108 */     getConfigs().add(config);
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
/*     */   public static int getUnvisitedCount() {
/* 127 */     int count = 0;
/* 128 */     for (Config config : getConfigs()) {
/* 129 */       if (!config.isVisited()) {
/* 130 */         count++;
/*     */       }
/*     */     } 
/* 133 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<Config> getConfigs() {
/* 141 */     Set<Config> mixinConfigs = (Set<Config>)GlobalProperties.get("mixin.configs.queue");
/* 142 */     if (mixinConfigs == null) {
/* 143 */       mixinConfigs = new LinkedHashSet<Config>();
/* 144 */       GlobalProperties.put("mixin.configs.queue", mixinConfigs);
/*     */     } 
/* 146 */     return mixinConfigs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerErrorHandlerClass(String handlerName) {
/* 155 */     if (handlerName != null) {
/* 156 */       errorHandlers.add(handlerName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> getErrorHandlerClasses() {
/* 164 */     return Collections.unmodifiableSet(errorHandlers);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\Mixins.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */