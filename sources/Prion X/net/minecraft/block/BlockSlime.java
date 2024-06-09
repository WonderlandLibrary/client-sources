package net.minecraft.block;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockSlime extends BlockBreakable
{
  private static final String __OBFID = "CL_00002063";
  
  public BlockSlime()
  {
    super(net.minecraft.block.material.Material.clay, false);
    setCreativeTab(CreativeTabs.tabDecorations);
    slipperiness = 0.8F;
  }
  
  public EnumWorldBlockLayer getBlockLayer()
  {
    return EnumWorldBlockLayer.TRANSLUCENT;
  }
  





  public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
  {
    if (entityIn.isSneaking())
    {
      super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }
    else
    {
      entityIn.fall(fallDistance, 0.0F);
    }
  }
  




  public void onLanded(World worldIn, Entity entityIn)
  {
    if (entityIn.isSneaking())
    {
      super.onLanded(worldIn, entityIn);
    }
    else if (motionY < 0.0D)
    {
      if (ModuleManager.getModByName("SlimeFly").isEnabled()) {
        motionY = (-motionY * getValuesSlimeFly);
      } else {
        motionY = (-motionY);
      }
    }
  }
  



  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
  {
    if ((Math.abs(motionY) < 0.1D) && (!entityIn.isSneaking()))
    {
      double var4 = 0.4D + Math.abs(motionY) * 0.2D;
      motionX *= var4;
      motionZ *= var4;
    }
    
    super.onEntityCollidedWithBlock(worldIn, pos, entityIn);
  }
}
