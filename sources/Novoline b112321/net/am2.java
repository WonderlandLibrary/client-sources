package net;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;

public class am2 {
   public static KeyFactory a(String var0) {
      return KeyFactory.getInstance(var0);
   }

   public static PublicKey a(KeyFactory var0, KeySpec var1) {
      return var0.generatePublic(var1);
   }

   public static PrivateKey b(KeyFactory var0, KeySpec var1) {
      return var0.generatePrivate(var1);
   }
}
