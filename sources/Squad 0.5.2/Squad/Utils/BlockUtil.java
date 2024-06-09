package Squad.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockUtil
{
  public static boolean isOnLiquid()
  {
    boolean onLiquid = false;
    Minecraft.getMinecraft().getMinecraft();
    if (getBlockAtPosC(Minecraft.getMinecraft().thePlayer, 0.30000001192092896D, 0.10000000149011612D, 0.30000001192092896D).getMaterial().isLiquid())
    {
      Minecraft.getMinecraft().getMinecraft();
      if (getBlockAtPosC(Minecraft.getMinecraft().thePlayer, -0.30000001192092896D, 0.10000000149011612D, -0.30000001192092896D).getMaterial().isLiquid()) {
        onLiquid = true;
      }
    }
    return onLiquid;
  }
  
  public static float getPlayerBlockDistance(BlockPos blockPos)
  {
    return getPlayerBlockDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
  }
  
  public static float getPlayerBlockDistance(double posX, double posY, double posZ)
  {
    Minecraft.getMinecraft().getMinecraft();float xDiff = (float)(Minecraft.getMinecraft().thePlayer.posX - posX);
    Minecraft.getMinecraft().getMinecraft();float yDiff = (float)(Minecraft.getMinecraft().thePlayer.posY - posY);
    Minecraft.getMinecraft().getMinecraft();float zDiff = (float)(Minecraft.getMinecraft().thePlayer.posZ - posZ);
    return getBlockDistance(xDiff, yDiff, zDiff);
  }
  
  public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing)
  {
    Minecraft.getMinecraft().getMinecraft();EntitySnowball temp = new EntitySnowball(Minecraft.getMinecraft().theWorld);
    temp.posX = (x + 0.5D);
    temp.posY = (y + 0.5D);
    temp.posZ = (z + 0.5D);
    temp.posX += facing.getDirectionVec().getX() * 0.25D;
    temp.posY += facing.getDirectionVec().getY() * 0.25D;
    temp.posZ += facing.getDirectionVec().getZ() * 0.25D;
    return getAngles(temp);
  }
  
  public static float[] getAngles(Entity e)
  {
    float[] tmp3_1 = new float[2];Minecraft.getMinecraft().getMinecraft();tmp3_1[0] = (getYawChangeToEntity(e) + Minecraft.getMinecraft().thePlayer.rotationYaw); float[] tmp21_3 = tmp3_1;Minecraft.getMinecraft().getMinecraft();tmp21_3[1] = (getPitchChangeToEntity(e) + Minecraft.getMinecraft().thePlayer.rotationPitch);return tmp21_3;
  }
  
  public static float getYawChangeToEntity(Entity entity)
  {
    Minecraft.getMinecraft().getMinecraft();double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
    Minecraft.getMinecraft().getMinecraft();double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
    double yawToEntity = (deltaZ < 0.0D) && (deltaX > 0.0D) ? -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0D) && (deltaX < 0.0D) ? 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ));
    Minecraft.getMinecraft().getMinecraft();return MathHelper.wrapAngleTo180_float(-Minecraft.getMinecraft().thePlayer.rotationYaw - (float)yawToEntity);
  }
  
  public static float getPitchChangeToEntity(Entity entity)
  {
    Minecraft.getMinecraft().getMinecraft();double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
    Minecraft.getMinecraft().getMinecraft();double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
    Minecraft.getMinecraft().getMinecraft();double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
    double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
    Minecraft.getMinecraft().getMinecraft();return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float)pitchToEntity);
  }
  
  public static boolean canSeeBlock(int x, int y, int z)
  {
    if (getFacing(new BlockPos(x, y, z)) != null) {
      return true;
    }
    return false;
  }
  
  public static EnumFacing getFacing(BlockPos pos)
  {
    EnumFacing[] orderedValues;
    EnumFacing[] arrenumFacing = orderedValues = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
    int n = arrenumFacing.length;
    int n2 = 0;
    while (n2 < n)
    {
      EnumFacing facing = arrenumFacing[n2];
      Minecraft.getMinecraft().getMinecraft();EntitySnowball temp = new EntitySnowball(Minecraft.getMinecraft().theWorld);
      temp.posX = (pos.getX() + 0.5D);
      temp.posY = (pos.getY() + 0.5D);
      temp.posZ = (pos.getZ() + 0.5D);
      temp.posX += facing.getDirectionVec().getX() * 0.5D;
      temp.posY += facing.getDirectionVec().getY() * 0.5D;
      temp.posZ += facing.getDirectionVec().getZ() * 0.5D;
      Minecraft.getMinecraft().getMinecraft();
      if (Minecraft.getMinecraft().thePlayer.canEntityBeSeen(temp)) {
        return facing;
      }
      n2++;
    }
    return null;
  }
  
  public static float getBlockDistance(float xDiff, float yDiff, float zDiff)
  {
    return MathHelper.sqrt_float((xDiff - 0.5F) * (xDiff - 0.5F) + (yDiff - 0.5F) * (yDiff - 0.5F) + (zDiff - 0.5F) * (zDiff - 0.5F));
  }
  
  public static boolean isOnLadder()
  {
    Minecraft.getMinecraft().getMinecraft();
    if (Minecraft.getMinecraft().thePlayer == null) {
      return false;
    }
    boolean onLadder = false;
    Minecraft.getMinecraft().getMinecraft();int y = (int)Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D).minY;
    Minecraft.getMinecraft().getMinecraft();int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minX);
    do
    {
      Minecraft.getMinecraft().getMinecraft();int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minZ);
      do
      {
        Block block = getBlock(x, y, z);
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if ((!(block instanceof BlockLadder)) && (!(block instanceof BlockVine))) {
            return false;
          }
          onLadder = true;
        }
        z++;Minecraft.getMinecraft().getMinecraft();
      } while (z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxZ) + 1);
      x++;Minecraft.getMinecraft().getMinecraft();
    } while (x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxX) + 1);
    if (!onLadder)
    {
      Minecraft.getMinecraft().getMinecraft();
      if (!Minecraft.getMinecraft().thePlayer.isOnLadder()) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean isOnIce()
  {
    Minecraft.getMinecraft().getMinecraft();
    if (Minecraft.getMinecraft().thePlayer == null) {
      return false;
    }
    boolean onIce = false;
    Minecraft.getMinecraft().getMinecraft();int y = (int)Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
    Minecraft.getMinecraft().getMinecraft();int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minX);
    do
    {
      Minecraft.getMinecraft().getMinecraft();int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minZ);
      do
      {
        Block block = getBlock(x, y, z);
        if ((block != null) && (!(block instanceof BlockAir)))
        {
          if ((!(block instanceof BlockIce)) && (!(block instanceof BlockPackedIce))) {
            return false;
          }
          onIce = true;
        }
        z++;Minecraft.getMinecraft().getMinecraft();
      } while (z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxZ) + 1);
      x++;Minecraft.getMinecraft().getMinecraft();
    } while (x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxX) + 1);
    return onIce;
  }
  
  public boolean isInsideBlock()
  {
    Minecraft.getMinecraft().getMinecraft();int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX);
    do
    {
      Minecraft.getMinecraft().getMinecraft();int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY);
      do
      {
        Minecraft.getMinecraft().getMinecraft();int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ);
        do
        {
          Minecraft.getMinecraft().getMinecraft();Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
          if ((block != null) && (!(block instanceof BlockAir)))
          {
            Minecraft.getMinecraft().getMinecraft();Minecraft.getMinecraft().getMinecraft();
            AxisAlignedBB boundingBox;
            if ((boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)))) != null)
            {
              Minecraft.getMinecraft().getMinecraft();
              if (Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox)) {
                return true;
              }
            }
          }
          z++;Minecraft.getMinecraft().getMinecraft();
        } while (z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1);
        y++;Minecraft.getMinecraft().getMinecraft();
      } while (y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1);
      x++;Minecraft.getMinecraft().getMinecraft();
    } while (x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1);
    return false;
  }
  
  public static boolean isBlockUnderPlayer(Material material, float height)
  {
    Minecraft.getMinecraft().getMinecraft();
    if (getBlockAtPosC(Minecraft.getMinecraft().thePlayer, 0.3100000023841858D, height, 0.3100000023841858D).getMaterial() == material)
    {
      Minecraft.getMinecraft().getMinecraft();
      if (getBlockAtPosC(Minecraft.getMinecraft().thePlayer, -0.3100000023841858D, height, -0.3100000023841858D).getMaterial() == material)
      {
        Minecraft.getMinecraft().getMinecraft();
        if (getBlockAtPosC(Minecraft.getMinecraft().thePlayer, -0.3100000023841858D, height, 0.3100000023841858D).getMaterial() == material)
        {
          Minecraft.getMinecraft().getMinecraft();
          if (getBlockAtPosC(Minecraft.getMinecraft().thePlayer, 0.3100000023841858D, height, -0.3100000023841858D).getMaterial() == material) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z)
  {
    return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
  }
  
  public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height)
  {
    return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ));
  }
  
  public static Block getBlockAbovePlayer(EntityPlayer inPlayer, double height)
  {
    return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + inPlayer.height + height, inPlayer.posZ));
  }
  
  public static Block getBlock(int x, int y, int z)
  {
    Minecraft.getMinecraft().getMinecraft();return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
  }
  
  public static Block getBlock(BlockPos pos)
  {
    Minecraft.getMinecraft().getMinecraft();return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
  }
}
