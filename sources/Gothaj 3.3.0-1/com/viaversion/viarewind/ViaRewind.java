package com.viaversion.viarewind;

import com.google.common.base.Preconditions;
import com.viaversion.viarewind.api.ViaRewindPlatform;

public class ViaRewind {
   private static ViaRewindPlatform platform;
   private static com.viaversion.viarewind.api.ViaRewindConfig config;

   public static void init(ViaRewindPlatform platform, com.viaversion.viarewind.api.ViaRewindConfig config) {
      Preconditions.checkArgument(ViaRewind.platform == null, "ViaRewind is already initialized");
      ViaRewind.platform = platform;
      ViaRewind.config = config;
   }

   public static ViaRewindPlatform getPlatform() {
      return platform;
   }

   public static com.viaversion.viarewind.api.ViaRewindConfig getConfig() {
      return config;
   }
}
