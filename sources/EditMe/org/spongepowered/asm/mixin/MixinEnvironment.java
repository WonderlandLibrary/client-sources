package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.JavaVersion;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.perf.Profiler;

public final class MixinEnvironment implements ITokenProvider {
   private static final Set excludeTransformers = Sets.newHashSet(new String[]{"net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer"});
   private static MixinEnvironment currentEnvironment;
   private static MixinEnvironment.Phase currentPhase;
   private static MixinEnvironment.CompatibilityLevel compatibility;
   private static boolean showHeader;
   private static final Logger logger;
   private static final Profiler profiler;
   private final IMixinService service = MixinService.getService();
   private final MixinEnvironment.Phase phase;
   private final String configsKey;
   private final boolean[] options;
   private final Set tokenProviderClasses = new HashSet();
   private final List tokenProviders = new ArrayList();
   private final Map internalTokens = new HashMap();
   private final RemapperChain remappers = new RemapperChain();
   private MixinEnvironment.Side side;
   private List transformers;
   private String obfuscationContext = null;

   MixinEnvironment(MixinEnvironment.Phase var1) {
      this.phase = var1;
      this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
      String var2 = this.getVersion();
      if (var2 != null && "0.7.4".equals(var2)) {
         this.service.checkEnv(this);
         this.options = new boolean[MixinEnvironment.Option.values().length];
         MixinEnvironment.Option[] var3 = MixinEnvironment.Option.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MixinEnvironment.Option var6 = var3[var5];
            this.options[var6.ordinal()] = var6.getBooleanValue();
         }

         if (showHeader) {
            showHeader = false;
            this.printHeader(var2);
         }

      } else {
         throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
      }
   }

   private void printHeader(Object var1) {
      String var2 = this.getCodeSource();
      String var3 = this.service.getName();
      MixinEnvironment.Side var4 = this.getSide();
      logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[]{var1, var2, var3, var4});
      boolean var5 = this.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
      if (var5 || this.getOption(MixinEnvironment.Option.DEBUG_EXPORT) || this.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
         PrettyPrinter var6 = new PrettyPrinter(32);
         var6.add("SpongePowered MIXIN%s", var5 ? " (Verbose debugging enabled)" : "").centre().hr();
         var6.kv("Code source", var2);
         var6.kv("Internal Version", var1);
         var6.kv("Java 8 Supported", MixinEnvironment.CompatibilityLevel.JAVA_8.isSupported()).hr();
         var6.kv("Service Name", var3);
         var6.kv("Service Class", this.service.getClass().getName()).hr();
         MixinEnvironment.Option[] var7 = MixinEnvironment.Option.values();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            MixinEnvironment.Option var10 = var7[var9];
            StringBuilder var11 = new StringBuilder();

            for(int var12 = 0; var12 < var10.depth; ++var12) {
               var11.append("- ");
            }

            var6.kv(var10.property, "%s<%s>", var11, var10);
         }

         var6.hr().kv("Detected Side", var4);
         var6.print(System.err);
      }

   }

   private String getCodeSource() {
      try {
         return this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
      } catch (Throwable var2) {
         return "Unknown";
      }
   }

   public MixinEnvironment.Phase getPhase() {
      return this.phase;
   }

   /** @deprecated */
   @Deprecated
   public List getMixinConfigs() {
      Object var1 = (List)GlobalProperties.get(this.configsKey);
      if (var1 == null) {
         var1 = new ArrayList();
         GlobalProperties.put(this.configsKey, var1);
      }

      return (List)var1;
   }

   /** @deprecated */
   @Deprecated
   public MixinEnvironment addConfiguration(String var1) {
      logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
      Mixins.addConfiguration(var1, this);
      return this;
   }

   void registerConfig(String var1) {
      List var2 = this.getMixinConfigs();
      if (!var2.contains(var1)) {
         var2.add(var1);
      }

   }

   /** @deprecated */
   @Deprecated
   public MixinEnvironment registerErrorHandlerClass(String var1) {
      Mixins.registerErrorHandlerClass(var1);
      return this;
   }

   public MixinEnvironment registerTokenProviderClass(String var1) {
      if (!this.tokenProviderClasses.contains(var1)) {
         try {
            Class var2 = this.service.getClassProvider().findClass(var1, true);
            IEnvironmentTokenProvider var3 = (IEnvironmentTokenProvider)var2.newInstance();
            this.registerTokenProvider(var3);
         } catch (Throwable var4) {
            logger.error("Error instantiating " + var1, var4);
         }
      }

      return this;
   }

   public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider var1) {
      if (var1 != null && !this.tokenProviderClasses.contains(var1.getClass().getName())) {
         String var2 = var1.getClass().getName();
         MixinEnvironment.TokenProviderWrapper var3 = new MixinEnvironment.TokenProviderWrapper(var1, this);
         logger.info("Adding new token provider {} to {}", new Object[]{var2, this});
         this.tokenProviders.add(var3);
         this.tokenProviderClasses.add(var2);
         Collections.sort(this.tokenProviders);
      }

      return this;
   }

   public Integer getToken(String var1) {
      var1 = var1.toUpperCase();
      Iterator var2 = this.tokenProviders.iterator();

      Integer var4;
      do {
         if (!var2.hasNext()) {
            return (Integer)this.internalTokens.get(var1);
         }

         MixinEnvironment.TokenProviderWrapper var3 = (MixinEnvironment.TokenProviderWrapper)var2.next();
         var4 = var3.getToken(var1);
      } while(var4 == null);

      return var4;
   }

   /** @deprecated */
   @Deprecated
   public Set getErrorHandlerClasses() {
      return Mixins.getErrorHandlerClasses();
   }

   public Object getActiveTransformer() {
      return GlobalProperties.get("mixin.transformer");
   }

   public void setActiveTransformer(ITransformer var1) {
      if (var1 != null) {
         GlobalProperties.put("mixin.transformer", var1);
      }

   }

   public MixinEnvironment setSide(MixinEnvironment.Side var1) {
      if (var1 != null && this.getSide() == MixinEnvironment.Side.UNKNOWN && var1 != MixinEnvironment.Side.UNKNOWN) {
         this.side = var1;
      }

      return this;
   }

   public MixinEnvironment.Side getSide() {
      if (this.side == null) {
         MixinEnvironment.Side[] var1 = MixinEnvironment.Side.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            MixinEnvironment.Side var4 = var1[var3];
            if (var4.detect()) {
               this.side = var4;
               break;
            }
         }
      }

      return this.side != null ? this.side : MixinEnvironment.Side.UNKNOWN;
   }

   public String getVersion() {
      return (String)GlobalProperties.get("mixin.initialised");
   }

   public boolean getOption(MixinEnvironment.Option var1) {
      return this.options[var1.ordinal()];
   }

   public void setOption(MixinEnvironment.Option var1, boolean var2) {
      this.options[var1.ordinal()] = var2;
   }

   public String getOptionValue(MixinEnvironment.Option var1) {
      return var1.getStringValue();
   }

   public Enum getOption(MixinEnvironment.Option var1, Enum var2) {
      return var1.getEnumValue(var2);
   }

   public void setObfuscationContext(String var1) {
      this.obfuscationContext = var1;
   }

   public String getObfuscationContext() {
      return this.obfuscationContext;
   }

   public String getRefmapObfuscationContext() {
      String var1 = MixinEnvironment.Option.OBFUSCATION_TYPE.getStringValue();
      return var1 != null ? var1 : this.obfuscationContext;
   }

   public RemapperChain getRemappers() {
      return this.remappers;
   }

   public void audit() {
      Object var1 = this.getActiveTransformer();
      if (var1 instanceof MixinTransformer) {
         MixinTransformer var2 = (MixinTransformer)var1;
         var2.audit(this);
      }

   }

   public List getTransformers() {
      if (this.transformers == null) {
         this.buildTransformerDelegationList();
      }

      return Collections.unmodifiableList(this.transformers);
   }

   public void addTransformerExclusion(String var1) {
      excludeTransformers.add(var1);
      this.transformers = null;
   }

   private void buildTransformerDelegationList() {
      logger.debug("Rebuilding transformer delegation list:");
      this.transformers = new ArrayList();
      Iterator var1 = this.service.getTransformers().iterator();

      while(true) {
         while(true) {
            ITransformer var2;
            do {
               if (!var1.hasNext()) {
                  logger.debug("Transformer delegation list created with {} entries", new Object[]{this.transformers.size()});
                  return;
               }

               var2 = (ITransformer)var1.next();
            } while(!(var2 instanceof ILegacyClassTransformer));

            ILegacyClassTransformer var3 = (ILegacyClassTransformer)var2;
            String var4 = var3.getName();
            boolean var5 = true;
            Iterator var6 = excludeTransformers.iterator();

            while(var6.hasNext()) {
               String var7 = (String)var6.next();
               if (var4.contains(var7)) {
                  var5 = false;
                  break;
               }
            }

            if (var5 && !var3.isDelegationExcluded()) {
               logger.debug("  Adding:    {}", new Object[]{var4});
               this.transformers.add(var3);
            } else {
               logger.debug("  Excluding: {}", new Object[]{var4});
            }
         }
      }
   }

   public String toString() {
      return String.format("%s[%s]", this.getClass().getSimpleName(), this.phase);
   }

   private static MixinEnvironment.Phase getCurrentPhase() {
      if (currentPhase == MixinEnvironment.Phase.NOT_INITIALISED) {
         init(MixinEnvironment.Phase.PREINIT);
      }

      return currentPhase;
   }

   public static void init(MixinEnvironment.Phase var0) {
      if (currentPhase == MixinEnvironment.Phase.NOT_INITIALISED) {
         currentPhase = var0;
         MixinEnvironment var1 = getEnvironment(var0);
         getProfiler().setActive(var1.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
         new MixinEnvironment.MixinLogger();
      }

   }

   public static MixinEnvironment getEnvironment(MixinEnvironment.Phase var0) {
      return var0 == null ? MixinEnvironment.Phase.DEFAULT.getEnvironment() : var0.getEnvironment();
   }

   public static MixinEnvironment getDefaultEnvironment() {
      return getEnvironment(MixinEnvironment.Phase.DEFAULT);
   }

   public static MixinEnvironment getCurrentEnvironment() {
      if (currentEnvironment == null) {
         currentEnvironment = getEnvironment(getCurrentPhase());
      }

      return currentEnvironment;
   }

   public static MixinEnvironment.CompatibilityLevel getCompatibilityLevel() {
      return compatibility;
   }

   /** @deprecated */
   @Deprecated
   public static void setCompatibilityLevel(MixinEnvironment.CompatibilityLevel var0) throws IllegalArgumentException {
      StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
      if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(var1[2].getClassName())) {
         logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
      }

      if (var0 != compatibility && var0.isAtLeast(compatibility)) {
         if (!var0.isSupported()) {
            throw new IllegalArgumentException("The requested compatibility level " + var0 + " could not be set. Level is not supported");
         }

         compatibility = var0;
         logger.info("Compatibility level set to {}", new Object[]{var0});
      }

   }

   public static Profiler getProfiler() {
      return profiler;
   }

   static void gotoPhase(MixinEnvironment.Phase var0) {
      if (var0 != null && var0.ordinal >= 0) {
         if (var0.ordinal > getCurrentPhase().ordinal) {
            MixinService.getService().beginPhase();
         }

         if (var0 == MixinEnvironment.Phase.DEFAULT) {
            org.apache.logging.log4j.core.Logger var1 = (org.apache.logging.log4j.core.Logger)LogManager.getLogger("FML");
            var1.removeAppender(MixinEnvironment.MixinLogger.appender);
         }

         currentPhase = var0;
         currentEnvironment = getEnvironment(getCurrentPhase());
      } else {
         throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
      }
   }

   static {
      currentPhase = MixinEnvironment.Phase.NOT_INITIALISED;
      compatibility = (MixinEnvironment.CompatibilityLevel)MixinEnvironment.Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(MixinEnvironment.CompatibilityLevel.JAVA_6);
      showHeader = true;
      logger = LogManager.getLogger("mixin");
      profiler = new Profiler();
   }

   static class MixinLogger {
      static MixinEnvironment.MixinLogger.MixinAppender appender = new MixinEnvironment.MixinLogger.MixinAppender("MixinLogger", (Filter)null, (Layout)null);

      public MixinLogger() {
         org.apache.logging.log4j.core.Logger var1 = (org.apache.logging.log4j.core.Logger)LogManager.getLogger("FML");
         appender.start();
         var1.addAppender(appender);
      }

      static class MixinAppender extends AbstractAppender {
         protected MixinAppender(String var1, Filter var2, Layout var3) {
            super(var1, var2, var3);
         }

         public void append(LogEvent var1) {
            if (var1.getLevel() == Level.DEBUG && "Validating minecraft".equals(var1.getMessage().getFormat())) {
               MixinEnvironment.gotoPhase(MixinEnvironment.Phase.INIT);
            }

         }
      }
   }

   static class TokenProviderWrapper implements Comparable {
      private static int nextOrder = 0;
      private final int priority;
      private final int order;
      private final IEnvironmentTokenProvider provider;
      private final MixinEnvironment environment;

      public TokenProviderWrapper(IEnvironmentTokenProvider var1, MixinEnvironment var2) {
         this.provider = var1;
         this.environment = var2;
         this.order = nextOrder++;
         this.priority = var1.getPriority();
      }

      public int compareTo(MixinEnvironment.TokenProviderWrapper var1) {
         if (var1 == null) {
            return 0;
         } else {
            return var1.priority == this.priority ? var1.order - this.order : var1.priority - this.priority;
         }
      }

      public IEnvironmentTokenProvider getProvider() {
         return this.provider;
      }

      Integer getToken(String var1) {
         return this.provider.getToken(var1, this.environment);
      }

      public int compareTo(Object var1) {
         return this.compareTo((MixinEnvironment.TokenProviderWrapper)var1);
      }
   }

   public static enum CompatibilityLevel {
      JAVA_6(6, 50, false),
      JAVA_7(7, 51, false) {
         boolean isSupported() {
            return JavaVersion.current() >= 1.7D;
         }
      },
      JAVA_8(8, 52, true) {
         boolean isSupported() {
            return JavaVersion.current() >= 1.8D;
         }
      },
      JAVA_9(9, 53, true) {
         boolean isSupported() {
            return false;
         }
      };

      private static final int CLASS_V1_9 = 53;
      private final int ver;
      private final int classVersion;
      private final boolean supportsMethodsInInterfaces;
      private MixinEnvironment.CompatibilityLevel maxCompatibleLevel;
      private static final MixinEnvironment.CompatibilityLevel[] $VALUES = new MixinEnvironment.CompatibilityLevel[]{JAVA_6, JAVA_7, JAVA_8, JAVA_9};

      private CompatibilityLevel(int var3, int var4, boolean var5) {
         this.ver = var3;
         this.classVersion = var4;
         this.supportsMethodsInInterfaces = var5;
      }

      private void setMaxCompatibleLevel(MixinEnvironment.CompatibilityLevel var1) {
         this.maxCompatibleLevel = var1;
      }

      boolean isSupported() {
         return true;
      }

      public int classVersion() {
         return this.classVersion;
      }

      public boolean supportsMethodsInInterfaces() {
         return this.supportsMethodsInInterfaces;
      }

      public boolean isAtLeast(MixinEnvironment.CompatibilityLevel var1) {
         return var1 == null || this.ver >= var1.ver;
      }

      public boolean canElevateTo(MixinEnvironment.CompatibilityLevel var1) {
         if (var1 != null && this.maxCompatibleLevel != null) {
            return var1.ver <= this.maxCompatibleLevel.ver;
         } else {
            return true;
         }
      }

      public boolean canSupport(MixinEnvironment.CompatibilityLevel var1) {
         return var1 == null ? true : var1.canElevateTo(this);
      }

      CompatibilityLevel(int var3, int var4, boolean var5, Object var6) {
         this(var3, var4, var5);
      }
   }

   public static enum Option {
      DEBUG_ALL("debug"),
      DEBUG_EXPORT(DEBUG_ALL, "export"),
      DEBUG_EXPORT_FILTER(DEBUG_EXPORT, "filter", false),
      DEBUG_EXPORT_DECOMPILE(DEBUG_EXPORT, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "decompile"),
      DEBUG_EXPORT_DECOMPILE_THREADED(DEBUG_EXPORT_DECOMPILE, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "async"),
      DEBUG_VERIFY(DEBUG_ALL, "verify"),
      DEBUG_VERBOSE(DEBUG_ALL, "verbose"),
      DEBUG_INJECTORS(DEBUG_ALL, "countInjections"),
      DEBUG_STRICT(DEBUG_ALL, MixinEnvironment.Option.Inherit.INDEPENDENT, "strict"),
      DEBUG_UNIQUE(DEBUG_STRICT, "unique"),
      DEBUG_TARGETS(DEBUG_STRICT, "targets"),
      DEBUG_PROFILER(DEBUG_ALL, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "profiler"),
      DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
      CHECK_ALL("checks"),
      CHECK_IMPLEMENTS(CHECK_ALL, "interfaces"),
      CHECK_IMPLEMENTS_STRICT(CHECK_IMPLEMENTS, MixinEnvironment.Option.Inherit.ALLOW_OVERRIDE, "strict"),
      IGNORE_CONSTRAINTS("ignoreConstraints"),
      HOT_SWAP("hotSwap"),
      ENVIRONMENT(MixinEnvironment.Option.Inherit.ALWAYS_FALSE, "env"),
      OBFUSCATION_TYPE(ENVIRONMENT, MixinEnvironment.Option.Inherit.ALWAYS_FALSE, "obf"),
      DISABLE_REFMAP(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "disableRefMap"),
      REFMAP_REMAP(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "remapRefMap"),
      REFMAP_REMAP_RESOURCE(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "refMapRemappingFile", ""),
      REFMAP_REMAP_SOURCE_ENV(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "refMapRemappingEnv", "searge"),
      IGNORE_REQUIRED(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "ignoreRequired"),
      DEFAULT_COMPATIBILITY_LEVEL(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "compatLevel"),
      SHIFT_BY_VIOLATION_BEHAVIOUR(ENVIRONMENT, MixinEnvironment.Option.Inherit.INDEPENDENT, "shiftByViolation", "warn"),
      INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");

      private static final String PREFIX = "mixin";
      final MixinEnvironment.Option parent;
      final MixinEnvironment.Option.Inherit inheritance;
      final String property;
      final String defaultValue;
      final boolean isFlag;
      final int depth;
      private static final MixinEnvironment.Option[] $VALUES = new MixinEnvironment.Option[]{DEBUG_ALL, DEBUG_EXPORT, DEBUG_EXPORT_FILTER, DEBUG_EXPORT_DECOMPILE, DEBUG_EXPORT_DECOMPILE_THREADED, DEBUG_VERIFY, DEBUG_VERBOSE, DEBUG_INJECTORS, DEBUG_STRICT, DEBUG_UNIQUE, DEBUG_TARGETS, DEBUG_PROFILER, DUMP_TARGET_ON_FAILURE, CHECK_ALL, CHECK_IMPLEMENTS, CHECK_IMPLEMENTS_STRICT, IGNORE_CONSTRAINTS, HOT_SWAP, ENVIRONMENT, OBFUSCATION_TYPE, DISABLE_REFMAP, REFMAP_REMAP, REFMAP_REMAP_RESOURCE, REFMAP_REMAP_SOURCE_ENV, IGNORE_REQUIRED, DEFAULT_COMPATIBILITY_LEVEL, SHIFT_BY_VIOLATION_BEHAVIOUR, INITIALISER_INJECTION_MODE};

      private Option(String var3) {
         this((MixinEnvironment.Option)null, var3, true);
      }

      private Option(MixinEnvironment.Option.Inherit var3, String var4) {
         this((MixinEnvironment.Option)null, var3, var4, true);
      }

      private Option(String var3, boolean var4) {
         this((MixinEnvironment.Option)null, var3, var4);
      }

      private Option(String var3, String var4) {
         this((MixinEnvironment.Option)null, MixinEnvironment.Option.Inherit.INDEPENDENT, var3, false, var4);
      }

      private Option(MixinEnvironment.Option var3, String var4) {
         this(var3, MixinEnvironment.Option.Inherit.INHERIT, var4, true);
      }

      private Option(MixinEnvironment.Option var3, MixinEnvironment.Option.Inherit var4, String var5) {
         this(var3, var4, var5, true);
      }

      private Option(MixinEnvironment.Option var3, String var4, boolean var5) {
         this(var3, MixinEnvironment.Option.Inherit.INHERIT, var4, var5, (String)null);
      }

      private Option(MixinEnvironment.Option var3, MixinEnvironment.Option.Inherit var4, String var5, boolean var6) {
         this(var3, var4, var5, var6, (String)null);
      }

      private Option(MixinEnvironment.Option var3, String var4, String var5) {
         this(var3, MixinEnvironment.Option.Inherit.INHERIT, var4, false, var5);
      }

      private Option(MixinEnvironment.Option var3, MixinEnvironment.Option.Inherit var4, String var5, String var6) {
         this(var3, var4, var5, false, var6);
      }

      private Option(MixinEnvironment.Option var3, MixinEnvironment.Option.Inherit var4, String var5, boolean var6, String var7) {
         this.parent = var3;
         this.inheritance = var4;
         this.property = (var3 != null ? var3.property : "mixin") + "." + var5;
         this.defaultValue = var7;
         this.isFlag = var6;

         int var8;
         for(var8 = 0; var3 != null; ++var8) {
            var3 = var3.parent;
         }

         this.depth = var8;
      }

      MixinEnvironment.Option getParent() {
         return this.parent;
      }

      String getProperty() {
         return this.property;
      }

      public String toString() {
         return this.isFlag ? String.valueOf(this.getBooleanValue()) : this.getStringValue();
      }

      private boolean getLocalBooleanValue(boolean var1) {
         return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(var1)));
      }

      private boolean getInheritedBooleanValue() {
         return this.parent != null && this.parent.getBooleanValue();
      }

      final boolean getBooleanValue() {
         if (this.inheritance == MixinEnvironment.Option.Inherit.ALWAYS_FALSE) {
            return false;
         } else {
            boolean var1 = this.getLocalBooleanValue(false);
            if (this.inheritance == MixinEnvironment.Option.Inherit.INDEPENDENT) {
               return var1;
            } else {
               boolean var2 = var1 || this.getInheritedBooleanValue();
               return this.inheritance == MixinEnvironment.Option.Inherit.INHERIT ? var2 : this.getLocalBooleanValue(var2);
            }
         }
      }

      final String getStringValue() {
         return this.parent != null && !this.parent.getBooleanValue() ? this.defaultValue : System.getProperty(this.property, this.defaultValue);
      }

      Enum getEnumValue(Enum var1) {
         String var2 = System.getProperty(this.property, var1.name());

         try {
            return Enum.valueOf(var1.getClass(), var2.toUpperCase());
         } catch (IllegalArgumentException var4) {
            return var1;
         }
      }

      private static enum Inherit {
         INHERIT,
         ALLOW_OVERRIDE,
         INDEPENDENT,
         ALWAYS_FALSE;

         private static final MixinEnvironment.Option.Inherit[] $VALUES = new MixinEnvironment.Option.Inherit[]{INHERIT, ALLOW_OVERRIDE, INDEPENDENT, ALWAYS_FALSE};
      }
   }

   public static enum Side {
      UNKNOWN {
         protected boolean detect() {
            return false;
         }
      },
      CLIENT {
         protected boolean detect() {
            String var1 = MixinService.getService().getSideName();
            return "CLIENT".equals(var1);
         }
      },
      SERVER {
         protected boolean detect() {
            String var1 = MixinService.getService().getSideName();
            return "SERVER".equals(var1) || "DEDICATEDSERVER".equals(var1);
         }
      };

      private static final MixinEnvironment.Side[] $VALUES = new MixinEnvironment.Side[]{UNKNOWN, CLIENT, SERVER};

      private Side() {
      }

      protected abstract boolean detect();

      Side(Object var3) {
         this();
      }
   }

   public static final class Phase {
      static final MixinEnvironment.Phase NOT_INITIALISED = new MixinEnvironment.Phase(-1, "NOT_INITIALISED");
      public static final MixinEnvironment.Phase PREINIT = new MixinEnvironment.Phase(0, "PREINIT");
      public static final MixinEnvironment.Phase INIT = new MixinEnvironment.Phase(1, "INIT");
      public static final MixinEnvironment.Phase DEFAULT = new MixinEnvironment.Phase(2, "DEFAULT");
      static final List phases;
      final int ordinal;
      final String name;
      private MixinEnvironment environment;

      private Phase(int var1, String var2) {
         this.ordinal = var1;
         this.name = var2;
      }

      public String toString() {
         return this.name;
      }

      public static MixinEnvironment.Phase forName(String var0) {
         Iterator var1 = phases.iterator();

         MixinEnvironment.Phase var2;
         do {
            if (!var1.hasNext()) {
               return null;
            }

            var2 = (MixinEnvironment.Phase)var1.next();
         } while(!var2.name.equals(var0));

         return var2;
      }

      MixinEnvironment getEnvironment() {
         if (this.ordinal < 0) {
            throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
         } else {
            if (this.environment == null) {
               this.environment = new MixinEnvironment(this);
            }

            return this.environment;
         }
      }

      static {
         phases = ImmutableList.of(PREINIT, INIT, DEFAULT);
      }
   }
}
