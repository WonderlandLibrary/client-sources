package vestige.util.player;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import oshi.util.tuples.Pair;
import vestige.util.IMinecraft;
import vestige.util.util.MathUtils;

public class AimSimulator {
   private double xRandom = 0.0D;
   private double yRandom = 0.0D;
   private double zRandom = 0.0D;
   private long lastNoiseRandom = System.currentTimeMillis();
   private double lastNoiseDeltaX = 0.0D;
   private double lastNoiseDeltaY = 0.0D;
   private double lastNoiseDeltaZ = 0.0D;
   private final List<AxisAlignedBB> boxHistory = new ArrayList(101);
   private boolean nearest = false;
   private double nearestAcc = 0.8D;
   private boolean lazy = false;
   private double lazyAcc = 0.95D;
   private boolean noise = false;
   private Pair<Float, Float> noiseRandom = new Pair(0.35F, 0.5F);
   private double noiseSpeed = 1.0D;
   private long noiseDelay = 100L;
   private boolean delay = false;
   private int delayTicks = 1;
   private Vec3 hitPos = new Vec3(0.0D, 0.0D, 0.0D);

   public void setNearest(boolean value, @Range(from = 0L,to = 1L) double acc) {
      this.nearestAcc = acc;
      this.nearest = value;
   }

   public void setLazy(boolean value, @Range(from = 0L,to = 1L) double acc) {
      this.lazyAcc = acc;
      this.lazy = value;
   }

   public void setNoise(boolean value, Pair<Float, Float> noiseRandom, double noiseSpeed, long noiseDelay) {
      this.noiseRandom = noiseRandom;
      this.noiseSpeed = noiseSpeed / 100.0D;
      this.noiseDelay = noiseDelay;
      this.noise = value;
   }

   public void setDelay(boolean value, int delayTicks) {
      this.delayTicks = delayTicks;
      this.delay = value;
   }

   @NotNull
   public Pair<Float, Float> getRotation(@NotNull EntityLivingBase target) {
      if (target == null) {
         $$$reportNull$$$0(0);
      }

      AxisAlignedBB targetBox = target.getEntityBoundingBox();
      if (this.boxHistory.size() >= 101) {
         this.boxHistory.remove(this.boxHistory.size() - 1);
      }

      while(this.boxHistory.size() < 101) {
         this.boxHistory.add(0, targetBox);
      }

      double yDiff = target.posY - IMinecraft.mc.thePlayer.posY;
      AxisAlignedBB aimBox = this.delay ? (AxisAlignedBB)this.boxHistory.get(this.delayTicks) : targetBox;
      Vec3 targetPosition;
      if (this.nearest) {
         targetPosition = RotationsUtil.getNearestPoint(aimBox, RotationsUtil.getEyePos());
         if (MovementUtil.isMoving() || MovementUtil.isMoving(target)) {
            targetPosition = targetPosition.add(MathUtils.randomizeDouble(this.nearestAcc - 1.0D, 1.0D - this.nearestAcc) * 0.4D, MathUtils.randomizeDouble(this.nearestAcc - 1.0D, 1.0D - this.nearestAcc) * 0.4D, MathUtils.randomizeDouble(this.nearestAcc - 1.0D, 1.0D - this.nearestAcc) * 0.4D);
         }
      } else {
         targetPosition = new Vec3((aimBox.maxX + aimBox.minX) / 2.0D, aimBox.minY + (double)target.getEyeHeight() - 0.15D, (aimBox.maxZ + aimBox.minZ) / 2.0D);
      }

      if (yDiff >= 0.0D && this.lazy) {
         if (targetPosition.yCoord - yDiff > target.posY) {
            targetPosition = new Vec3(targetPosition.xCoord, targetPosition.yCoord - yDiff, targetPosition.zCoord);
         } else {
            targetPosition = new Vec3(target.posX, target.posY + 0.2D, target.posZ);
         }

         if (!target.onGround && (MovementUtil.isMoving() || MovementUtil.isMoving(target))) {
            targetPosition = targetPosition.add(MathUtils.randomizeDouble(this.lazyAcc - 1.0D, 1.0D - this.lazyAcc) * 0.4D, 0.0D, 0.0D);
         }
      }

      if (this.noise) {
         if (System.currentTimeMillis() - this.lastNoiseRandom >= this.noiseDelay) {
            this.xRandom = (double)random((double)(Float)this.noiseRandom.getA());
            this.yRandom = (double)random((double)(Float)this.noiseRandom.getB());
            this.zRandom = (double)random((double)(Float)this.noiseRandom.getA());
            this.lastNoiseRandom = System.currentTimeMillis();
         }

         this.lastNoiseDeltaX = (double)rotMove(this.xRandom, this.lastNoiseDeltaX, this.noiseSpeed);
         this.lastNoiseDeltaY = (double)rotMove(this.yRandom, this.lastNoiseDeltaY, this.noiseSpeed);
         this.lastNoiseDeltaZ = (double)rotMove(this.zRandom, this.lastNoiseDeltaZ, this.noiseSpeed);
         targetPosition = new Vec3(normal(aimBox.maxX, aimBox.minX, targetPosition.xCoord + this.lastNoiseDeltaX), normal(aimBox.maxY, aimBox.minY, targetPosition.yCoord + this.lastNoiseDeltaY), normal(aimBox.maxZ, aimBox.minZ, targetPosition.zCoord + this.lastNoiseDeltaZ));
      }

      float yaw = RotationsUtil.getYaw(targetPosition);
      float pitch = RotationsUtil.getPitch(targetPosition);
      this.hitPos = targetPosition;
      return new Pair(yaw, pitch);
   }

   private static float random(double multiple) {
      return (float)((Math.random() - 0.5D) * 2.0D * multiple);
   }

   private static double normal(double max, double min, double current) {
      return current >= max ? max : Math.max(current, min);
   }

   public static float rotMove(double target, double current, double diff) {
      return rotMoveNoRandom((float)target, (float)current, (float)diff);
   }

   public static float rotMoveNoRandom(float target, float current, float diff) {
      float delta;
      float dist1;
      float dist2;
      if (target > current) {
         dist1 = target - current;
         dist2 = current + 360.0F - target;
         if (dist1 > dist2) {
            delta = -current - 360.0F + target;
         } else {
            delta = dist1;
         }
      } else {
         if (!(target < current)) {
            return current;
         }

         dist1 = current - target;
         dist2 = target + 360.0F - current;
         if (dist1 > dist2) {
            delta = current + 360.0F - target;
         } else {
            delta = -dist1;
         }
      }

      delta = RotationsUtil.normalize(delta);
      if ((double)Math.abs(delta) < 0.1D * Math.random() + 0.1D) {
         return current;
      } else {
         return Math.abs(delta) <= diff ? current + delta : current + (delta < 0.0F ? -diff : diff);
      }
   }

   public static boolean yawEquals(float yaw1, float yaw2) {
      return (double)Math.abs(RotationsUtil.normalize(yaw1) - RotationsUtil.normalize(yaw2)) < 0.1D;
   }

   public static boolean equals(@NotNull Vec3 rot1, @NotNull Vec3 rot2) {
      if (rot1 == null) {
         $$$reportNull$$$0(1);
      }

      if (rot2 == null) {
         $$$reportNull$$$0(2);
      }

      return yawEquals((float)rot1.xCoord, (float)rot2.xCoord) && Math.abs(rot1.yCoord - rot2.yCoord) < 0.1D;
   }

   public Vec3 getHitPos() {
      return this.hitPos;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "target";
         break;
      case 1:
         var10001[0] = "rot1";
         break;
      case 2:
         var10001[0] = "rot2";
      }

      var10001[1] = "vestige/util/player/AimSimulator";
      switch(var0) {
      case 0:
      default:
         var10001[2] = "getRotation";
         break;
      case 1:
      case 2:
         var10001[2] = "equals";
      }

      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
