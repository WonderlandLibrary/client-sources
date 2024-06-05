package me.satisfactory.base.utils.aura;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import optifine.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;

public class RayTraceUtil
{
    private static Minecraft mc;
    
    public static Entity rayTrace(final float yaw, final float pitch, final double range) {
        final Entity entity = RayTraceUtil.mc.getRenderViewEntity();
        if (entity != null && RayTraceUtil.mc.theWorld != null) {
            RayTraceUtil.mc.pointedEntity = null;
            final double d0 = range;
            final float partialTicks = 1.0f;
            RayTraceUtil.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
            final double d2 = d0;
            final Vec3 vec3 = entity.getPositionEyes(partialTicks);
            final Vec3 vec4 = RayTraceUtil.mc.thePlayer.getRotationVec(pitch, yaw);
            final Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
            Entity pointedEntity = null;
            Vec3 vec6 = null;
            final float f = 1.0f;
            final List list = RayTraceUtil.mc.theWorld.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(f, f, f));
            double d3 = d2;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i) instanceof EntityLivingBase) {
                    final EntityLivingBase entity2 = list.get(i);
                    final float f2 = entity2.getCollisionBorderSize();
                    final AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().expand(f2, f2, f2);
                    final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec5);
                    if (axisalignedbb.isVecInside(vec3)) {
                        if (d3 >= 0.0) {
                            pointedEntity = entity2;
                            vec6 = ((movingobjectposition == null) ? vec3 : movingobjectposition.hitVec);
                            d3 = 0.0;
                        }
                    }
                    else if (movingobjectposition != null) {
                        final double d4 = vec3.distanceTo(movingobjectposition.hitVec);
                        if (d4 < d3 || d3 == 0.0) {
                            boolean flag2 = false;
                            if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                                flag2 = Reflector.callBoolean(entity2, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                            }
                            if (entity2 == entity.ridingEntity && !flag2) {
                                if (d3 == 0.0) {
                                    pointedEntity = entity2;
                                    vec6 = movingobjectposition.hitVec;
                                }
                            }
                            else {
                                pointedEntity = entity2;
                                vec6 = movingobjectposition.hitVec;
                                d3 = d4;
                            }
                        }
                    }
                }
                if (pointedEntity != null && (d3 < d2 || RayTraceUtil.mc.objectMouseOver == null)) {
                    RayTraceUtil.mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec6);
                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                        return pointedEntity;
                    }
                }
            }
        }
        return null;
    }
    
    static {
        RayTraceUtil.mc = Minecraft.getMinecraft();
    }
}
