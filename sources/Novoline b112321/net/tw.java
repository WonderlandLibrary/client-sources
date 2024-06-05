package net;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;

class tw implements Callable {
   private static final String b = "CL_00001197";
   final CrashReport a;

   tw(CrashReport var1) {
      this.a = var1;
   }

   public String a() {
      return "1.8.8";
   }
}
