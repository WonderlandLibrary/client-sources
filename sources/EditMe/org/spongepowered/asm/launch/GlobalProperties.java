package org.spongepowered.asm.launch;

import java.util.ServiceLoader;
import org.spongepowered.asm.service.IGlobalPropertyService;

public final class GlobalProperties {
   private static IGlobalPropertyService service;

   private GlobalProperties() {
   }

   private static IGlobalPropertyService getService() {
      if (service == null) {
         ServiceLoader var0 = ServiceLoader.load(IGlobalPropertyService.class, GlobalProperties.class.getClassLoader());
         service = (IGlobalPropertyService)var0.iterator().next();
      }

      return service;
   }

   public static Object get(String var0) {
      return getService().getProperty(var0);
   }

   public static void put(String var0, Object var1) {
      getService().setProperty(var0, var1);
   }

   public static Object get(String var0, Object var1) {
      return getService().getProperty(var0, var1);
   }

   public static String getString(String var0, String var1) {
      return getService().getPropertyString(var0, var1);
   }

   public static final class Keys {
      public static final String INIT = "mixin.initialised";
      public static final String AGENTS = "mixin.agents";
      public static final String CONFIGS = "mixin.configs";
      public static final String TRANSFORMER = "mixin.transformer";
      public static final String PLATFORM_MANAGER = "mixin.platform";
      public static final String FML_LOAD_CORE_MOD = "mixin.launch.fml.loadcoremodmethod";
      public static final String FML_GET_REPARSEABLE_COREMODS = "mixin.launch.fml.reparseablecoremodsmethod";
      public static final String FML_CORE_MOD_MANAGER = "mixin.launch.fml.coremodmanagerclass";
      public static final String FML_GET_IGNORED_MODS = "mixin.launch.fml.ignoredmodsmethod";

      private Keys() {
      }
   }
}
