package cc.slack.utils.player;

import cc.slack.utils.client.mc;
import cc.slack.utils.other.MathUtil;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil extends mc {
   public static boolean isEnabled = false;
   public static float[] clientRotation = new float[]{0.0F, 0.0F};
   public static float keepRotationTicks = 0.0F;
   public static float randomizeAmount = 0.0F;
   public static boolean strafeFix = true;
   public static boolean strictStrafeFix = false;

   public static void setStrafeFix(boolean enabled, boolean strict) {
      strafeFix = enabled;
      strictStrafeFix = strict;
   }

   public static void disable() {
      isEnabled = false;
      keepRotationTicks = 0.0F;
      strafeFix = false;
      strictStrafeFix = true;
   }

   public static void setClientRotation(float[] targetRotation) {
      setClientRotation(targetRotation, 0);
   }

   public static void setClientRotation(float[] targetRotation, int keepRotation) {
      if (!isEnabled || keepRotationTicks <= 0.0F) {
         isEnabled = true;
         keepRotationTicks = (float)keepRotation;
         clientRotation = targetRotation;
      }

   }

   public static void setPlayerRotation(float[] targetRotation) {
      targetRotation = applyGCD(targetRotation, new float[]{mc.getPlayer().prevRotationYaw, mc.getPlayer().prevRotationPitch});
      mc.getPlayer().rotationYaw = targetRotation[0];
      mc.getPlayer().rotationPitch = targetRotation[1];
   }

   public static float[] getNeededRotations(Vec3 vec) {
      Vec3 playerVector = new Vec3(getPlayer().posX, getPlayer().posY + (double)getPlayer().getEyeHeight(), getPlayer().posZ);
      double y = vec.yCoord - playerVector.yCoord;
      double x = vec.xCoord - playerVector.xCoord;
      double z = vec.zCoord - playerVector.zCoord;
      double dff = Math.sqrt(x * x + z * z);
      float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(y, dff)));
      return new float[]{updateRots(yaw, yaw, 180.0F), updateRots(pitch, pitch, 90.0F)};
   }

   public static float[] getTargetRotations(AxisAlignedBB aabb, cc.slack.utils.player.RotationUtil.TargetRotation mode, double random) {
      double minX = 0.0D;
      double maxX = 1.0D;
      double minY = 0.0D;
      double maxY = 1.0D;
      double minZ = 0.0D;
      double maxZ = 1.0D;
      switch(mode) {
      case EDGE:
      case OPTIMAL:
      default:
         break;
      case CENTER:
         minX = 0.5D;
         maxX = 0.5D;
         minY = 0.5D;
         maxY = 0.5D;
         minZ = 0.5D;
         maxZ = 0.5D;
         break;
      case MIDDLE:
         minX = 0.4D;
         maxX = 0.6D;
         minY = 0.4D;
         maxY = 0.6D;
         minZ = 0.4D;
         maxZ = 0.6D;
         break;
      case TOPHALF:
         minX = 0.1D;
         maxX = 0.9D;
         minY = 0.5D;
         maxY = 0.9D;
         minZ = 0.1D;
         maxZ = 0.9D;
      }

      Vec3 rotPoint = new Vec3(aabb.minX, aabb.minY, aabb.minZ);
      double minRotDiff;
      double currentRotDiff;
      if (mode == cc.slack.utils.player.RotationUtil.TargetRotation.OPTIMAL) {
         rotPoint = new Vec3(Math.max(aabb.minX, Math.min(aabb.maxX, mc.getPlayer().posX)), Math.max(aabb.minY, Math.min(aabb.maxY, mc.getPlayer().posY)), Math.max(aabb.minZ, Math.min(aabb.maxZ, mc.getPlayer().posZ)));
      } else {
         minRotDiff = 180.0D;

         for(double x = minX; x <= maxX; x += 0.1D) {
            for(double y = minY; y <= maxY; y += 0.1D) {
               for(double z = minZ; z <= maxZ; z += 0.1D) {
                  currentRotDiff = getRotationDifference(MathUtil.interpolate(aabb.maxX, aabb.minX, x), MathUtil.interpolate(aabb.maxY, aabb.minY, y), MathUtil.interpolate(aabb.maxZ, aabb.minZ, z));
                  if (currentRotDiff < minRotDiff) {
                     minRotDiff = currentRotDiff;
                     rotPoint = new Vec3(MathUtil.interpolate(aabb.maxX, aabb.minX, x), MathUtil.interpolate(aabb.maxY, aabb.minY, y), MathUtil.interpolate(aabb.maxZ, aabb.minZ, z));
                  }
               }
            }
         }
      }

      minRotDiff = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
      currentRotDiff = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
      double randZ = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
      rotPoint.addVector(minRotDiff, currentRotDiff, randZ);
      return getRotations(rotPoint);
   }

   public static float[] getRotations(Vec3 start, Vec3 dst) {
      double xDif = dst.xCoord - start.xCoord;
      double yDif = dst.yCoord - start.yCoord;
      double zDif = dst.zCoord - start.zCoord;
      double distXZ = Math.sqrt(xDif * xDif + zDif * zDif);
      return new float[]{(float)(Math.atan2(zDif, xDif) * 180.0D / 3.141592653589793D) - 90.0F, (float)(-(Math.atan2(yDif, distXZ) * 180.0D / 3.141592653589793D))};
   }

   public static float[] getRotations(Entity entity) {
      return getRotations(entity.posX, entity.posY, entity.posZ);
   }

   public static float[] getRotations(Vec3 vec) {
      return getRotations(vec.xCoord, vec.yCoord, vec.zCoord);
   }

   public static float[] getRotations(double x, double y, double z) {
      Vec3 lookVec = getPlayer().getPositionEyes(1.0F);
      double dx = lookVec.xCoord - x;
      double dy = lookVec.yCoord - y;
      double dz = lookVec.zCoord - z;
      double dist = Math.hypot(dx, dz);
      double yaw = Math.toDegrees(Math.atan2(dz, dx));
      double pitch = Math.toDegrees(Math.atan2(dy, dist));
      return new float[]{(float)yaw + 90.0F, (float)pitch};
   }

   public static Vec3 getNormalRotVector(float[] rotation) {
      return getNormalRotVector(rotation[0], rotation[1]);
   }

   public static Vec3 getNormalRotVector(float yaw, float pitch) {
      return mc.getPlayer().getVectorForRotation(pitch, yaw);
   }

   public static double getRotationDifference(Entity e) {
      float[] entityRotation = getRotations(e.posX, e.posY, e.posZ);
      return getRotationDifference(entityRotation);
   }

   public static double getRotationDifference(Vec3 e) {
      float[] entityRotation = getRotations(e.xCoord, e.yCoord, e.zCoord);
      return getRotationDifference(entityRotation);
   }

   public static double getRotationDifference(double x, double y, double z) {
      float[] entityRotation = getRotations(x, y, z);
      return getRotationDifference(entityRotation);
   }

   public static double getRotationDifference(float[] e) {
      double yawDif = MathHelper.wrapAngleTo180_double((double)(mc.getPlayer().rotationYaw - e[0]));
      double pitchDif = MathHelper.wrapAngleTo180_double((double)(mc.getPlayer().rotationPitch - e[1]));
      return Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
   }

   public static float[] getRotations(float[] lastRotations, float smoothing, Vec3 start, Vec3 dst) {
      float[] rotations = getRotations(start, dst);
      applySmoothing(lastRotations, smoothing, rotations);
      return rotations;
   }

   public static void applySmoothing(float[] lastRotations, float smoothing, float[] dstRotation) {
      if (smoothing > 0.0F) {
         float yawChange = MathHelper.wrapAngleTo180_float(dstRotation[0] - lastRotations[0]);
         float pitchChange = MathHelper.wrapAngleTo180_float(dstRotation[1] - lastRotations[1]);
         float smoothingFactor = Math.max(1.0F, smoothing / 10.0F);
         dstRotation[0] = lastRotations[0] + yawChange / smoothingFactor;
         dstRotation[1] = Math.max(Math.min(90.0F, lastRotations[1] + pitchChange / smoothingFactor), -90.0F);
      }

   }

   public static float[] applyGCD(float[] rotations, float[] prevRots) {
      float mouseSensitivity = (float)((double)mc.getGameSettings().mouseSensitivity * (1.0D + Math.random() / 1.0E7D) * 0.6000000238418579D + 0.20000000298023224D);
      double multiplier = (double)(mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F) * 0.15D;
      float yaw = prevRots[0] + (float)((double)Math.round((double)(rotations[0] - prevRots[0]) / multiplier) * multiplier);
      float pitch = prevRots[1] + (float)((double)Math.round((double)(rotations[1] - prevRots[1]) / multiplier) * multiplier);
      return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0F, 90.0F)};
   }

   public static float updateRots(float from, float to, float speed) {
      float f = MathHelper.wrapAngleTo180_float(to - from);
      if (f > speed) {
         f = speed;
      }

      if (f < -speed) {
         f = -speed;
      }

      return from + f;
   }

   public static float[] getLimitedRotation(float[] from, float[] to, float speed) {
      double yawDif = MathHelper.wrapAngleTo180_double((double)(from[0] - to[0]));
      double pitchDif = MathHelper.wrapAngleTo180_double((double)(from[1] - to[1]));
      double rotDif = Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
      double yawLimit = yawDif * (double)speed / rotDif;
      double pitchLimit = pitchDif * (double)speed / rotDif;
      return new float[]{updateRots(from[0], to[0], (float)yawLimit), updateRots(from[1], to[1], (float)pitchLimit)};
   }

   public static EnumFacing getEnumDirection(float yaw) {
      return EnumFacing.getHorizontal(MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3);
   }
}
