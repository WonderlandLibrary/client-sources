package net.augustus.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import net.augustus.utils.interfaces.MC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class RotationUtil implements MC {
   public static double ACCURATE_ROTATION_YAW_LEVEL;
   public static double ACCURATE_ROTATION_YAW_VL;
   public static double ACCURATE_ROTATION_PITCH_LEVEL;
   public static double ACCURATE_ROTATION_PITCH_VL;
   public static double ACCURATE_ROTATION_YAW_LEVEL1;
   public static double ACCURATE_ROTATION_YAW_VL1;
   public static double ACCURATE_ROTATION_PITCH_LEVEL1;
   public static double ACCURATE_ROTATION_PITCH_VL1;
   private double lastX;
   private double lastY;
   private double lastZ;
   private Entity lastTarget;
   private double x;
   private double y;
   private double z;
   private double lastAngle;

   public static Vec3 getBestHitVec(Entity entity) {
      Vec3 positionEyes = mc.thePlayer.getPositionEyes(1.0F);
      float f11 = entity.getCollisionBorderSize();
      AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand((double)f11, (double)f11, (double)f11);
      double ex = MathHelper.clamp_double(positionEyes.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
      double ey = MathHelper.clamp_double(positionEyes.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
      double ez = MathHelper.clamp_double(positionEyes.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);
      return new Vec3(ex, ey, ez);
   }

   public static Vec3 getBestHitVec(Entity entity, float partialTicks) {
      Vec3 positionEyes = mc.thePlayer.getPositionEyes(partialTicks);
      float f11 = entity.getCollisionBorderSize();
      double x = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
      double y = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks;
      double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
      float width = entity.width / 2.0F;
      AxisAlignedBB entityBoundingBox = new AxisAlignedBB(
         x - (double)width, y, z - (double)width, x + (double)width, y + (double)entity.height, z + (double)width
      );
      double ex = MathHelper.clamp_double(positionEyes.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
      double ey = MathHelper.clamp_double(positionEyes.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
      double ez = MathHelper.clamp_double(positionEyes.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);
      return new Vec3(ex, ey, ez);
   }

   public static float rotateStaticYaw(float currentYaw, float calcYaw) {
      float yaw = updateRotation(currentYaw, calcYaw, 180.0F);
      if (yaw == currentYaw) {
         return currentYaw;
      } else {
         if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
            mc.gameSettings.mouseSensitivity = 0.47887325F;
         }

         float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float f2 = f1 * f1 * f1 * 8.0F;
         int deltaX = (int)((6.667 * (double)yaw - 6.6666667 * (double)currentYaw) / (double)f2);
         float f5 = (float)deltaX * f2;
         return (float)((double)currentYaw + (double)f5 * 0.15);
      }
   }

   public static float updateRotation(float current, float calc, float maxDelta) {
      float f = MathHelper.wrapAngleTo180_float(calc - current);
      if (f > maxDelta) {
         f = maxDelta;
      }

      if (f < -maxDelta) {
         f = -maxDelta;
      }

      return current + f;
   }

   public float[] basicRotation(Entity entity, float currentYaw, float currentPitch, boolean random) {
      Vec3 ePos = getBestHitVec(entity);
      double x = ePos.xCoord - mc.thePlayer.posX;
      double y = ePos.yCoord - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = ePos.zCoord - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(currentYaw, calcYaw, 180.0F);
      float pitch = updateRotation(currentPitch, calcPitch, 180.0F);
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian());
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian());
      }

      return mouseSens(yaw, pitch, currentYaw, currentPitch);
   }

   public float[] middleRotation(Entity entity, float currentYaw, float currentPitch, boolean random) {
      double x = entity.posX - mc.thePlayer.posX;
      double y = entity.posY + (double)entity.getEyeHeight() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = entity.posZ - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(currentYaw, calcYaw, 180.0F);
      float pitch = updateRotation(currentPitch, calcPitch, 180.0F);
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian());
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian());
      }

      return mouseSens(yaw, pitch, currentYaw, currentPitch);
   }

   public float[] positionRotation(double posX, double posY, double posZ, float currentYaw, float currentPitch, boolean random) {
      double x = posX - mc.thePlayer.posX;
      double y = posY + 1.53 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = posZ - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(currentYaw, calcYaw, 180.0F);
      float pitch = updateRotation(currentPitch, calcPitch, 180.0F);
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian());
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian());
      }

      return mouseSens(yaw, pitch, currentYaw, currentPitch);
   }

   public float[] positionRotation(
      double posX, double posY, double posZ, float currentYaw, float currentPitch, float yawSpeed, float pitchSpeed, boolean random
   ) {
      double x = posX - mc.thePlayer.posX;
      double y = posY + 1.53 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = posZ - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(currentYaw, calcYaw, yawSpeed);
      float pitch = updateRotation(currentPitch, calcPitch, pitchSpeed);
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian());
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian());
      }

      return mouseSens(yaw, pitch, currentYaw, currentPitch);
   }

   public static float[] positionRotation(double posX, double posY, double posZ, float[] lastRots, float yawSpeed, float pitchSpeed, boolean random) {
      double x = posX - mc.thePlayer.posX;
      double y = posY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = posZ - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(lastRots[0], calcYaw, yawSpeed);
      float pitch = updateRotation(lastRots[1], calcPitch, pitchSpeed);
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian());
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian());
      }

      return new float[]{yaw, pitch};
   }

   public static float[] positionRotation(
      double posX, double posY, double posZ, float[] lastRots, float yawSpeed, float pitchSpeed, boolean random, float partialTicks
   ) {
      double px = mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double)partialTicks;
      double py = mc.thePlayer.prevPosY + (mc.thePlayer.posY - mc.thePlayer.prevPosY) * (double)partialTicks;
      double pz = mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double)partialTicks;
      double x = posX - px;
      double y = posY - (py + (double)mc.thePlayer.getEyeHeight());
      double z = posZ - pz;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(lastRots[0], calcYaw, yawSpeed);
      float pitch = updateRotation(lastRots[1], calcPitch, pitchSpeed);
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian());
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian());
      }

      return new float[]{yaw, pitch};
   }

   public float[] faceEntityCustom(
      Entity entity,
      float yawSpeed,
      float pitchSpeed,
      float currentYaw,
      float currentPitch,
      String randomMode,
      boolean interpolateRotation,
      boolean smartAim,
      boolean stopOnTarget,
      float randomStrength,
      Vec3 best,
      boolean throughWalls,
      boolean advancedRots,
      boolean heuristics,
      boolean intave,
      boolean bestHitVec
   ) {
      if (smartAim && !throughWalls) {
         double ePosX = best.xCoord;
         double ePosY = best.yCoord;
         double ePosZ = best.zCoord;
         if (heuristics) {
            double[] xyz = this.heuristics(entity, new double[]{ePosX, ePosY, ePosZ});
            ePosX = xyz[0];
            ePosY = xyz[1];
            ePosZ = xyz[2];
         }

         this.x = ePosX - mc.thePlayer.posX;
         this.y = ePosY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
         this.z = ePosZ - mc.thePlayer.posZ;
      } else {
         double ex = entity.posX;
         double ey = entity.posY + (double)entity.getEyeHeight();
         double ez = entity.posZ;
         if (bestHitVec) {
            Vec3 entityVec = getBestHitVec(entity);
            ex = entityVec.xCoord;
            ey = entityVec.yCoord;
            ez = entityVec.zCoord;
         }

         if (heuristics) {
            double[] xyz = this.heuristics(entity, new double[]{ex, ey, ez});
            ex = xyz[0];
            ey = xyz[1];
            ez = xyz[2];
         }

         this.x = ex - mc.thePlayer.posX;
         this.y = ey - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
         this.z = ez - mc.thePlayer.posZ;
      }

      float calcYaw = (float)(Math.atan2(this.z, this.x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(this.y, (double)MathHelper.sqrt_double(this.x * this.x + this.z * this.z)) * 180.0 / Math.PI));
      if (stopOnTarget && mc.objectMouseOver != null && mc.objectMouseOver.entityHit == entity) {
         yawSpeed = 0.0F;
         pitchSpeed = 0.0F;
      }

      double diffYaw = (double)MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
      double diffPitch = (double)MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
      float pitch;
      float yaw;
      if (interpolateRotation) {
         yaw = this.interpolateRotation(currentYaw, calcYaw, yawSpeed / RandomUtil.nextFloat(170.0F, 180.0F));
         pitch = this.interpolateRotation(currentPitch, calcPitch, pitchSpeed / RandomUtil.nextFloat(170.0F, 180.0F));
      } else if (heuristics) {
         float[] f = this.testRots(currentYaw, currentPitch, calcYaw, calcPitch, entity, yawSpeed, pitchSpeed, best, smartAim, throughWalls);
         yaw = f[0];
         pitch = f[1];
      } else {
         yaw = updateRotation(currentYaw, calcYaw, yawSpeed);
         pitch = updateRotation(currentPitch, calcPitch, pitchSpeed);
      }

      switch(randomMode) {
         case "Basic":
            yaw = (float)(
               (double)yaw
                  + (
                     intave
                        ? (double)RandomUtil.nextSecureFloat(1.0, 2.0) * Math.sin((double)pitch * Math.PI) * (double)randomStrength
                        : ThreadLocalRandom.current().nextGaussian() * (double)randomStrength
                  )
            );
            pitch = (float)(
               (double)pitch
                  + (
                     intave
                        ? (double)RandomUtil.nextSecureFloat(1.0, 2.0) * Math.sin((double)yaw * Math.PI) * (double)randomStrength
                        : ThreadLocalRandom.current().nextGaussian() * (double)randomStrength
                  )
            );
            break;
         case "OnlyRotation":
            if (!((double)(-yawSpeed) <= diffYaw)
               || !(diffYaw <= (double)yawSpeed)
               || !((double)(-pitchSpeed) <= diffPitch)
               || !(diffPitch <= (double)pitchSpeed)) {
               yaw = (float)(
                  (double)yaw
                     + (
                        intave
                           ? (double)RandomUtil.nextSecureFloat(1.0, 2.0) * Math.sin((double)pitch * Math.PI) * (double)randomStrength
                           : ThreadLocalRandom.current().nextGaussian() * (double)randomStrength
                     )
               );
               pitch = (float)(
                  (double)pitch
                     + (
                        intave
                           ? (double)RandomUtil.nextSecureFloat(1.0, 2.0) * Math.sin((double)yaw * Math.PI) * (double)randomStrength
                           : ThreadLocalRandom.current().nextGaussian() * (double)randomStrength
                     )
               );
            }
            break;
         case "Doubled":
            float random1 = RandomUtil.nextSecureFloat((double)(-randomStrength), (double)randomStrength);
            float random2 = RandomUtil.nextSecureFloat((double)(-randomStrength), (double)randomStrength);
            float random3 = RandomUtil.nextSecureFloat((double)(-randomStrength), (double)randomStrength);
            float random4 = RandomUtil.nextSecureFloat((double)(-randomStrength), (double)randomStrength);
            yaw += RandomUtil.nextSecureFloat((double)Math.min(random1, random2), (double)Math.max(random1, random2));
            pitch += RandomUtil.nextSecureFloat((double)Math.min(random3, random4), (double)Math.max(random3, random4));
      }

      if (advancedRots) {
         pitch = (float)((double)pitch + Math.sin(0.06981317007977318 * (double)(updateRotation(currentYaw, calcYaw, 180.0F) - yaw)) * 8.0);
      }

      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      int deltaX = (int)((6.667 * (double)yaw - 6.667 * (double)currentYaw) / (double)f2);
      int deltaY = (int)((6.667 * (double)pitch - 6.667 * (double)currentPitch) / (double)f2) * -1;
      float f5 = (float)deltaX * f2;
      float f3 = (float)deltaY * f2;
      yaw = (float)((double)currentYaw + (double)f5 * 0.15);
      float f4 = (float)((double)currentPitch - (double)f3 * 0.15);
      pitch = MathHelper.clamp_float(f4, -90.0F, 90.0F);
      this.lastX = entity.posX;
      this.lastY = entity.posY;
      this.lastZ = entity.posZ;
      if (entity instanceof EntityLivingBase) {
         this.checkRotationAnalysis((EntityLivingBase)entity, yaw, pitch, currentYaw, currentPitch);
      }

      return new float[]{yaw, pitch};
   }

   private double[] heuristics(Entity entity, double[] xyz) {
      double boxSize = 0.2;
      float f11 = entity.getCollisionBorderSize();
      double minX = MathHelper.clamp_double(
         xyz[0] - boxSize, entity.getEntityBoundingBox().minX - (double)f11, entity.getEntityBoundingBox().maxX + (double)f11
      );
      double minY = MathHelper.clamp_double(
         xyz[1] - boxSize, entity.getEntityBoundingBox().minY - (double)f11, entity.getEntityBoundingBox().maxY + (double)f11
      );
      double minZ = MathHelper.clamp_double(
         xyz[2] - boxSize, entity.getEntityBoundingBox().minZ - (double)f11, entity.getEntityBoundingBox().maxZ + (double)f11
      );
      double maxX = MathHelper.clamp_double(
         xyz[0] + boxSize, entity.getEntityBoundingBox().minX - (double)f11, entity.getEntityBoundingBox().maxX + (double)f11
      );
      double maxY = MathHelper.clamp_double(
         xyz[1] + boxSize, entity.getEntityBoundingBox().minY - (double)f11, entity.getEntityBoundingBox().maxY + (double)f11
      );
      double maxZ = MathHelper.clamp_double(
         xyz[2] + boxSize, entity.getEntityBoundingBox().minZ - (double)f11, entity.getEntityBoundingBox().maxZ + (double)f11
      );
      xyz[0] = MathHelper.clamp_double(xyz[0] + RandomUtil.randomSin(), minX, maxX);
      xyz[1] = MathHelper.clamp_double(xyz[1] + RandomUtil.randomSin(), minY, maxY);
      xyz[2] = MathHelper.clamp_double(xyz[2] + RandomUtil.randomSin(), minZ, maxZ);
      return xyz;
   }

   public static int[] getDxDy(float yaw, float pitch, float lastYaw, float lastPitch) {
      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      int deltaX = (int)Math.round((6.667 * (double)yaw - 6.667 * (double)lastYaw) / (double)f2);
      int deltaY = (int)Math.round((6.667 * (double)pitch - 6.667 * (double)lastPitch) / (double)f2) * -1;
      return new int[]{deltaX, deltaY};
   }

   public static float[] dxToRots(int deltaX, int deltaY, float lastYaw, float lastPitch) {
      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      float f5 = (float)deltaX * f2;
      float f3 = (float)deltaY * f2;
      float yaw = (float)((double)lastYaw + (double)f5 * 0.15);
      float f4 = (float)((double)lastPitch - (double)f3 * 0.15);
      float pitch = MathHelper.clamp_float(f4, -90.0F, 90.0F);
      return new float[]{yaw, pitch};
   }

   public static float[] mouseSens(float yaw, float pitch, float lastYaw, float lastPitch) {
      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      if (yaw == lastYaw && pitch == lastPitch) {
         return new float[]{yaw, pitch};
      } else {
         float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float f2 = f1 * f1 * f1 * 8.0F;
         int deltaX = (int)((6.667 * (double)yaw - 6.667 * (double)lastYaw) / (double)f2);
         int deltaY = (int)((6.667 * (double)pitch - 6.667 * (double)lastPitch) / (double)f2) * -1;
         float f5 = (float)deltaX * f2;
         float f3 = (float)deltaY * f2;
         yaw = (float)((double)lastYaw + (double)f5 * 0.15);
         float f4 = (float)((double)lastPitch - (double)f3 * 0.15);
         pitch = MathHelper.clamp_float(f4, -90.0F, 90.0F);
         return new float[]{yaw, pitch};
      }
   }

   public float rotateToYaw(float yawSpeed, float currentYaw, float calcYaw) {
      float yaw = updateRotation(currentYaw, calcYaw, yawSpeed + RandomUtil.nextFloat(0.0F, 15.0F));
      double diffYaw = (double)MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
      if (!((double)(-yawSpeed) <= diffYaw) || !(diffYaw <= (double)yawSpeed)) {
         yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0F, 2.0F) * Math.sin((double)mc.thePlayer.rotationPitch * Math.PI));
      }

      if (yaw == currentYaw) {
         return currentYaw;
      } else {
         if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
            mc.gameSettings.mouseSensitivity = 0.47887325F;
         }

         float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float f2 = f1 * f1 * f1 * 8.0F;
         int deltaX = (int)((6.667 * (double)yaw - 6.666666666666667 * (double)currentYaw) / (double)f2);
         float f5 = (float)deltaX * f2;
         return (float)((double)currentYaw + (double)f5 * 0.15);
      }
   }

   public float rotateToYaw(float yawSpeed, float[] currentRots, float calcYaw) {
      float yaw = updateRotation(currentRots[0], calcYaw, yawSpeed + RandomUtil.nextFloat(0.0F, 15.0F));
      if (yaw != calcYaw) {
         yaw = (float)((double)yaw + (double)RandomUtil.nextFloat(1.0F, 2.0F) * Math.sin((double)currentRots[1] * Math.PI));
      }

      if (yaw == currentRots[0]) {
         return currentRots[0];
      } else {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian() * 0.2);
         if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
            mc.gameSettings.mouseSensitivity = 0.47887325F;
         }

         float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float f2 = f1 * f1 * f1 * 8.0F;
         int deltaX = (int)((6.667 * (double)yaw - 6.6666667 * (double)currentRots[0]) / (double)f2);
         float f5 = (float)deltaX * f2;
         return (float)((double)currentRots[0] + (double)f5 * 0.15);
      }
   }

   public float rotateToPitch(float pitchSpeed, float currentPitch, float calcPitch) {
      float pitch = updateRotation(currentPitch, calcPitch, pitchSpeed + RandomUtil.nextFloat(0.0F, 15.0F));
      if (pitch != calcPitch) {
         pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0F, 2.0F) * Math.sin((double)mc.thePlayer.rotationYaw * Math.PI));
      }

      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      int deltaY = (int)((6.667 * (double)pitch - 6.666667 * (double)currentPitch) / (double)f2) * -1;
      float f3 = (float)deltaY * f2;
      float f4 = (float)((double)currentPitch - (double)f3 * 0.15);
      return MathHelper.clamp_float(f4, -90.0F, 90.0F);
   }

   public float rotateToPitch(float pitchSpeed, float[] currentRots, float calcPitch) {
      float pitch = updateRotation(currentRots[1], calcPitch, pitchSpeed + RandomUtil.nextFloat(0.0F, 15.0F));
      if (pitch != calcPitch) {
         pitch = (float)((double)pitch + (double)RandomUtil.nextFloat(1.0F, 2.0F) * Math.sin((double)currentRots[0] * Math.PI));
      }

      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      int deltaY = (int)((6.667 * (double)pitch - 6.666667 * (double)currentRots[1]) / (double)f2) * -1;
      float f3 = (float)deltaY * f2;
      float f4 = (float)((double)currentRots[1] - (double)f3 * 0.15);
      return MathHelper.clamp_float(f4, -90.0F, 90.0F);
   }

   public float[] backRotate(float yawSpeed, float pitchSpeed, float currentYaw, float currentPitch, float calcYaw, float calcPitch) {
      float yaw = updateRotation(currentYaw, calcYaw + RandomUtil.nextFloat(-2.0F, 2.0F), 20.0F + RandomUtil.nextFloat(0.0F, 15.0F));
      float pitch = updateRotation(currentPitch, calcPitch + RandomUtil.nextFloat(-2.0F, 2.0F), 10.0F + RandomUtil.nextFloat(0.0F, 15.0F));
      yaw = (float)((double)yaw + ThreadLocalRandom.current().nextGaussian() * 0.6);
      pitch = (float)((double)pitch + ThreadLocalRandom.current().nextGaussian() * 0.6);
      if ((double)mc.gameSettings.mouseSensitivity == 0.5) {
         mc.gameSettings.mouseSensitivity = 0.47887325F;
      }

      float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      int deltaX = (int)((6.667 * (double)yaw - 6.6666667 * (double)currentYaw) / (double)f2);
      int deltaY = (int)((6.667 * (double)pitch - 6.666667 * (double)currentPitch) / (double)f2) * -1;
      float f5 = (float)deltaX * f2;
      float f3 = (float)deltaY * f2;
      yaw = (float)((double)currentYaw + (double)f5 * 0.15);
      float f4 = (float)((double)currentPitch - (double)f3 * 0.15);
      pitch = MathHelper.clamp_float(f4, -90.0F, 90.0F);
      return new float[]{yaw, pitch};
   }

   public float[] scaffoldRots(double bx, double by, double bz, float lastYaw, float lastPitch, float yawSpeed, float pitchSpeed, boolean random) {
      double x = bx - mc.thePlayer.posX;
      double y = by - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = bz - mc.thePlayer.posZ;
      float calcYaw = (float)(Math.toDegrees(MathHelper.func_181159_b(z, x)) - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float pitch = updateRotation(lastPitch, calcPitch, pitchSpeed + RandomUtil.nextFloat(0.0F, 15.0F));
      float yaw = updateRotation(lastYaw, calcYaw, yawSpeed + RandomUtil.nextFloat(0.0F, 15.0F));
      if (random) {
         yaw = (float)((double)yaw + ThreadLocalRandom.current().nextDouble(-2.0, 2.0));
         pitch = (float)((double)pitch + ThreadLocalRandom.current().nextDouble(-0.2, 0.2));
      }

      return new float[]{yaw, pitch};
   }

   public float[] advancedScaffoldRots(BlockPos blockPos, double[] expandXZ, float yaw, float[] lastRots, float yawSpeed, float pitchSpeed) {
      ArrayList<Float> yaws = new ArrayList<>();
      ArrayList<Float> pitches = new ArrayList<>();

      for(int bx = blockPos.getX(); bx <= blockPos.getX() + 1; ++bx) {
         for(int bz = blockPos.getZ(); bz <= blockPos.getZ() + 1; ++bz) {
            for(int by = blockPos.getY(); by <= blockPos.getY() + 1; ++by) {
               double x = (double)bx - mc.thePlayer.posX;
               double z = (double)bz - mc.thePlayer.posZ;
               double y = (double)by - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
               yaws.add(updateRotation(lastRots[0], (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0), 180.0F));
               float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
               pitches.add(MathHelper.clamp_float(calcPitch, -90.0F, 90.0F));
            }
         }
      }

      Collections.sort(yaws);
      Collections.sort(pitches);
      float yaww = MathHelper.wrapAngleTo180_float(yaw) + 180.0F;
      float maxYaw = MathHelper.wrapAngleTo180_float(yaws.get(yaws.size() - 1)) + 180.0F;
      float minYaw = MathHelper.wrapAngleTo180_float(yaws.get(0)) + 180.0F;
      if ((!(yaww > minYaw) || !(yaww < maxYaw)) && (!(minYaw > maxYaw) || !(yaww < minYaw) || !(yaww > maxYaw))) {
         double p = 0.85;
         if ((mc.gameSettings.keyBindJump.isKeyDown() || !mc.thePlayer.onGround) && mc.thePlayer.moveForward != 0.0F) {
            p = 0.65;
         }

         return this.scaffoldRots(
            (double)blockPos.getX() + expandXZ[0],
            (double)blockPos.getY() + p,
            (double)blockPos.getZ() + expandXZ[1],
            lastRots[0],
            lastRots[1],
            yawSpeed,
            pitchSpeed,
            false
         );
      } else {
         ArrayList<MovingObjectPosition> movingObjectPositions = new ArrayList<>();
         ArrayList<MovingObjectPosition> movingObjectPositions2 = new ArrayList<>();

         for(float i = pitches.get(0); i < pitches.get(pitches.size() - 1); i += 0.01F) {
            MovingObjectPosition m = mc.thePlayer.customRayTrace(4.5, 1.0F, yaw, i);
            if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.isOkBlock(m.getBlockPos()) && !movingObjectPositions.contains(m)) {
               if (m.sideHit != EnumFacing.DOWN && m.sideHit != EnumFacing.UP) {
                  movingObjectPositions.add(m);
               }

               movingObjectPositions2.add(m);
            }
         }

         movingObjectPositions.sort(Comparator.comparingDouble(mx -> mc.thePlayer.getDistanceSq(mx.getBlockPos().add(0.5, 0.5, 0.5))));
         MovingObjectPosition m = null;
         if (movingObjectPositions.size() > 0) {
            m = movingObjectPositions.get(0);
         }

         BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
         if (mc.theWorld.getBlockState(b).getBlock().getMaterial() == Material.air && m != null) {
            float[] f = this.scaffoldRots(
               m.hitVec.xCoord,
               (double)m.getBlockPos().getY() + RandomUtil.nextDouble(0.45, 0.55),
               m.hitVec.zCoord,
               lastRots[0],
               lastRots[1],
               yawSpeed,
               pitchSpeed,
               false
            );
            return new float[]{yaw, f[1]};
         } else if (movingObjectPositions2.size() != 0) {
            return new float[]{yaw, lastRots[1]};
         } else {
            movingObjectPositions = new ArrayList<>();
            movingObjectPositions2 = new ArrayList<>();

            for(float i = pitches.get(0); i < pitches.get(pitches.size() - 1); i += 0.01F) {
               MovingObjectPosition m2 = mc.thePlayer.customRayTrace(4.5, 1.0F, yaw, i);
               if (m2.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.isOkBlock(m2.getBlockPos()) && !movingObjectPositions.contains(m2)) {
                  if (m2.sideHit != EnumFacing.DOWN && m2.sideHit != EnumFacing.UP) {
                     movingObjectPositions.add(m2);
                  }

                  movingObjectPositions2.add(m2);
               }
            }

            movingObjectPositions.sort(Comparator.comparingDouble(mx -> mc.thePlayer.getDistanceSq(mx.getBlockPos().add(0.5, 0.5, 0.5))));
            m = null;
            if (movingObjectPositions.size() > 0) {
               m = movingObjectPositions.get(0);
            }

            b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(b).getBlock().getMaterial() == Material.air && m != null) {
               float[] f = this.scaffoldRots(
                  m.hitVec.xCoord,
                  (double)m.getBlockPos().getY() + RandomUtil.nextDouble(0.45, 0.55),
                  m.hitVec.zCoord,
                  lastRots[0],
                  lastRots[1],
                  yawSpeed,
                  pitchSpeed,
                  false
               );
               return new float[]{yaw, f[1]};
            } else {
               return movingObjectPositions2.size() != 0 ? new float[]{yaw, lastRots[1]} : lastRots;
            }
         }
      }
   }

   private boolean isOkBlock(BlockPos blockPos) {
      Block block = mc.theWorld.getBlockState(blockPos).getBlock();
      return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
   }

   public float interpolateRotation(float current, float predicted, float percentage) {
      float f = MathHelper.wrapAngleTo180_float(predicted - current);
      if (!(f > 10.0F) && !(f < -10.0F)) {
         percentage = 1.0F;
      }

      return current + percentage * f;
   }

   public static float[] getFovToTarget(double posX, double posY, double posZ, float yaw, float pitch) {
      float[] f = new float[2];
      double x = posX - mc.thePlayer.posX;
      double y = posY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = posZ - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float diffY = MathHelper.wrapAngleTo180_float(calcYaw - yaw);
      float diffP = MathHelper.wrapAngleTo180_float(calcPitch - pitch);
      return new float[]{diffY, diffP};
   }

   private float[] testRots(
      float currentYaw,
      float currentPitch,
      float calcYaw,
      float calcPitch,
      Entity entity,
      float speedYaw,
      float speedPitch,
      Vec3 best,
      boolean smartAim,
      boolean throughWalls
   ) {
      double radius = RandomUtil.nextDouble(0.001, 2.0);
      this.lastAngle = this.lastAngle > 360.0 ? 0.0 : this.lastAngle + RandomUtil.nextDouble(-0.4, 1.2);
      double x = Math.sin(this.lastAngle) * radius;
      double y = Math.cos(this.lastAngle) * radius;
      calcYaw = (float)((double)calcYaw + x);
      calcPitch = (float)((double)calcPitch + y);
      float diffYaw = MathHelper.wrapAngleTo180_float(calcYaw - currentYaw);
      if (Math.abs(diffYaw) > 10.0F) {
         if (diffYaw > speedYaw) {
            diffYaw = speedYaw;
         }

         if (diffYaw < -speedYaw) {
            diffYaw = -speedYaw;
         }
      } else {
         diffYaw = RandomUtil.nextFloat(0.3, 0.7) * diffYaw;
      }

      float diffPitch = MathHelper.wrapAngleTo180_float(calcPitch - currentPitch);
      if (Math.abs(diffPitch) > 10.0F) {
         if (diffPitch > speedPitch) {
            diffPitch = speedPitch;
         }

         if (diffPitch < -speedPitch) {
            diffPitch = -speedPitch;
         }
      } else {
         diffPitch = RandomUtil.nextFloat(0.3, 0.7) * diffPitch;
      }

      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entityPlayer = (EntityLivingBase)entity;
         if (entityPlayer.hurtTime < 1) {
            MovingObjectPosition objectPosition = RayTraceUtil.rayCast(1.0F, new float[]{currentYaw + diffYaw, currentPitch + diffPitch});
            if (smartAim && !throughWalls) {
               double ePosX = best.xCoord;
               double ePosY = best.yCoord;
               double ePosZ = best.zCoord;
               this.x = ePosX - mc.thePlayer.posX;
               this.y = ePosY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
               this.z = ePosZ - mc.thePlayer.posZ;
            } else {
               Vec3 entityVec = getBestHitVec(entity);
               this.x = entityVec.xCoord - mc.thePlayer.posX;
               this.y = entityVec.yCoord - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
               this.z = entityVec.zCoord - mc.thePlayer.posZ;
            }

            float newCalcYaw = (float)(MathHelper.func_181159_b(this.z, this.x) * 180.0 / Math.PI - 90.0);
            float newCalcPitch = (float)(
               -(MathHelper.func_181159_b(this.y, (double)MathHelper.sqrt_double(this.x * this.x + this.z * this.z)) * 180.0 / Math.PI)
            );
            float diffY = MathHelper.wrapAngleTo180_float(newCalcYaw - currentYaw);
            if (Math.abs(diffY) > -1.0F) {
               if (diffY > speedYaw) {
                  diffY = speedYaw;
               }

               if (diffY < -speedYaw) {
                  diffY = -speedYaw;
               }
            } else {
               diffY = RandomUtil.nextFloat(0.3, 0.7) * diffY;
            }

            float diffP = MathHelper.wrapAngleTo180_float(newCalcPitch - currentPitch);
            if (Math.abs(diffP) > -1.0F) {
               if (diffP > speedPitch) {
                  diffP = speedPitch;
               }

               if (diffP < -speedPitch) {
                  diffP = -speedPitch;
               }
            } else {
               diffP = RandomUtil.nextFloat(0.3, 0.7) * diffP;
            }

            MovingObjectPosition objectPosition2 = RayTraceUtil.rayCast(1.0F, new float[]{currentYaw + diffY, currentPitch + diffP});
            if (objectPosition != null
               && objectPosition2 != null
               && objectPosition2.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
               && objectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
               diffYaw = diffY;
               diffPitch = diffP;
            }
         }
      }

      if (entity == null) {
         ACCURATE_ROTATION_YAW_LEVEL = 0.0;
         ACCURATE_ROTATION_YAW_VL = 0.0;
         ACCURATE_ROTATION_PITCH_LEVEL = 0.0;
         ACCURATE_ROTATION_PITCH_VL = 0.0;
      }

      float bestYaw = currentYaw + diffYaw;
      float bestPitch = currentPitch + diffPitch;
      MovingObjectPosition objectPosition = RayTraceUtil.rayCast(1.0F, new float[]{bestYaw, bestPitch});
      float yawSpeed = Math.abs(bestYaw % 360.0F - currentYaw % 360.0F);
      float perfectYaw = this.basicRotation(entity, bestYaw, bestPitch, false)[0];
      double bestYawRotationDistance = (double)Math.abs(bestYaw - perfectYaw);
      float pitchSpeed = Math.abs(bestPitch % 360.0F - currentPitch % 360.0F);
      float perfectPitch = this.basicRotation(entity, bestYaw, bestPitch, false)[1];
      double bestPitchRotationDistance = (double)Math.abs(bestPitch - perfectPitch);
      boolean targetIsMoving = Math.abs(entity.posX - entity.lastTickPosX) > 0.01 || Math.abs(entity.posZ - entity.lastTickPosZ) > 0.01;
      if (yawSpeed > 0.5F && targetIsMoving) {
         double correctYaw = ACCURATE_ROTATION_YAW_LEVEL / 0.25 / 0.8;
         if (bestYawRotationDistance / 0.8 < 2.0) {
            correctYaw += 2.0 - bestYawRotationDistance / 0.8;
         }

         bestYaw = (float)(RandomUtil.nextInt(0, 100000) % 2 == 0 ? (double)bestYaw - correctYaw : (double)bestYaw + correctYaw);
      }

      while(bestPitch == perfectPitch) {
         bestPitch = (float)(
            (double)bestPitch
               + (double)(
                  RandomUtil.nextFloat(-1.0F, 1.0F) / 4.0F
                     + RandomUtil.nextFloat(-1.0F, 1.0F) / 4.0F
                     + MathHelper.clamp_float((float)ThreadLocalRandom.current().nextGaussian(), -1.0F, 1.0F) / 4.0F
               )
               + RandomUtil.randomSin() / 4.0
         );
      }

      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
         if (entityLivingBase.hurtTime <= 4) {
            MovingObjectPosition objectPosition2 = RayTraceUtil.rayCast(1.0F, new float[]{bestYaw, bestPitch});
            if (objectPosition != null
               && objectPosition2 != null
               && objectPosition2.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
               && objectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
            }
         }
      }

      yawSpeed = Math.abs(bestYaw % 360.0F - currentYaw % 360.0F);
      perfectYaw = this.basicRotation(entity, bestYaw, bestPitch, false)[0];
      bestYawRotationDistance = (double)Math.abs(bestYaw - perfectYaw);
      pitchSpeed = Math.abs(bestPitch % 360.0F - currentPitch % 360.0F);
      perfectPitch = this.basicRotation(entity, bestYaw, bestPitch, false)[1];

      for(bestPitchRotationDistance = (double)Math.abs(bestPitch - perfectPitch);
         bestPitchRotationDistance == 0.0;
         bestPitchRotationDistance = (double)Math.abs(bestPitch - perfectPitch)
      ) {
         bestPitch = (float)(
            (double)bestPitch
               + (double)(
                  RandomUtil.nextFloat(-1.0F, 1.0F) / 4.0F
                     + RandomUtil.nextFloat(-1.0F, 1.0F) / 4.0F
                     + MathHelper.clamp_float((float)ThreadLocalRandom.current().nextGaussian(), -1.0F, 1.0F) / 4.0F
               )
               + RandomUtil.randomSin() / 4.0
         );
      }

      if (yawSpeed > 0.5F) {
         if (targetIsMoving) {
            ACCURATE_ROTATION_YAW_LEVEL += 2.0 - bestYawRotationDistance / 0.8;
            ACCURATE_ROTATION_YAW_LEVEL = Math.max(0.0, ACCURATE_ROTATION_YAW_LEVEL);
            int suspiciousLevel = (int)ACCURATE_ROTATION_YAW_LEVEL;
            if (suspiciousLevel > 12) {
               ++ACCURATE_ROTATION_YAW_VL;
               if (ACCURATE_ROTATION_YAW_VL > 3.0) {
                  mc.ingameGUI
                     .getChatGUI()
                     .printChatMessage(
                        new ChatComponentText(
                           "Too accurate yaw rotation §7(§c" + bestYawRotationDistance + " §7| " + ACCURATE_ROTATION_YAW_LEVEL + " §7| " + yawSpeed + ") "
                        )
                     );
               }
            }
         }
      } else if (ACCURATE_ROTATION_YAW_VL > 0.0) {
         ACCURATE_ROTATION_YAW_VL -= 1.0E-5;
      }

      if (pitchSpeed > 0.5F) {
         if (targetIsMoving && bestPitchRotationDistance == 0.0 && currentPitch != bestPitch) {
            ACCURATE_ROTATION_PITCH_LEVEL += 2.0 - bestPitchRotationDistance / 0.8;
            ACCURATE_ROTATION_PITCH_LEVEL = Math.max(0.0, ACCURATE_ROTATION_PITCH_LEVEL);
            int suspiciousLevel = (int)ACCURATE_ROTATION_PITCH_LEVEL;
            if (suspiciousLevel > 8) {
               ++ACCURATE_ROTATION_PITCH_VL;
               if (ACCURATE_ROTATION_PITCH_VL > 3.0) {
                  mc.ingameGUI
                     .getChatGUI()
                     .printChatMessage(
                        new ChatComponentText(
                           "Too accurate pitch rotation §7(§c"
                              + bestPitchRotationDistance
                              + " §7| "
                              + ACCURATE_ROTATION_PITCH_LEVEL
                              + " §7| "
                              + yawSpeed
                              + ") "
                        )
                     );
               }
            }
         }
      } else if (ACCURATE_ROTATION_PITCH_VL > 0.0) {
         ACCURATE_ROTATION_PITCH_VL -= 1.0E-5;
      }

      this.lastTarget = entity;
      return new float[]{bestYaw, bestPitch};
   }

   public void checkRotationAnalysis(EntityLivingBase target, float yaw, float pitch, float prevYaw, float prevPitch) {
      if (target == null) {
         ACCURATE_ROTATION_YAW_LEVEL1 = 0.0;
         ACCURATE_ROTATION_YAW_VL1 = 0.0;
         ACCURATE_ROTATION_PITCH_LEVEL1 = 0.0;
         ACCURATE_ROTATION_PITCH_VL1 = 0.0;
      } else {
         float yawSpeed = Math.abs(yaw % 360.0F - prevYaw % 360.0F);
         float perfectYaw = this.basicRotation(target, yaw, pitch, false)[0];
         double bestYawRotationDistance = (double)Math.abs(yaw - perfectYaw);
         float pitchSpeed = Math.abs(pitch % 360.0F - prevPitch % 360.0F);
         float perfectPitch = this.basicRotation(target, yaw, pitch, false)[1];
         double bestPitchRotationDistance = (double)Math.abs(pitch - perfectPitch);
         boolean targetIsMoving = Math.abs(target.posX - target.lastTickPosX) > 0.01 || Math.abs(target.posZ - target.lastTickPosZ) > 0.01;
         if (yawSpeed > 0.5F) {
            if (targetIsMoving) {
               ACCURATE_ROTATION_YAW_LEVEL1 += 2.0 - bestYawRotationDistance / 0.8;
               ACCURATE_ROTATION_YAW_LEVEL1 = Math.max(0.0, ACCURATE_ROTATION_YAW_LEVEL1);
               int suspiciousLevel = (int)ACCURATE_ROTATION_YAW_LEVEL1;
               if (suspiciousLevel > 12) {
                  ++ACCURATE_ROTATION_YAW_VL1;
                  if (ACCURATE_ROTATION_YAW_VL1 > 3.0) {
                  }
               }
            }
         } else if (ACCURATE_ROTATION_YAW_VL1 > 0.0) {
            ACCURATE_ROTATION_YAW_VL1 -= 1.0E-5;
         }

         if (pitchSpeed > 0.5F) {
            if (targetIsMoving && bestPitchRotationDistance == 0.0 && prevPitch != pitch) {
               ACCURATE_ROTATION_PITCH_LEVEL1 += 2.0 - bestPitchRotationDistance / 0.8;
               ACCURATE_ROTATION_PITCH_LEVEL1 = Math.max(0.0, ACCURATE_ROTATION_PITCH_LEVEL1);
               int suspiciousLevel = (int)ACCURATE_ROTATION_PITCH_LEVEL1;
               if (suspiciousLevel > 8) {
                  ++ACCURATE_ROTATION_PITCH_VL1;
                  if (ACCURATE_ROTATION_PITCH_VL1 > 3.0) {
                  }
               }
            }
         } else if (ACCURATE_ROTATION_PITCH_VL1 > 0.0) {
            ACCURATE_ROTATION_PITCH_VL1 -= 1.0E-5;
         }
      }
   }
}
