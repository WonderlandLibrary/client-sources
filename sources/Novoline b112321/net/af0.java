package net;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class af0 implements Callable {
   private static final String a = "CL_00001222";
   final CrashReport b;

   af0(CrashReport var1) {
      this.b = var1;
   }

   public String a() {
      return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
   }
}
