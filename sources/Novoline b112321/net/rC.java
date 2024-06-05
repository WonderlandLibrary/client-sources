package net;

import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;

public class rC {
   public static boolean a(WorldGeneratorBonusChest var0, World var1, Random var2, BlockPos var3) {
      return var0.generate(var1, var2, var3);
   }
}
