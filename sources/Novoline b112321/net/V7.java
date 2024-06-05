package net;

import javax.sound.sampled.FloatControl;
import net.acE;

public class V7 {
   private static acE[] b;

   public static void a(FloatControl var0, float var1) {
      var0.setValue(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[1]);
      }

   }
}
