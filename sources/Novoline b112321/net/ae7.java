package net;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

public class ae7 {
   private static int b;

   public static HardwareAbstractionLayer a(SystemInfo var0) {
      return var0.getHardware();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 9;
   }

   static {
      if(c() == 0) {
         b(28);
      }

   }
}
