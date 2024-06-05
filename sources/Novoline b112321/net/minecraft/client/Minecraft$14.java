package net.minecraft.client;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

class Minecraft$14 implements Callable {
   final MinecraftServer a;

   Minecraft$14(MinecraftServer var1) {
      this.a = var1;
   }

   public String call() throws Exception {
      return this.a.theProfiler.profilingEnabled?this.a.theProfiler.getNameOfLastSection():"N/A (disabled)";
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
