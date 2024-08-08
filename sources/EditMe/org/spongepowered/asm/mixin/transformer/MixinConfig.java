package org.spongepowered.asm.mixin.transformer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.VersionNumber;

final class MixinConfig implements Comparable, IMixinConfig {
   private static int configOrder = 0;
   private static final Set globalMixinList = new HashSet();
   private final Logger logger = LogManager.getLogger("mixin");
   private final transient Map mixinMapping = new HashMap();
   private final transient Set unhandledTargets = new HashSet();
   private final transient List mixins = new ArrayList();
   private transient Config handle;
   @SerializedName("target")
   private String selector;
   @SerializedName("minVersion")
   private String version;
   @SerializedName("compatibilityLevel")
   private String compatibility;
   @SerializedName("required")
   private boolean required;
   @SerializedName("priority")
   private int priority = 1000;
   @SerializedName("mixinPriority")
   private int mixinPriority = 1000;
   @SerializedName("package")
   private String mixinPackage;
   @SerializedName("mixins")
   private List mixinClasses;
   @SerializedName("client")
   private List mixinClassesClient;
   @SerializedName("server")
   private List mixinClassesServer;
   @SerializedName("setSourceFile")
   private boolean setSourceFile = false;
   @SerializedName("refmap")
   private String refMapperConfig;
   @SerializedName("verbose")
   private boolean verboseLogging;
   private final transient int order;
   private final transient List listeners;
   private transient IMixinService service;
   private transient MixinEnvironment env;
   private transient String name;
   @SerializedName("plugin")
   private String pluginClassName;
   @SerializedName("injectors")
   private MixinConfig.InjectorOptions injectorOptions;
   @SerializedName("overwrites")
   private MixinConfig.OverwriteOptions overwriteOptions;
   private transient IMixinConfigPlugin plugin;
   private transient IReferenceMapper refMapper;
   private transient boolean prepared;
   private transient boolean visited;

   private MixinConfig() {
      this.order = configOrder++;
      this.listeners = new ArrayList();
      this.injectorOptions = new MixinConfig.InjectorOptions();
      this.overwriteOptions = new MixinConfig.OverwriteOptions();
      this.prepared = false;
      this.visited = false;
   }

   private boolean onLoad(IMixinService var1, String var2, MixinEnvironment var3) {
      this.service = var1;
      this.name = var2;
      this.env = this.parseSelector(this.selector, var3);
      this.required &= !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED);
      this.initCompatibilityLevel();
      this.initInjectionPoints();
      return this.checkVersion();
   }

   private void initCompatibilityLevel() {
      if (this.compatibility != null) {
         MixinEnvironment.CompatibilityLevel var1 = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase());
         MixinEnvironment.CompatibilityLevel var2 = MixinEnvironment.getCompatibilityLevel();
         if (var1 != var2) {
            if (var2.isAtLeast(var1) && !var2.canSupport(var1)) {
               throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + var1 + " which is too old");
            } else if (!var2.canElevateTo(var1)) {
               throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + var1 + " which is prohibited by " + var2);
            } else {
               MixinEnvironment.setCompatibilityLevel(var1);
            }
         }
      }
   }

   private MixinEnvironment parseSelector(String var1, MixinEnvironment var2) {
      if (var1 != null) {
         String[] var3 = var1.split("[&\\| ]");
         String[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            var7 = var7.trim();
            Pattern var8 = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
            Matcher var9 = var8.matcher(var7);
            if (var9.matches()) {
               return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(var9.group(1)));
            }
         }

         MixinEnvironment.Phase var10 = MixinEnvironment.Phase.forName(var1);
         if (var10 != null) {
            return MixinEnvironment.getEnvironment(var10);
         }
      }

      return var2;
   }

   private void initInjectionPoints() {
      if (this.injectorOptions.injectionPoints != null) {
         Iterator var1 = this.injectorOptions.injectionPoints.iterator();

         while(var1.hasNext()) {
            String var2 = (String)var1.next();

            try {
               Class var3 = this.service.getClassProvider().findClass(var2, true);
               if (InjectionPoint.class.isAssignableFrom(var3)) {
                  InjectionPoint.register(var3);
               } else {
                  this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[]{var3, this});
               }
            } catch (Throwable var4) {
               this.logger.catching(var4);
            }
         }

      }
   }

   private boolean checkVersion() throws MixinInitialisationError {
      if (this.version == null) {
         this.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[]{this.name});
      }

      VersionNumber var1 = VersionNumber.parse(this.version);
      VersionNumber var2 = VersionNumber.parse(this.env.getVersion());
      if (var1.compareTo(var2) > 0) {
         this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[]{this.name, var1, var2});
         if (this.required) {
            throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + var1);
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   void addListener(MixinConfig.IListener var1) {
      this.listeners.add(var1);
   }

   void onSelect() {
      if (this.pluginClassName != null) {
         try {
            Class var1 = this.service.getClassProvider().findClass(this.pluginClassName, true);
            this.plugin = (IMixinConfigPlugin)var1.newInstance();
            if (this.plugin != null) {
               this.plugin.onLoad(this.mixinPackage);
            }
         } catch (Throwable var2) {
            var2.printStackTrace();
            this.plugin = null;
         }
      }

      if (!this.mixinPackage.endsWith(".")) {
         this.mixinPackage = this.mixinPackage + ".";
      }

      boolean var3 = false;
      if (this.refMapperConfig == null) {
         if (this.plugin != null) {
            this.refMapperConfig = this.plugin.getRefMapperConfig();
         }

         if (this.refMapperConfig == null) {
            var3 = true;
            this.refMapperConfig = "mixin.refmap.json";
         }
      }

      this.refMapper = ReferenceMapper.read(this.refMapperConfig);
      this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
      if (!var3 && this.refMapper.isDefault() && !this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
         this.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[]{this.refMapperConfig, this});
      }

      if (this.env.getOption(MixinEnvironment.Option.REFMAP_REMAP)) {
         this.refMapper = RemappingReferenceMapper.of(this.env, this.refMapper);
      }

   }

   void prepare() {
      if (!this.prepared) {
         this.prepared = true;
         this.prepareMixins(this.mixinClasses, false);
         switch(this.env.getSide()) {
         case CLIENT:
            this.prepareMixins(this.mixinClassesClient, false);
            break;
         case SERVER:
            this.prepareMixins(this.mixinClassesServer, false);
            break;
         case UNKNOWN:
         default:
            this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
         }

      }
   }

   void postInitialise() {
      if (this.plugin != null) {
         List var1 = this.plugin.getMixins();
         this.prepareMixins(var1, true);
      }

      Iterator var7 = this.mixins.iterator();

      while(var7.hasNext()) {
         MixinInfo var2 = (MixinInfo)var7.next();

         try {
            var2.validate();
            Iterator var3 = this.listeners.iterator();

            while(var3.hasNext()) {
               MixinConfig.IListener var4 = (MixinConfig.IListener)var3.next();
               var4.onInit(var2);
            }
         } catch (InvalidMixinException var5) {
            this.logger.error(var5.getMixin() + ": " + var5.getMessage(), var5);
            this.removeMixin(var2);
            var7.remove();
         } catch (Exception var6) {
            this.logger.error(var6.getMessage(), var6);
            this.removeMixin(var2);
            var7.remove();
         }
      }

   }

   private void removeMixin(MixinInfo var1) {
      Iterator var2 = this.mixinMapping.values().iterator();

      while(var2.hasNext()) {
         List var3 = (List)var2.next();
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            if (var1 == var4.next()) {
               var4.remove();
            }
         }
      }

   }

   private void prepareMixins(List var1, boolean var2) {
      if (var1 != null) {
         Iterator var3 = var1.iterator();

         while(true) {
            String var4;
            String var5;
            do {
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  var4 = (String)var3.next();
                  var5 = this.mixinPackage + var4;
               } while(var4 == null);
            } while(globalMixinList.contains(var5));

            MixinInfo var6 = null;

            try {
               var6 = new MixinInfo(this.service, this, var4, true, this.plugin, var2);
               if (var6.getTargetClasses().size() > 0) {
                  globalMixinList.add(var5);
                  Iterator var7 = var6.getTargetClasses().iterator();

                  while(var7.hasNext()) {
                     String var8 = (String)var7.next();
                     String var9 = var8.replace('/', '.');
                     this.mixinsFor(var9).add(var6);
                     this.unhandledTargets.add(var9);
                  }

                  var7 = this.listeners.iterator();

                  while(var7.hasNext()) {
                     MixinConfig.IListener var12 = (MixinConfig.IListener)var7.next();
                     var12.onPrepare(var6);
                  }

                  this.mixins.add(var6);
               }
            } catch (InvalidMixinException var10) {
               if (this.required) {
                  throw var10;
               }

               this.logger.error(var10.getMessage(), var10);
            } catch (Exception var11) {
               if (this.required) {
                  throw new InvalidMixinException(var6, "Error initialising mixin " + var6 + " - " + var11.getClass() + ": " + var11.getMessage(), var11);
               }

               this.logger.error(var11.getMessage(), var11);
            }
         }
      }
   }

   void postApply(String var1, ClassNode var2) {
      this.unhandledTargets.remove(var1);
   }

   public Config getHandle() {
      if (this.handle == null) {
         this.handle = new Config(this);
      }

      return this.handle;
   }

   public boolean isRequired() {
      return this.required;
   }

   public MixinEnvironment getEnvironment() {
      return this.env;
   }

   public String getName() {
      return this.name;
   }

   public String getMixinPackage() {
      return this.mixinPackage;
   }

   public int getPriority() {
      return this.priority;
   }

   public int getDefaultMixinPriority() {
      return this.mixinPriority;
   }

   public int getDefaultRequiredInjections() {
      return this.injectorOptions.defaultRequireValue;
   }

   public String getDefaultInjectorGroup() {
      String var1 = this.injectorOptions.defaultGroup;
      return var1 != null && !var1.isEmpty() ? var1 : "default";
   }

   public boolean conformOverwriteVisibility() {
      return this.overwriteOptions.conformAccessModifiers;
   }

   public boolean requireOverwriteAnnotations() {
      return this.overwriteOptions.requireOverwriteAnnotations;
   }

   public int getMaxShiftByValue() {
      return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 0);
   }

   public boolean select(MixinEnvironment var1) {
      this.visited = true;
      return this.env == var1;
   }

   boolean isVisited() {
      return this.visited;
   }

   int getDeclaredMixinCount() {
      return getCollectionSize(this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer);
   }

   int getMixinCount() {
      return this.mixins.size();
   }

   public List getClasses() {
      return Collections.unmodifiableList(this.mixinClasses);
   }

   public boolean shouldSetSourceFile() {
      return this.setSourceFile;
   }

   public IReferenceMapper getReferenceMapper() {
      if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
         return ReferenceMapper.DEFAULT_MAPPER;
      } else {
         this.refMapper.setContext(this.env.getRefmapObfuscationContext());
         return this.refMapper;
      }
   }

   String remapClassName(String var1, String var2) {
      return this.getReferenceMapper().remap(var1, var2);
   }

   public IMixinConfigPlugin getPlugin() {
      return this.plugin;
   }

   public Set getTargets() {
      return Collections.unmodifiableSet(this.mixinMapping.keySet());
   }

   public Set getUnhandledTargets() {
      return Collections.unmodifiableSet(this.unhandledTargets);
   }

   public Level getLoggingLevel() {
      return this.verboseLogging ? Level.INFO : Level.DEBUG;
   }

   public boolean packageMatch(String var1) {
      return var1.startsWith(this.mixinPackage);
   }

   public boolean hasMixinsFor(String var1) {
      return this.mixinMapping.containsKey(var1);
   }

   public List getMixinsFor(String var1) {
      return this.mixinsFor(var1);
   }

   private List mixinsFor(String var1) {
      Object var2 = (List)this.mixinMapping.get(var1);
      if (var2 == null) {
         var2 = new ArrayList();
         this.mixinMapping.put(var1, var2);
      }

      return (List)var2;
   }

   public List reloadMixin(String var1, byte[] var2) {
      Iterator var3 = this.mixins.iterator();

      MixinInfo var4;
      do {
         if (!var3.hasNext()) {
            return Collections.emptyList();
         }

         var4 = (MixinInfo)var3.next();
      } while(!var4.getClassName().equals(var1));

      var4.reloadMixin(var2);
      return var4.getTargetClasses();
   }

   public String toString() {
      return this.name;
   }

   public int compareTo(MixinConfig var1) {
      if (var1 == null) {
         return 0;
      } else {
         return var1.priority == this.priority ? this.order - var1.order : this.priority - var1.priority;
      }
   }

   static Config create(String var0, MixinEnvironment var1) {
      try {
         IMixinService var2 = MixinService.getService();
         MixinConfig var3 = (MixinConfig)(new Gson()).fromJson(new InputStreamReader(var2.getResourceAsStream(var0)), MixinConfig.class);
         return var3.onLoad(var2, var0, var1) ? var3.getHandle() : null;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", var0), var4);
      }
   }

   private static int getCollectionSize(Collection... var0) {
      int var1 = 0;
      Collection[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Collection var5 = var2[var4];
         if (var5 != null) {
            var1 += var5.size();
         }
      }

      return var1;
   }

   public int compareTo(Object var1) {
      return this.compareTo((MixinConfig)var1);
   }

   interface IListener {
      void onPrepare(MixinInfo var1);

      void onInit(MixinInfo var1);
   }

   static class OverwriteOptions {
      @SerializedName("conformVisibility")
      boolean conformAccessModifiers;
      @SerializedName("requireAnnotations")
      boolean requireOverwriteAnnotations;
   }

   static class InjectorOptions {
      @SerializedName("defaultRequire")
      int defaultRequireValue = 0;
      @SerializedName("defaultGroup")
      String defaultGroup = "default";
      @SerializedName("injectionPoints")
      List injectionPoints;
      @SerializedName("maxShiftBy")
      int maxShiftBy = 0;
   }
}
