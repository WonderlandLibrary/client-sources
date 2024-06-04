package yung.purity.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;


public class RotationUtil
{
  public RotationUtil() {}
  
  public static float pitch()
  {
    return mcthePlayer.rotationPitch;
  }
  
  public static void pitch(float pitch) {
    mcthePlayer.rotationPitch = pitch;
  }
  
  public static float yaw()
  {
    return mcthePlayer.rotationYaw;
  }
  
  public static void yaw(float yaw) {
    mcthePlayer.rotationYaw = yaw;
  }
  

  public static float[] getRotations(Entity entity)
  {
    if (entity == null) {
      return null;
    }
    

    double diffX = posX - mcthePlayer.posX;
    double diffZ = posZ - mcthePlayer.posZ;
    
    double diffY;
    double diffY;
    if ((entity instanceof EntityLivingBase))
    {
      EntityLivingBase elb = (EntityLivingBase)entity;
      
      diffY = posY + (elb.getEyeHeight() - 0.4D) - (mcthePlayer.posY + mcthePlayer.getEyeHeight());
    }
    else {
      diffY = (boundingBox.minY + boundingBox.maxY) / 2.0D - (mcthePlayer.posY + mcthePlayer.getEyeHeight());
    }
    

    double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
    return new float[] { yaw, pitch };
  }
  

  public static float getDistanceBetweenAngles(float angle1, float angle2)
  {
    float angle3 = Math.abs(angle1 - angle2) % 360.0F;
    
    if (angle3 > 180.0F) {
      angle3 = 0.0F;
    }
    return angle3;
  }
}
