package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenReed extends WorldGenerator {
   private static final String __OBFID = "CL_00000429";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      for(int var4 = 0; var4 < 20; ++var4) {
         BlockPos var5 = var3.add(var2.nextInt(4) - var2.nextInt(4), 0, var2.nextInt(4) - var2.nextInt(4));
         if (var1.isAirBlock(var5)) {
            BlockPos var6 = var5.offsetDown();
            if (var1.getBlockState(var6.offsetWest()).getBlock().getMaterial() == Material.water || var1.getBlockState(var6.offsetEast()).getBlock().getMaterial() == Material.water || var1.getBlockState(var6.offsetNorth()).getBlock().getMaterial() == Material.water || var1.getBlockState(var6.offsetSouth()).getBlock().getMaterial() == Material.water) {
               int var7 = 2 + var2.nextInt(var2.nextInt(3) + 1);

               for(int var8 = 0; var8 < var7; ++var8) {
                  if (Blocks.reeds.func_176354_d(var1, var5)) {
                     var1.setBlockState(var5.offsetUp(var8), Blocks.reeds.getDefaultState(), 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
