/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launchwrapper.ITweaker;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IRemapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinPlatformAgentFML
/*     */   extends MixinPlatformAgentAbstract
/*     */ {
/*     */   private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
/*     */   private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
/*     */   private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
/*     */   private static final String CORE_MOD_MANAGER_CLASS_LEGACY = "cpw.mods.fml.relauncher.CoreModManager";
/*     */   private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
/*     */   private static final String GET_IGNORED_MODS_METHOD_LEGACY = "getLoadedCoremods";
/*     */   private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
/*     */   private static final String FML_CMDLINE_COREMODS = "fml.coreMods.load";
/*     */   private static final String FML_PLUGIN_WRAPPER_CLASS = "FMLPluginWrapper";
/*     */   private static final String FML_CORE_MOD_INSTANCE_FIELD = "coreModInstance";
/*     */   private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
/*     */   private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
/*     */   private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
/*     */   private static final String FML_TWEAKER_DEOBF = "FMLDeobfTweaker";
/*     */   private static final String FML_TWEAKER_INJECTION = "FMLInjectionAndSortingTweaker";
/*     */   private static final String FML_TWEAKER_TERMINAL = "TerminalTweaker";
/*  87 */   private static final Set<String> loadedCoreMods = new HashSet<String>();
/*     */   private final ITweaker coreModWrapper;
/*     */   private final String fileName;
/*     */   private Class<?> clCoreModManager;
/*     */   private boolean initInjectionState;
/*     */   
/*     */   static {
/*  94 */     for (String cmdLineCoreMod : System.getProperty("fml.coreMods.load", "").split(",")) {
/*  95 */       if (!cmdLineCoreMod.isEmpty()) {
/*  96 */         MixinPlatformAgentAbstract.logger.debug("FML platform agent will ignore coremod {} specified on the command line", new Object[] { cmdLineCoreMod });
/*  97 */         loadedCoreMods.add(cmdLineCoreMod);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinPlatformAgentFML(MixinPlatformManager manager, URI uri) {
/* 132 */     super(manager, uri);
/* 133 */     this.fileName = this.container.getName();
/* 134 */     this.coreModWrapper = initFMLCoreMod();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ITweaker initFMLCoreMod() {
/*     */     try {
/*     */       try {
/* 143 */         this.clCoreModManager = getCoreModManagerClass();
/* 144 */       } catch (ClassNotFoundException ex) {
/* 145 */         MixinPlatformAgentAbstract.logger.info("FML platform manager could not load class {}. Proceeding without FML support.", new Object[] { ex
/* 146 */               .getMessage() });
/* 147 */         return null;
/*     */       } 
/*     */       
/* 150 */       if ("true".equalsIgnoreCase(this.attributes.get("ForceLoadAsMod"))) {
/* 151 */         MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[] { this.fileName });
/* 152 */         loadAsMod();
/*     */       } 
/*     */       
/* 155 */       return injectCorePlugin();
/* 156 */     } catch (Exception ex) {
/* 157 */       MixinPlatformAgentAbstract.logger.catching(ex);
/* 158 */       return null;
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
/*     */   private void loadAsMod() {
/*     */     try {
/* 175 */       getIgnoredMods(this.clCoreModManager).remove(this.fileName);
/* 176 */     } catch (Exception ex) {
/* 177 */       MixinPlatformAgentAbstract.logger.catching(ex);
/*     */     } 
/*     */     
/* 180 */     if (this.attributes.get("FMLCorePluginContainsFMLMod") != null) {
/* 181 */       if (isIgnoredReparseable()) {
/* 182 */         MixinPlatformAgentAbstract.logger.debug("Ignoring request to add {} to reparseable coremod collection - it is a deobfuscated dependency", new Object[] { this.fileName });
/*     */         
/*     */         return;
/*     */       } 
/* 186 */       addReparseableJar();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isIgnoredReparseable() {
/* 191 */     return this.container.toString().contains("deobfedDeps");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addReparseableJar() {
/*     */     try {
/* 200 */       Method mdGetReparsedCoremods = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.reparseablecoremodsmethod", "getReparseableCoremods"), new Class[0]);
/*     */ 
/*     */       
/* 203 */       List<String> reparsedCoremods = (List<String>)mdGetReparsedCoremods.invoke((Object)null, new Object[0]);
/* 204 */       if (!reparsedCoremods.contains(this.fileName)) {
/* 205 */         MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[] { this.fileName });
/* 206 */         reparsedCoremods.add(this.fileName);
/*     */       } 
/* 208 */     } catch (Exception ex) {
/* 209 */       MixinPlatformAgentAbstract.logger.catching(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 214 */     String coreModName = this.attributes.get("FMLCorePlugin");
/* 215 */     if (coreModName == null) {
/* 216 */       return null;
/*     */     }
/*     */     
/* 219 */     if (isAlreadyInjected(coreModName)) {
/* 220 */       MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Skipping because it was already injected.", new Object[] { this.fileName, coreModName });
/* 221 */       return null;
/*     */     } 
/*     */     
/* 224 */     MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[] { this.fileName, coreModName });
/* 225 */     Method mdLoadCoreMod = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.loadcoremodmethod", "loadCoreMod"), new Class[] { LaunchClassLoader.class, String.class, File.class });
/*     */     
/* 227 */     mdLoadCoreMod.setAccessible(true);
/* 228 */     ITweaker wrapper = (ITweaker)mdLoadCoreMod.invoke((Object)null, new Object[] { Launch.classLoader, coreModName, this.container });
/* 229 */     if (wrapper == null) {
/* 230 */       MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[] { coreModName });
/* 231 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 236 */     this.initInjectionState = isTweakerQueued("FMLInjectionAndSortingTweaker");
/*     */     
/* 238 */     loadedCoreMods.add(coreModName);
/* 239 */     return wrapper;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAlreadyInjected(String coreModName) {
/* 244 */     if (loadedCoreMods.contains(coreModName)) {
/* 245 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 250 */       List<ITweaker> tweakers = (List<ITweaker>)GlobalProperties.get("Tweaks");
/* 251 */       if (tweakers == null) {
/* 252 */         return false;
/*     */       }
/*     */       
/* 255 */       for (ITweaker tweaker : tweakers) {
/* 256 */         Class<? extends ITweaker> tweakClass = (Class)tweaker.getClass();
/* 257 */         if ("FMLPluginWrapper".equals(tweakClass.getSimpleName())) {
/* 258 */           Field fdCoreModInstance = tweakClass.getField("coreModInstance");
/* 259 */           fdCoreModInstance.setAccessible(true);
/* 260 */           Object coreMod = fdCoreModInstance.get(tweaker);
/* 261 */           if (coreModName.equals(coreMod.getClass().getName())) {
/* 262 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 266 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPhaseProvider() {
/* 275 */     return MixinPlatformAgentFML.class.getName() + "$PhaseProvider";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 283 */     this.initInjectionState |= isTweakerQueued("FMLInjectionAndSortingTweaker");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPrimaryContainer() {
/* 292 */     if (this.clCoreModManager != null)
/*     */     {
/* 294 */       injectRemapper();
/*     */     }
/*     */   }
/*     */   
/*     */   private void injectRemapper() {
/*     */     try {
/* 300 */       MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[] { "org.spongepowered.asm.bridge.RemapperAdapterFML" });
/* 301 */       Class<?> clFmlRemapperAdapter = Class.forName("org.spongepowered.asm.bridge.RemapperAdapterFML", true, (ClassLoader)Launch.classLoader);
/* 302 */       Method mdCreate = clFmlRemapperAdapter.getDeclaredMethod("create", new Class[0]);
/* 303 */       IRemapper remapper = (IRemapper)mdCreate.invoke((Object)null, new Object[0]);
/* 304 */       MixinEnvironment.getDefaultEnvironment().getRemappers().add(remapper);
/* 305 */     } catch (Exception ex) {
/* 306 */       MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inject() {
/* 315 */     if (this.coreModWrapper != null && checkForCoInitialisation()) {
/* 316 */       MixinPlatformAgentAbstract.logger.debug("FML agent is co-initiralising coremod instance {} for {}", new Object[] { this.coreModWrapper, this.uri });
/* 317 */       this.coreModWrapper.injectIntoClassLoader(Launch.classLoader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLaunchTarget() {
/* 326 */     return null;
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
/*     */   protected final boolean checkForCoInitialisation() {
/* 342 */     boolean injectionTweaker = isTweakerQueued("FMLInjectionAndSortingTweaker");
/* 343 */     boolean terminalTweaker = isTweakerQueued("TerminalTweaker");
/* 344 */     if ((this.initInjectionState && terminalTweaker) || injectionTweaker) {
/* 345 */       MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML will inject it normally", new Object[] { this.coreModWrapper });
/* 346 */       return false;
/*     */     } 
/*     */     
/* 349 */     return !isTweakerQueued("FMLDeobfTweaker");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isTweakerQueued(String tweakerName) {
/* 360 */     for (String tweaker : GlobalProperties.get("TweakClasses")) {
/* 361 */       if (tweaker.endsWith(tweakerName)) {
/* 362 */         return true;
/*     */       }
/*     */     } 
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getCoreModManagerClass() throws ClassNotFoundException {
/*     */     try {
/* 374 */       return Class.forName(GlobalProperties.getString("mixin.launch.fml.coremodmanagerclass", "net.minecraftforge.fml.relauncher.CoreModManager"));
/*     */     }
/* 376 */     catch (ClassNotFoundException ex) {
/* 377 */       return Class.forName("cpw.mods.fml.relauncher.CoreModManager");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<String> getIgnoredMods(Class<?> clCoreModManager) throws IllegalAccessException, InvocationTargetException {
/* 383 */     Method mdGetIgnoredMods = null;
/*     */     
/*     */     try {
/* 386 */       mdGetIgnoredMods = clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.ignoredmodsmethod", "getIgnoredMods"), new Class[0]);
/*     */     }
/* 388 */     catch (NoSuchMethodException ex1) {
/*     */       
/*     */       try {
/* 391 */         mdGetIgnoredMods = clCoreModManager.getDeclaredMethod("getLoadedCoremods", new Class[0]);
/* 392 */       } catch (NoSuchMethodException ex2) {
/* 393 */         MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, ex2);
/* 394 */         return Collections.emptyList();
/*     */       } 
/*     */     } 
/*     */     
/* 398 */     return (List<String>)mdGetIgnoredMods.invoke((Object)null, new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentFML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */