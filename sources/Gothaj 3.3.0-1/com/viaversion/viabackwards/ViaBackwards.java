package com.viaversion.viabackwards;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.api.ViaBackwardsPlatform;

public final class ViaBackwards {
   private static ViaBackwardsPlatform platform;
   private static com.viaversion.viabackwards.api.ViaBackwardsConfig config;

   public static void init(ViaBackwardsPlatform platform, com.viaversion.viabackwards.api.ViaBackwardsConfig config) {
      Preconditions.checkArgument(ViaBackwards.platform == null, "ViaBackwards is already initialized");
      ViaBackwards.platform = platform;
      ViaBackwards.config = config;
   }

   public static ViaBackwardsPlatform getPlatform() {
      return platform;
   }

   public static com.viaversion.viabackwards.api.ViaBackwardsConfig getConfig() {
      return config;
   }
}
