package net;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class hk {
   private static int[] b;

   public static HashFunction a() {
      return Hashing.sha1();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[4]);
      }

   }
}
