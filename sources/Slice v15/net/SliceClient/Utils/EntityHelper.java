package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EntityHelper
{
  private static Minecraft mc;
  
  public EntityHelper() {}
  
  public static float[] getRotations(Entity ent)
  {
    double x = posX;
    double z = posZ;
    double y = boundingBox.maxY - 4.5D;
    return getRotationFromPosition(x, z, y);
  }
  
  public static float[] getRotationsforBow(Entity ent)
  {
    double x = posX;
    double z = posZ;
    double y = boundingBox.maxY;
    return getRotationFromPosition(x, z, y + 2.0D);
  }
  
  public static float[] getRotationFromPosition(double x, double z, double y)
  {
    Minecraft.getMinecraft();double xDiff = x - thePlayerposX;
    Minecraft.getMinecraft();double zDiff = z - thePlayerposZ;
    Minecraft.getMinecraft();Minecraft.getMinecraft();double yDiff = y - thePlayerposY + Minecraft.thePlayer.getEyeHeight();
    
    double dist = net.minecraft.util.MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
    return new float[] { yaw, pitch };
  }
  
  public static float getYawChangeToEntity(Entity entity)
  {
    double deltaX = posX - thePlayerposX;
    double deltaZ = posZ - thePlayerposZ;
    
    double yawToEntity1 = 0.0D;
    if ((deltaZ < 0.0D) && (deltaX < 0.0D))
    {
      yawToEntity1 = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
    }
    else
    {
      double yawToEntity11;
      if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
        yawToEntity11 = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else {
        yawToEntity11 = Math.toDegrees(-Math.atan(deltaX / deltaZ));
      }
    }
    return net.minecraft.util.MathHelper.wrapAngleTo180_float(-(thePlayerrotationYaw - (float)yawToEntity1));
  }
  
  public static float getPitchChangeToEntity(Entity entity)
  {
    double deltaX = posX - thePlayerposX;
    double deltaZ = posZ - thePlayerposZ;
    double deltaY = posY - 1.6D + entity.getEyeHeight() - thePlayerposY;
    double distanceXZ = net.minecraft.util.MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
    
    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
    
    return -net.minecraft.util.MathHelper.wrapAngleTo180_float(thePlayerrotationPitch - (float)pitchToEntity);
  }
  
  public static float[] getAngles(Entity e)
  {
    return new float[] { getYawChangeToEntity(e) + thePlayerrotationYaw, getPitchChangeToEntity(e) + thePlayerrotationPitch };
  }
  
  public static double getDirectionCheckVal(Entity e, net.minecraft.util.Vec3 lookVec)
  {
    return directionCheck(thePlayerposX, thePlayerposY + Minecraft.thePlayer.getEyeHeight(), thePlayerposZ, xCoord, yCoord, zCoord, posX, posY + height / 2.0D, posZ, width, height, 5.0D);
  }
  
  private static double directionCheck(double sourceX, double sourceY, double sourceZ, double dirX, double dirY, double dirZ, double targetX, double targetY, double targetZ, double targetWidth, double targetHeight, double precision)
  {
    double dirLength = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
    if (dirLength == 0.0D) {
      dirLength = 1.0D;
    }
    double dX = targetX - sourceX;
    double dY = targetY - sourceY;
    double dZ = targetZ - sourceZ;
    
    double targetDist = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    
    double xPrediction = targetDist * dirX / dirLength;
    double yPrediction = targetDist * dirY / dirLength;
    double zPrediction = targetDist * dirZ / dirLength;
    
    double off = 0.0D;
    
    off += Math.max(Math.abs(dX - xPrediction) - (targetWidth / 2.0D + precision), 0.0D);
    off += Math.max(Math.abs(dZ - zPrediction) - (targetWidth / 2.0D + precision), 0.0D);
    off += Math.max(Math.abs(dY - yPrediction) - (targetHeight / 2.0D + precision), 0.0D);
    if (off > 1.0D) {
      off = Math.sqrt(off);
    }
    return off;
  }
  
  public static float[] getEntityRotations(EntityPlayer player, Entity target)
  {
    double posX = posX - player.posX;
    double posY = posY + target.getEyeHeight() - (player.posY + player.getEyeHeight());
    double posZ = posZ - player.posZ;
    double var14 = net.minecraft.util.MathHelper.sqrt_double(posX * posX + posZ * posZ);
    float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(posY, var14) * 180.0D / 3.141592653589793D);
    return new float[] { yaw, pitch };
  }
}
