package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPotato extends BlockCrops
{
  private static final String __OBFID = "CL_00000286";
  
  public BlockPotato() {}
  
  protected net.minecraft.item.Item getSeed()
  {
    return Items.potato;
  }
  
  protected net.minecraft.item.Item getCrop()
  {
    return Items.potato;
  }
  






  public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
  {
    super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    
    if (!isRemote)
    {
      if ((((Integer)state.getValue(AGE)).intValue() >= 7) && (rand.nextInt(50) == 0))
      {
        spawnAsEntity(worldIn, pos, new net.minecraft.item.ItemStack(Items.poisonous_potato));
      }
    }
  }
}
