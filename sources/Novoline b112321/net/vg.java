package net;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class vg implements Callable {
   private static final String b = "CL_00001302";
   final CrashReport a;

   vg(CrashReport var1) {
      this.a = var1;
   }

   public String a() {
      Runtime var1 = Runtime.getRuntime();
      long var2 = var1.maxMemory();
      long var4 = var1.totalMemory();
      long var6 = var1.freeMemory();
      long var8 = var2 / 1024L / 1024L;
      long var10 = var4 / 1024L / 1024L;
      long var12 = var6 / 1024L / 1024L;
      return var6 + " bytes (" + var12 + " MB) / " + var4 + " bytes (" + var10 + " MB) up to " + var2 + " bytes (" + var8 + " MB)";
   }
}
