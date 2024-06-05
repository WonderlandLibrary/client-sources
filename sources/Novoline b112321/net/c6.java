package net;

import net.acE;
import net.cA;
import viaversion.viaversion.api.data.UserConnection;

public class c6 extends cA {
   private String c;
   private static acE[] d;

   public c6(UserConnection var1) {
      a();
      super(var1);
      this.c = "";
      if(acE.b() == null) {
         b(new acE[3]);
      }

   }

   public String b() {
      return this.c;
   }

   public void a(String var1) {
      this.c = var1;
   }

   public String toString() {
      acE[] var1 = a();
      return "ResourcePackTracker{lastHash=\'" + this.c + '\'' + '}';
   }

   public static void b(acE[] var0) {
      d = var0;
   }

   public static acE[] a() {
      return d;
   }

   static {
      if(a() == null) {
         b(new acE[3]);
      }

   }
}
