package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator
{
  private BlockFlower flower;
  private net.minecraft.block.state.IBlockState field_175915_b;
  private static final String __OBFID = "CL_00000410";
  
  public WorldGenFlowers(BlockFlower p_i45632_1_, BlockFlower.EnumFlowerType p_i45632_2_)
  {
    setGeneratedBlock(p_i45632_1_, p_i45632_2_);
  }
  
  public void setGeneratedBlock(BlockFlower p_175914_1_, BlockFlower.EnumFlowerType p_175914_2_)
  {
    flower = p_175914_1_;
    field_175915_b = p_175914_1_.getDefaultState().withProperty(p_175914_1_.func_176494_l(), p_175914_2_);
  }
  
  public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
  {
    for (int var4 = 0; var4 < 64; var4++)
    {
      BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
      
      if ((worldIn.isAirBlock(var5)) && ((!provider.getHasNoSky()) || (var5.getY() < 255)) && (flower.canBlockStay(worldIn, var5, field_175915_b)))
      {
        worldIn.setBlockState(var5, field_175915_b, 2);
      }
    }
    
    return true;
  }
}
