package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenerator
{
  private final boolean doBlockNotify;
  private static final String __OBFID = "CL_00000409";
  
  public WorldGenerator()
  {
    this(false);
  }
  
  public WorldGenerator(boolean p_i2013_1_)
  {
    this.doBlockNotify = p_i2013_1_;
  }
  
  public abstract boolean generate(World paramWorld, Random paramRandom, BlockPos paramBlockPos);
  
  public void func_175904_e() {}
  
  protected void func_175906_a(World worldIn, BlockPos p_175906_2_, Block p_175906_3_)
  {
    func_175905_a(worldIn, p_175906_2_, p_175906_3_, 0);
  }
  
  protected void func_175905_a(World worldIn, BlockPos p_175905_2_, Block p_175905_3_, int p_175905_4_)
  {
    func_175903_a(worldIn, p_175905_2_, p_175905_3_.getStateFromMeta(p_175905_4_));
  }
  
  protected void func_175903_a(World worldIn, BlockPos p_175903_2_, IBlockState p_175903_3_)
  {
    if (this.doBlockNotify) {
      worldIn.setBlockState(p_175903_2_, p_175903_3_, 3);
    } else {
      worldIn.setBlockState(p_175903_2_, p_175903_3_, 2);
    }
  }
}
