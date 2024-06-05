package club.mega.util;

import club.mega.interfaces.MinecraftInterface;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import net.optifine.reflect.Reflector;

import java.util.List;

public final class RayCastUtil implements MinecraftInterface {

    private static Entity pointedEntity;

    public static boolean canSeeEntity(final EntityLivingBase entityLivingBase) {
    //    System.out.println(getMouseOver(600, RotationUtil.getRotations(RotationUtil.getNearestPositionInBB(MC.thePlayer.getPositionEyes(1.0F), entityLivingBase.getEntityBoundingBox()))[0], RotationUtil.getRotations(RotationUtil.getNearestPositionInBB(MC.thePlayer.getPositionEyes(1.0F), entityLivingBase.getEntityBoundingBox()))[1]).getName());
        return getMouseOver(600, RotationUtil.getRotations(RotationUtil.getNearestPositionInBB(MC.thePlayer.getPositionEyes(1.0F), entityLivingBase.getEntityBoundingBox()))[0], RotationUtil.getRotations(RotationUtil.getNearestPositionInBB(MC.thePlayer.getPositionEyes(1.0F), entityLivingBase.getEntityBoundingBox()))[1]) == entityLivingBase;
    }

    public static Entity getMouseOver(double range, final float yaw, final float pitch)
    {
        double d1 = range;
        double d2 = d1;
        Vec3 vec31 = MC.thePlayer.getPositionEyes(1.0F);
        boolean bool1 = false;
        boolean bool2 = true;
        if (d1 > 3.0D)
            bool1 = true;
        Vec3 vec32 = MC.thePlayer.getVectorForRotation(pitch, yaw);
        Vec3 vec33 = vec31.addVector(vec32.xCoord * d1, vec32.yCoord * d1, vec32.zCoord * d1);
        Entity entity = null;
        Vec3 vec34 = null;
        float f = 1.0F;
        List<Entity> list = MC.theWorld.getEntitiesInAABBexcluding(MC.getRenderViewEntity(), MC.getRenderViewEntity().getEntityBoundingBox().addCoord(vec32.xCoord * d1, vec32.yCoord * d1, vec32.zCoord * d1).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d3 = d2;
        for (byte b = 0; b < list.size(); b++) {
            Entity entity1 = list.get(b);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisAlignedBB = entity1.getEntityBoundingBox().expand(f1, f1, f1);
            MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(vec31, vec33);
            if (axisAlignedBB.isVecInside(vec31)) {
                if (d3 >= 0.0D) {
                    entity = entity1;
                    vec34 = (movingObjectPosition == null) ? vec31 : movingObjectPosition.hitVec;
                    d3 = 0.0D;
                }
            } else if (movingObjectPosition != null) {
                double d = vec31.distanceTo(movingObjectPosition.hitVec);
                if (d < d3 || d3 == 0.0D) {
                    boolean bool = false;
                    if (Reflector.ForgeEntity_canRiderInteract.exists())
                        bool = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                    if (entity1 == (MC.getRenderViewEntity()).ridingEntity && !bool) {
                        if (d3 == 0.0D) {
                            entity = entity1;
                            vec34 = movingObjectPosition.hitVec;
                        }
                    } else {
                        entity = entity1;
                        vec34 = movingObjectPosition.hitVec;
                        d3 = d;
                    }
                }
            }
        }
        return entity;
    }

}
