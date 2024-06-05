package net;

import java.util.concurrent.Callable;
import net.minecraft.server.MinecraftServer;

class aqE implements Callable {
   final MinecraftServer a;

   aqE(MinecraftServer var1) {
      this.a = var1;
   }

   public String a() throws Exception {
      return this.a.theProfiler.profilingEnabled?this.a.theProfiler.getNameOfLastSection():"N/A (disabled)";
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
