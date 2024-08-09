package dev.darkmoon.client.utility.math;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import org.lwjgl.util.vector.Vector2f;

public class AdvancedCast implements Utility {
    public static AdvancedCast instance = new AdvancedCast();

    public Entity getMouseOver(Entity target, float yaw, float pitch, double distance, boolean ignoreWalls) {
        double d3;
        Minecraft mc = Minecraft.getMinecraft();
        Entity entity = mc.getRenderViewEntity();
        if (entity == null) return null;
        if (mc.world == null) {
            return null;
        }
        RayTraceResult objectMouseOver = ignoreWalls ? null : DistanceUtility.rayTrace(distance, yaw, pitch);
        Vec3d vec3d = entity.getPositionEyes(1.0f);
        boolean flag = false;
        double d1 = distance;
        if (distance > 3.0) {
            flag = true;
        }
        if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3d);
        }
        Vec3d vec3d2 = DistanceUtility.getVectorForRotation(pitch, yaw);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * distance, vec3d2.y * distance, vec3d2.z * distance);
        Entity pointedEntity = null;
        Vec3d vec3d4 = null;
        double d2 = d1;
        AxisAlignedBB axisalignedbb = target.getEntityBoundingBox().grow((double)target.getCollisionBorderSize());
        RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d3);
        if (axisalignedbb.contains(vec3d)) {
            if (d2 >= 0.0) {
                pointedEntity = target;
                vec3d4 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
                d2 = 0.0;
            }
        } else if (raytraceresult != null && ((d3 = vec3d.distanceTo(raytraceresult.hitVec)) < d2 || d2 == 0.0)) {
            boolean flag2 = false;
            if (target.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
                if (d2 == 0.0) {
                    pointedEntity = target;
                    vec3d4 = raytraceresult.hitVec;
                }
            } else {
                pointedEntity = target;
                vec3d4 = raytraceresult.hitVec;
                d2 = d3;
            }
        }
        if (pointedEntity != null && flag && vec3d.distanceTo(vec3d4) > distance) {
            pointedEntity = null;
            objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d4, null, new BlockPos(vec3d4));
        }
        if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            objectMouseOver = new RayTraceResult(pointedEntity, vec3d4);
        }
        if (objectMouseOver != null) return objectMouseOver.entityHit;
        return null;
    }
    public static Vector2f getVectorForCoord(Vector2f rot, Vec3d point) {
        EntityPlayerSP client = Minecraft.getMinecraft().player;
        double x = point.x - client.posX;
        double y = point.y - client.getPositionEyes(1).y;
        double z = point.z - client.posZ;
        double dst = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        float yawToTarget = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90);
        float pitchToTarget = (float) (-Math.toDegrees(Math.atan2(y, dst)));
        float yawDelta = MathHelper.wrapDegrees(yawToTarget - rot.x);
        float pitchDelta = (pitchToTarget - rot.y);
        return new Vector2f(yawDelta, pitchDelta);
    }
    public RayTraceResult rayTrace(double blockReachDistance, float yaw, float pitch) {
        Vec3d vec3d = Minecraft.getMinecraft().player.getPositionEyes(1);
        Vec3d vec3d1 = getVectorForRotation(pitch, yaw);
        Vec3d vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance,
                vec3d1.z * blockReachDistance);
        return Minecraft.getMinecraft().world.rayTraceBlocks(vec3d, vec3d2, true, true, true);
    }

    protected final Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }
}