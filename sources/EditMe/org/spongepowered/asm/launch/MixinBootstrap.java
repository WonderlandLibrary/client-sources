package org.spongepowered.asm.launch;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.MixinService;

public abstract class MixinBootstrap {
   public static final String VERSION = "0.7.4";
   private static final Logger logger = LogManager.getLogger("mixin");
   private static boolean initialised = false;
   private static boolean initState = true;
   private static MixinPlatformManager platform;

   private MixinBootstrap() {
   }

   /** @deprecated */
   @Deprecated
   public static void addProxy() {
      MixinService.getService().beginPhase();
   }

   public static MixinPlatformManager getPlatform() {
      if (platform == null) {
         Object var0 = GlobalProperties.get("mixin.platform");
         if (var0 instanceof MixinPlatformManager) {
            platform = (MixinPlatformManager)var0;
         } else {
            platform = new MixinPlatformManager();
            GlobalProperties.put("mixin.platform", platform);
            platform.init();
         }
      }

      return platform;
   }

   public static void init() {
      if (start()) {
         doInit((List)null);
      }
   }

   static boolean start() {
      if (isSubsystemRegistered()) {
         if (!checkSubsystemVersion()) {
            throw new MixinInitialisationError("Mixin subsystem version " + getActiveSubsystemVersion() + " was already initialised. Cannot bootstrap version " + "0.7.4");
         } else {
            return false;
         }
      } else {
         registerSubsystem("0.7.4");
         if (!initialised) {
            initialised = true;
            String var0 = System.getProperty("sun.java.command");
            if (var0 != null && var0.contains("GradleStart")) {
               System.setProperty("mixin.env.remapRefMap", "true");
            }

            MixinEnvironment.Phase var1 = MixinService.getService().getInitialPhase();
            if (var1 == MixinEnvironment.Phase.DEFAULT) {
               logger.error("Initialising mixin subsystem after game pre-init phase! Some mixins may be skipped.");
               MixinEnvironment.init(var1);
               getPlatform().prepare((List)null);
               initState = false;
            } else {
               MixinEnvironment.init(var1);
            }

            MixinService.getService().beginPhase();
         }

         getPlatform();
         return true;
      }
   }

   static void doInit(List var0) {
      if (!initialised) {
         if (isSubsystemRegistered()) {
            logger.warn("Multiple Mixin containers present, init suppressed for 0.7.4");
         } else {
            throw new IllegalStateException("MixinBootstrap.doInit() called before MixinBootstrap.start()");
         }
      } else {
         getPlatform().getPhaseProviderClasses();
         if (initState) {
            getPlatform().prepare(var0);
            MixinService.getService().init();
         }

      }
   }

   static void inject() {
      getPlatform().inject();
   }

   private static boolean isSubsystemRegistered() {
      return GlobalProperties.get("mixin.initialised") != null;
   }

   private static boolean checkSubsystemVersion() {
      return "0.7.4".equals(getActiveSubsystemVersion());
   }

   private static Object getActiveSubsystemVersion() {
      Object var0 = GlobalProperties.get("mixin.initialised");
      return var0 != null ? var0 : "";
   }

   private static void registerSubsystem(String var0) {
      GlobalProperties.put("mixin.initialised", var0);
   }

   static {
      MixinService.boot();
      MixinService.getService().prepare();
   }
}
