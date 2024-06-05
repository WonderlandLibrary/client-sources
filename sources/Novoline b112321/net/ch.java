package net;

import net.cA;
import viaversion.viaversion.api.data.UserConnection;

public class ch extends cA {
   private short d;
   private static int c;

   public ch(UserConnection var1) {
      super(var1);
      a();
      this.d = -1;
   }

   public short c() {
      return this.d;
   }

   public void a(short var1) {
      this.d = var1;
   }

   public static void b(int var0) {
      c = var0;
   }

   public static int a() {
      return c;
   }

   public static int b() {
      int var0 = a();
      return 121;
   }

   static {
      if(a() != 0) {
         b(50);
      }

   }
}
