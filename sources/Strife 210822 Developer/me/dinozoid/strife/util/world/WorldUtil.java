package me.dinozoid.strife.util.world;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import me.dinozoid.strife.module.implementations.combat.KillAuraModule;
import me.dinozoid.strife.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public final class WorldUtil extends MinecraftUtil {

    private static double lastRaycastRange = -1;

    public static List<EntityLivingBase> getLivingEntities(Predicate<EntityLivingBase> validator) {
        List<EntityLivingBase> entities = new ArrayList<>();
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase e = (EntityLivingBase) entity;
                if (validator.apply(e))
                    entities.add(e);
            }
        }
        return entities;
    }

    public static boolean checkPing(EntityPlayer entityPlayer) {
        NetworkPlayerInfo info = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());
        return info != null && info.getResponseTime() >= 1;
    }

    public static EntityLivingBase raycast(double range, float[] rotations) {
        // code from getMouseOver() in EntityRenderer
        // get position vector with yPos of eyes
        Vec3 eyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        // get vector for rotation
        Vec3 look = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
        // make a vector encasing the range
        Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        // get all entities in the bounding box (only players)
        List<Entity> entities = mc.theWorld.getEntitiesInAABBexcluding(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(1, 1, 1), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith, EntityLivingBase.class::isInstance, (entity) -> KillAuraModule.instance().isValid((EntityLivingBase) entity)));
        EntityLivingBase raycastedEntity = null;
        for(Entity ent : entities) {
            final EntityLivingBase entity = (EntityLivingBase) ent;
            if(entity == mc.thePlayer) continue;

            // get border size of the bounding box
            final float borderSize = entity.getCollisionBorderSize();
            // expand it so that its not part of the collision
            final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
            // calculate where the eyes and the vec intercept
            final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);

            // check if player is inside mc.thePlayer
            if (axisalignedbb.isVecInside(eyes)) {
                if (range >= 0) {
                    raycastedEntity = entity;
                    range = 0;
                }
            // if found object and not in mc.thePlayer
            } else if (movingobjectposition != null) {
                // get the distance to the object
                double distance = eyes.distanceTo(movingobjectposition.hitVec);
                // check if the distance is in range or 0
                if (distance < range || range == 0) {
                    // check if riding entity
                    if (entity == entity.ridingEntity) {
                        // set the raycastedEntity to the current entity in the loop
                        if (range == 0) raycastedEntity = entity;
                    // not the riding entity
                    } else {
                        // set raycastedEntity and range
                        raycastedEntity = entity;
                        range = distance;
                    }
                }
            }
        }
        lastRaycastRange = range;
        return raycastedEntity;
    }

    public static double lastRaycastRange() {
        return lastRaycastRange;
    }
}
