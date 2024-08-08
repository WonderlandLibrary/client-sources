package org.spongepowered.asm.mixin;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.mixin.transformer.Config;

public final class Mixins {
   private static final Logger logger = LogManager.getLogger("mixin");
   private static final String CONFIGS_KEY = "mixin.configs.queue";
   private static final Set errorHandlers = new LinkedHashSet();

   private Mixins() {
   }

   public static void addConfigurations(String... var0) {
      MixinEnvironment var1 = MixinEnvironment.getDefaultEnvironment();
      String[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         createConfiguration(var5, var1);
      }

   }

   public static void addConfiguration(String var0) {
      createConfiguration(var0, MixinEnvironment.getDefaultEnvironment());
   }

   /** @deprecated */
   @Deprecated
   static void addConfiguration(String var0, MixinEnvironment var1) {
      createConfiguration(var0, var1);
   }

   private static void createConfiguration(String var0, MixinEnvironment var1) {
      Config var2 = null;

      try {
         var2 = Config.create(var0, var1);
      } catch (Exception var4) {
         logger.error("Error encountered reading mixin config " + var0 + ": " + var4.getClass().getName() + " " + var4.getMessage(), var4);
      }

      registerConfiguration(var2);
   }

   private static void registerConfiguration(Config var0) {
      if (var0 != null) {
         MixinEnvironment var1 = var0.getEnvironment();
         if (var1 != null) {
            var1.registerConfig(var0.getName());
         }

         getConfigs().add(var0);
      }
   }

   public static int getUnvisitedCount() {
      int var0 = 0;
      Iterator var1 = getConfigs().iterator();

      while(var1.hasNext()) {
         Config var2 = (Config)var1.next();
         if (!var2.isVisited()) {
            ++var0;
         }
      }

      return var0;
   }

   public static Set getConfigs() {
      Object var0 = (Set)GlobalProperties.get("mixin.configs.queue");
      if (var0 == null) {
         var0 = new LinkedHashSet();
         GlobalProperties.put("mixin.configs.queue", var0);
      }

      return (Set)var0;
   }

   public static void registerErrorHandlerClass(String var0) {
      if (var0 != null) {
         errorHandlers.add(var0);
      }

   }

   public static Set getErrorHandlerClasses() {
      return Collections.unmodifiableSet(errorHandlers);
   }
}
