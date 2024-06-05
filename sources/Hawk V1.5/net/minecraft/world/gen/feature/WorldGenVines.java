package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenVines extends WorldGenerator {
   private static final String __OBFID = "CL_00000439";

   public boolean generate(World var1, Random var2, BlockPos var3) {
      for(; var3.getY() < 128; var3 = var3.offsetUp()) {
         if (var1.isAirBlock(var3)) {
            EnumFacing[] var4 = EnumFacing.Plane.HORIZONTAL.facings();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               EnumFacing var7 = var4[var6];
               if (Blocks.vine.canPlaceBlockOnSide(var1, var3, var7)) {
                  IBlockState var8 = Blocks.vine.getDefaultState().withProperty(BlockVine.field_176273_b, var7 == EnumFacing.NORTH).withProperty(BlockVine.field_176278_M, var7 == EnumFacing.EAST).withProperty(BlockVine.field_176279_N, var7 == EnumFacing.SOUTH).withProperty(BlockVine.field_176280_O, var7 == EnumFacing.WEST);
                  var1.setBlockState(var3, var8, 2);
                  break;
               }
            }
         } else {
            var3 = var3.add(var2.nextInt(4) - var2.nextInt(4), 0, var2.nextInt(4) - var2.nextInt(4));
         }
      }

      return true;
   }
}
