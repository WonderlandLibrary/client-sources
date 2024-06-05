package net;

import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class p6 {
   public static void a(WorldGenAbstractTree var0) {
      var0.func_175904_e();
   }

   public static boolean a(WorldGenAbstractTree var0, World var1, Random var2, BlockPos var3) {
      return var0.generate(var1, var2, var3);
   }

   public static void b(WorldGenAbstractTree var0, World var1, Random var2, BlockPos var3) {
      var0.func_180711_a(var1, var2, var3);
   }
}
