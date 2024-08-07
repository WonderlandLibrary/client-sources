package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import space.lunaclient.luna.impl.events.EventSlowDown;

public class BlockSoulSand
  extends Block
{
  private static final String __OBFID = "CL_00000310";
  
  public BlockSoulSand()
  {
    super(Material.sand);
    setCreativeTab(CreativeTabs.tabBlock);
  }
  
  public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
  {
    float var4 = 0.125F;
    return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 - var4, pos.getZ() + 1);
  }
  
  public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
  {
    EventSlowDown eventSlowDown = new EventSlowDown();
    eventSlowDown.call();
    if (eventSlowDown.isCancelled()) {
      return;
    }
    entityIn.motionX *= 0.4D;
    entityIn.motionZ *= 0.4D;
  }
}
