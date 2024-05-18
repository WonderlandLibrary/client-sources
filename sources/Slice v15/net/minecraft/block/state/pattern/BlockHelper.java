package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import net.SliceClient.Utils.EntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class BlockHelper implements Predicate
{
  private final Block block;
  private static Minecraft mc;
  
  public static BlockHelper forBlock(Block p_177642_0_)
  {
    return new BlockHelper(p_177642_0_);
  }
  
  private BlockHelper(Block p_i45654_1_)
  {
    block = p_i45654_1_;
  }
  
  public boolean apply(Object p_apply_1_)
  {
    return isBlockEqualTo((IBlockState)p_apply_1_);
  }
  
  public boolean isBlockEqualTo(IBlockState p_177643_1_)
  {
    return (p_177643_1_ != null) && (p_177643_1_.getBlock() == block);
  }
  
  public static Block getBlock(int x, int y, int z)
  {
    return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
  }
  
  public static boolean isOnLiquid()
  {
    boolean onLiquid = false;
    int y = (int)thePlayergetEntityBoundingBoxoffset0.0D-0.01D0.0DminY;
    for (int x = MathHelper.floor_double(thePlayergetEntityBoundingBoxminX); x < MathHelper.floor_double(thePlayergetEntityBoundingBoxmaxX) + 1; x++) {
      for (int z = MathHelper.floor_double(thePlayergetEntityBoundingBoxminZ); z < MathHelper.floor_double(thePlayergetEntityBoundingBoxmaxZ) + 1; z++)
      {
        Block block = getBlock(x, y, z);
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          onLiquid = true;
        }
      }
    }
    return onLiquid;
  }
  
  public static boolean isInLiquid()
  {
    boolean inLiquid = false;
    int y = (int)thePlayergetEntityBoundingBoxminY;
    for (int x = MathHelper.floor_double(thePlayergetEntityBoundingBoxminX); x < MathHelper.floor_double(thePlayergetEntityBoundingBoxmaxX) + 1; x++) {
      for (int z = MathHelper.floor_double(thePlayergetEntityBoundingBoxminZ); z < MathHelper.floor_double(thePlayergetEntityBoundingBoxmaxZ) + 1; z++)
      {
        Block block = getBlock(x, y, z);
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          inLiquid = true;
        }
      }
    }
    return inLiquid;
  }
  
  public static boolean isOnLadder()
  {
    boolean onLadder = false;
    int y = (int)thePlayergetEntityBoundingBoxoffset0.0D1.0D0.0DminY;
    for (int x = MathHelper.floor_double(thePlayergetEntityBoundingBoxminX); x < MathHelper.floor_double(thePlayergetEntityBoundingBoxmaxX) + 1; x++) {
      for (int z = MathHelper.floor_double(thePlayergetEntityBoundingBoxminZ); z < MathHelper.floor_double(thePlayergetEntityBoundingBoxmaxZ) + 1; z++)
      {
        Block block = getBlock(x, y, z);
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if (!(block instanceof net.minecraft.block.BlockLadder)) {
            return false;
          }
          onLadder = true;
        }
      }
    }
    return (onLadder) || (Minecraft.thePlayer.isOnLadder());
  }
  
  public static boolean canSeeBlock(int x, int y, int z)
  {
    return getFacing(new BlockPos(x, y, z)) != null;
  }
  
  public static EnumFacing getFacing(BlockPos pos)
  {
    EnumFacing[] orderedValues = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
    EnumFacing[] arrayOfEnumFacing1;
    int j = (arrayOfEnumFacing1 = orderedValues).length;
    for (int i = 0; i < j; i++)
    {
      EnumFacing facing = arrayOfEnumFacing1[i];
      
      Entity temp = new EntitySnowball(Minecraft.theWorld);
      posX = (pos.getX() + 0.5D);
      posY = (pos.getY() + 0.5D);
      posZ = (pos.getZ() + 0.5D);
      posX += facing.getDirectionVec().getX() * 0.5D;
      posY += facing.getDirectionVec().getY() * 0.5D;
      posZ += facing.getDirectionVec().getZ() * 0.5D;
      if (Minecraft.thePlayer.canEntityBeSeen(temp)) {
        return facing;
      }
    }
    return null;
  }
  
  public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing)
  {
    Entity temp = new EntitySnowball(Minecraft.theWorld);
    posX = (x + 0.5D);
    posY = (y + 0.5D);
    posZ = (z + 0.5D);
    posX += facing.getDirectionVec().getX() * 0.25D;
    posY += facing.getDirectionVec().getY() * 0.25D;
    posZ += facing.getDirectionVec().getZ() * 0.25D;
    return EntityHelper.getAngles(temp);
  }
  
  public static boolean isOnIce() {
    boolean onIce = false;
    int y = (int)(thePlayerboundingBox.minY - 1.0D);
    for (int x = MathHelper.floor_double(thePlayerboundingBox.minX); x < MathHelper.floor_double(thePlayerboundingBox.maxX) + 1; x++) {
      for (int z = MathHelper.floor_double(thePlayerboundingBox.minZ); z < MathHelper.floor_double(thePlayerboundingBox.maxZ) + 1; z++) {
        Block block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
        if ((block != null) && (!(block instanceof BlockAir)) && (((block instanceof net.minecraft.block.BlockPackedIce)) || ((block instanceof BlockIce)))) {
          onIce = true;
        }
      }
    }
    return onIce;
  }
}
