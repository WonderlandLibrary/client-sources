package net;

import java.security.Key;
import javax.crypto.Mac;

public class abZ {
   public static void a(Mac var0, Key var1) {
      var0.init(var1);
   }

   public static byte[] a(Mac var0, byte[] var1) {
      return var0.doFinal(var1);
   }

   public static Mac a(String var0) {
      return Mac.getInstance(var0);
   }
}
