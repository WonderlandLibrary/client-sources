package net;

import java.math.BigInteger;

public class iL {
   private static String[] b;

   public static String a(BigInteger var0, int var1) {
      return var0.toString(var1);
   }

   public static byte[] a(BigInteger var0) {
      return var0.toByteArray();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[1]);
      }

   }
}
