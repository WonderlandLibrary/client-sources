package com.viaversion.viaversion.api;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import net.lc;
import us.myles.ViaVersion.api.ViaAPI;

public class Via {
   private static ViaPlatform a;
   private static lc b;

   public static void a(lc var0) {
      Preconditions.checkArgument(b == null, "ViaManager is already set");
      a = var0.p();
      b = var0;
   }

   public static ViaAPI a() {
      Preconditions.checkArgument(a != null, "ViaVersion has not loaded the Platform");
      return a.o();
   }

   public static ViaVersionConfig c() {
      Preconditions.checkArgument(a != null, "ViaVersion has not loaded the Platform");
      return a.getConf();
   }

   public static ViaPlatform d() {
      return a;
   }

   public static lc b() {
      return b;
   }
}
