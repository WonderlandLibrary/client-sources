package net;

import net.acE;
import net.cA;
import viaversion.viaversion.api.data.UserConnection;

public class cM extends cA {
   private boolean d;
   private static acE[] c;

   public cM(UserConnection var1) {
      super(var1);
   }

   public boolean d() {
      return this.d;
   }

   public void a(boolean var1) {
      this.d = var1;
   }

   public static void b(acE[] var0) {
      c = var0;
   }

   public static acE[] a() {
      return c;
   }

   static {
      if(a() != null) {
         b(new acE[5]);
      }

   }
}
