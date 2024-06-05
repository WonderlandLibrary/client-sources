package net;

import viaversion.viaversion.api.minecraft.chunks.NibbleArray;

public class S_ {
   public static void a(NibbleArray var0, int var1, int var2, int var3, int var4) {
      var0.set(var1, var2, var3, var4);
   }

   public static void a(NibbleArray var0, int var1, int var2) {
      var0.a(var1, var2);
   }

   public static byte a(NibbleArray var0, int var1) {
      return var0.a(var1);
   }

   public static byte a(NibbleArray var0, int var1, int var2, int var3) {
      return var0.get(var1, var2, var3);
   }

   public static void a(NibbleArray var0, byte[] var1) {
      var0.setHandle(var1);
   }

   public static byte[] a(NibbleArray var0) {
      return var0.getHandle();
   }
}
