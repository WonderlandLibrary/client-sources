package net;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.FlatLayerInfo;

public class a4g {
   public static int c(FlatLayerInfo var0) {
      return var0.getMinY();
   }

   public static int b(FlatLayerInfo var0) {
      return var0.getLayerCount();
   }

   public static IBlockState a(FlatLayerInfo var0) {
      return var0.func_175900_c();
   }

   public static void a(FlatLayerInfo var0, int var1) {
      var0.setMinY(var1);
   }
}
