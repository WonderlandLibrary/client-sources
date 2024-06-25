package cc.slack.utils.player;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.utils.client.mc;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;

public class MovementUtil extends mc {
   public static void setSpeed(MoveEvent event, double speed) {
      setBaseSpeed(event, speed, getPlayer().rotationYaw, (double)getPlayer().moveForward, (double)getPlayer().moveStrafing);
   }

   public static void setSpeed(MoveEvent event, double speed, float yawDegrees) {
      setBaseSpeed(event, speed, yawDegrees, (double)getPlayer().moveForward, (double)getPlayer().moveStrafing);
   }

   public static void minLimitStrafe(float speed) {
      strafe();
      if (getSpeed() < (double)speed) {
         strafe(speed);
      }

   }

   public static void strafe() {
      strafe((float)getSpeed());
   }

   public static void strafe(float speed) {
      float yaw = getDirection();
      getPlayer().motionX = Math.cos(Math.toRadians((double)(yaw + 90.0F))) * (double)speed;
      getPlayer().motionZ = Math.cos(Math.toRadians((double)yaw)) * (double)speed;
   }

   private static void setBaseSpeed(MoveEvent event, double speed, float yaw, double forward, double strafing) {
      if (getPlayer() != null && getWorld() != null) {
         boolean reversed = forward < 0.0D;
         float strafingYaw = 90.0F * (forward > 0.0D ? 0.5F : (reversed ? -0.5F : 1.0F));
         if (reversed) {
            yaw += 180.0F;
         }

         if (strafing > 0.0D) {
            yaw -= strafingYaw;
         } else if (strafing < 0.0D) {
            yaw += strafingYaw;
         }

         double finalX = Math.cos(Math.toRadians((double)(yaw + 90.0F))) * speed;
         double finalZ = Math.cos(Math.toRadians((double)yaw)) * speed;
         if (event != null) {
            if (isMoving()) {
               event.setX(finalX);
               event.setZ(finalZ);
            } else {
               event.setZeroXZ();
            }
         } else if (isMoving()) {
            getPlayer().motionX = finalX;
            getPlayer().motionZ = finalZ;
         } else {
            resetMotion(false);
         }
      }

   }

   public static boolean isMoving() {
      return getPlayer() != null && (getPlayer().moveForward != 0.0F || getPlayer().moveStrafing != 0.0F);
   }

   public static boolean isBindsMoving() {
      return getPlayer() != null && (GameSettings.isKeyDown(mc.getGameSettings().keyBindForward) || GameSettings.isKeyDown(mc.getGameSettings().keyBindRight) || GameSettings.isKeyDown(mc.getGameSettings().keyBindBack) || GameSettings.isKeyDown(mc.getGameSettings().keyBindLeft));
   }

   public static float getDirection() {
      return RotationUtil.isEnabled && RotationUtil.strafeFix ? getBindsDirection(getPlayer().rotationYaw) : getDirection(getPlayer().rotationYaw, getPlayer().moveForward, getPlayer().moveStrafing);
   }

   public static float getDirection(float rotationYaw, float moveForward, float moveStrafing) {
      if (moveForward == 0.0F && moveStrafing == 0.0F) {
         return rotationYaw;
      } else {
         boolean reversed = moveForward < 0.0F;
         double strafingYaw = 90.0D * (moveForward > 0.0F ? 0.5D : (reversed ? -0.5D : 1.0D));
         if (reversed) {
            rotationYaw += 180.0F;
         }

         if (moveStrafing > 0.0F) {
            rotationYaw = (float)((double)rotationYaw - strafingYaw);
         } else if (moveStrafing < 0.0F) {
            rotationYaw = (float)((double)rotationYaw + strafingYaw);
         }

         return rotationYaw;
      }
   }

   public static float getBindsDirection(float rotationYaw) {
      int moveForward = 0;
      if (GameSettings.isKeyDown(mc.getGameSettings().keyBindForward)) {
         ++moveForward;
      }

      if (GameSettings.isKeyDown(mc.getGameSettings().keyBindBack)) {
         --moveForward;
      }

      int moveStrafing = 0;
      if (GameSettings.isKeyDown(mc.getGameSettings().keyBindRight)) {
         ++moveStrafing;
      }

      if (GameSettings.isKeyDown(mc.getGameSettings().keyBindLeft)) {
         --moveStrafing;
      }

      boolean reversed = moveForward < 0;
      double strafingYaw = 90.0D * (moveForward > 0 ? 0.5D : (reversed ? -0.5D : 1.0D));
      if (reversed) {
         rotationYaw += 180.0F;
      }

      if (moveStrafing > 0) {
         rotationYaw = (float)((double)rotationYaw + strafingYaw);
      } else if (moveStrafing < 0) {
         rotationYaw = (float)((double)rotationYaw - strafingYaw);
      }

      return rotationYaw;
   }

   public static void resetMotion() {
      getPlayer().motionX = getPlayer().motionZ = 0.0D;
   }

   public static void resetMotion(boolean stopY) {
      getPlayer().motionX = getPlayer().motionZ = 0.0D;
      if (stopY) {
         getPlayer().motionY = 0.0D;
      }

   }

   public static double getSpeed() {
      return Math.hypot(getPlayer().motionX, getPlayer().motionZ);
   }

   public static double getSpeed(MoveEvent event) {
      return Math.hypot(event.getX(), event.getZ());
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      if (getPlayer().isPotionActive(Potion.moveSpeed)) {
         double amplifier = (double)getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (amplifier + 1.0D);
      }

      return baseSpeed;
   }

   public static void updateBinds() {
      mc.getGameSettings().keyBindJump.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindJump);
      mc.getGameSettings().keyBindSprint.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindSprint);
      mc.getGameSettings().keyBindForward.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindForward);
      mc.getGameSettings().keyBindRight.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindRight);
      mc.getGameSettings().keyBindBack.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindBack);
      mc.getGameSettings().keyBindLeft.pressed = GameSettings.isKeyDown(mc.getGameSettings().keyBindLeft);
   }
}
