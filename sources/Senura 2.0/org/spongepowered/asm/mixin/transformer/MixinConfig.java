/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.MixinInitialisationError;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.util.VersionNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MixinConfig
/*     */   implements Comparable<MixinConfig>, IMixinConfig
/*     */ {
/*     */   static class InjectorOptions
/*     */   {
/*     */     @SerializedName("defaultRequire")
/*  76 */     int defaultRequireValue = 0;
/*     */     
/*     */     @SerializedName("defaultGroup")
/*  79 */     String defaultGroup = "default";
/*     */     
/*     */     @SerializedName("injectionPoints")
/*     */     List<String> injectionPoints;
/*     */     
/*     */     @SerializedName("maxShiftBy")
/*  85 */     int maxShiftBy = 0;
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
/*     */   static class OverwriteOptions
/*     */   {
/*     */     @SerializedName("conformVisibility")
/*     */     boolean conformAccessModifiers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @SerializedName("requireAnnotations")
/*     */     boolean requireOverwriteAnnotations;
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
/* 128 */   private static int configOrder = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   private static final Set<String> globalMixinList = new HashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   private final transient Map<String, List<MixinInfo>> mixinMapping = new HashMap<String, List<MixinInfo>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   private final transient Set<String> unhandledTargets = new HashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private final transient List<MixinInfo> mixins = new ArrayList<MixinInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Config handle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("target")
/*     */   private String selector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("minVersion")
/*     */   private String version;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("compatibilityLevel")
/*     */   private String compatibility;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("required")
/*     */   private boolean required;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("priority")
/* 190 */   private int priority = 1000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("mixinPriority")
/* 199 */   private int mixinPriority = 1000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("package")
/*     */   private String mixinPackage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("mixins")
/*     */   private List<String> mixinClasses;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("client")
/*     */   private List<String> mixinClassesClient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("server")
/*     */   private List<String> mixinClassesServer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("setSourceFile")
/*     */   private boolean setSourceFile = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("refmap")
/*     */   private String refMapperConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("verbose")
/*     */   private boolean verboseLogging;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   private final transient int order = configOrder++;
/*     */   
/* 252 */   private final transient List<IListener> listeners = new ArrayList<IListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient IMixinService service;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient MixinEnvironment env;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("plugin")
/*     */   private String pluginClassName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("injectors")
/* 280 */   private InjectorOptions injectorOptions = new InjectorOptions();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("overwrites")
/* 286 */   private OverwriteOptions overwriteOptions = new OverwriteOptions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient IMixinConfigPlugin plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient IReferenceMapper refMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean prepared = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean visited = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean onLoad(IMixinService service, String name, MixinEnvironment fallbackEnvironment) {
/* 325 */     this.service = service;
/* 326 */     this.name = name;
/* 327 */     this.env = parseSelector(this.selector, fallbackEnvironment);
/* 328 */     this.required &= !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED) ? 1 : 0;
/* 329 */     initCompatibilityLevel();
/* 330 */     initInjectionPoints();
/* 331 */     return checkVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initCompatibilityLevel() {
/* 336 */     if (this.compatibility == null) {
/*     */       return;
/*     */     }
/*     */     
/* 340 */     MixinEnvironment.CompatibilityLevel level = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase());
/* 341 */     MixinEnvironment.CompatibilityLevel current = MixinEnvironment.getCompatibilityLevel();
/*     */     
/* 343 */     if (level == current) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 348 */     if (current.isAtLeast(level) && 
/* 349 */       !current.canSupport(level)) {
/* 350 */       throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + level + " which is too old");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 355 */     if (!current.canElevateTo(level)) {
/* 356 */       throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + level + " which is prohibited by " + current);
/*     */     }
/*     */ 
/*     */     
/* 360 */     MixinEnvironment.setCompatibilityLevel(level);
/*     */   }
/*     */ 
/*     */   
/*     */   private MixinEnvironment parseSelector(String target, MixinEnvironment fallbackEnvironment) {
/* 365 */     if (target != null) {
/* 366 */       String[] selectors = target.split("[&\\| ]");
/* 367 */       for (String sel : selectors) {
/* 368 */         sel = sel.trim();
/* 369 */         Pattern environmentSelector = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
/* 370 */         Matcher environmentSelectorMatcher = environmentSelector.matcher(sel);
/* 371 */         if (environmentSelectorMatcher.matches())
/*     */         {
/* 373 */           return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(environmentSelectorMatcher.group(1)));
/*     */         }
/*     */       } 
/*     */       
/* 377 */       MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(target);
/* 378 */       if (phase != null) {
/* 379 */         return MixinEnvironment.getEnvironment(phase);
/*     */       }
/*     */     } 
/* 382 */     return fallbackEnvironment;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initInjectionPoints() {
/* 387 */     if (this.injectorOptions.injectionPoints == null) {
/*     */       return;
/*     */     }
/*     */     
/* 391 */     for (String injectionPoint : this.injectorOptions.injectionPoints) {
/*     */       try {
/* 393 */         Class<?> injectionPointClass = this.service.getClassProvider().findClass(injectionPoint, true);
/* 394 */         if (InjectionPoint.class.isAssignableFrom(injectionPointClass)) {
/* 395 */           InjectionPoint.register(injectionPointClass); continue;
/*     */         } 
/* 397 */         this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[] { injectionPointClass, this });
/*     */       }
/* 399 */       catch (Throwable th) {
/* 400 */         this.logger.catching(th);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkVersion() throws MixinInitialisationError {
/* 406 */     if (this.version == null) {
/* 407 */       this.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[] { this.name });
/*     */     }
/*     */     
/* 410 */     VersionNumber minVersion = VersionNumber.parse(this.version);
/* 411 */     VersionNumber curVersion = VersionNumber.parse(this.env.getVersion());
/* 412 */     if (minVersion.compareTo(curVersion) > 0) {
/* 413 */       this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[] { this.name, minVersion, curVersion });
/*     */ 
/*     */       
/* 416 */       if (this.required) {
/* 417 */         throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + minVersion);
/*     */       }
/*     */       
/* 420 */       return false;
/*     */     } 
/*     */     
/* 423 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addListener(IListener listener) {
/* 432 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void onSelect() {
/* 439 */     if (this.pluginClassName != null) {
/*     */       try {
/* 441 */         Class<?> pluginClass = this.service.getClassProvider().findClass(this.pluginClassName, true);
/* 442 */         this.plugin = (IMixinConfigPlugin)pluginClass.newInstance();
/*     */         
/* 444 */         if (this.plugin != null) {
/* 445 */           this.plugin.onLoad(this.mixinPackage);
/*     */         }
/* 447 */       } catch (Throwable th) {
/* 448 */         th.printStackTrace();
/* 449 */         this.plugin = null;
/*     */       } 
/*     */     }
/*     */     
/* 453 */     if (!this.mixinPackage.endsWith(".")) {
/* 454 */       this.mixinPackage += ".";
/*     */     }
/*     */     
/* 457 */     boolean suppressRefMapWarning = false;
/*     */     
/* 459 */     if (this.refMapperConfig == null) {
/* 460 */       if (this.plugin != null) {
/* 461 */         this.refMapperConfig = this.plugin.getRefMapperConfig();
/*     */       }
/*     */       
/* 464 */       if (this.refMapperConfig == null) {
/* 465 */         suppressRefMapWarning = true;
/* 466 */         this.refMapperConfig = "mixin.refmap.json";
/*     */       } 
/*     */     } 
/*     */     
/* 470 */     this.refMapper = (IReferenceMapper)ReferenceMapper.read(this.refMapperConfig);
/* 471 */     this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
/*     */     
/* 473 */     if (!suppressRefMapWarning && this.refMapper.isDefault() && !this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
/* 474 */       this.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[] { this.refMapperConfig, this });
/*     */     }
/*     */ 
/*     */     
/* 478 */     if (this.env.getOption(MixinEnvironment.Option.REFMAP_REMAP)) {
/* 479 */       this.refMapper = RemappingReferenceMapper.of(this.env, this.refMapper);
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
/*     */   void prepare() {
/* 497 */     if (this.prepared) {
/*     */       return;
/*     */     }
/* 500 */     this.prepared = true;
/*     */     
/* 502 */     prepareMixins(this.mixinClasses, false);
/*     */     
/* 504 */     switch (this.env.getSide()) {
/*     */       case CLIENT:
/* 506 */         prepareMixins(this.mixinClassesClient, false);
/*     */         return;
/*     */       case SERVER:
/* 509 */         prepareMixins(this.mixinClassesServer, false);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 514 */     this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void postInitialise() {
/* 520 */     if (this.plugin != null) {
/* 521 */       List<String> pluginMixins = this.plugin.getMixins();
/* 522 */       prepareMixins(pluginMixins, true);
/*     */     } 
/*     */     
/* 525 */     for (Iterator<MixinInfo> iter = this.mixins.iterator(); iter.hasNext(); ) {
/* 526 */       MixinInfo mixin = iter.next();
/*     */       try {
/* 528 */         mixin.validate();
/* 529 */         for (IListener listener : this.listeners) {
/* 530 */           listener.onInit(mixin);
/*     */         }
/* 532 */       } catch (InvalidMixinException ex) {
/* 533 */         this.logger.error(ex.getMixin() + ": " + ex.getMessage(), (Throwable)ex);
/* 534 */         removeMixin(mixin);
/* 535 */         iter.remove();
/* 536 */       } catch (Exception ex) {
/* 537 */         this.logger.error(ex.getMessage(), ex);
/* 538 */         removeMixin(mixin);
/* 539 */         iter.remove();
/*     */       } 
/*     */     } 
/*     */   } static interface IListener {
/*     */     void onPrepare(MixinInfo param1MixinInfo); void onInit(MixinInfo param1MixinInfo); }
/*     */   private void removeMixin(MixinInfo remove) {
/* 545 */     for (List<MixinInfo> mixinsFor : this.mixinMapping.values()) {
/* 546 */       for (Iterator<MixinInfo> iter = mixinsFor.iterator(); iter.hasNext();) {
/* 547 */         if (remove == iter.next()) {
/* 548 */           iter.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void prepareMixins(List<String> mixinClasses, boolean suppressPlugin) {
/* 555 */     if (mixinClasses == null) {
/*     */       return;
/*     */     }
/*     */     
/* 559 */     for (String mixinClass : mixinClasses) {
/* 560 */       String fqMixinClass = this.mixinPackage + mixinClass;
/*     */       
/* 562 */       if (mixinClass == null || globalMixinList.contains(fqMixinClass)) {
/*     */         continue;
/*     */       }
/*     */       
/* 566 */       MixinInfo mixin = null;
/*     */       
/*     */       try {
/* 569 */         mixin = new MixinInfo(this.service, this, mixinClass, true, this.plugin, suppressPlugin);
/* 570 */         if (mixin.getTargetClasses().size() > 0) {
/* 571 */           globalMixinList.add(fqMixinClass);
/* 572 */           for (String targetClass : mixin.getTargetClasses()) {
/* 573 */             String targetClassName = targetClass.replace('/', '.');
/* 574 */             mixinsFor(targetClassName).add(mixin);
/* 575 */             this.unhandledTargets.add(targetClassName);
/*     */           } 
/* 577 */           for (IListener listener : this.listeners) {
/* 578 */             listener.onPrepare(mixin);
/*     */           }
/* 580 */           this.mixins.add(mixin);
/*     */         } 
/* 582 */       } catch (InvalidMixinException ex) {
/* 583 */         if (this.required) {
/* 584 */           throw ex;
/*     */         }
/* 586 */         this.logger.error(ex.getMessage(), (Throwable)ex);
/* 587 */       } catch (Exception ex) {
/* 588 */         if (this.required) {
/* 589 */           throw new InvalidMixinException(mixin, "Error initialising mixin " + mixin + " - " + ex.getClass() + ": " + ex.getMessage(), ex);
/*     */         }
/* 591 */         this.logger.error(ex.getMessage(), ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void postApply(String transformedName, ClassNode targetClass) {
/* 597 */     this.unhandledTargets.remove(transformedName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Config getHandle() {
/* 604 */     if (this.handle == null) {
/* 605 */       this.handle = new Config(this);
/*     */     }
/* 607 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRequired() {
/* 615 */     return this.required;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment getEnvironment() {
/* 624 */     return this.env;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 632 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMixinPackage() {
/* 640 */     return this.mixinPackage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 648 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultMixinPriority() {
/* 656 */     return this.mixinPriority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultRequiredInjections() {
/* 666 */     return this.injectorOptions.defaultRequireValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultInjectorGroup() {
/* 675 */     String defaultGroup = this.injectorOptions.defaultGroup;
/* 676 */     return (defaultGroup != null && !defaultGroup.isEmpty()) ? defaultGroup : "default";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean conformOverwriteVisibility() {
/* 686 */     return this.overwriteOptions.conformAccessModifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requireOverwriteAnnotations() {
/* 696 */     return this.overwriteOptions.requireOverwriteAnnotations;
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
/*     */   public int getMaxShiftByValue() {
/* 708 */     return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean select(MixinEnvironment environment) {
/* 713 */     this.visited = true;
/* 714 */     return (this.env == environment);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isVisited() {
/* 719 */     return this.visited;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDeclaredMixinCount() {
/* 728 */     return getCollectionSize((Collection<?>[])new Collection[] { this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getMixinCount() {
/* 737 */     return this.mixins.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getClasses() {
/* 744 */     return Collections.unmodifiableList(this.mixinClasses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldSetSourceFile() {
/* 752 */     return this.setSourceFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReferenceMapper getReferenceMapper() {
/* 759 */     if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
/* 760 */       return (IReferenceMapper)ReferenceMapper.DEFAULT_MAPPER;
/*     */     }
/* 762 */     this.refMapper.setContext(this.env.getRefmapObfuscationContext());
/* 763 */     return this.refMapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String remapClassName(String className, String reference) {
/* 771 */     return getReferenceMapper().remap(className, reference);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinConfigPlugin getPlugin() {
/* 779 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getTargets() {
/* 787 */     return Collections.unmodifiableSet(this.mixinMapping.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getUnhandledTargets() {
/* 794 */     return Collections.unmodifiableSet(this.unhandledTargets);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getLoggingLevel() {
/* 801 */     return this.verboseLogging ? Level.INFO : Level.DEBUG;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean packageMatch(String className) {
/* 812 */     return className.startsWith(this.mixinPackage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMixinsFor(String targetClass) {
/* 823 */     return this.mixinMapping.containsKey(targetClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MixinInfo> getMixinsFor(String targetClass) {
/* 833 */     return mixinsFor(targetClass);
/*     */   }
/*     */   
/*     */   private List<MixinInfo> mixinsFor(String targetClass) {
/* 837 */     List<MixinInfo> mixins = this.mixinMapping.get(targetClass);
/* 838 */     if (mixins == null) {
/* 839 */       mixins = new ArrayList<MixinInfo>();
/* 840 */       this.mixinMapping.put(targetClass, mixins);
/*     */     } 
/* 842 */     return mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> reloadMixin(String mixinClass, byte[] bytes) {
/* 853 */     for (Iterator<MixinInfo> iter = this.mixins.iterator(); iter.hasNext(); ) {
/* 854 */       MixinInfo mixin = iter.next();
/* 855 */       if (mixin.getClassName().equals(mixinClass)) {
/* 856 */         mixin.reloadMixin(bytes);
/* 857 */         return mixin.getTargetClasses();
/*     */       } 
/*     */     } 
/* 860 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 865 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(MixinConfig other) {
/* 873 */     if (other == null) {
/* 874 */       return 0;
/*     */     }
/* 876 */     if (other.priority == this.priority) {
/* 877 */       return this.order - other.order;
/*     */     }
/* 879 */     return this.priority - other.priority;
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
/*     */   static Config create(String configFile, MixinEnvironment outer) {
/*     */     try {
/* 892 */       IMixinService service = MixinService.getService();
/* 893 */       MixinConfig config = (MixinConfig)(new Gson()).fromJson(new InputStreamReader(service.getResourceAsStream(configFile)), MixinConfig.class);
/* 894 */       if (config.onLoad(service, configFile, outer)) {
/* 895 */         return config.getHandle();
/*     */       }
/* 897 */       return null;
/* 898 */     } catch (Exception ex) {
/* 899 */       ex.printStackTrace();
/* 900 */       throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", new Object[] { configFile }), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getCollectionSize(Collection<?>... collections) {
/* 905 */     int total = 0;
/* 906 */     for (Collection<?> collection : collections) {
/* 907 */       if (collection != null) {
/* 908 */         total += collection.size();
/*     */       }
/*     */     } 
/* 911 */     return total;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MixinConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */