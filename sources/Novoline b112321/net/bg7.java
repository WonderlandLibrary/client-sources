package net;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import net.acE;

public class bg7 {
   private static acE[] b;

   public static void a(Cipher var0, int var1, Key var2, AlgorithmParameterSpec var3) {
      var0.init(var1, var2, var3);
   }

   public static byte[] a(Cipher var0, byte[] var1) {
      return var0.doFinal(var1);
   }

   public static int a(Cipher var0) {
      return var0.getBlockSize();
   }

   public static Cipher a(String var0) {
      return Cipher.getInstance(var0);
   }

   public static void a(Cipher var0, int var1, Key var2) {
      var0.init(var1, var2);
   }

   public static int a(Cipher var0, int var1) {
      return var0.getOutputSize(var1);
   }

   public static int a(Cipher var0, byte[] var1, int var2, int var3, byte[] var4, int var5) {
      return var0.update(var1, var2, var3, var4, var5);
   }

   public static int a(Cipher var0, byte[] var1, int var2, int var3, byte[] var4) {
      return var0.update(var1, var2, var3, var4);
   }

   public static int b(String var0) {
      return Cipher.getMaxAllowedKeyLength(var0);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
