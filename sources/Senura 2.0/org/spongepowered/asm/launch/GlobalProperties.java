/*     */ package org.spongepowered.asm.launch;
/*     */ 
/*     */ import java.util.ServiceLoader;
/*     */ import org.spongepowered.asm.service.IGlobalPropertyService;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GlobalProperties
/*     */ {
/*     */   private static IGlobalPropertyService service;
/*     */   
/*     */   public static final class Keys
/*     */   {
/*     */     public static final String INIT = "mixin.initialised";
/*     */     public static final String AGENTS = "mixin.agents";
/*     */     public static final String CONFIGS = "mixin.configs";
/*     */     public static final String TRANSFORMER = "mixin.transformer";
/*     */     public static final String PLATFORM_MANAGER = "mixin.platform";
/*     */     public static final String FML_LOAD_CORE_MOD = "mixin.launch.fml.loadcoremodmethod";
/*     */     public static final String FML_GET_REPARSEABLE_COREMODS = "mixin.launch.fml.reparseablecoremodsmethod";
/*     */     public static final String FML_CORE_MOD_MANAGER = "mixin.launch.fml.coremodmanagerclass";
/*     */     public static final String FML_GET_IGNORED_MODS = "mixin.launch.fml.ignoredmodsmethod";
/*     */   }
/*     */   
/*     */   private static IGlobalPropertyService getService() {
/*  62 */     if (service == null) {
/*     */       
/*  64 */       ServiceLoader<IGlobalPropertyService> serviceLoader = ServiceLoader.load(IGlobalPropertyService.class, GlobalProperties.class.getClassLoader());
/*  65 */       service = serviceLoader.iterator().next();
/*     */     } 
/*  67 */     return service;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T get(String key) {
/*  78 */     return (T)getService().getProperty(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put(String key, Object value) {
/*  88 */     getService().setProperty(key, value);
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
/*     */   public static <T> T get(String key, T defaultValue) {
/* 101 */     return (T)getService().getProperty(key, defaultValue);
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
/*     */   public static String getString(String key, String defaultValue) {
/* 114 */     return getService().getPropertyString(key, defaultValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\GlobalProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */