package net;

import java.security.MessageDigest;

public class apl {
   private static String b;

   public static MessageDigest a(String var0) {
      return MessageDigest.getInstance(var0);
   }

   public static void b(MessageDigest var0, byte[] var1) {
      var0.update(var1);
   }

   public static byte[] a(MessageDigest var0) {
      return var0.digest();
   }

   public static void b(MessageDigest var0) {
      var0.reset();
   }

   public static byte[] a(MessageDigest var0, byte[] var1) {
      return var0.digest(var1);
   }

   public static void a(MessageDigest var0, byte[] var1, int var2, int var3) {
      var0.update(var1, var2, var3);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("EbZMgc");
      }

   }
}
