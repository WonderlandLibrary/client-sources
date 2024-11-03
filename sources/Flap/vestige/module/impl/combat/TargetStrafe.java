package vestige.module.impl.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import vestige.Flap;
import vestige.event.impl.MoveEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.movement.Fly;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.util.player.MovementUtil;
import vestige.util.player.RotationsUtil;
import vestige.util.world.WorldUtil;

public class TargetStrafe extends Module {
   private final DoubleSetting maxRange = new DoubleSetting("Max range", 3.0D, 1.0D, 6.0D, 0.1D);
   public final BooleanSetting whilePressingSpace = new BooleanSetting("While pressing space", false);
   public final DoubleSetting distance = new DoubleSetting("Distance", 4.0D, 1.0D, 6.0D, 0.1D);
   private boolean goingRight = true;
   private int index = 0;
   private int ticks = 0;
   private Killaura killaura;

   public TargetStrafe() {
      super("Target Strafe", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.maxRange, this.whilePressingSpace, this.distance});
   }

   public void onClientStarted() {
      this.killaura = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
   }

   public void onMove(MoveEvent event) {
      if (mc.gameSettings.keyBindJump.isKeyDown()) {
         this.setSpeed(event, this.getSpeed());
      }
   }

   public double getSpeed() {
      return 0.3D;
   }

   public float getDirection() {
      if (mc.thePlayer.isCollidedHorizontally || !WorldUtil.isBlockUnder(3)) {
         this.goingRight = !this.goingRight;
      }

      EntityLivingBase target = this.killaura.getTarget();
      if (target == null) {
         return 0.0F;
      } else {
         double distance = this.killaura.getDistanceToEntity(target);
         float direction;
         if (distance > this.maxRange.getValue()) {
            direction = RotationsUtil.getRotationsToEntity(target, false)[0];
         } else {
            double offset = 90.0D - distance * 5.0D;
            if (!this.goingRight) {
               offset = -offset;
            }

            direction = (float)((double)RotationsUtil.getRotationsToEntity(target, false)[0] + offset);
         }

         return (float)Math.toRadians((double)direction);
      }
   }

   public void setSpeed(MoveEvent event, double speed) {
      EntityLivingBase target = this.killaura.getTarget();
      if (target != null) {
         List<TargetStrafe.Point> points = this.getPoints(target);
         this.index = this.getIndex(points);
         if ((!((TargetStrafe.Point)points.get(this.index)).isValid() || mc.thePlayer.isCollidedHorizontally) && this.ticks > 5) {
            this.goingRight = !this.goingRight;
            this.ticks = 0;
         }

         ++this.ticks;
         this.changeIndex();
         this.index = Math.max(this.index, 0);
         TargetStrafe.Point point = (TargetStrafe.Point)points.get(this.index);
         float yaw = getRotationFromPosition(point.getX(), point.getZ());
         if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) {
               mc.thePlayer.jump();
               MovementUtil.strafe(0.4749999940395355D);
               if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                  float amplifier = (float)mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                  MovementUtil.strafe((double)(0.46F + 0.023F * (amplifier + 1.0F)));
               }

               mc.thePlayer.motionY = MovementUtil.getJumpHeight();
            }
         } else {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX *= 1.0005D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.0005D;
         }

         setSpeedTargetStrafe(event, speed, yaw, this.goingRight ? 1.0D : -1.0D, (double)mc.thePlayer.getDistanceToEntity(target) > this.distance.getValue() ? 1.0D : 0.0D);
      }
   }

   public List<TargetStrafe.Point> getPoints(EntityLivingBase entity) {
      List<TargetStrafe.Point> points = new ArrayList();

      for(int i = 0; i <= 360; ++i) {
         TargetStrafe.Point point = new TargetStrafe.Point(entity.posX + (entity.posX - entity.lastTickPosX) - Math.sin(Math.toRadians((double)i)) * this.distance.getValue(), entity.posY, entity.posZ + (entity.posZ - entity.lastTickPosZ) + Math.cos(Math.toRadians((double)i)) * this.distance.getValue(), true);
         Block block = mc.theWorld.getBlockState(new BlockPos(point.getX(), point.getY(), point.getZ())).getBlock();
         if (!this.couldFall(point) && block.getMaterial().isSolid()) {
            point.setValid(false);
         }

         points.add(point);
      }

      return points;
   }

   public boolean couldFall(TargetStrafe.Point point) {
      if (((Fly)Flap.instance.getModuleManager().getModule(Fly.class)).isEnabled()) {
         return true;
      } else {
         for(int i = (int)point.getY(); (double)i > point.getY() - 3.0D; --i) {
            if (!(mc.theWorld.getBlockState(new BlockPos(point.getX(), (double)i, point.getZ())).getBlock() instanceof BlockAir)) {
               return true;
            }
         }

         return false;
      }
   }

   public int getIndex(List<TargetStrafe.Point> points) {
      List<TargetStrafe.Point> sortedPoints = new ArrayList(points);
      sortedPoints.sort(Comparator.comparingDouble((p) -> {
         return mc.thePlayer.getDistance(p.getX(), p.getY(), p.getZ());
      }));
      return points.indexOf(sortedPoints.get(0));
   }

   public void changeIndex() {
      if (this.goingRight) {
         this.index += 5;
         if (this.index >= 360) {
            this.index -= 360;
         }
      } else {
         this.index -= 5;
         if (this.index < 0) {
            this.index += 360;
         }
      }

   }

   public static void setSpeedTargetStrafe(MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
      double forward = pseudoForward;
      double strafe = pseudoStrafe;
      float yaw = pseudoYaw;
      if (pseudoForward != 0.0D) {
         if (pseudoStrafe > 0.0D) {
            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
         } else if (pseudoStrafe < 0.0D) {
            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
         }

         strafe = 0.0D;
         forward = pseudoForward > 0.0D ? 1.0D : -1.0D;
      }

      strafe = strafe > 0.0D ? 1.0D : (strafe < 0.0D ? -1.0D : 0.0D);
      double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
      moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
      moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
   }

   public static float getRotationFromPosition(double x, double z) {
      double xDiff = x - mc.thePlayer.posX;
      double zDiff = z - mc.thePlayer.posZ;
      return (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
   }

   public static class Point {
      private double x;
      private double y;
      private double z;
      private boolean valid;

      public Point(double x, double y, double z, boolean valid) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.valid = valid;
      }

      public double getX() {
         return this.x;
      }

      public double getY() {
         return this.y;
      }

      public double getZ() {
         return this.z;
      }

      public boolean isValid() {
         return this.valid;
      }

      public void setValid(boolean valid) {
         this.valid = valid;
      }
   }
}
