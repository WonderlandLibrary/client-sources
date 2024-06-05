package net;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class lg implements Callable {
   private static final String b = "CL_00001275";
   final CrashReport a;

   lg(CrashReport var1) {
      this.a = var1;
   }

   public String a() {
      return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
   }
}
