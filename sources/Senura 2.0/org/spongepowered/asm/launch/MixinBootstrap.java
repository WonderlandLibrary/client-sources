/*     */ package org.spongepowered.asm.launch;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.platform.MixinPlatformManager;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MixinBootstrap
/*     */ {
/*     */   public static final String VERSION = "0.7.11";
/*  68 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   private static boolean initialised = false;
/*     */   
/*     */   private static boolean initState = true;
/*     */   private static MixinPlatformManager platform;
/*     */   
/*     */   static {
/*  76 */     MixinService.boot();
/*  77 */     MixinService.getService().prepare();
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
/*     */   @Deprecated
/*     */   public static void addProxy() {
/*  92 */     MixinService.getService().beginPhase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MixinPlatformManager getPlatform() {
/*  99 */     if (platform == null) {
/* 100 */       Object globalPlatformManager = GlobalProperties.get("mixin.platform");
/* 101 */       if (globalPlatformManager instanceof MixinPlatformManager) {
/* 102 */         platform = (MixinPlatformManager)globalPlatformManager;
/*     */       } else {
/* 104 */         platform = new MixinPlatformManager();
/* 105 */         GlobalProperties.put("mixin.platform", platform);
/* 106 */         platform.init();
/*     */       } 
/*     */     } 
/* 109 */     return platform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/* 116 */     if (!start()) {
/*     */       return;
/*     */     }
/*     */     
/* 120 */     doInit(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean start() {
/* 127 */     if (isSubsystemRegistered()) {
/* 128 */       if (!checkSubsystemVersion()) {
/* 129 */         throw new MixinInitialisationError("Mixin subsystem version " + getActiveSubsystemVersion() + " was already initialised. Cannot bootstrap version " + "0.7.11");
/*     */       }
/*     */       
/* 132 */       return false;
/*     */     } 
/*     */     
/* 135 */     registerSubsystem("0.7.11");
/*     */     
/* 137 */     if (!initialised) {
/* 138 */       initialised = true;
/*     */       
/* 140 */       String command = System.getProperty("sun.java.command");
/* 141 */       if (command != null && command.contains("GradleStart")) {
/* 142 */         System.setProperty("mixin.env.remapRefMap", "true");
/*     */       }
/*     */       
/* 145 */       MixinEnvironment.Phase initialPhase = MixinService.getService().getInitialPhase();
/* 146 */       if (initialPhase == MixinEnvironment.Phase.DEFAULT) {
/* 147 */         logger.error("Initialising mixin subsystem after game pre-init phase! Some mixins may be skipped.");
/* 148 */         MixinEnvironment.init(initialPhase);
/* 149 */         getPlatform().prepare(null);
/* 150 */         initState = false;
/*     */       } else {
/* 152 */         MixinEnvironment.init(initialPhase);
/*     */       } 
/*     */       
/* 155 */       MixinService.getService().beginPhase();
/*     */     } 
/*     */     
/* 158 */     getPlatform();
/*     */     
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void doInit(List<String> args) {
/* 167 */     if (!initialised) {
/* 168 */       if (isSubsystemRegistered()) {
/* 169 */         logger.warn("Multiple Mixin containers present, init suppressed for 0.7.11");
/*     */         
/*     */         return;
/*     */       } 
/* 173 */       throw new IllegalStateException("MixinBootstrap.doInit() called before MixinBootstrap.start()");
/*     */     } 
/*     */     
/* 176 */     getPlatform().getPhaseProviderClasses();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     if (initState) {
/* 183 */       getPlatform().prepare(args);
/* 184 */       MixinService.getService().init();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void inject() {
/* 189 */     getPlatform().inject();
/*     */   }
/*     */   
/*     */   private static boolean isSubsystemRegistered() {
/* 193 */     return (GlobalProperties.get("mixin.initialised") != null);
/*     */   }
/*     */   
/*     */   private static boolean checkSubsystemVersion() {
/* 197 */     return "0.7.11".equals(getActiveSubsystemVersion());
/*     */   }
/*     */   
/*     */   private static Object getActiveSubsystemVersion() {
/* 201 */     Object version = GlobalProperties.get("mixin.initialised");
/* 202 */     return (version != null) ? version : "";
/*     */   }
/*     */   
/*     */   private static void registerSubsystem(String version) {
/* 206 */     GlobalProperties.put("mixin.initialised", version);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\MixinBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */