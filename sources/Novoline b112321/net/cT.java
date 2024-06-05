package net;

import net.cA;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Environment;

public class cT extends cA {
   private Environment d;
   private static int c;

   public cT(UserConnection var1) {
      super(var1);
   }

   public Environment a() {
      return this.d;
   }

   public void c(int var1) {
      this.d = Environment.getEnvironmentById(var1);
   }

   public static void b(int var0) {
      c = var0;
   }

   public static int b() {
      return c;
   }

   public static int c() {
      int var0 = b();
      return 107;
   }

   static {
      if(c() == 0) {
         b(92);
      }

   }
}
