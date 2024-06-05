package net;

import net.acE;
import net.cA;
import viaversion.viaversion.api.data.UserConnection;

public class co extends cA {
   private String e;
   private int d = -1;
   private static String c;

   public co(UserConnection var1) {
      super(var1);
   }

   public String c() {
      return this.e;
   }

   public void c(String var1) {
      this.e = var1;
   }

   public int b() {
      return this.d;
   }

   public void a(int var1) {
      this.d = var1;
   }

   public String toString() {
      String var1 = a();
      String var10000 = "WindowTracker{inventory=\'" + this.e + '\'' + ", entityId=" + this.d + '}';
      if(acE.b() == null) {
         b("Le5TNb");
      }

      return var10000;
   }

   public static void b(String var0) {
      c = var0;
   }

   public static String a() {
      return c;
   }

   static {
      b("gxejRc");
   }
}
