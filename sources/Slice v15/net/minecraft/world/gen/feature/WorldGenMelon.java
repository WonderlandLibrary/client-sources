package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.BlockPos;

public class WorldGenMelon extends WorldGenerator
{
  private static final String __OBFID = "CL_00000424";
  
  public WorldGenMelon() {}
  
  public boolean generate(net.minecraft.world.World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
  {
    for (int var4 = 0; var4 < 64; var4++)
    {
      BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
      
      if ((net.minecraft.init.Blocks.melon_block.canPlaceBlockAt(worldIn, var5)) && (worldIn.getBlockState(var5.offsetDown()).getBlock() == net.minecraft.init.Blocks.grass))
      {
        worldIn.setBlockState(var5, net.minecraft.init.Blocks.melon_block.getDefaultState(), 2);
      }
    }
    
    return true;
  }
}
