package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockHelper implements Predicate
{
    private final Block block;
    private static final String __OBFID = "CL_00002020";

    private BlockHelper(Block p_i45654_1_)
    {
        this.block = p_i45654_1_;
    }

    public static BlockHelper forBlock(Block p_177642_0_)
    {
        return new BlockHelper(p_177642_0_);
    }

    public boolean isBlockEqualTo(IBlockState p_177643_1_)
    {
        return p_177643_1_ != null && p_177643_1_.getBlock() == this.block;
    }

    public boolean apply(Object p_apply_1_)
    {
        return this.isBlockEqualTo((IBlockState)p_apply_1_);
    }
    
    public static EnumFacing getFacing(BlockPos pos)
    {
      EnumFacing[] orderedValues = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
      EnumFacing[] arrayOfEnumFacing1;
      int j = (arrayOfEnumFacing1 = orderedValues).length;
      for (int i = 0; i < j; i++)
      {
        EnumFacing facing = arrayOfEnumFacing1[i];
        Entity temp = new EntitySnowball(Minecraft.getMinecraft().theWorld);
        temp.posX = (pos.getX() + 0.5D);
        temp.posY = (pos.getY() + 0.5D);
        temp.posZ = (pos.getZ() + 0.5D);
        Entity entity = temp;
        entity.posX += facing.getDirectionVec().getX() * 0.5D;
        Entity entity2 = temp;
        entity2.posY += facing.getDirectionVec().getY() * 0.5D;
        Entity entity3 = temp;
        entity3.posZ += facing.getDirectionVec().getZ() * 0.5D;
        if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(temp)) {
          return facing;
        }
      }
      return null;
    }
    
    public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing)
    {
      Entity temp = new EntitySnowball(Minecraft.getMinecraft().theWorld);
      temp.posX = (x + 0.5D);
      temp.posY = (y + 0.5D);
      temp.posZ = (z + 0.5D);
      try
      {
        Entity entity = temp;
        entity.posX += facing.getDirectionVec().getX() * 0.25D;
        Entity entity2 = temp;
        entity2.posY += facing.getDirectionVec().getY() * 0.25D;
        Entity entity3 = temp;
        entity3.posZ += facing.getDirectionVec().getZ() * 0.25D;
      }
      catch (NullPointerException localNullPointerException) {}
      return getAngles(temp);
    }
    
    public static float[] getAngles(Entity e)
    {
      return new float[] { getYawChangeToEntity(e) + Minecraft.getMinecraft().thePlayer.rotationYaw, getPitchChangeToEntity(e) + Minecraft.getMinecraft().thePlayer.rotationPitch };
    }
    
    public static float getPitchChangeToEntity(Entity entity)
    {
      double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
      double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
      double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
      return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity);
    }
    
    public static float getYawChangeToEntity(Entity entity)
    {
      double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double yawToEntity;
      if ((deltaZ < 0.0D) && (deltaX < 0.0D))
      {
        yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      }
      else
      {
        if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
          yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
          yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
      }
      return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity));
    }
}
