package net;

import viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage$ReplacementData;

public class all {
   public static int b(BlockStorage$ReplacementData var0) {
      return var0.getOriginal();
   }

   public static void a(BlockStorage$ReplacementData var0, int var1) {
      var0.setReplacement(var1);
   }

   public static int a(BlockStorage$ReplacementData var0) {
      return var0.getReplacement();
   }
}
