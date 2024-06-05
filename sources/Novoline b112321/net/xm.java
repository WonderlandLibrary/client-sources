package net;

import viaversion.viaversion.api.minecraft.Environment;

public class xm {
   public static int a(Environment var0) {
      return var0.getId();
   }

   public static Environment a(int var0) {
      return Environment.getEnvironmentById(var0);
   }
}
