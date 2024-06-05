package net;

import java.io.InputStream;
import java.security.KeyStore;

public class gj {
   public static KeyStore a(String var0) {
      return KeyStore.getInstance(var0);
   }

   public static void a(KeyStore var0, InputStream var1, char[] var2) {
      var0.load(var1, var2);
   }
}
