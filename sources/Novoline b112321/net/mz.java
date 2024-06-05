package net;

import java.lang.management.RuntimeMXBean;
import java.util.List;

public class mz {
   private static int b;

   public static List a(RuntimeMXBean var0) {
      return var0.getInputArguments();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 56;
   }

   static {
      if(b() != 0) {
         b(89);
      }

   }
}
