package net;

import net.minecraft.client.renderer.block.model.BlockFaceUV;

public class a4b {
   public static float c(BlockFaceUV var0, int var1) {
      return var0.func_178348_a(var1);
   }

   public static float b(BlockFaceUV var0, int var1) {
      return var0.func_178346_b(var1);
   }

   public static int a(BlockFaceUV var0, int var1) {
      return var0.func_178345_c(var1);
   }

   public static void a(BlockFaceUV var0, float[] var1) {
      var0.setUvs(var1);
   }
}
