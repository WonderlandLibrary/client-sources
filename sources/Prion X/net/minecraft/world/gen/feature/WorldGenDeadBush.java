package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenDeadBush extends WorldGenerator
{
  private static final String __OBFID = "CL_00000406";
  
  public WorldGenDeadBush() {}
  
  public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
  {
    Block var4;
    while ((((var4 = worldIn.getBlockState(p_180709_3_).getBlock()).getMaterial() == net.minecraft.block.material.Material.air) || (var4.getMaterial() == net.minecraft.block.material.Material.leaves)) && (p_180709_3_.getY() > 0)) {
      Block var4;
      p_180709_3_ = p_180709_3_.offsetDown();
    }
    
    for (int var5 = 0; var5 < 4; var5++)
    {
      BlockPos var6 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
      
      if ((worldIn.isAirBlock(var6)) && (net.minecraft.init.Blocks.deadbush.canBlockStay(worldIn, var6, net.minecraft.init.Blocks.deadbush.getDefaultState())))
      {
        worldIn.setBlockState(var6, net.minecraft.init.Blocks.deadbush.getDefaultState(), 2);
      }
    }
    
    return true;
  }
}
