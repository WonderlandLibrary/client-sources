package net;

import java.security.PublicKey;
import java.security.Signature;

public class uG {
   public static Signature a(String var0) {
      return Signature.getInstance(var0);
   }

   public static void a(Signature var0, PublicKey var1) {
      var0.initVerify(var1);
   }

   public static void b(Signature var0, byte[] var1) {
      var0.update(var1);
   }

   public static boolean a(Signature var0, byte[] var1) {
      return var0.verify(var1);
   }
}
