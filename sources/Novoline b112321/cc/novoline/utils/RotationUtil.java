package cc.novoline.utils;

import cc.novoline.utils.Timer;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public final class RotationUtil {
   private static Minecraft mc = Minecraft.getInstance();
   private static float c;
   private static float b;

   private RotationUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static float[] a(float[] var0, int var1) {
      float var2 = MathHelper.clamp_float(1.0F - (float)var1 / 100.0F, 0.1F, 1.0F);
      c += (var0[0] - c) * var2;
      b += (var0[1] - b) * var2;
      return new float[]{MathHelper.wrapAngleTo180_float(c), b};
   }

   public static Vec3 getVectorForRotation(float var0, float var1) {
      float var2 = MathHelper.cos(Math.toRadians((double)(-var1)) - 3.1415927410125732D);
      float var3 = MathHelper.sin(Math.toRadians((double)(-var1)) - 3.1415927410125732D);
      float var4 = -MathHelper.cos(Math.toRadians((double)(-var0)));
      float var5 = MathHelper.sin(Math.toRadians((double)(-var0)));
      return new Vec3((double)(var3 * var4), (double)var5, (double)(var2 * var4));
   }

   public static Vec3 getPositionEyes(float var0) {
      return new Vec3(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
   }

   public static float getYawChange(double var0, double var2) {
      Timer.e();
      EntityPlayerSP var5 = mc.player;
      double var6 = var0 - var5.posX;
      double var8 = var2 - var5.posZ;
      double var12 = Math.toDegrees(Math.atan(var8 / var6));
      if(var8 < 0.0D && var6 < 0.0D) {
         double var10 = 90.0D + var12;
      }

      if(var8 < 0.0D && var6 > 0.0D) {
         double var14 = -90.0D + var12;
      }

      double var15 = Math.toDegrees(-Math.atan(var6 / var8));
      return MathHelper.wrapAngleTo180_float(-(var5.rotationYaw - (float)var15));
   }

   public static float[] getRotations(double var0, double var2, double var4) {
      EntityPlayerSP var6 = mc.player;
      double var7 = var0 - var6.posX;
      double var9 = var2 - (var6.posY + (double)var6.getEyeHeight());
      double var11 = var4 - var6.posZ;
      double var13 = (double)MathHelper.sqrt_double(var7 * var7 + var11 * var11);
      float var15 = (float)(Math.atan2(var11, var7) * 180.0D / 3.141592653589793D) - 90.0F;
      float var16 = (float)(-(Math.atan2(var9, var13) * 180.0D / 3.141592653589793D));
      return new float[]{var15, var16};
   }

   public static float[] getRotationsEntity(EntityLivingBase var0) {
      String[] var1 = Timer.e();
      return mc.player.isMoving()?getRotations(var0.posX + ThreadLocalRandom.current().nextDouble(0.03D, -0.03D), var0.posY + (double)var0.getEyeHeight() - 0.4D + ThreadLocalRandom.current().nextDouble(0.07D, -0.07D), var0.posZ + ThreadLocalRandom.current().nextDouble(0.03D, -0.03D)):getRotations(var0.posX, var0.posY + (double)var0.getEyeHeight() - 0.4D, var0.posZ);
   }

   public static float getYawToPoint(double var0, double var2) {
      Minecraft var4 = mc;
      double var5 = var0 - (var4.player.lastTickPosX + (var4.player.posX - var4.player.lastTickPosX) * (double)var4.timer.elapsedPartialTicks);
      double var7 = var2 - (var4.player.lastTickPosZ + (var4.player.posZ - var4.player.lastTickPosZ) * (double)var4.timer.elapsedPartialTicks);
      double var9 = (double)MathHelper.sqrt_double(var5 * var5 + var7 * var7);
      return (float)(Math.atan2(var7, var5) * 180.0D / 3.141592653589793D) - 90.0F;
   }

   public static float a(float var0, float var1, float var2) {
      Timer.e();
      float var4 = var1 - var0;
      if(var4 < -180.0F) {
         var4 += 360.0F;
      }

      if(var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return var0 + var2 * var4;
   }

   public static float getPitchChange(Entity var0, double var1) {
      double var3 = var0.posX - mc.player.posX;
      double var5 = var0.posZ - mc.player.posZ;
      double var7 = var1 - 2.2D + (double)var0.getEyeHeight() - mc.player.posY;
      double var9 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5);
      double var11 = -Math.toDegrees(Math.atan(var7 / var9));
      return -MathHelper.wrapAngleTo180_float(mc.player.rotationPitch - (float)var11) - 2.5F;
   }

   public static float[] c(EntityLivingBase var0) {
      double var1 = var0.posX + (var0.posX - var0.lastTickPosX);
      double var3 = var0.posZ + (var0.posZ - var0.lastTickPosZ);
      double var5 = var0.posY + (double)(var0.getEyeHeight() / 2.0F);
      return a(var1, var3, var5);
   }

   public static float[] a(EntityLivingBase var0) {
      double var1 = var0.posX;
      double var3 = var0.posZ;
      double var5 = var0.posY + (double)(var0.getEyeHeight() / 2.0F);
      return a(var1, var3, var5);
   }

   public static float[] a(double var0, double var2, double var4) {
      double var6 = var0 - Minecraft.getInstance().player.posX;
      double var8 = var2 - Minecraft.getInstance().player.posZ;
      double var10 = var4 - Minecraft.getInstance().player.posY - 1.2D;
      double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var8 * var8);
      float var14 = (float)(Math.atan2(var8, var6) * 180.0D / 3.141592653589793D) - 90.0F;
      float var15 = (float)(-(Math.atan2(var10, var12) * 180.0D / 3.141592653589793D));
      return new float[]{var14, var15};
   }

   public static float[] aimAtLocation(double var0, double var2, double var4) {
      double var6 = var0 - mc.player.posX;
      double var8 = var2 - mc.player.posY;
      double var10 = var4 - mc.player.posZ;
      double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var10 * var10);
      return new float[]{(float)(Math.atan2(var10, var6) * 180.0D / 3.141592653589793D) - 90.0F, (float)(-(Math.atan2(var8, var12) * 180.0D / 3.141592653589793D))};
   }

   public static float[] b(EntityLivingBase var0) {
      return null;
   }

   public static float[] getAngles(BlockPos var0) {
      double var1 = (double)var0.getX();
      double var3 = (double)var0.getY();
      double var5 = (double)var0.getZ();
      EntityPlayerSP var7 = mc.player;
      double var8 = var1 - var7.posX;
      double var10 = var3 - (var7.posY + (double)var7.getEyeHeight());
      double var12 = var5 - var7.posZ;
      double var14 = (double)MathHelper.sqrt_double(var8 * var8 + var12 * var12);
      float var16 = (float)(Math.atan2(var12, var8) * 180.0D / 3.141592653589793D) - 90.0F;
      float var17 = (float)(-(Math.atan2(var10, var14) * 180.0D / 3.141592653589793D));
      return new float[]{var7.rotationYaw + MathHelper.wrapAngleTo180_float(var16 - var7.rotationYaw), var7.rotationPitch + MathHelper.wrapAngleTo180_float(var17 - var7.rotationPitch)};
   }

   public static float[] d(double var0, double var2, double var4) {
      EntityPlayerSP var6 = mc.player;
      double var7 = var0 - var6.posX;
      double var9 = var2 - (var6.posY + (double)var6.getEyeHeight());
      double var11 = var4 - var6.posZ;
      double var13 = (double)MathHelper.sqrt_double(var7 * var7 + var11 * var11);
      float var15 = (float)(Math.atan2(var11, var7) * 180.0D / 3.141592653589793D) - 90.0F;
      float var16 = (float)(-(Math.atan2(var9, var13) * 180.0D / 3.141592653589793D));
      return new float[]{var6.rotationYaw + MathHelper.wrapAngleTo180_float(var15 - var6.rotationYaw), var6.rotationPitch + MathHelper.wrapAngleTo180_float(var16 - var6.rotationPitch)};
   }

   public static float[] getAngles(Vec3 var0) {
      double var1 = var0.getX();
      double var3 = var0.getY();
      double var5 = var0.getZ();
      EntityPlayerSP var7 = mc.player;
      double var8 = var1 - var7.posX;
      double var10 = var3 - (var7.posY + (double)var7.getEyeHeight());
      double var12 = var5 - var7.posZ;
      double var14 = (double)MathHelper.sqrt_double(var8 * var8 + var12 * var12);
      float var16 = (float)(Math.atan2(var12, var8) * 180.0D / 3.141592653589793D) - 90.0F;
      float var17 = (float)(-(Math.atan2(var10, var14) * 180.0D / 3.141592653589793D));
      return new float[]{MathHelper.wrapAngleTo180_float(var7.rotationYaw + var16 - var7.rotationYaw), var7.rotationPitch + MathHelper.wrapAngleTo180_float(var17 - var7.rotationPitch)};
   }

   public static MovingObjectPosition b(float var0, float var1) {
      float var2 = mc.player.getCollisionBorderSize();
      Vec3 var3 = getPositionEyes(1.0F);
      Vec3 var4 = getVectorForRotation(var1, var0);
      Vec3 var5 = var3.addVector(var4.xCoord * 3.0D, var4.yCoord * 3.0D, var4.zCoord * 3.0D);
      return mc.world.rayTraceBlocks(var3, var5, false, false, false);
   }

   public static float b(float var0) {
      return MathHelper.wrapAngleTo180_float((float)((double)var0 + MathHelper.incValue(ThreadLocalRandom.current().nextDouble(0.5D, 3.0D), 0.2D)));
   }

   public static float a(float var0) {
      String[] var1 = Timer.e();
      return (float)(var0 > 87.0F?(double)var0 - MathHelper.incValue(ThreadLocalRandom.current().nextDouble(0.5D, 3.0D), 0.2D):(double)var0 + MathHelper.incValue(ThreadLocalRandom.current().nextDouble(0.5D, 3.0D), 0.2D));
   }

   public static float c(float var0) {
      Timer.e();
      var0 = var0 % 360.0F;
      if(var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if(var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static float getDistanceBetweenAngles(float var0, float var1) {
      Timer.e();
      float var3 = Math.abs(var0 - var1) % 360.0F;
      return var3 > 180.0F?360.0F - var3:var3;
   }

   public static double[] getDistance(double var0, double var2, double var4) {
      double var6 = (double)MathHelper.sqrt_double(var0 * var0 + var2 * var2);
      double var8 = Math.atan2(var2, var0) * 180.0D / 3.141592653589793D - 90.0D;
      double var10 = -(Math.atan2(var4, var6) * 180.0D / 3.141592653589793D);
      return new double[]{(double)(mc.player.rotationYaw + MathHelper.wrapAngleTo180_float((float)(var8 - (double)mc.player.rotationYaw))), (double)(mc.player.rotationPitch + MathHelper.wrapAngleTo180_float((float)(var10 - (double)mc.player.rotationPitch)))};
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
