/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import optifine.Reflector;
import optifine.ReflectorMethod;

public class RayCastUtilNew {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static Entity rayCast(double range, float yaw, float pitch) {
        double d0;
        double d1 = d0 = range;
        Vec3 vec3 = Minecraft.thePlayer.func_174824_e(1.0f);
        boolean flag = false;
        boolean flag1 = true;
        if (d0 > 3.0) {
            flag = true;
        }
        Vec3 vec31 = RayCastUtilNew.getVectorForRotation(pitch, yaw);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
        Entity pointedEntity = null;
        Vec3 vec33 = null;
        float f2 = 1.0f;
        List list = RayCastUtilNew.mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.func_175606_aa(), mc.func_175606_aa().getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f2, f2, f2));
        double d2 = d1;
        for (int i2 = 0; i2 < list.size(); ++i2) {
            double d3;
            Entity entity1 = (Entity)list.get(i2);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
                if (!(d2 >= 0.0)) continue;
                pointedEntity = entity1;
                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = vec3.distanceTo(movingobjectposition.hitVec)) < d2) && d2 != 0.0) continue;
            boolean flag2 = false;
            if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
            }
            if (entity1 == RayCastUtilNew.mc.func_175606_aa().ridingEntity && !flag2) {
                if (d2 != 0.0) continue;
                pointedEntity = entity1;
                vec33 = movingobjectposition.hitVec;
                continue;
            }
            pointedEntity = entity1;
            vec33 = movingobjectposition.hitVec;
            d2 = d3;
        }
        return pointedEntity;
    }

    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f2 = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        float f1 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        float f22 = -MathHelper.cos(-pitch * 0.017453292f);
        float f3 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3(f1 * f22, f3, f2 * f22);
    }
}

