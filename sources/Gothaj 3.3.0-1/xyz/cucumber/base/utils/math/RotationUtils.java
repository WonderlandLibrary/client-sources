package xyz.cucumber.base.utils.math;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.optifine.reflect.Reflector;

public class RotationUtils {
   public static Minecraft mc = Minecraft.getMinecraft();
   public static float serverYaw;
   public static float serverPitch;
   public static boolean customRots;

   public static float interpolateRotation(float current, float predicted, float percentage) {
      float f = MathHelper.wrapAngleTo180_float(predicted - current);
      if (f <= 10.0F && f >= -10.0F) {
         percentage = 1.0F;
      }

      return current + percentage * f;
   }

   public static double fovFromEntity(Entity en) {
      return ((double)(mc.thePlayer.rotationYaw - fovToEntity(en)) % 360.0 + 540.0) % 360.0 - 180.0;
   }

   public static double fovFromPosition(double[] pos) {
      return ((double)(mc.thePlayer.rotationYaw - fovToPosition(pos)) % 360.0 + 540.0) % 360.0 - 180.0;
   }

   public static float fovToEntity(Entity ent) {
      double x = ent.posX - mc.thePlayer.posX;
      double z = ent.posZ - mc.thePlayer.posZ;
      double yaw = (double)((float)(MathHelper.atan2(z, x) * 180.0 / Math.PI - 90.0));
      return (float)yaw;
   }

   public static float getRotationDifference(float rot1, float rot2) {
      float angle = Math.abs(rot1 - rot2);
      return angle > 180.0F ? 360.0F - angle : angle;
   }

   public static float fovToPosition(double[] pos) {
      double x = pos[0] - mc.thePlayer.posX;
      double z = pos[1] - mc.thePlayer.posZ;
      double yaw = (double)((float)(MathHelper.atan2(z, x) * 180.0 / Math.PI - 90.0));
      return (float)yaw;
   }

   public static Entity rayTrace(double range, float[] rotations) {
      if (mc.objectMouseOver.entityHit != null) {
         return mc.objectMouseOver.entityHit;
      } else {
         Vec3 vec3 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0F);
         Vec3 vec31 = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
         Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
         Entity pointedEntity = null;
         float f = 1.0F;
         List<?> list = Minecraft.getMinecraft()
            .theWorld
            .getEntitiesInAABBexcluding(
               Minecraft.getMinecraft().getRenderViewEntity(),
               Minecraft.getMinecraft()
                  .getRenderViewEntity()
                  .getEntityBoundingBox()
                  .addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range)
                  .expand((double)f, (double)f, (double)f),
               Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith)
            );
         double d2 = range;

         for (Object o : list) {
            Entity entity1 = (Entity)o;
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d2 >= 0.0) {
                  pointedEntity = entity1;
                  d2 = 0.0;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0) {
                  boolean flag2 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (entity1 != Minecraft.getMinecraft().getRenderViewEntity().ridingEntity || flag2) {
                     pointedEntity = entity1;
                     d2 = d3;
                  } else if (d2 == 0.0) {
                     pointedEntity = entity1;
                  }
               }
            }
         }

         return pointedEntity;
      }
   }

   public static float[] positionRotation(double posX, double posY, double posZ, float[] lastRots, float yawSpeed, float pitchSpeed, boolean random) {
      double x = posX - mc.thePlayer.posX;
      double y = posY - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = posZ - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.atan2(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.atan2(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = updateRotation(lastRots[0], calcYaw, yawSpeed);
      float pitch = updateRotation(lastRots[1], calcPitch, pitchSpeed);
      if (random) {
         yaw += (float)ThreadLocalRandom.current().nextGaussian();
         pitch += (float)ThreadLocalRandom.current().nextGaussian();
      }

      return new float[]{yaw, pitch};
   }

   public static boolean lookingAtBlock(BlockPos blockPos, float yaw, float pitch, EnumFacing enumFacing, boolean strict) {
      MovingObjectPosition movingObjectPosition = mc.thePlayer
         .rayTraceCustom((double)mc.playerController.getBlockReachDistance(), mc.timer.renderPartialTicks, yaw, pitch);
      if (movingObjectPosition == null) {
         return false;
      } else {
         Vec3 hitVec = movingObjectPosition.hitVec;
         return hitVec == null
            ? false
            : movingObjectPosition.getBlockPos().equals(blockPos)
               && (!strict || movingObjectPosition.sideHit == enumFacing && movingObjectPosition.sideHit != null);
      }
   }

   public static float[] getRotationsToBlock(BlockPos blockPos, EnumFacing enumFacing) {
      double x = (double)blockPos.getX() + 0.5 - mc.thePlayer.posX + (double)enumFacing.getFrontOffsetX() / 2.0;
      double z = (double)blockPos.getZ() + 0.5 - mc.thePlayer.posZ + (double)enumFacing.getFrontOffsetZ() / 2.0;
      double y = (double)blockPos.getY() + 0.5;
      double dist = mc.thePlayer
         .getDistance(
            (double)blockPos.getX() + 0.5 + (double)enumFacing.getFrontOffsetX() / 2.0,
            (double)blockPos.getY(),
            (double)blockPos.getZ() + 0.5 + (double)enumFacing.getFrontOffsetZ() / 2.0
         );
      y += 0.5;
      double d1 = mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - y;
      double d3 = (double)MathHelper.sqrt_double(x * x + z * z);
      float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0F;
      float pitch = (float)(Math.atan2(d1, d3) * 180.0 / Math.PI);
      if (yaw < 0.0F) {
         yaw += 360.0F;
      }

      return new float[]{yaw, pitch};
   }

   public static float getYawBasedPitch(BlockPos blockPos, EnumFacing facing, float currentYaw, float lastPitch, int maxPitch) {
      float increment = (float)(Math.random() / 20.0) + 0.05F;

      for (float i = (float)maxPitch; i > 45.0F; i -= increment) {
         MovingObjectPosition ray = rayCast(1.0F, new float[]{currentYaw, i}, (double)mc.playerController.getBlockReachDistance(), 2.0);
         if (ray.getBlockPos() == null || ray.sideHit == null) {
            return lastPitch;
         }

         if (ray.getBlockPos().equalsBlockPos(blockPos) && ray.sideHit == facing) {
            return i;
         }
      }

      return lastPitch;
   }

   public static MovingObjectPosition rayCast(float partialTicks, float[] rots, double range, double hitBoxExpand) {
      MovingObjectPosition objectMouseOver = null;
      Entity entity = mc.getRenderViewEntity();
      if (entity != null && mc.theWorld != null) {
         mc.mcProfiler.startSection("pick");
         mc.pointedEntity = null;
         double d0 = range;
         objectMouseOver = entity.rayTraceCustom(range, partialTicks, rots[0], rots[1]);
         double d2 = range;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         boolean flag2 = true;
         if (mc.playerController.extendedReach()) {
            d0 = 6.0;
            d2 = 6.0;
         } else if (range > 3.0) {
            flag = true;
         }

         if (objectMouseOver != null) {
            d2 = objectMouseOver.hitVec.distanceTo(vec3);
         }

         Vec3 vec4 = entity.getLookCustom(partialTicks, rots[0], rots[1]);
         Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
         Entity pointedEntity = null;
         Vec3 vec6 = null;
         float f = 1.0F;
         List list = mc.theWorld
            .getEntitiesInAABBexcluding(
               entity,
               entity.getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(1.0, 1.0, 1.0),
               Predicates.and(new Predicate[]{EntitySelectors.NOT_SPECTATING})
            );
         double d3 = d2;
         AxisAlignedBB realBB = null;

         for (int i = 0; i < list.size(); i++) {
            Entity entity2 = (Entity)list.get(i);
            float f2 = (float)((double)entity2.getCollisionBorderSize() + hitBoxExpand);
            AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand((double)f2, (double)f2, (double)f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d3 >= 0.0) {
                  pointedEntity = entity2;
                  vec6 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                  d3 = 0.0;
               }
            } else if (movingobjectposition != null) {
               double d4 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d4 < d3 || d3 == 0.0) {
                  boolean flag3 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag3 = Reflector.callBoolean(entity2, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (entity2 != entity.ridingEntity || flag3) {
                     pointedEntity = entity2;
                     vec6 = movingobjectposition.hitVec;
                     d3 = d4;
                  } else if (d3 == 0.0) {
                     pointedEntity = entity2;
                     vec6 = movingobjectposition.hitVec;
                  }
               }
            }
         }

         if (pointedEntity != null && flag && vec3.distanceTo(vec6) > range) {
            pointedEntity = null;
            objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec6, null, new BlockPos(vec6));
         }

         if (pointedEntity != null && (d3 < d2 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
         }
      }

      return objectMouseOver;
   }

   public static float[] getFixedRotation(float[] rotations, float[] lastRotations) {
      float yaw = rotations[0];
      float pitch = rotations[1];
      float lastYaw = lastRotations[0];
      float lastPitch = lastRotations[1];
      float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f1 = f * f * f * 8.0F;
      float deltaYaw = yaw - lastYaw;
      float deltaPitch = pitch - lastPitch;
      float fixedDeltaYaw = deltaYaw - deltaYaw % f1;
      float fixedDeltaPitch = deltaPitch - deltaPitch % f1;
      float fixedYaw = lastYaw + fixedDeltaYaw;
      float fixedPitch = lastPitch + fixedDeltaPitch;
      return new float[]{fixedYaw, fixedPitch};
   }

   public static double getDirectionWrappedTo90() {
      float rotationYaw = mc.thePlayer.rotationYaw;
      if (mc.thePlayer.moveForward < 0.0F && mc.thePlayer.moveStrafing == 0.0F) {
         rotationYaw += 180.0F;
      }

      float forward = 1.0F;
      if (mc.thePlayer.moveStrafing > 0.0F) {
         rotationYaw -= 90.0F;
      }

      if (mc.thePlayer.moveStrafing < 0.0F) {
         rotationYaw += 90.0F;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static float[] getDirectionToBlock(double x, double y, double z, EnumFacing enumfacing) {
      EntityEgg face = new EntityEgg(mc.theWorld);
      face.posX = x + 0.5;
      face.posY = y + 0.5;
      face.posZ = z + 0.5;
      face.posX = face.posX + (double)enumfacing.getDirectionVec().getX() * 0.5;
      face.posY = face.posY + (double)enumfacing.getDirectionVec().getY() * 0.5;
      face.posZ = face.posZ + (double)enumfacing.getDirectionVec().getZ() * 0.5;
      return getRotationFromPosition(face.posX, face.posY, face.posZ);
   }

   public static double getMouseGCD() {
      float sens = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float pow = sens * sens * sens * 8.0F;
      return (double)pow * 0.15;
   }

   public static float[] getNormalAuraRotations(
      float yaw, float pitch, Entity target, double x, double y, double z, float yawSpeed, float pitchSpeed, boolean hitVec
   ) {
      if (target != null) {
         pitchSpeed = (float)((double)pitchSpeed * 0.5);
         if (yawSpeed < 0.0F) {
            yawSpeed *= -1.0F;
         }

         if (pitchSpeed < 0.0F) {
            pitchSpeed *= -1.0F;
         }

         float sYaw = updateRotation(yaw, getInstantTargetRotation(target, x, y, z, hitVec)[0], yawSpeed);
         float sPitch = updateRotation(pitch, getInstantTargetRotation(target, x, y, z, hitVec)[1], pitchSpeed);
         yaw = updateRotation(yaw, sYaw, 360.0F);
         pitch = updateRotation(pitch, sPitch, 360.0F);
         if (pitch > 90.0F) {
            pitch = 90.0F;
         } else if (pitch < -90.0F) {
            pitch = -90.0F;
         }
      }

      return new float[]{yaw, pitch};
   }

   public static float updateRotation(float current, float intended, float factor) {
      float var4 = MathHelper.wrapAngleTo180_float(intended - current);
      if (var4 > factor) {
         var4 = factor;
      }

      if (var4 < -factor) {
         var4 = -factor;
      }

      return current + var4;
   }

   public static float[] getInstantTargetRotation(Entity ent, double x, double y, double z, boolean hitVec) {
      double eyeHeight = (double)ent.getEyeHeight();
      double playerY = mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight();
      if (playerY >= y + eyeHeight) {
         y += eyeHeight;
         y -= 0.4;
      } else if (!(playerY < y)) {
         y = playerY - 0.4;
      }

      if (!hitVec) {
         return getRotationFromPosition(x, y, z);
      } else {
         Vec3 best = getBestHitVec(ent);
         double nearest = 15.0;
         AxisAlignedBB boundingBox = ent.getEntityBoundingBox();

         for (double x1 = boundingBox.minX; x1 <= boundingBox.maxX; x1 += 0.07) {
            for (double z1 = boundingBox.minZ; z1 <= boundingBox.maxZ; z1 += 0.07) {
               for (double y1 = boundingBox.minY; y1 <= boundingBox.maxY; y1 += 0.07) {
                  Vec3 pos = new Vec3(x1, y1, z1);
                  if (mc.thePlayer.canPosBeSeen(pos)) {
                     Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
                     double dist = Math.sqrt(Math.pow(x1 - eyes.xCoord, 2.0) + Math.pow(y1 - eyes.yCoord, 2.0) + Math.pow(z1 - eyes.zCoord, 2.0));
                     if (dist <= nearest) {
                        nearest = dist;
                        best = pos;
                     }
                  }
               }
            }
         }

         return getRotationFromPosition(best.xCoord, y, best.zCoord);
      }
   }

   public static Vec3 getBestHitVec(Entity entity) {
      Vec3 positionEyes = mc.thePlayer.getPositionEyes(1.0F);
      float f11 = entity.getCollisionBorderSize();
      AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand((double)f11, (double)f11, (double)f11);
      double ex = MathHelper.clamp_double(positionEyes.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
      double ey = MathHelper.clamp_double(positionEyes.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
      double ez = MathHelper.clamp_double(positionEyes.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);
      return new Vec3(ex, ey - 0.4, ez);
   }

   public static float[] getRotationFromPosition(double x, double y, double z) {
      double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
      double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
      double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
      double dist = (double)MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
      float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0F;
      float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
      return new float[]{yaw, pitch};
   }

   public static float[] getRotationsFromPositionToPosition(double startX, double startY, double startZ, double posX, double posY, double posZ) {
      Vector3d to = new Vector3d(posX, posY, posZ);
      Vector3d from = new Vector3d(startX, startY, startZ);
      Vector3d diff = to.subtract(from);
      double distance = Math.hypot(diff.getX(), diff.getZ());
      float yaw = (float)Math.toDegrees(MathHelper.atan2(diff.getZ(), diff.getX())) - 90.0F;
      float pitch = (float)(-Math.toDegrees(MathHelper.atan2(diff.getY(), distance)));
      return new float[]{yaw, pitch};
   }

   public static Vec3 getVec3(BlockPos pos, EnumFacing face) {
      double x = (double)pos.getX() + 0.5;
      double y = (double)pos.getY() + 0.5;
      double z = (double)pos.getZ() + 0.5;
      x += (double)face.getFrontOffsetX() / 2.0;
      z += (double)face.getFrontOffsetZ() / 2.0;
      y += (double)face.getFrontOffsetY() / 2.0;
      if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
         y += new Random().nextDouble() / 2.0 - 0.25;
      } else {
         x += new Random().nextDouble() / 2.0 - 0.25;
         z += new Random().nextDouble() / 2.0 - 0.25;
      }

      if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
         z += new Random().nextDouble() / 2.0 - 0.25;
      }

      if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
         x += new Random().nextDouble() / 2.0 - 0.25;
      }

      return new Vec3(x, y, z);
   }
}
