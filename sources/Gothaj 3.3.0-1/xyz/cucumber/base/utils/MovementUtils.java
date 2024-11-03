package xyz.cucumber.base.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.ext.EventHit;
import xyz.cucumber.base.events.ext.EventMoveFlying;
import xyz.cucumber.base.utils.math.RotationUtils;
import xyz.cucumber.base.utils.math.Vector3d;

public class MovementUtils {
   public static Minecraft mc = Minecraft.getMinecraft();

   public static List<Vector3d> findPath(double tpX, double tpY, double tpZ, double offset) {
      List<Vector3d> positions = new ArrayList<>();
      double steps = Math.ceil(getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, tpX, tpY, tpZ) / offset);
      double dX = tpX - mc.thePlayer.posX;
      double dY = tpY - mc.thePlayer.posY;
      double dZ = tpZ - mc.thePlayer.posZ;

      for (double d = 1.0; d <= steps; d++) {
         positions.add(new Vector3d(mc.thePlayer.posX + dX * d / steps, mc.thePlayer.posY + dY * d / steps, mc.thePlayer.posZ + dZ * d / steps));
      }

      return positions;
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

   private static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
      double xDiff = x1 - x2;
      double yDiff = y1 - y2;
      double zDiff = z1 - z2;
      return (double)MathHelper.sqrt_double(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
   }

   public static double getPredictedPlayerDistance(double x, double y, double z, int predict) {
      double posX = mc.thePlayer.posX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)predict;
      double posY = mc.thePlayer.posY;
      double posZ = mc.thePlayer.posZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)predict;
      double d0 = posX - x;
      double d1 = posY - y;
      double d2 = posZ - z;
      return (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public static boolean isGoingDiagonally() {
      return Math.abs(mc.thePlayer.motionX) > 0.04 && Math.abs(mc.thePlayer.motionZ) > 0.04;
   }

   public static boolean isLookingDiagonally() {
      return MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) >= 160.0F
         || MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) <= 20.0F
         || MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) >= 70.0F && MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) <= 110.0F;
   }

   public static int depthStriderLevel() {
      return EnchantmentHelper.getDepthStriderModifier(mc.thePlayer);
   }

   public static double jumpBoostMotion(double motionY) {
      return mc.thePlayer.isPotionActive(Potion.jump)
         ? motionY + (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F)
         : motionY;
   }

   public static boolean canSprint(boolean legit) {
      return legit
         ? mc.thePlayer.moveForward >= 0.8F
            && !mc.thePlayer.isCollidedHorizontally
            && (mc.thePlayer.getFoodStats().getFoodLevel() > 6 || mc.thePlayer.capabilities.allowFlying)
            && !mc.thePlayer.isPotionActive(Potion.blindness)
            && !mc.thePlayer.isUsingItem()
            && !mc.thePlayer.isSneaking()
         : enoughMovementForSprinting();
   }

   public static boolean enoughMovementForSprinting() {
      return Math.abs(mc.thePlayer.moveForward) >= 0.8F || Math.abs(mc.thePlayer.moveStrafing) >= 0.8F;
   }

   public static double getPredictedMotionY(double motionY) {
      return (motionY - 0.08) * 0.98F;
   }

   public static void forward(double length) {
      double angleA = Math.toRadians(normalizeAngle((double)(mc.thePlayer.rotationYawHead - 90.0F)));
      mc.thePlayer.setPosition(mc.thePlayer.posX - Math.cos(angleA) * length, mc.thePlayer.posY, mc.thePlayer.posZ - Math.sin(angleA) * length);
   }

   public static double normalizeAngle(double angle) {
      return (angle + 360.0) % 360.0;
   }

   public static double roundToOnGround(double posY) {
      return posY - posY % 0.015625;
   }

   public static double distanceToGround(Vec3 vec3) {
      double playerY = vec3.yCoord;
      return playerY - (double)getBlockBellow(vec3).getY() - 1.0;
   }

   public static double distanceToGround() {
      Vec3 vec3 = mc.thePlayer.getPositionVector();
      double playerY = vec3.yCoord;
      return playerY - (double)getBlockBellow(vec3).getY() - 1.0;
   }

   public static BlockPos getBlockBellow(Vec3 playerPos) {
      while (playerPos.yCoord > 0.0) {
         BlockPos blockPos = new BlockPos(playerPos);
         if (!isAir(blockPos)) {
            return blockPos;
         }

         playerPos = playerPos.addVector(0.0, -1.0, 0.0);
      }

      return BlockPos.ORIGIN;
   }

   public static boolean isAir(BlockPos blockPos) {
      return mc.theWorld.getBlockState(blockPos).getBlock() == Blocks.air;
   }

   public static double getDirection(float yaw) {
      float rotationYaw = yaw;
      if (mc.thePlayer.moveForward < 0.0F) {
         rotationYaw = yaw + 180.0F;
      }

      float forward = 1.0F;
      if (mc.thePlayer.moveForward < 0.0F) {
         forward = -0.5F;
      } else if (mc.thePlayer.moveForward > 0.0F) {
         forward = 0.5F;
      }

      if (mc.thePlayer.moveStrafing > 0.0F) {
         rotationYaw -= 90.0F * forward;
      }

      if (mc.thePlayer.moveStrafing < 0.0F) {
         rotationYaw += 90.0F * forward;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static double getDirectionKeybinds(float yaw) {
      float rotationYaw = yaw;
      if (!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
         rotationYaw = yaw + 180.0F;
      }

      float forward = 1.0F;
      if (!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
         forward = -0.5F;
      } else if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) && !Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
         forward = 0.5F;
      }

      if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) && !Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
         rotationYaw -= 90.0F * forward;
      }

      if (!Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) && Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
         rotationYaw += 90.0F * forward;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static double getDirection(float yaw, float moveForward, float moveStrafing) {
      float rotationYaw = yaw;
      if (moveForward < 0.0F) {
         rotationYaw = yaw + 180.0F;
      }

      float forward = 1.0F;
      if (moveForward < 0.0F) {
         forward = -0.5F;
      } else if (moveForward > 0.0F) {
         forward = 0.5F;
      }

      if (moveStrafing > 0.0F) {
         rotationYaw -= 90.0F * forward;
      }

      if (moveStrafing < 0.0F) {
         rotationYaw += 90.0F * forward;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0 + 0.2 * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return baseSpeed;
   }

   public static double getSpeed() {
      return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
   }

   public static double getEntitySpeed(Entity entity) {
      return Math.hypot(entity.lastTickPosX - entity.posX, entity.lastTickPosZ - entity.posZ);
   }

   public static boolean isMoving() {
      return Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()
         || Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()
         || Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown()
         || Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown();
   }

   public static void strafe(float speed) {
      double yaw = getDirection(mc.thePlayer.rotationYaw);
      mc.thePlayer.motionX = -Math.sin((double)((float)yaw)) * (double)speed;
      mc.thePlayer.motionZ = Math.cos((double)((float)yaw)) * (double)speed;
   }

   public static void strafe(float speed, float yaw) {
      mc.thePlayer.motionX = -Math.sin((double)yaw) * (double)speed;
      mc.thePlayer.motionZ = Math.cos((double)yaw) * (double)speed;
   }

   public static void strafe(float speed, float yaw, float moveForward, float moveStrafing) {
      yaw = (float)getDirection(yaw, moveForward, moveStrafing);
      mc.thePlayer.motionX = -Math.sin((double)yaw) * (double)speed;
      mc.thePlayer.motionZ = Math.cos((double)yaw) * (double)speed;
   }

   public static void strafe() {
      double yaw = getDirection(mc.thePlayer.rotationYaw);
      mc.thePlayer.motionX = -Math.sin((double)((float)yaw)) * getSpeed();
      mc.thePlayer.motionZ = Math.cos((double)((float)yaw)) * getSpeed();
   }

   public static void silentMoveFix(EventMoveFlying event) {
      int dif = (int)((MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - RotationUtils.serverYaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
      float yaw = RotationUtils.serverYaw;
      float strafe = event.getStrafe();
      float forward = event.getForward();
      float friction = event.getFriction();
      float calcForward = 0.0F;
      float calcStrafe = 0.0F;
      switch (dif) {
         case 0:
            calcForward = forward;
            calcStrafe = strafe;
            break;
         case 1:
            calcForward += forward;
            calcStrafe -= forward;
            calcForward += strafe;
            calcStrafe += strafe;
            break;
         case 2:
            calcForward = strafe;
            calcStrafe = -forward;
            break;
         case 3:
            calcForward -= forward;
            calcStrafe -= forward;
            calcForward += strafe;
            calcStrafe -= strafe;
            break;
         case 4:
            calcForward = -forward;
            calcStrafe = -strafe;
            break;
         case 5:
            calcForward -= forward;
            calcStrafe += forward;
            calcForward -= strafe;
            calcStrafe -= strafe;
            break;
         case 6:
            calcForward = -strafe;
            calcStrafe = forward;
            break;
         case 7:
            calcForward += forward;
            calcStrafe += forward;
            calcForward -= strafe;
            calcStrafe += strafe;
      }

      if (calcForward > 1.0F || calcForward < 0.9F && calcForward > 0.3F || calcForward < -1.0F || calcForward > -0.9F && calcForward < -0.3F) {
         calcForward *= 0.5F;
      }

      if (calcStrafe > 1.0F || calcStrafe < 0.9F && calcStrafe > 0.3F || calcStrafe < -1.0F || calcStrafe > -0.9F && calcStrafe < -0.3F) {
         calcStrafe *= 0.5F;
      }

      float d = calcStrafe * calcStrafe + calcForward * calcForward;
      if (d >= 1.0E-4F) {
         d = MathHelper.sqrt_float(d);
         if (d < 1.0F) {
            d = 1.0F;
         }

         d = friction / d;
         calcStrafe *= d;
         calcForward *= d;
         float yawSin = MathHelper.sin((float)((double)yaw * Math.PI / 180.0));
         float yawCos = MathHelper.cos((float)((double)yaw * Math.PI / 180.0));
         mc.thePlayer.motionX += (double)(calcStrafe * yawCos - calcForward * yawSin);
         mc.thePlayer.motionZ += (double)(calcForward * yawCos + calcStrafe * yawSin);
      }
   }

   public static boolean isOnGround(double height) {
      return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
   }

   public static double[] getPredictedPos(
      float forward, float strafe, double motionX, double motionY, double motionZ, double posX, double posY, double posZ, boolean isJumping
   ) {
      strafe *= 0.98F;
      forward *= 0.98F;
      float f4 = 0.91F;
      boolean isSprinting = mc.thePlayer.isSprinting();
      if (isJumping && mc.thePlayer.onGround && mc.thePlayer.jumpTicks == 0) {
         motionY = 0.42;
         if (mc.thePlayer.isPotionActive(Potion.jump)) {
            motionY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
         }

         if (isSprinting) {
            float f5 = mc.thePlayer.rotationYaw * (float) (Math.PI / 180.0);
            motionX -= (double)(MathHelper.sin(f5) * 0.2F);
            motionZ += (double)(MathHelper.cos(f5) * 0.2F);
         }
      }

      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(posY) - 1, MathHelper.floor_double(posZ)))
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      float f6 = 0.16277136F / (f4 * f4 * f4);
      float friction;
      if (mc.thePlayer.onGround) {
         friction = mc.thePlayer.getAIMoveSpeed() * f6;
      } else {
         friction = mc.thePlayer.jumpMovementFactor;
      }

      float f7 = strafe * strafe + forward * forward;
      if (f7 >= 1.0E-4F) {
         f7 = MathHelper.sqrt_float(f7);
         if (f7 < 1.0F) {
            f7 = 1.0F;
         }

         f7 = friction / f7;
         strafe *= f7;
         forward *= f7;
         float f8 = MathHelper.sin(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         float f9 = MathHelper.cos(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         motionX += (double)(strafe * f9 - forward * f8);
         motionZ += (double)(forward * f9 + strafe * f8);
      }

      posX += motionX;
      posY += motionY;
      posZ += motionZ;
      f4 = 0.91F;
      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(posX), MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      if (mc.thePlayer.worldObj.isRemote
         && (
            !mc.thePlayer.worldObj.isBlockLoaded(new BlockPos((int)posX, 0, (int)posZ))
               || !mc.thePlayer.worldObj.getChunkFromBlockCoords(new BlockPos((int)posX, 0, (int)posZ)).isLoaded()
         )) {
         if (posY > 0.0) {
            motionY = -0.1;
         } else {
            motionY = 0.0;
         }
      } else {
         motionY -= 0.08;
      }

      motionY *= 0.98F;
      motionX *= (double)f4;
      motionZ *= (double)f4;
      return new double[]{posX, posY, posZ, motionX, motionY, motionZ};
   }

   public static Vec3 getPredictedPos(boolean isHitting, Entity targetEntity, float forward, float strafe) {
      strafe *= 0.98F;
      forward *= 0.98F;
      float f4 = 0.91F;
      double motionX = mc.thePlayer.motionX;
      double motionZ = mc.thePlayer.motionZ;
      double motionY = mc.thePlayer.motionY;
      boolean isSprinting = mc.thePlayer.isSprinting();
      if (isHitting) {
         float f5 = (float)mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         float f6 = 0.0F;
         if (targetEntity instanceof EntityLivingBase) {
            f6 = EnchantmentHelper.getModifierForCreature(mc.thePlayer.getHeldItem(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
         } else {
            f6 = EnchantmentHelper.getModifierForCreature(mc.thePlayer.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
         }

         if (f5 > 0.0F || f6 > 0.0F) {
            int i = EnchantmentHelper.getKnockbackModifier(mc.thePlayer);
            if (mc.thePlayer.isSprinting()) {
               i++;
            }

            boolean flag2 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(mc.thePlayer), f5);
            if (flag2 && i > 0) {
               EventHit event = new EventHit(false);
               Client.INSTANCE.getEventBus().call(event);
               motionX *= 0.6;
               motionZ *= 0.6;
               isSprinting = false;
            }
         }
      }

      if (mc.thePlayer.isJumping && mc.thePlayer.onGround && mc.thePlayer.jumpTicks == 0) {
         motionY = 0.42;
         if (mc.thePlayer.isPotionActive(Potion.jump)) {
            motionY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
         }

         if (isSprinting) {
            float f5x = mc.thePlayer.rotationYaw * (float) (Math.PI / 180.0);
            motionX -= (double)(MathHelper.sin(f5x) * 0.2F);
            motionZ += (double)(MathHelper.cos(f5x) * 0.2F);
         }
      }

      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(mc.thePlayer.posX),
                     MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1,
                     MathHelper.floor_double(mc.thePlayer.posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      float f7 = 0.16277136F / (f4 * f4 * f4);
      float friction;
      if (mc.thePlayer.onGround) {
         friction = mc.thePlayer.getAIMoveSpeed() * f7;
      } else {
         friction = mc.thePlayer.jumpMovementFactor;
      }

      float f8 = strafe * strafe + forward * forward;
      if (f8 >= 1.0E-4F) {
         f8 = MathHelper.sqrt_float(f8);
         if (f8 < 1.0F) {
            f8 = 1.0F;
         }

         f8 = friction / f8;
         strafe *= f8;
         forward *= f8;
         float f9 = MathHelper.sin(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         float f10 = MathHelper.cos(mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F);
         motionX += (double)(strafe * f10 - forward * f9);
         motionZ += (double)(forward * f10 + strafe * f9);
      }

      f4 = 0.91F;
      if (mc.thePlayer.onGround) {
         f4 = mc.thePlayer
               .worldObj
               .getBlockState(
                  new BlockPos(
                     MathHelper.floor_double(mc.thePlayer.posX),
                     MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1,
                     MathHelper.floor_double(mc.thePlayer.posZ)
                  )
               )
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      motionY *= 0.98F;
      motionX *= (double)f4;
      motionZ *= (double)f4;
      return new Vec3(motionX, motionY, motionZ);
   }

   public static double[] getMotion(double speed, float strafe, float forward, float yaw) {
      float friction = (float)speed;
      float f1 = MathHelper.sin((float)Math.toRadians((double)yaw));
      float f2 = MathHelper.cos((float)Math.toRadians((double)yaw));
      double motionX = (double)(strafe * friction * f2 - forward * friction * f1);
      double motionZ = (double)(forward * friction * f2 + strafe * friction * f1);
      return new double[]{motionX, motionZ};
   }

   public static float[] silentStrafe(float strafe, float forward, float yaw, boolean advanced) {
      int dif = (int)((MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - RotationUtils.serverYaw - 23.5F - 135.0F) + 180.0F) / 45.0F);
      float calcForward = forward;
      float calcStrafe = strafe;
      switch (dif) {
         case 0:
            calcForward = forward;
            calcStrafe = strafe;
            break;
         case 1:
            calcForward = forward + forward;
            calcStrafe = strafe - forward;
            calcForward += strafe;
            calcStrafe += strafe;
            break;
         case 2:
            calcForward = strafe;
            calcStrafe = -forward;
            break;
         case 3:
            calcForward = forward - forward;
            calcStrafe = strafe - forward;
            calcForward += strafe;
            calcStrafe -= strafe;
            break;
         case 4:
            calcForward = -forward;
            calcStrafe = -strafe;
            break;
         case 5:
            calcForward = forward - forward;
            calcStrafe = strafe + forward;
            calcForward -= strafe;
            calcStrafe -= strafe;
            break;
         case 6:
            calcForward = -strafe;
            calcStrafe = forward;
            break;
         case 7:
            calcForward = forward + forward;
            calcStrafe = strafe + forward;
            calcForward -= strafe;
            calcStrafe += strafe;
      }

      if (calcForward > 1.0F || calcForward < 0.9F && calcForward > 0.3F || calcForward < -1.0F || calcForward > -0.9F && calcForward < -0.3F) {
         calcForward *= 0.5F;
      }

      if (calcStrafe > 1.0F || calcStrafe < 0.9F && calcStrafe > 0.3F || calcStrafe < -1.0F || calcStrafe > -0.9F && calcStrafe < -0.3F) {
         calcStrafe *= 0.5F;
      }

      return new float[]{calcStrafe, calcForward};
   }

   public static double predictedMotion(double motion, int ticks) {
      if (ticks == 0) {
         return motion;
      } else {
         double predicted = motion;

         for (int i = 0; i < ticks; i++) {
            predicted = (predicted - 0.08) * 0.98F;
         }

         return predicted;
      }
   }
}
