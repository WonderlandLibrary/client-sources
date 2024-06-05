package net.minecraft.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import net.minecraft.util.Util$EnumOS;
import org.apache.logging.log4j.Logger;

public final class Util {
   public static Util$EnumOS getOSType() {
      String var0 = System.getProperty("os.name").toLowerCase();
      return var0.contains("win")?Util$EnumOS.WINDOWS:(var0.contains("mac")?Util$EnumOS.OSX:(var0.contains("solaris")?Util$EnumOS.SOLARIS:(var0.contains("sunos")?Util$EnumOS.SOLARIS:(var0.contains("linux")?Util$EnumOS.LINUX:(var0.contains("unix")?Util$EnumOS.LINUX:Util$EnumOS.UNKNOWN)))));
   }

   public static Object func_181617_a(FutureTask var0, Logger var1) {
      FutureTask var10000 = var0;

      try {
         var10000.run();
         return var0.get();
      } catch (InterruptedException | ExecutionException var3) {
         var1.fatal("Error executing task", var3);
         return null;
      }
   }
}
