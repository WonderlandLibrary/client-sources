package net.augustus.utils;

import com.google.common.base.Predicates;
import java.util.List;
import net.augustus.utils.interfaces.MC;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import optifine.Reflector;

public class RayTraceUtil implements MC {
   public static MovingObjectPosition getHitVec(BlockPos blockPos, float yaw, float pitch, double range) {
      Vec3 vec31 = mc.thePlayer.getVectorForRotation(pitch, yaw);
      Vec3 vec32 = mc.thePlayer.getPositionEyes(1.0F).addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
      Block block = mc.theWorld.getBlockState(blockPos).getBlock();
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(
         (double)blockPos.getX(),
         (double)blockPos.getY(),
         (double)blockPos.getZ(),
         (double)blockPos.getX() + block.getBlockBoundsMaxX(),
         (double)blockPos.getY() + block.getBlockBoundsMaxY(),
         (double)blockPos.getZ() + block.getBlockBoundsMaxZ()
      );
      return axisalignedbb.calculateIntercept(mc.thePlayer.getPositionEyes(1.0F), vec32);
   }

   public static MovingObjectPosition rayCast(float partialTicks) {
      MovingObjectPosition objectMouseOver = null;
      Entity entity = mc.getRenderViewEntity();
      if (entity != null && mc.theWorld != null) {
         mc.mcProfiler.startSection("pick");
         mc.pointedEntity = null;
         double d0 = (double)mc.playerController.getBlockReachDistance();
         objectMouseOver = entity.rayTrace(d0, partialTicks);
         double d1 = d0;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         boolean flag1 = true;
         if (mc.playerController.extendedReach()) {
            d0 = 6.0;
            d1 = 6.0;
         } else {
            if (d0 > 3.0) {
               flag = true;
            }

            d0 = d0;
         }

         if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3);
         }

         Vec3 vec31 = entity.getLook(partialTicks);
         Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
         Entity pointedEntity = null;
         Vec3 vec33 = null;
         float f = 1.0F;
         List list = mc.theWorld
            .getEntitiesInAABBexcluding(
               entity,
               entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f),
               Predicates.and(EntitySelectors.NOT_SPECTATING)
            );
         double d2 = d1;
         AxisAlignedBB realBB = null;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d2 >= 0.0) {
                  pointedEntity = entity1;
                  vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                  d2 = 0.0;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0) {
                  boolean flag2 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (entity1 != entity.ridingEntity || flag2) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                     d2 = d3;
                  } else if (d2 == 0.0) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                  }
               }
            }
         }

         if (pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0) {
            pointedEntity = null;
            objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
         }

         if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
            if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
               ;
            }
         }
      }

      return objectMouseOver;
   }

   public static MovingObjectPosition rayCast(float partialTicks, float[] rots) {
      MovingObjectPosition objectMouseOver = null;
      Entity entity = mc.getRenderViewEntity();
      if (entity != null && mc.theWorld != null) {
         mc.mcProfiler.startSection("pick");
         mc.pointedEntity = null;
         double d0 = (double)mc.playerController.getBlockReachDistance();
         objectMouseOver = entity.customRayTrace(d0, partialTicks, rots[0], rots[1]);
         double d1 = d0;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         boolean flag1 = true;
         if (mc.playerController.extendedReach()) {
            d0 = 6.0;
            d1 = 6.0;
         } else {
            if (d0 > 3.0) {
               flag = true;
            }

            d0 = d0;
         }

         if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3);
         }

         Vec3 vec31 = entity.getCustomLook(partialTicks, rots[0], rots[1]);
         Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
         Entity pointedEntity = null;
         Vec3 vec33 = null;
         float f = 1.0F;
         List list = mc.theWorld
            .getEntitiesInAABBexcluding(
               entity,
               entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f),
               Predicates.and(EntitySelectors.NOT_SPECTATING)
            );
         double d2 = d1;
         AxisAlignedBB realBB = null;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d2 >= 0.0) {
                  pointedEntity = entity1;
                  vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                  d2 = 0.0;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0) {
                  boolean flag2 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (entity1 != entity.ridingEntity || flag2) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                     d2 = d3;
                  } else if (d2 == 0.0) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                  }
               }
            }
         }

         if (pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0) {
            pointedEntity = null;
            objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
         }

         if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
            if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
               ;
            }
         }
      }

      return objectMouseOver;
   }

   public static MovingObjectPosition rayCast(float partialTicks, float[] rots, double range, double hitBoxExpand) {
      MovingObjectPosition objectMouseOver = null;
      Entity entity = mc.getRenderViewEntity();
      if (entity != null && mc.theWorld != null) {
         mc.mcProfiler.startSection("pick");
         mc.pointedEntity = null;
         objectMouseOver = entity.customRayTrace(range, partialTicks, rots[0], rots[1]);
         double d1 = range;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         boolean flag1 = true;
         double d0;
         if (mc.playerController.extendedReach()) {
            d0 = 6.0;
            d1 = 6.0;
         } else {
            if (range > 3.0) {
               flag = true;
            }

            d0 = range;
         }

         if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3);
         }

         Vec3 vec31 = entity.getCustomLook(partialTicks, rots[0], rots[1]);
         Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
         Entity pointedEntity = null;
         Vec3 vec33 = null;
         float f = 1.0F;
         List list = mc.theWorld
            .getEntitiesInAABBexcluding(
               entity,
               entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f),
               Predicates.and(EntitySelectors.NOT_SPECTATING)
            );
         double d2 = d1;
         AxisAlignedBB realBB = null;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            float f1 = (float)((double)entity1.getCollisionBorderSize() + hitBoxExpand);
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d2 >= 0.0) {
                  pointedEntity = entity1;
                  vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                  d2 = 0.0;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0) {
                  boolean flag2 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (entity1 != entity.ridingEntity || flag2) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                     d2 = d3;
                  } else if (d2 == 0.0) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                  }
               }
            }
         }

         if (pointedEntity != null && flag && vec3.distanceTo(vec33) > range) {
            pointedEntity = null;
            objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
         }

         if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
            if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
               ;
            }
         }
      }

      return objectMouseOver;
   }

   public static boolean couldHit(Entity hitEntity, float partialTicks, float currentYaw, float currentPitch, float yawSpeed, float pitchSpeed) {
      new RotationUtil();
      Vec3 positionEyes = mc.thePlayer.getPositionEyes(partialTicks);
      float f11 = hitEntity.getCollisionBorderSize();
      double ex = MathHelper.clamp_double(
         positionEyes.xCoord, hitEntity.getEntityBoundingBox().minX - (double)f11, hitEntity.getEntityBoundingBox().maxX + (double)f11
      );
      double ey = MathHelper.clamp_double(
         positionEyes.yCoord, hitEntity.getEntityBoundingBox().minY - (double)f11, hitEntity.getEntityBoundingBox().maxY + (double)f11
      );
      double ez = MathHelper.clamp_double(
         positionEyes.zCoord, hitEntity.getEntityBoundingBox().minZ - (double)f11, hitEntity.getEntityBoundingBox().maxZ + (double)f11
      );
      double x = ex - mc.thePlayer.posX;
      double y = ey - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double z = ez - mc.thePlayer.posZ;
      float calcYaw = (float)(MathHelper.func_181159_b(z, x) * 180.0 / Math.PI - 90.0);
      float calcPitch = (float)(-(MathHelper.func_181159_b(y, (double)MathHelper.sqrt_double(x * x + z * z)) * 180.0 / Math.PI));
      float yaw = RotationUtil.updateRotation(currentYaw, calcYaw, 180.0F);
      float pitch = RotationUtil.updateRotation(currentPitch, calcPitch, 180.0F);
      MovingObjectPosition objectMouseOver = null;
      Entity entity = mc.getRenderViewEntity();
      if (entity != null && mc.theWorld != null) {
         mc.mcProfiler.startSection("pick");
         mc.pointedEntity = null;
         double d0 = (double)mc.playerController.getBlockReachDistance();
         objectMouseOver = entity.customRayTrace(d0, partialTicks, yaw, pitch);
         double d1 = d0;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         boolean flag1 = true;
         if (mc.playerController.extendedReach()) {
            d0 = 6.0;
            d1 = 6.0;
         } else {
            if (d0 > 3.0) {
               flag = true;
            }

            d0 = d0;
         }

         if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3);
         }

         Vec3 vec31 = entity.getCustomLook(partialTicks, yaw, pitch);
         Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
         Entity pointedEntity = null;
         Vec3 vec33 = null;
         float f = 1.0F;
         List list = mc.theWorld
            .getEntitiesInAABBexcluding(
               entity,
               entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f),
               Predicates.and(EntitySelectors.NOT_SPECTATING)
            );
         double d2 = d1;
         AxisAlignedBB realBB = null;

         for(int i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d2 >= 0.0) {
                  pointedEntity = entity1;
                  vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                  d2 = 0.0;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0) {
                  boolean flag2 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (entity1 != entity.ridingEntity || flag2) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                     d2 = d3;
                  } else if (d2 == 0.0) {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                  }
               }
            }
         }

         if (pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0) {
            pointedEntity = null;
            objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
         }

         if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
            if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
               ;
            }
         }
      }

      return objectMouseOver != null
         && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY
         && objectMouseOver.entityHit.getEntityId() == hitEntity.getEntityId();
   }
}
