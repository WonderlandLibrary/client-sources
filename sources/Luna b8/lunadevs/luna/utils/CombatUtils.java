package lunadevs.luna.utils;

import lunadevs.luna.utils.faithsminiutils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class CombatUtils {

	 public static float yaw;
	  public static float tyaw;
	  public static float pitch;
	  public static float tpitch;
	  public static float blockYaw;
	  public static float blockPitch;
	  
	  public static void faceTileEntity(TileEntity target, float p_70625_2_, float p_70625_3_)
	  {
	    double var4 = target.getPos().getX() + 0.5D - Wrapper.mc.thePlayer.posX;
	    double var5 = target.getPos().getZ() + 0.5D - Wrapper.mc.thePlayer.posZ;
	    double var6 = target.getPos().getY() - (Wrapper.mc.thePlayer.posY + 1.0D);
	    double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
	    float var8 = (float)(Math.atan2(var5, var4) * 180.0D / 3.141592653589793D) - 90.0F;
	    float var9 = (float)-(Math.atan2(var6, var7) * 180.0D / 3.141592653589793D);
	    tpitch = changeRotation(Wrapper.mc.thePlayer.rotationPitch, var9, p_70625_3_);
	    tyaw = changeRotation(Wrapper.mc.thePlayer.rotationYaw, var8, p_70625_2_);
	    Wrapper.mc.thePlayer.rotationPitch = tpitch;
	    Wrapper.mc.thePlayer.rotationYaw = tyaw;
	  }
	  
	  public static void faceBlock(BlockPos block, float p_70625_2_, float p_70625_3_)
	  {
	    double var4 = block.getX() - Wrapper.mc.thePlayer.posX;
	    double var5 = block.getZ() - Wrapper.mc.thePlayer.posZ;
	    double var6 = block.getY() + block.getY() - 6.0D - (Wrapper.mc.thePlayer.posY + Wrapper.mc.thePlayer.getEyeHeight());
	    double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
	    float var8 = (float)(Math.atan2(var5, var4) * 180.0D / 3.141592653589793D) - 90.0F;
	    float var9 = (float)-(Math.atan2(var6, var7) * 180.0D / 3.141592653589793D);
	    blockPitch = changeRotation(Wrapper.mc.thePlayer.rotationPitch, var9, p_70625_3_);
	    blockYaw = changeRotation(Wrapper.mc.thePlayer.rotationYaw, var8, p_70625_2_);
	  }
	  
	  public static double angleDifference(double a, double b)
	  {
	    return ((a - b) % 360.0D + 540.0D) % 360.0D - 180.0D;
	  }
	  
	  public static float[] faceTarget(Entity target, float p_70625_2_, float p_70625_3_)
	  {
	    double var4 = target.posX - Wrapper.getPlayer().posX;
	    double var8 = target.posZ - Wrapper.getPlayer().posZ;
	    double var6;
	    if ((target instanceof EntityLivingBase))
	    {
	      EntityLivingBase var10 = (EntityLivingBase)target;
	      var6 = var10.posY + var10.getEyeHeight() - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
	    }
	    else
	    {
	      var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
	    }
	    double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
	    float var12 = (float)(Math.atan2(var8, var4) * 180.0D / 3.141592653589793D) - 90.0F;
	    float var13 = (float)-(Math.atan2(var6 - ((target instanceof EntityPlayer) ? 0.5F : 0.0F), var14) * 180.0D / 3.141592653589793D);
	    float pitch = changeRotation(Wrapper.getPlayer().rotationPitch, var13, p_70625_3_);
	    float yaw = changeRotation(Wrapper.getPlayer().rotationYaw, var12, p_70625_2_);
	    return new float[] { yaw, pitch };
	  }
	  
	  public static float changeRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_)
	  {
	    float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
	    if (var4 > p_70663_3_) {
	      var4 = p_70663_3_;
	    }
	    if (var4 < -p_70663_3_) {
	      var4 = -p_70663_3_;
	    }
	    return p_70663_1_ + var4;
	  }
	  
	  public boolean isCloser(Entity now, Entity first, float error)
	  {
	    if (first.getClass().isAssignableFrom(now.getClass())) {
	      return getDistanceToEntity(Minecraft.getMinecraft().thePlayer, now) < getDistanceToEntity(Minecraft.getMinecraft().thePlayer, first);
	    }
	    return getDistanceToEntity(Minecraft.getMinecraft().thePlayer, now) < getDistanceToEntity(Minecraft.getMinecraft().thePlayer, first) + error;
	  }
	  
	  public float getDistanceToEntity(Entity from, Entity to)
	  {
	    return from.getDistanceToEntity(to);
	  }
	  
	  public static float getDistanceBetweenAngles(float par1, float par2)
	  {
	    float angle = Math.abs(par1 - par2) % 360.0F;
	    if (angle > 180.0F) {
	      angle = 360.0F - angle;
	    }
	    return angle;
	  }
	  
	  public static float[] getRotations(Entity ent)
	  {
	    double x = ent.posX;
	    double z = ent.posZ;
	    double y = ent.boundingBox.maxY - 4.0D;
	    return getRotationFromPosition(x, z, y);
	  }
	  
	  public static float angleDifference(float a, float b)
	  {
	    return ((a - b) % 360.0F + 540.0F) % 360.0F - 180.0F;
	  }
	  
	  public static float[] getRotationFromPosition(double x, double z, double y)
	  {
	    double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
	    double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
	    double yDiff = y - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight();
	    double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
	    float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
	    return new float[] { yaw, pitch };
	  }
	
}
