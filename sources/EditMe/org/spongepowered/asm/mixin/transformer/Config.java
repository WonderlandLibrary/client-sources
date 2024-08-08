package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;

public class Config {
   private final String name;
   private final MixinConfig config;

   public Config(MixinConfig var1) {
      this.name = var1.getName();
      this.config = var1;
   }

   public String getName() {
      return this.name;
   }

   MixinConfig get() {
      return this.config;
   }

   public boolean isVisited() {
      return this.config.isVisited();
   }

   public IMixinConfig getConfig() {
      return this.config;
   }

   public MixinEnvironment getEnvironment() {
      return this.config.getEnvironment();
   }

   public String toString() {
      return this.config.toString();
   }

   public boolean equals(Object var1) {
      return var1 instanceof Config && this.name.equals(((Config)var1).name);
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   /** @deprecated */
   @Deprecated
   public static Config create(String var0, MixinEnvironment var1) {
      return MixinConfig.create(var0, var1);
   }

   public static Config create(String var0) {
      return MixinConfig.create(var0, MixinEnvironment.getDefaultEnvironment());
   }
}
