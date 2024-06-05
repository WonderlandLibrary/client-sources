package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenDoublePlant extends WorldGenerator {
   private static final String __OBFID = "CL_00000408";
   private BlockDoublePlant.EnumPlantType field_150549_a;

   public void func_180710_a(BlockDoublePlant.EnumPlantType var1) {
      this.field_150549_a = var1;
   }

   public boolean generate(World var1, Random var2, BlockPos var3) {
      boolean var4 = false;

      for(int var5 = 0; var5 < 64; ++var5) {
         BlockPos var6 = var3.add(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.isAirBlock(var6) && (!var1.provider.getHasNoSky() || var6.getY() < 254) && Blocks.double_plant.canPlaceBlockAt(var1, var6)) {
            Blocks.double_plant.func_176491_a(var1, var6, this.field_150549_a, 2);
            var4 = true;
         }
      }

      return var4;
   }
}
