package net.minecraft.block;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockReed
  extends Block
{
  public static final PropertyInteger field_176355_a = PropertyInteger.create("age", 0, 15);
  private static final String __OBFID = "CL_00000300";
  
  protected BlockReed()
  {
    super(Material.plants);
    setDefaultState(this.blockState.getBaseState().withProperty(field_176355_a, Integer.valueOf(0)));
    float var1 = 0.375F;
    setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 1.0F, 0.5F + var1);
    setTickRandomly(true);
  }
  
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
  {
    if ((worldIn.getBlockState(pos.offsetDown()).getBlock() == Blocks.reeds) || (func_176353_e(worldIn, pos, state))) {
      if (worldIn.isAirBlock(pos.offsetUp()))
      {
        for (int var5 = 1; worldIn.getBlockState(pos.offsetDown(var5)).getBlock() == this; var5++) {}
        if (var5 < 3)
        {
          int var6 = ((Integer)state.getValue(field_176355_a)).intValue();
          if (var6 == 15)
          {
            worldIn.setBlockState(pos.offsetUp(), getDefaultState());
            worldIn.setBlockState(pos, state.withProperty(field_176355_a, Integer.valueOf(0)), 4);
          }
          else
          {
            worldIn.setBlockState(pos, state.withProperty(field_176355_a, Integer.valueOf(var6 + 1)), 4);
          }
        }
      }
    }
  }
  
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
  {
    Block var3 = worldIn.getBlockState(pos.offsetDown()).getBlock();
    if (var3 == this) {
      return true;
    }
    if ((var3 != Blocks.grass) && (var3 != Blocks.dirt) && (var3 != Blocks.sand)) {
      return false;
    }
    Iterator var4 = EnumFacing.Plane.HORIZONTAL.iterator();
    EnumFacing var5;
    do
    {
      if (!var4.hasNext()) {
        return false;
      }
      var5 = (EnumFacing)var4.next();
    } while (worldIn.getBlockState(pos.offset(var5).offsetDown()).getBlock().getMaterial() != Material.water);
    return true;
  }
  
  public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
  {
    func_176353_e(worldIn, pos, state);
  }
  
  protected final boolean func_176353_e(World worldIn, BlockPos p_176353_2_, IBlockState p_176353_3_)
  {
    if (func_176354_d(worldIn, p_176353_2_)) {
      return true;
    }
    dropBlockAsItem(worldIn, p_176353_2_, p_176353_3_, 0);
    worldIn.setBlockToAir(p_176353_2_);
    return false;
  }
  
  public boolean func_176354_d(World worldIn, BlockPos p_176354_2_)
  {
    return canPlaceBlockAt(worldIn, p_176354_2_);
  }
  
  public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
  {
    return null;
  }
  
  public Item getItemDropped(IBlockState state, Random rand, int fortune)
  {
    return Items.reeds;
  }
  
  public boolean isOpaqueCube()
  {
    return false;
  }
  
  public boolean isFullCube()
  {
    return false;
  }
  
  public Item getItem(World worldIn, BlockPos pos)
  {
    return Items.reeds;
  }
  
  public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
  {
    return worldIn.getBiomeGenForCoords(pos).func_180627_b(pos);
  }
  
  public EnumWorldBlockLayer getBlockLayer()
  {
    return EnumWorldBlockLayer.CUTOUT;
  }
  
  public IBlockState getStateFromMeta(int meta)
  {
    return getDefaultState().withProperty(field_176355_a, Integer.valueOf(meta));
  }
  
  public int getMetaFromState(IBlockState state)
  {
    return ((Integer)state.getValue(field_176355_a)).intValue();
  }
  
  protected BlockState createBlockState()
  {
    return new BlockState(this, new IProperty[] { field_176355_a });
  }
}
