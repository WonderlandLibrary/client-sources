package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenVines extends WorldGenerator {
   public boolean generate(World var1, Random var2, BlockPos var3) {
      for(; var3.getY() < 128; var3 = var3.up()) {
         if(var1.isAirBlock(var3)) {
            for(EnumFacing var7 : EnumFacing$Plane.HORIZONTAL.facings()) {
               if(Blocks.vine.canPlaceBlockOnSide(var1, var3, var7)) {
                  IBlockState var8 = Blocks.vine.getDefaultState().withProperty(BlockVine.NORTH, Boolean.valueOf(var7 == EnumFacing.NORTH)).withProperty(BlockVine.EAST, Boolean.valueOf(var7 == EnumFacing.EAST)).withProperty(BlockVine.SOUTH, Boolean.valueOf(var7 == EnumFacing.SOUTH)).withProperty(BlockVine.WEST, Boolean.valueOf(var7 == EnumFacing.WEST));
                  var1.setBlockState(var3, var8, 2);
                  break;
               }
            }
         } else {
            var3 = var3.a(var2.nextInt(4) - var2.nextInt(4), 0, var2.nextInt(4) - var2.nextInt(4));
         }
      }

      return true;
   }
}
