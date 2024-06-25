package cc.slack.utils.other;

import cc.slack.utils.client.mc;
import cc.slack.utils.player.RotationUtil;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class RaycastUtil {
   public static EntityLivingBase rayCast(double range) {
      return rayCast(range, RotationUtil.clientRotation);
   }

   public static EntityLivingBase rayCast(double range, float[] rotations) {
      Vec3 eyes = mc.getPlayer().getPositionEyes(mc.getTimer().renderPartialTicks);
      Vec3 look = mc.getPlayer().getVectorForRotation(rotations[1], rotations[0]);
      Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
      List<Entity> entities = mc.getWorld().getEntitiesInAABBexcluding(mc.getPlayer(), mc.getPlayer().getEntityBoundingBox().addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
      EntityLivingBase raycastedEntity = null;
      Iterator var8 = entities.iterator();

      while(true) {
         while(true) {
            EntityLivingBase entity;
            do {
               if (!var8.hasNext()) {
                  return raycastedEntity;
               }

               Entity ent = (Entity)var8.next();
               if (!(ent instanceof EntityLivingBase)) {
                  return null;
               }

               entity = (EntityLivingBase)ent;
            } while(entity == mc.getPlayer());

            float borderSize = entity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double)borderSize, (double)borderSize, (double)borderSize);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);
            if (axisalignedbb.isVecInside(eyes)) {
               if (range >= 0.0D) {
                  raycastedEntity = entity;
                  range = 0.0D;
               }
            } else if (movingobjectposition != null) {
               double distance = eyes.distanceTo(movingobjectposition.hitVec);
               if (distance < range || range == 0.0D) {
                  if (entity == entity.ridingEntity) {
                     if (range == 0.0D) {
                        raycastedEntity = entity;
                     }
                  } else {
                     raycastedEntity = entity;
                     range = distance;
                  }
               }
            }
         }
      }
   }

   public static Vec3 rayCastHitVec(EntityLivingBase entity, double range, float[] rotations) {
      Vec3 eyes = mc.getPlayer().getPositionEyes(mc.getTimer().renderPartialTicks);
      Vec3 look = mc.getPlayer().getVectorForRotation(rotations[1], rotations[0]);
      Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
      float borderSize = entity.getCollisionBorderSize();
      AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double)borderSize, (double)borderSize, (double)borderSize);
      MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);
      return movingobjectposition == null ? null : movingobjectposition.hitVec;
   }
}
