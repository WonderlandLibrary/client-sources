package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;




public class RotationUtil
{
  private static Minecraft mc = ;
  
  public RotationUtil() {}
  
  public static float[] getYawAndPitch(Entity entity) { double posX = entity.posX;
    double n = posX - thePlayerposX;
    double posZ = entity.posZ;
    double n2 = posZ - thePlayerposZ;
    return new float[] { (float)(Math.atan2(n2, n) * 180.0D / 3.141592653589793D) - 90.0F, 
      (float)(Math.atan2(thePlayerposY + 0.12D - (posY + 1.82D), MathHelper.sqrt_double(n + n2)) * 
      180.0D / 3.141592653589793D) };
  }
  
  public static float getDistanceBetweenAngles(float paramFloat)
  {
    float n = Math.abs(paramFloat - thePlayerrotationYaw) % 360.0F;
    if (n > 180.0F) {
      n = 360.0F - n;
    }
    return n;
  }
  
  public static float[] getEntityRotations(EntityPlayer player, Entity target)
  {
    double posX = posX - player.posX;
    double posY = posY + target.getEyeHeight() - (player.posY + player.getEyeHeight());
    double posZ = posZ - player.posZ;
    double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
    float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
    float pitch = (float)-(Math.atan2(posY, var14) * 180.0D / 3.141592653589793D);
    return new float[] { yaw, pitch };
  }
}
