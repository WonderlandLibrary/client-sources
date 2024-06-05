package net;

import net.cA;
import viaversion.viaversion.api.data.UserConnection;

public class cV extends cA {
   private String c;
   private static String d;

   public cV(UserConnection var1) {
      super(var1);
   }

   public String a() {
      return this.c;
   }

   public void c(String var1) {
      this.c = var1;
   }

   public static void b(String var0) {
      d = var0;
   }

   public static String b() {
      return d;
   }

   static {
      if(b() == null) {
         b("ahaOR");
      }

   }
}
