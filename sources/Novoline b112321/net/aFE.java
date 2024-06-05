package net;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class aFE {
   private static int b;

   public static HttpEntity a(HttpResponse var0) {
      return var0.getEntity();
   }

   public static Header a(HttpResponse var0, String var1) {
      return var0.getFirstHeader(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 54;
   }

   static {
      if(a() != 0) {
         b(125);
      }

   }
}
