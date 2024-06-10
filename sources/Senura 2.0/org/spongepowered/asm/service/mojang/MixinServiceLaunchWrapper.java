/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.launchwrapper.IClassNameTransformer;
/*     */ import net.minecraft.launchwrapper.IClassTransformer;
/*     */ import net.minecraft.launchwrapper.ITweaker;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.lib.ClassReader;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.service.IClassBytecodeProvider;
/*     */ import org.spongepowered.asm.service.IClassProvider;
/*     */ import org.spongepowered.asm.service.ILegacyClassTransformer;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.ITransformer;
/*     */ import org.spongepowered.asm.util.ReEntranceLock;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinServiceLaunchWrapper
/*     */   implements IMixinService, IClassProvider, IClassBytecodeProvider
/*     */ {
/*     */   public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
/*     */   public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
/*     */   private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
/*     */   private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
/*     */   private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
/*     */   private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
/*  79 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private final LaunchClassLoaderUtil classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private final ReEntranceLock lock = new ReEntranceLock(1);
/*     */ 
/*     */ 
/*     */   
/*     */   private IClassNameTransformer nameTransformer;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return "LaunchWrapper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*     */     try {
/* 109 */       Launch.classLoader.hashCode();
/* 110 */     } catch (Throwable ex) {
/* 111 */       return false;
/*     */     } 
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 122 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.Phase getInitialPhase() {
/* 130 */     if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
/* 131 */       return MixinEnvironment.Phase.DEFAULT;
/*     */     }
/* 133 */     return MixinEnvironment.Phase.PREINIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 141 */     if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
/* 142 */       logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
/*     */     }
/*     */     
/* 145 */     List<String> tweakClasses = (List<String>)GlobalProperties.get("TweakClasses");
/* 146 */     if (tweakClasses != null) {
/* 147 */       tweakClasses.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock getReEntranceLock() {
/* 156 */     return this.lock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPlatformAgents() {
/* 164 */     return (Collection<String>)ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassProvider getClassProvider() {
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassBytecodeProvider getBytecodeProvider() {
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String name) throws ClassNotFoundException {
/* 191 */     return Launch.classLoader.findClass(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
/* 200 */     return Class.forName(name, initialize, (ClassLoader)Launch.classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
/* 209 */     return Class.forName(name, initialize, Launch.class.getClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginPhase() {
/* 217 */     Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkEnv(Object bootSource) {
/* 226 */     if (bootSource.getClass().getClassLoader() != Launch.class.getClassLoader()) {
/* 227 */       throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String name) {
/* 237 */     return Launch.classLoader.getResourceAsStream(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInvalidClass(String className) {
/* 246 */     this.classLoaderUtil.registerInvalidClass(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassLoaded(String className) {
/* 255 */     return this.classLoaderUtil.isClassLoaded(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassRestrictions(String className) {
/* 264 */     String restrictions = "";
/* 265 */     if (this.classLoaderUtil.isClassClassLoaderExcluded(className, null)) {
/* 266 */       restrictions = "PACKAGE_CLASSLOADER_EXCLUSION";
/*     */     }
/* 268 */     if (this.classLoaderUtil.isClassTransformerExcluded(className, null)) {
/* 269 */       restrictions = ((restrictions.length() > 0) ? (restrictions + ",") : "") + "PACKAGE_TRANSFORMER_EXCLUSION";
/*     */     }
/* 271 */     return restrictions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL[] getClassPath() {
/* 279 */     return (URL[])Launch.classLoader.getSources().toArray((Object[])new URL[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ITransformer> getTransformers() {
/* 287 */     List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
/* 288 */     List<ITransformer> wrapped = new ArrayList<ITransformer>(transformers.size());
/* 289 */     for (IClassTransformer transformer : transformers) {
/* 290 */       if (transformer instanceof ITransformer) {
/* 291 */         wrapped.add((ITransformer)transformer);
/*     */       } else {
/* 293 */         wrapped.add(new LegacyTransformerHandle(transformer));
/*     */       } 
/*     */       
/* 296 */       if (transformer instanceof IClassNameTransformer) {
/* 297 */         logger.debug("Found name transformer: {}", new Object[] { transformer.getClass().getName() });
/* 298 */         this.nameTransformer = (IClassNameTransformer)transformer;
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     return wrapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getClassBytes(String name, String transformedName) throws IOException {
/* 311 */     byte[] classBytes = Launch.classLoader.getClassBytes(name);
/* 312 */     if (classBytes != null) {
/* 313 */       return classBytes;
/*     */     }
/*     */     
/* 316 */     URLClassLoader appClassLoader = (URLClassLoader)Launch.class.getClassLoader();
/*     */     
/* 318 */     InputStream classStream = null;
/*     */     try {
/* 320 */       String resourcePath = transformedName.replace('.', '/').concat(".class");
/* 321 */       classStream = appClassLoader.getResourceAsStream(resourcePath);
/* 322 */       return IOUtils.toByteArray(classStream);
/* 323 */     } catch (Exception ex) {
/* 324 */       return null;
/*     */     } finally {
/* 326 */       IOUtils.closeQuietly(classStream);
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
/*     */   public byte[] getClassBytes(String className, boolean runTransformers) throws ClassNotFoundException, IOException {
/* 342 */     String transformedName = className.replace('/', '.');
/* 343 */     String name = unmapClassName(transformedName);
/*     */     
/* 345 */     Profiler profiler = MixinEnvironment.getProfiler();
/* 346 */     Profiler.Section loadTime = profiler.begin(1, "class.load");
/* 347 */     byte[] classBytes = getClassBytes(name, transformedName);
/* 348 */     loadTime.end();
/*     */     
/* 350 */     if (runTransformers) {
/* 351 */       Profiler.Section transformTime = profiler.begin(1, "class.transform");
/* 352 */       classBytes = applyTransformers(name, transformedName, classBytes, profiler);
/* 353 */       transformTime.end();
/*     */     } 
/*     */     
/* 356 */     if (classBytes == null) {
/* 357 */       throw new ClassNotFoundException(String.format("The specified class '%s' was not found", new Object[] { transformedName }));
/*     */     }
/*     */     
/* 360 */     return classBytes;
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
/*     */   private byte[] applyTransformers(String name, String transformedName, byte[] basicClass, Profiler profiler) {
/* 374 */     if (this.classLoaderUtil.isClassExcluded(name, transformedName)) {
/* 375 */       return basicClass;
/*     */     }
/*     */     
/* 378 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/*     */     
/* 380 */     for (ILegacyClassTransformer transformer : environment.getTransformers()) {
/*     */       
/* 382 */       this.lock.clear();
/*     */       
/* 384 */       int pos = transformer.getName().lastIndexOf('.');
/* 385 */       String simpleName = transformer.getName().substring(pos + 1);
/* 386 */       Profiler.Section transformTime = profiler.begin(2, simpleName.toLowerCase());
/* 387 */       transformTime.setInfo(transformer.getName());
/* 388 */       basicClass = transformer.transformClassBytes(name, transformedName, basicClass);
/* 389 */       transformTime.end();
/*     */       
/* 391 */       if (this.lock.isSet()) {
/*     */         
/* 393 */         environment.addTransformerExclusion(transformer.getName());
/*     */         
/* 395 */         this.lock.clear();
/* 396 */         logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[] { transformer
/* 397 */               .getName() });
/*     */       } 
/*     */     } 
/*     */     
/* 401 */     return basicClass;
/*     */   }
/*     */   
/*     */   private String unmapClassName(String className) {
/* 405 */     if (this.nameTransformer == null) {
/* 406 */       findNameTransformer();
/*     */     }
/*     */     
/* 409 */     if (this.nameTransformer != null) {
/* 410 */       return this.nameTransformer.unmapClassName(className);
/*     */     }
/*     */     
/* 413 */     return className;
/*     */   }
/*     */   
/*     */   private void findNameTransformer() {
/* 417 */     List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
/* 418 */     for (IClassTransformer transformer : transformers) {
/* 419 */       if (transformer instanceof IClassNameTransformer) {
/* 420 */         logger.debug("Found name transformer: {}", new Object[] { transformer.getClass().getName() });
/* 421 */         this.nameTransformer = (IClassNameTransformer)transformer;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode(String className) throws ClassNotFoundException, IOException {
/* 432 */     return getClassNode(getClassBytes(className, true), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassNode getClassNode(byte[] classBytes, int flags) {
/* 443 */     ClassNode classNode = new ClassNode();
/* 444 */     ClassReader classReader = new ClassReader(classBytes);
/* 445 */     classReader.accept((ClassVisitor)classNode, flags);
/* 446 */     return classNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSideName() {
/* 456 */     for (ITweaker tweaker : GlobalProperties.get("Tweaks")) {
/* 457 */       if (tweaker.getClass().getName().endsWith(".common.launcher.FMLServerTweaker"))
/* 458 */         return "SERVER"; 
/* 459 */       if (tweaker.getClass().getName().endsWith(".common.launcher.FMLTweaker")) {
/* 460 */         return "CLIENT";
/*     */       }
/*     */     } 
/*     */     
/* 464 */     String name = getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
/* 465 */     if (name != null) {
/* 466 */       return name;
/*     */     }
/*     */     
/* 469 */     name = getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
/* 470 */     if (name != null) {
/* 471 */       return name;
/*     */     }
/*     */     
/* 474 */     name = getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
/* 475 */     if (name != null) {
/* 476 */       return name;
/*     */     }
/*     */     
/* 479 */     return "UNKNOWN";
/*     */   }
/*     */   
/*     */   private String getSideName(String className, String methodName) {
/*     */     try {
/* 484 */       Class<?> clazz = Class.forName(className, false, (ClassLoader)Launch.classLoader);
/* 485 */       Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
/* 486 */       return ((Enum)method.invoke(null, new Object[0])).name();
/* 487 */     } catch (Exception ex) {
/* 488 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int findInStackTrace(String className, String methodName) {
/* 493 */     Thread currentThread = Thread.currentThread();
/*     */     
/* 495 */     if (!"main".equals(currentThread.getName())) {
/* 496 */       return 0;
/*     */     }
/*     */     
/* 499 */     StackTraceElement[] stackTrace = currentThread.getStackTrace();
/* 500 */     for (StackTraceElement s : stackTrace) {
/* 501 */       if (className.equals(s.getClassName()) && methodName.equals(s.getMethodName())) {
/* 502 */         return s.getLineNumber();
/*     */       }
/*     */     } 
/*     */     
/* 506 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\service\mojang\MixinServiceLaunchWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */