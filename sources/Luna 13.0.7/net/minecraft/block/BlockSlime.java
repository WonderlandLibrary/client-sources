package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockSlime
  extends BlockBreakable
{
  private static final String __OBFID = "CL_00002063";
  
  public BlockSlime()
  {
    super(Material.clay, false);
    setCreativeTab(CreativeTabs.tabDecorations);
    this.slipperiness = 0.8F;
  }
  
  public EnumWorldBlockLayer getBlockLayer()
  {
    return EnumWorldBlockLayer.TRANSLUCENT;
  }
  
  public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
  {
    if (entityIn.isSneaking()) {
      super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    } else {
      entityIn.fall(fallDistance, 0.0F);
    }
  }
  
  public void onLanded(World worldIn, Entity entityIn)
  {
    if (entityIn.isSneaking()) {
      super.onLanded(worldIn, entityIn);
    } else if (entityIn.motionY < 0.0D) {
      entityIn.motionY = (-entityIn.motionY);
    }
  }
  
  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
  {
    if ((Math.abs(entityIn.motionY) < 0.1D) && (!entityIn.isSneaking()))
    {
      double var4 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
      entityIn.motionX *= var4;
      entityIn.motionZ *= var4;
    }
    super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
  }
}
