package org.spongepowered.asm.mixin.extensibility;

import java.util.Set;
import org.spongepowered.asm.mixin.MixinEnvironment;

public interface IMixinConfig {
   int DEFAULT_PRIORITY = 1000;

   MixinEnvironment getEnvironment();

   String getName();

   String getMixinPackage();

   int getPriority();

   IMixinConfigPlugin getPlugin();

   boolean isRequired();

   Set getTargets();
}
