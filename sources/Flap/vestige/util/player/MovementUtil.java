package vestige.util.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import vestige.Flap;
import vestige.event.impl.MoveEvent;
import vestige.module.impl.combat.Killaura;
import vestige.module.impl.combat.TargetStrafe;
import vestige.util.IMinecraft;
import vestige.util.world.BlockUtils;

public class MovementUtil implements IMinecraft {
   public static final double WALK_SPEED = 0.221D;
   public static final double BUNNY_SLOPE = 0.66D;
   public static final double MOD_SPRINTING = 1.2999999523162842D;
   public static final double MOD_SNEAK = 0.30000001192092896D;
   public static final double MOD_ICE = 2.5D;
   public static final double MOD_WEB = 0.4751131221719457D;
   public static final double JUMP_HEIGHT = 0.41999998688697815D;
   public static final double BUNNY_FRICTION = 159.89999389648438D;
   public static final double Y_ON_GROUND_MIN = 1.0E-5D;
   public static final double Y_ON_GROUND_MAX = 0.0626D;
   public static final double AIR_FRICTION = 0.9800000190734863D;
   public static final double WATER_FRICTION = 0.800000011920929D;
   public static final double LAVA_FRICTION = 0.5D;
   public static final double MOD_SWIM = 0.5203620003898759D;
   public static final double[] MOD_DEPTH_STRIDER = new double[]{1.0D, 1.4304347400741908D, 1.7347825295420372D, 1.9217390955733897D};
   public static final double UNLOADED_CHUNK_MOTION = -0.09800000190735147D;
   public static final double HEAD_HITTER_MOTION = -0.0784000015258789D;
   public static boolean onStrafe = false;
   public static boolean waitingSpoof = false;
   public static boolean nextSpoof = false;

   public static void stop() {
      mc.thePlayer.motionX = 0.0D;
      mc.thePlayer.motionZ = 0.0D;
   }

   public static void reduceSpeed(float speed, boolean alterarY) {
      EntityPlayerSP var10000 = mc.thePlayer;
      var10000.motionX *= (double)speed;
      var10000 = mc.thePlayer;
      var10000.motionZ *= (double)speed;
      if (alterarY) {
         var10000 = mc.thePlayer;
         var10000.motionY *= (double)speed;
      }

   }

   public static void adjustMotionYForFall() {
      if (!mc.thePlayer.onGround) {
         double gravity = -0.08D;
         double airResistance = 0.98D;
         if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.0D;
         } else {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionY += gravity;
            var10000 = mc.thePlayer;
            var10000.motionY *= airResistance;
         }
      }

   }

   private static double getGroundHeight() {
      for(int y = (int)mc.thePlayer.posY; y > 0; --y) {
         if (!mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer.posX, (double)y, mc.thePlayer.posZ))) {
            return (double)(y + 1);
         }
      }

      return 0.0D;
   }

   public static float getDirection() {
      return getDirection(mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
   }

   public static void customStrafeStrength(double strength) {
      strength /= 100.0D;
      strength = Math.min(1.0D, Math.max(0.0D, strength));
      double motionX = mc.thePlayer.motionX;
      double motionZ = mc.thePlayer.motionZ;
      strafe();
      mc.thePlayer.motionX = motionX + (mc.thePlayer.motionX - motionX) * strength;
      mc.thePlayer.motionZ = motionZ + (mc.thePlayer.motionZ - motionZ) * strength;
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

   public static void strafe() {
      strafe(getHorizontalMotion());
   }

   public static void strafe(MoveEvent event) {
      strafe(event, getHorizontalMotion());
   }

   public static void strafe(double speed) {
      float direction = (float)Math.toRadians((double)getPlayerDirection());
      if (isMoving()) {
         mc.thePlayer.motionX = -Math.sin((double)direction) * speed;
         mc.thePlayer.motionZ = Math.cos((double)direction) * speed;
      } else {
         mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
      }

   }

   public static boolean enoughMovementForSprinting() {
      return Math.abs(mc.thePlayer.moveForward) >= 0.8F || Math.abs(mc.thePlayer.moveStrafing) >= 0.8F;
   }

   public static boolean canSprint(boolean legit) {
      return legit ? mc.thePlayer.moveForward >= 0.8F && !mc.thePlayer.isCollidedHorizontally && (mc.thePlayer.getFoodStats().getFoodLevel() > 6 || mc.thePlayer.capabilities.allowFlying) && !mc.thePlayer.isPotionActive(Potion.blindness) && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() : enoughMovementForSprinting();
   }

   public static int depthStriderLevel() {
      return EnchantmentHelper.getDepthStriderModifier(mc.thePlayer);
   }

   public static double getAllowedHorizontalDistance() {
      boolean useBaseModifiers = false;
      double horizontalDistance;
      if (mc.thePlayer.isInWeb) {
         horizontalDistance = 0.105D;
      } else if (!mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
         if (mc.thePlayer.isSneaking()) {
            horizontalDistance = 0.0663000026345253D;
         } else {
            horizontalDistance = 0.221D;
            useBaseModifiers = true;
         }
      } else {
         horizontalDistance = 0.11500000208616258D;
         int depthStriderLevel = mc.thePlayer.getCurrentArmor(0) != null ? EnchantmentHelper.getEnchantmentLevel(Enchantment.depthStrider.effectId, mc.thePlayer.getCurrentArmor(0)) : 0;
         if (depthStriderLevel > 0) {
            horizontalDistance *= MOD_DEPTH_STRIDER[depthStriderLevel];
            useBaseModifiers = true;
         }
      }

      if (useBaseModifiers) {
         if (mc.thePlayer.isSprinting()) {
            horizontalDistance *= 1.2999999523162842D;
         }

         if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            horizontalDistance *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
         }

         if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
            horizontalDistance *= 0.5D;
         }
      }

      return horizontalDistance;
   }

   public static void strafes() {
      strafe(speed());
   }

   public static double speed() {
      return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
   }

   public static double direction() {
      float rotationYaw = mc.thePlayer.rotationYaw;
      if (mc.thePlayer.moveForward < 0.0F) {
         rotationYaw += 180.0F;
      }

      float forward = 1.0F;
      if (mc.thePlayer.moveForward < 0.0F) {
         forward = -0.5F;
      } else if (mc.thePlayer.moveForward > 0.0F) {
         forward = 0.5F;
      }

      if (mc.thePlayer.moveStrafing > 0.0F) {
         rotationYaw -= 70.0F * forward;
      }

      if (mc.thePlayer.moveStrafing < 0.0F) {
         rotationYaw += 70.0F * forward;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static void moveFlying(double increase) {
      if (isMoving()) {
         double yaw = direction();
         EntityPlayerSP var10000 = mc.thePlayer;
         var10000.motionX += (double)(-MathHelper.sin((float)yaw)) * increase;
         var10000 = mc.thePlayer;
         var10000.motionZ += (double)MathHelper.cos((float)yaw) * increase;
      }
   }

   public static void strafe(float speed, float yaw) {
      onStrafe = true;
      mc.thePlayer.motionX = Math.cos(Math.toRadians((double)(yaw + 90.0F))) * (double)speed;
      mc.thePlayer.motionZ = Math.cos(Math.toRadians((double)yaw)) * (double)speed;
   }

   public static double getJumpHeight() {
      return getJumpHeight(0.41999998688698D);
   }

   public static double getJumpHeight(double height) {
      return mc.thePlayer.isPotionActive(Potion.jump) ? height + (double)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1D : height;
   }

   public static void strafe(MoveEvent event, double speed) {
      float direction = (float)Math.toRadians((double)getPlayerDirection());
      Killaura killaura = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
      TargetStrafe targetStrafe = (TargetStrafe)Flap.instance.getModuleManager().getModule(TargetStrafe.class);
      if (killaura.isEnabled() && killaura.getTarget() != null && targetStrafe.isEnabled() && (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) || !targetStrafe.whilePressingSpace.isEnabled())) {
         direction = targetStrafe.getDirection();
      }

      if (isMoving()) {
         event.setX(mc.thePlayer.motionX = -Math.sin((double)direction) * speed);
         event.setZ(mc.thePlayer.motionZ = Math.cos((double)direction) * speed);
      } else {
         event.setX(mc.thePlayer.motionX = 0.0D);
         event.setZ(mc.thePlayer.motionZ = 0.0D);
      }

   }

   public static void strafeNoTargetStrafe(MoveEvent event, double speed) {
      float direction = (float)Math.toRadians((double)getPlayerDirection());
      if (isMoving()) {
         event.setX(mc.thePlayer.motionX = -Math.sin((double)direction) * speed);
         event.setZ(mc.thePlayer.motionZ = Math.cos((double)direction) * speed);
      } else {
         event.setX(mc.thePlayer.motionX = 0.0D);
         event.setZ(mc.thePlayer.motionZ = 0.0D);
      }

   }

   public static void spoofNextC03(boolean spoof) {
      waitingSpoof = true;
      nextSpoof = spoof;
   }

   public static float getPlayerDirection() {
      float direction = mc.thePlayer.rotationYaw;
      if (mc.thePlayer.moveForward > 0.0F) {
         if (mc.thePlayer.moveStrafing > 0.0F) {
            direction -= 45.0F;
         } else if (mc.thePlayer.moveStrafing < 0.0F) {
            direction += 45.0F;
         }
      } else if (mc.thePlayer.moveForward < 0.0F) {
         if (mc.thePlayer.moveStrafing > 0.0F) {
            direction -= 135.0F;
         } else if (mc.thePlayer.moveStrafing < 0.0F) {
            direction += 135.0F;
         } else {
            direction -= 180.0F;
         }
      } else if (mc.thePlayer.moveStrafing > 0.0F) {
         direction -= 90.0F;
      } else if (mc.thePlayer.moveStrafing < 0.0F) {
         direction += 90.0F;
      }

      return direction;
   }

   public static float getPlayerDirection(float baseYaw) {
      float direction = baseYaw;
      if (mc.thePlayer.moveForward > 0.0F) {
         if (mc.thePlayer.moveStrafing > 0.0F) {
            direction = baseYaw - 45.0F;
         } else if (mc.thePlayer.moveStrafing < 0.0F) {
            direction = baseYaw + 45.0F;
         }
      } else if (mc.thePlayer.moveForward < 0.0F) {
         if (mc.thePlayer.moveStrafing > 0.0F) {
            direction = baseYaw - 135.0F;
         } else if (mc.thePlayer.moveStrafing < 0.0F) {
            direction = baseYaw + 135.0F;
         } else {
            direction = baseYaw - 180.0F;
         }
      } else if (mc.thePlayer.moveStrafing > 0.0F) {
         direction = baseYaw - 90.0F;
      } else if (mc.thePlayer.moveStrafing < 0.0F) {
         direction = baseYaw + 90.0F;
      }

      return direction;
   }

   public static double getHorizontalMotion() {
      return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
   }

   public static boolean isMoving() {
      return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F;
   }

   public static int getSpeedAmplifier() {
      return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() : 0;
   }

   public static boolean isGoingDiagonally() {
      return Math.abs(mc.thePlayer.motionX) > 0.08D && Math.abs(mc.thePlayer.motionZ) > 0.08D;
   }

   public static void motionMult(double mult) {
      EntityPlayerSP var10000 = mc.thePlayer;
      var10000.motionX *= mult;
      var10000 = mc.thePlayer;
      var10000.motionZ *= mult;
   }

   public static void motionMult(MoveEvent event, double mult) {
      EntityPlayerSP var10001 = mc.thePlayer;
      event.setX(var10001.motionX *= mult);
      var10001 = mc.thePlayer;
      event.setZ(var10001.motionZ *= mult);
   }

   public static void boost(double amount) {
      float f = getPlayerDirection() * 0.017453292F;
      EntityPlayerSP var10000 = mc.thePlayer;
      var10000.motionX -= (double)MathHelper.sin(f) * amount;
      var10000 = mc.thePlayer;
      var10000.motionZ += (double)MathHelper.cos(f) * amount;
   }

   public static void boost(MoveEvent event, double amount) {
      float f = getPlayerDirection() * 0.017453292F;
      EntityPlayerSP var10001 = mc.thePlayer;
      event.setX(var10001.motionX -= (double)MathHelper.sin(f) * amount);
      var10001 = mc.thePlayer;
      event.setZ(var10001.motionZ += (double)MathHelper.cos(f) * amount);
   }

   public static void jump(MoveEvent event) {
      double jumpY = (double)mc.thePlayer.getJumpUpwardsMotion();
      if (mc.thePlayer.isPotionActive(Potion.jump)) {
         jumpY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
      }

      event.setY(mc.thePlayer.motionY = jumpY);
   }

   public static void hclip(double dist) {
      float direction = (float)Math.toRadians((double)mc.thePlayer.rotationYaw);
      mc.thePlayer.setPosition(mc.thePlayer.posX - Math.sin((double)direction) * dist, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos((double)direction) * dist);
   }

   public static float[] incrementMoveDirection(float forward, float strafe) {
      if (forward != 0.0F || strafe != 0.0F) {
         float value = forward != 0.0F ? Math.abs(forward) : Math.abs(strafe);
         if (forward > 0.0F) {
            if (strafe > 0.0F) {
               strafe = 0.0F;
            } else if (strafe == 0.0F) {
               strafe = -value;
            } else if (strafe < 0.0F) {
               forward = 0.0F;
            }
         } else if (forward == 0.0F) {
            if (strafe > 0.0F) {
               forward = value;
            } else {
               forward = -value;
            }
         } else if (strafe < 0.0F) {
            strafe = 0.0F;
         } else if (strafe == 0.0F) {
            strafe = value;
         } else if (strafe > 0.0F) {
            forward = 0.0F;
         }
      }

      return new float[]{forward, strafe};
   }

   public static double predictedMotion(double motion, int ticks) {
      return PlayerUtil.predictedMotion(motion, ticks);
   }

   @Contract(
      pure = true
   )
   public static boolean isMoving(@NotNull EntityLivingBase entity) {
      return entity.moveForward != 0.0F || entity.moveStrafing != 0.0F;
   }

   public static double predictedMotionXZ(double motion, int tick, boolean moving) {
      for(int i = 0; i < tick; ++i) {
         if (!moving) {
            motion /= 0.5D;
         }

         if (motion < 0.005D) {
            return 0.0D;
         }
      }

      return motion;
   }

   @NotNull
   public Vec3 predictedPos(@NotNull EntityLivingBase entity, Vec3 motion, Vec3 result, int predTicks) {
       for(int i = 0; i < predTicks; ++i) {
         result = result.add(predictedMotionXZ(motion.xCoord, i, isMoving(entity)), !entity.onGround && BlockUtils.replaceable(new BlockPos(result)) ? predictedMotion(motion.yCoord, i) : 0.0D, predictedMotionXZ(motion.zCoord, i, isMoving(entity)));
      }
      return result;
   }

}
