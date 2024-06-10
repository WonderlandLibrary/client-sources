/*      */ package org.spongepowered.asm.mixin;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.logging.log4j.core.Appender;
/*      */ import org.apache.logging.log4j.core.LogEvent;
/*      */ import org.apache.logging.log4j.core.Logger;
/*      */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*      */ import org.spongepowered.asm.launch.GlobalProperties;
/*      */ import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
/*      */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.MixinTransformer;
/*      */ import org.spongepowered.asm.obfuscation.RemapperChain;
/*      */ import org.spongepowered.asm.service.ILegacyClassTransformer;
/*      */ import org.spongepowered.asm.service.IMixinService;
/*      */ import org.spongepowered.asm.service.ITransformer;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.ITokenProvider;
/*      */ import org.spongepowered.asm.util.JavaVersion;
/*      */ import org.spongepowered.asm.util.PrettyPrinter;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class MixinEnvironment
/*      */   implements ITokenProvider
/*      */ {
/*      */   public static final class Phase
/*      */   {
/*   75 */     static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   81 */     public static final Phase PREINIT = new Phase(0, "PREINIT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   86 */     public static final Phase INIT = new Phase(1, "INIT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   91 */     public static final Phase DEFAULT = new Phase(2, "DEFAULT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   96 */     static final List<Phase> phases = (List<Phase>)ImmutableList.of(PREINIT, INIT, DEFAULT);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int ordinal;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MixinEnvironment environment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Phase(int ordinal, String name) {
/*  118 */       this.ordinal = ordinal;
/*  119 */       this.name = name;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  124 */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Phase forName(String name) {
/*  135 */       for (Phase phase : phases) {
/*  136 */         if (phase.name.equals(name)) {
/*  137 */           return phase;
/*      */         }
/*      */       } 
/*  140 */       return null;
/*      */     }
/*      */     
/*      */     MixinEnvironment getEnvironment() {
/*  144 */       if (this.ordinal < 0) {
/*  145 */         throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
/*      */       }
/*      */       
/*  148 */       if (this.environment == null) {
/*  149 */         this.environment = new MixinEnvironment(this);
/*      */       }
/*      */       
/*  152 */       return this.environment;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Side
/*      */   {
/*  164 */     UNKNOWN
/*      */     {
/*      */       protected boolean detect() {
/*  167 */         return false;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  174 */     CLIENT
/*      */     {
/*      */       protected boolean detect() {
/*  177 */         String sideName = MixinService.getService().getSideName();
/*  178 */         return "CLIENT".equals(sideName);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  185 */     SERVER
/*      */     {
/*      */       protected boolean detect() {
/*  188 */         String sideName = MixinService.getService().getSideName();
/*  189 */         return ("SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName));
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract boolean detect();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Option
/*      */   {
/*  204 */     DEBUG_ALL("debug"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  211 */     DEBUG_EXPORT((String)DEBUG_ALL, "export"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  226 */     DEBUG_EXPORT_FILTER((String)DEBUG_EXPORT, "filter", false),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  231 */     DEBUG_EXPORT_DECOMPILE((String)DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, (Option)"decompile"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  239 */     DEBUG_EXPORT_DECOMPILE_THREADED((String)DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, (Option)"async"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES((String)DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, (Option)"mergeGenericSignatures"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  255 */     DEBUG_VERIFY((String)DEBUG_ALL, "verify"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  261 */     DEBUG_VERBOSE((String)DEBUG_ALL, "verbose"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  267 */     DEBUG_INJECTORS((String)DEBUG_ALL, "countInjections"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  272 */     DEBUG_STRICT((String)DEBUG_ALL, Inherit.INDEPENDENT, (Option)"strict"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  279 */     DEBUG_UNIQUE((String)DEBUG_STRICT, "unique"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  284 */     DEBUG_TARGETS((String)DEBUG_STRICT, "targets"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     DEBUG_PROFILER((String)DEBUG_ALL, Inherit.ALLOW_OVERRIDE, (Option)"profiler"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  301 */     CHECK_ALL("checks"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  307 */     CHECK_IMPLEMENTS((String)CHECK_ALL, "interfaces"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  315 */     CHECK_IMPLEMENTS_STRICT((String)CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, (Option)"strict"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  320 */     IGNORE_CONSTRAINTS("ignoreConstraints"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  325 */     HOT_SWAP("hotSwap"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  330 */     ENVIRONMENT((String)Inherit.ALWAYS_FALSE, "env"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  335 */     OBFUSCATION_TYPE((String)ENVIRONMENT, Inherit.ALWAYS_FALSE, (Option)"obf"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  340 */     DISABLE_REFMAP((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"disableRefMap"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     REFMAP_REMAP((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"remapRefMap"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  360 */     REFMAP_REMAP_RESOURCE((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"refMapRemappingFile", (Inherit)""),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  367 */     REFMAP_REMAP_SOURCE_ENV((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"refMapRemappingEnv", (Inherit)"searge"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  376 */     REFMAP_REMAP_ALLOW_PERMISSIVE((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"allowPermissiveMatch", true, "true"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  381 */     IGNORE_REQUIRED((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"ignoreRequired"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     DEFAULT_COMPATIBILITY_LEVEL((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"compatLevel"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  406 */     SHIFT_BY_VIOLATION_BEHAVIOUR((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"shiftByViolation", (Inherit)"warn"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  412 */     INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");
/*      */     private static final String PREFIX = "mixin";
/*      */     final Option parent;
/*      */     final Inherit inheritance;
/*      */     final String property;
/*      */     final String defaultValue;
/*      */     final boolean isFlag;
/*      */     final int depth;
/*      */     
/*      */     private enum Inherit {
/*  422 */       INHERIT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  429 */       ALLOW_OVERRIDE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  435 */       INDEPENDENT,
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  440 */       ALWAYS_FALSE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Option(Option parent, Inherit inheritance, String property, boolean isFlag, String defaultStringValue) {
/*  521 */       this.parent = parent;
/*  522 */       this.inheritance = inheritance;
/*  523 */       this.property = ((parent != null) ? parent.property : "mixin") + "." + property;
/*  524 */       this.defaultValue = defaultStringValue;
/*  525 */       this.isFlag = isFlag;
/*  526 */       int depth = 0;
/*  527 */       for (; parent != null; depth++) {
/*  528 */         parent = parent.parent;
/*      */       }
/*  530 */       this.depth = depth;
/*      */     }
/*      */     
/*      */     Option getParent() {
/*  534 */       return this.parent;
/*      */     }
/*      */     
/*      */     String getProperty() {
/*  538 */       return this.property;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  543 */       return this.isFlag ? String.valueOf(getBooleanValue()) : getStringValue();
/*      */     }
/*      */     
/*      */     private boolean getLocalBooleanValue(boolean defaultValue) {
/*  547 */       return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(defaultValue)));
/*      */     }
/*      */     
/*      */     private boolean getInheritedBooleanValue() {
/*  551 */       return (this.parent != null && this.parent.getBooleanValue());
/*      */     }
/*      */     
/*      */     final boolean getBooleanValue() {
/*  555 */       if (this.inheritance == Inherit.ALWAYS_FALSE) {
/*  556 */         return false;
/*      */       }
/*      */       
/*  559 */       boolean local = getLocalBooleanValue(false);
/*  560 */       if (this.inheritance == Inherit.INDEPENDENT) {
/*  561 */         return local;
/*      */       }
/*      */       
/*  564 */       boolean inherited = (local || getInheritedBooleanValue());
/*  565 */       return (this.inheritance == Inherit.INHERIT) ? inherited : getLocalBooleanValue(inherited);
/*      */     }
/*      */     
/*      */     final String getStringValue() {
/*  569 */       return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? 
/*  570 */         System.getProperty(this.property, this.defaultValue) : this.defaultValue;
/*      */     }
/*      */ 
/*      */     
/*      */     <E extends Enum<E>> E getEnumValue(E defaultValue) {
/*  575 */       String value = System.getProperty(this.property, defaultValue.name());
/*      */       try {
/*  577 */         return Enum.valueOf((Class)defaultValue.getClass(), value.toUpperCase());
/*  578 */       } catch (IllegalArgumentException ex) {
/*  579 */         return defaultValue;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum CompatibilityLevel
/*      */   {
/*  592 */     JAVA_6(6, 50, false),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  597 */     JAVA_7(7, 51, false)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  601 */         return (JavaVersion.current() >= 1.7D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  609 */     JAVA_8(8, 52, true)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  613 */         return (JavaVersion.current() >= 1.8D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  621 */     JAVA_9(9, 53, true)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  625 */         return false;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*      */     private static final int CLASS_V1_9 = 53;
/*      */     
/*      */     private final int ver;
/*      */     
/*      */     private final int classVersion;
/*      */     
/*      */     private final boolean supportsMethodsInInterfaces;
/*      */     
/*      */     private CompatibilityLevel maxCompatibleLevel;
/*      */ 
/*      */     
/*      */     CompatibilityLevel(int ver, int classVersion, boolean resolveMethodsInInterfaces) {
/*  642 */       this.ver = ver;
/*  643 */       this.classVersion = classVersion;
/*  644 */       this.supportsMethodsInInterfaces = resolveMethodsInInterfaces;
/*      */     }
/*      */ 
/*      */     
/*      */     private void setMaxCompatibleLevel(CompatibilityLevel maxCompatibleLevel) {
/*  649 */       this.maxCompatibleLevel = maxCompatibleLevel;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isSupported() {
/*  657 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int classVersion() {
/*  664 */       return this.classVersion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean supportsMethodsInInterfaces() {
/*  672 */       return this.supportsMethodsInInterfaces;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAtLeast(CompatibilityLevel level) {
/*  683 */       return (level == null || this.ver >= level.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canElevateTo(CompatibilityLevel level) {
/*  693 */       if (level == null || this.maxCompatibleLevel == null) {
/*  694 */         return true;
/*      */       }
/*  696 */       return (level.ver <= this.maxCompatibleLevel.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canSupport(CompatibilityLevel level) {
/*  706 */       if (level == null) {
/*  707 */         return true;
/*      */       }
/*      */       
/*  710 */       return level.canElevateTo(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TokenProviderWrapper
/*      */     implements Comparable<TokenProviderWrapper>
/*      */   {
/*  720 */     private static int nextOrder = 0;
/*      */     
/*      */     private final int priority;
/*      */     
/*      */     private final int order;
/*      */     private final IEnvironmentTokenProvider provider;
/*      */     private final MixinEnvironment environment;
/*      */     
/*      */     public TokenProviderWrapper(IEnvironmentTokenProvider provider, MixinEnvironment environment) {
/*  729 */       this.provider = provider;
/*  730 */       this.environment = environment;
/*  731 */       this.order = nextOrder++;
/*  732 */       this.priority = provider.getPriority();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(TokenProviderWrapper other) {
/*  737 */       if (other == null) {
/*  738 */         return 0;
/*      */       }
/*  740 */       if (other.priority == this.priority) {
/*  741 */         return other.order - this.order;
/*      */       }
/*  743 */       return other.priority - this.priority;
/*      */     }
/*      */     
/*      */     public IEnvironmentTokenProvider getProvider() {
/*  747 */       return this.provider;
/*      */     }
/*      */     
/*      */     Integer getToken(String token) {
/*  751 */       return this.provider.getToken(token, this.environment);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class MixinLogWatcher
/*      */   {
/*  761 */     static MixinAppender appender = new MixinAppender();
/*      */     static Logger log;
/*  763 */     static Level oldLevel = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void begin() {
/*  779 */       Logger fmlLog = LogManager.getLogger("FML");
/*  780 */       if (!(fmlLog instanceof Logger)) {
/*      */         return;
/*      */       }
/*      */       
/*  784 */       log = (Logger)fmlLog;
/*  785 */       oldLevel = log.getLevel();
/*      */       
/*  787 */       appender.start();
/*  788 */       log.addAppender((Appender)appender);
/*      */       
/*  790 */       log.setLevel(Level.ALL);
/*      */     }
/*      */     
/*      */     static void end() {
/*  794 */       if (log != null)
/*      */       {
/*  796 */         log.removeAppender((Appender)appender);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static class MixinAppender
/*      */       extends AbstractAppender
/*      */     {
/*      */       MixinAppender() {
/*  806 */         super("MixinLogWatcherAppender", null, null);
/*      */       }
/*      */ 
/*      */       
/*      */       public void append(LogEvent event) {
/*  811 */         if (event.getLevel() != Level.DEBUG || !"Validating minecraft".equals(event.getMessage().getFormattedMessage())) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  816 */         MixinEnvironment.gotoPhase(MixinEnvironment.Phase.INIT);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  823 */         if (MixinEnvironment.MixinLogWatcher.log.getLevel() == Level.ALL) {
/*  824 */           MixinEnvironment.MixinLogWatcher.log.setLevel(MixinEnvironment.MixinLogWatcher.oldLevel);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  835 */   private static final Set<String> excludeTransformers = Sets.newHashSet((Object[])new String[] { "net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MixinEnvironment currentEnvironment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  851 */   private static Phase currentPhase = Phase.NOT_INITIALISED;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  856 */   private static CompatibilityLevel compatibility = Option.DEFAULT_COMPATIBILITY_LEVEL.<CompatibilityLevel>getEnumValue(CompatibilityLevel.JAVA_6);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean showHeader = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  866 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  871 */   private static final Profiler profiler = new Profiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final IMixinService service;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Phase phase;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String configsKey;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean[] options;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  896 */   private final Set<String> tokenProviderClasses = new HashSet<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  901 */   private final List<TokenProviderWrapper> tokenProviders = new ArrayList<TokenProviderWrapper>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  906 */   private final Map<String, Integer> internalTokens = new HashMap<String, Integer>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  911 */   private final RemapperChain remappers = new RemapperChain();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Side side;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<ILegacyClassTransformer> transformers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  929 */   private String obfuscationContext = null;
/*      */   
/*      */   MixinEnvironment(Phase phase) {
/*  932 */     this.service = MixinService.getService();
/*  933 */     this.phase = phase;
/*  934 */     this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
/*      */ 
/*      */     
/*  937 */     Object version = getVersion();
/*  938 */     if (version == null || !"0.7.11".equals(version)) {
/*  939 */       throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
/*      */     }
/*      */ 
/*      */     
/*  943 */     this.service.checkEnv(this);
/*      */     
/*  945 */     this.options = new boolean[(Option.values()).length];
/*  946 */     for (Option option : Option.values()) {
/*  947 */       this.options[option.ordinal()] = option.getBooleanValue();
/*      */     }
/*      */     
/*  950 */     if (showHeader) {
/*  951 */       showHeader = false;
/*  952 */       printHeader(version);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void printHeader(Object version) {
/*  957 */     String codeSource = getCodeSource();
/*  958 */     String serviceName = this.service.getName();
/*  959 */     Side side = getSide();
/*  960 */     logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[] { version, codeSource, serviceName, side });
/*      */     
/*  962 */     boolean verbose = getOption(Option.DEBUG_VERBOSE);
/*  963 */     if (verbose || getOption(Option.DEBUG_EXPORT) || getOption(Option.DEBUG_PROFILER)) {
/*  964 */       PrettyPrinter printer = new PrettyPrinter(32);
/*  965 */       printer.add("SpongePowered MIXIN%s", new Object[] { verbose ? " (Verbose debugging enabled)" : "" }).centre().hr();
/*  966 */       printer.kv("Code source", codeSource);
/*  967 */       printer.kv("Internal Version", version);
/*  968 */       printer.kv("Java 8 Supported", Boolean.valueOf(CompatibilityLevel.JAVA_8.isSupported())).hr();
/*  969 */       printer.kv("Service Name", serviceName);
/*  970 */       printer.kv("Service Class", this.service.getClass().getName()).hr();
/*  971 */       for (Option option : Option.values()) {
/*  972 */         StringBuilder indent = new StringBuilder();
/*  973 */         for (int i = 0; i < option.depth; i++) {
/*  974 */           indent.append("- ");
/*      */         }
/*  976 */         printer.kv(option.property, "%s<%s>", new Object[] { indent, option });
/*      */       } 
/*  978 */       printer.hr().kv("Detected Side", side);
/*  979 */       printer.print(System.err);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getCodeSource() {
/*      */     try {
/*  985 */       return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
/*  986 */     } catch (Throwable th) {
/*  987 */       return "Unknown";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phase getPhase() {
/*  997 */     return this.phase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public List<String> getMixinConfigs() {
/* 1008 */     List<String> mixinConfigs = (List<String>)GlobalProperties.get(this.configsKey);
/* 1009 */     if (mixinConfigs == null) {
/* 1010 */       mixinConfigs = new ArrayList<String>();
/* 1011 */       GlobalProperties.put(this.configsKey, mixinConfigs);
/*      */     } 
/* 1013 */     return mixinConfigs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MixinEnvironment addConfiguration(String config) {
/* 1025 */     logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
/* 1026 */     Mixins.addConfiguration(config, this);
/* 1027 */     return this;
/*      */   }
/*      */   
/*      */   void registerConfig(String config) {
/* 1031 */     List<String> configs = getMixinConfigs();
/* 1032 */     if (!configs.contains(config)) {
/* 1033 */       configs.add(config);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MixinEnvironment registerErrorHandlerClass(String handlerName) {
/* 1046 */     Mixins.registerErrorHandlerClass(handlerName);
/* 1047 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment registerTokenProviderClass(String providerName) {
/* 1057 */     if (!this.tokenProviderClasses.contains(providerName)) {
/*      */       
/*      */       try {
/*      */         
/* 1061 */         Class<? extends IEnvironmentTokenProvider> providerClass = this.service.getClassProvider().findClass(providerName, true);
/* 1062 */         IEnvironmentTokenProvider provider = providerClass.newInstance();
/* 1063 */         registerTokenProvider(provider);
/* 1064 */       } catch (Throwable th) {
/* 1065 */         logger.error("Error instantiating " + providerName, th);
/*      */       } 
/*      */     }
/* 1068 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider provider) {
/* 1078 */     if (provider != null && !this.tokenProviderClasses.contains(provider.getClass().getName())) {
/* 1079 */       String providerName = provider.getClass().getName();
/* 1080 */       TokenProviderWrapper wrapper = new TokenProviderWrapper(provider, this);
/* 1081 */       logger.info("Adding new token provider {} to {}", new Object[] { providerName, this });
/* 1082 */       this.tokenProviders.add(wrapper);
/* 1083 */       this.tokenProviderClasses.add(providerName);
/* 1084 */       Collections.sort(this.tokenProviders);
/*      */     } 
/*      */     
/* 1087 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getToken(String token) {
/* 1099 */     token = token.toUpperCase();
/*      */     
/* 1101 */     for (TokenProviderWrapper provider : this.tokenProviders) {
/* 1102 */       Integer value = provider.getToken(token);
/* 1103 */       if (value != null) {
/* 1104 */         return value;
/*      */       }
/*      */     } 
/*      */     
/* 1108 */     return this.internalTokens.get(token);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Set<String> getErrorHandlerClasses() {
/* 1119 */     return Mixins.getErrorHandlerClasses();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getActiveTransformer() {
/* 1128 */     return GlobalProperties.get("mixin.transformer");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setActiveTransformer(ITransformer transformer) {
/* 1137 */     if (transformer != null) {
/* 1138 */       GlobalProperties.put("mixin.transformer", transformer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment setSide(Side side) {
/* 1149 */     if (side != null && getSide() == Side.UNKNOWN && side != Side.UNKNOWN) {
/* 1150 */       this.side = side;
/*      */     }
/* 1152 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Side getSide() {
/* 1161 */     if (this.side == null) {
/* 1162 */       for (Side side : Side.values()) {
/* 1163 */         if (side.detect()) {
/* 1164 */           this.side = side;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1170 */     return (this.side != null) ? this.side : Side.UNKNOWN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1179 */     return (String)GlobalProperties.get("mixin.initialised");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOption(Option option) {
/* 1189 */     return this.options[option.ordinal()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOption(Option option, boolean value) {
/* 1199 */     this.options[option.ordinal()] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOptionValue(Option option) {
/* 1209 */     return option.getStringValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E getOption(Option option, E defaultValue) {
/* 1221 */     return option.getEnumValue(defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObfuscationContext(String context) {
/* 1230 */     this.obfuscationContext = context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObfuscationContext() {
/* 1237 */     return this.obfuscationContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRefmapObfuscationContext() {
/* 1244 */     String overrideObfuscationType = Option.OBFUSCATION_TYPE.getStringValue();
/* 1245 */     if (overrideObfuscationType != null) {
/* 1246 */       return overrideObfuscationType;
/*      */     }
/* 1248 */     return this.obfuscationContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RemapperChain getRemappers() {
/* 1255 */     return this.remappers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void audit() {
/* 1262 */     Object activeTransformer = getActiveTransformer();
/* 1263 */     if (activeTransformer instanceof MixinTransformer) {
/* 1264 */       MixinTransformer transformer = (MixinTransformer)activeTransformer;
/* 1265 */       transformer.audit(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ILegacyClassTransformer> getTransformers() {
/* 1276 */     if (this.transformers == null) {
/* 1277 */       buildTransformerDelegationList();
/*      */     }
/*      */     
/* 1280 */     return Collections.unmodifiableList(this.transformers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTransformerExclusion(String name) {
/* 1289 */     excludeTransformers.add(name);
/*      */ 
/*      */     
/* 1292 */     this.transformers = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildTransformerDelegationList() {
/* 1302 */     logger.debug("Rebuilding transformer delegation list:");
/* 1303 */     this.transformers = new ArrayList<ILegacyClassTransformer>();
/* 1304 */     for (ITransformer transformer : this.service.getTransformers()) {
/* 1305 */       if (!(transformer instanceof ILegacyClassTransformer)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1309 */       ILegacyClassTransformer legacyTransformer = (ILegacyClassTransformer)transformer;
/* 1310 */       String transformerName = legacyTransformer.getName();
/* 1311 */       boolean include = true;
/* 1312 */       for (String excludeClass : excludeTransformers) {
/* 1313 */         if (transformerName.contains(excludeClass)) {
/* 1314 */           include = false;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1318 */       if (include && !legacyTransformer.isDelegationExcluded()) {
/* 1319 */         logger.debug("  Adding:    {}", new Object[] { transformerName });
/* 1320 */         this.transformers.add(legacyTransformer); continue;
/*      */       } 
/* 1322 */       logger.debug("  Excluding: {}", new Object[] { transformerName });
/*      */     } 
/*      */ 
/*      */     
/* 1326 */     logger.debug("Transformer delegation list created with {} entries", new Object[] { Integer.valueOf(this.transformers.size()) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1334 */     return String.format("%s[%s]", new Object[] { getClass().getSimpleName(), this.phase });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Phase getCurrentPhase() {
/* 1341 */     if (currentPhase == Phase.NOT_INITIALISED) {
/* 1342 */       init(Phase.PREINIT);
/*      */     }
/*      */     
/* 1345 */     return currentPhase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init(Phase phase) {
/* 1354 */     if (currentPhase == Phase.NOT_INITIALISED) {
/* 1355 */       currentPhase = phase;
/* 1356 */       MixinEnvironment env = getEnvironment(phase);
/* 1357 */       getProfiler().setActive(env.getOption(Option.DEBUG_PROFILER));
/*      */       
/* 1359 */       MixinLogWatcher.begin();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getEnvironment(Phase phase) {
/* 1370 */     if (phase == null) {
/* 1371 */       return Phase.DEFAULT.getEnvironment();
/*      */     }
/* 1373 */     return phase.getEnvironment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getDefaultEnvironment() {
/* 1382 */     return getEnvironment(Phase.DEFAULT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getCurrentEnvironment() {
/* 1391 */     if (currentEnvironment == null) {
/* 1392 */       currentEnvironment = getEnvironment(getCurrentPhase());
/*      */     }
/*      */     
/* 1395 */     return currentEnvironment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompatibilityLevel getCompatibilityLevel() {
/* 1402 */     return compatibility;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void setCompatibilityLevel(CompatibilityLevel level) throws IllegalArgumentException {
/* 1414 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/* 1415 */     if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(stackTrace[2].getClassName())) {
/* 1416 */       logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
/*      */     }
/*      */     
/* 1419 */     if (level != compatibility && level.isAtLeast(compatibility)) {
/* 1420 */       if (!level.isSupported()) {
/* 1421 */         throw new IllegalArgumentException("The requested compatibility level " + level + " could not be set. Level is not supported");
/*      */       }
/*      */       
/* 1424 */       compatibility = level;
/* 1425 */       logger.info("Compatibility level set to {}", new Object[] { level });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Profiler getProfiler() {
/* 1435 */     return profiler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void gotoPhase(Phase phase) {
/* 1444 */     if (phase == null || phase.ordinal < 0) {
/* 1445 */       throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
/*      */     }
/*      */     
/* 1448 */     if (phase.ordinal > (getCurrentPhase()).ordinal) {
/* 1449 */       MixinService.getService().beginPhase();
/*      */     }
/*      */     
/* 1452 */     if (phase == Phase.DEFAULT) {
/* 1453 */       MixinLogWatcher.end();
/*      */     }
/*      */     
/* 1456 */     currentPhase = phase;
/* 1457 */     currentEnvironment = getEnvironment(getCurrentPhase());
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\MixinEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */