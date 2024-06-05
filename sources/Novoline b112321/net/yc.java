package net;

import com.mojang.authlib.exceptions.AuthenticationException;

public class yc {
   private static String b;

   public static String a(AuthenticationException var0) {
      return var0.getMessage();
   }

   public static void b(AuthenticationException var0) {
      var0.printStackTrace();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("yo94Fb");
      }

   }
}
