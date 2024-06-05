package net;

import viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;

public class zt {
   public static void a(BlockConnectionStorage var0, int var1, int var2, int var3, int var4) {
      var0.store(var1, var2, var3, var4);
   }

   public static void b(BlockConnectionStorage var0, int var1, int var2, int var3) {
      var0.remove(var1, var2, var3);
   }

   public static int a(BlockConnectionStorage var0, int var1, int var2, int var3) {
      return var0.get(var1, var2, var3);
   }

   public static void a(BlockConnectionStorage var0) {
      var0.clear();
   }

   public static void a(BlockConnectionStorage var0, int var1, int var2) {
      var0.unloadChunk(var1, var2);
   }
}
