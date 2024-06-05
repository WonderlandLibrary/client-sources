package net;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.structure.StructureComponent$BlockSelector;

public class am0 {
   public static void a(StructureComponent$BlockSelector var0, Random var1, int var2, int var3, int var4, boolean var5) {
      var0.selectBlocks(var1, var2, var3, var4, var5);
   }

   public static IBlockState a(StructureComponent$BlockSelector var0) {
      return var0.getBlockState();
   }
}
