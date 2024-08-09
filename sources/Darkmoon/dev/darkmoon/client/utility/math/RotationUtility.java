package dev.darkmoon.client.utility.math;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector2f;

public class RotationUtility implements Utility {
    public static Vector2f getDeltaForCoord(Vector2f rot, Vec3d point) {
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
    public static boolean checkIfVisible(float angleX, float angleY, float hitboxSize, Entity target, double distance) {
        Vec3d playerPos = mc.player.getPositionEyes(1.0F);
        Vec3d lookDirection = mc.player.getVectorForRotation(angleY, angleX);
        Vec3d reach = playerPos.add(lookDirection.scale(distance));
        RayTraceResult result = mc.world.rayTraceBlocks(playerPos, reach, false, false, true);
        if (result == null) {
            return false;
        } else {
            return target.getEntityBoundingBox().expand(hitboxSize).calculateIntercept(playerPos, reach) != null;
        }
    }
    public static boolean isHitBoxVisible(Entity target, Vec3d vector, double dst) {
        return RayCastUtility.getPointedEntity(getRotationForCoord(vector), dst, 1, !entityBehindWall(),
                target) == target;
    }
    public static boolean entityBehindWall() {
        BlockPos pos = new BlockPos(mc.player.lastReportedPosX, mc.player.lastReportedPosY, mc.player.lastReportedPosZ);
        return mc.world.getBlockState(pos).getMaterial() == Material.AIR;
    }
    public static boolean isHitBoxVisible(Vec3d vec3d) {
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight(), mc.player.posZ);
        return mc.world.rayTraceBlocks(eyesPos, vec3d, false, true, false) == null;
    }
    public static Vector2f getRotationForCoord(Vec3d point) {
        EntityPlayerSP client = Minecraft.getMinecraft().player;
        double x = point.x - client.posX;
        double y = point.y - client.getPositionEyes(1).y;
        double z = point.z - client.posZ;
        double dst = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        float yawToTarget = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90);
        float pitchToTarget = (float) (-Math.toDegrees(Math.atan2(y, dst)));
        return new Vector2f(yawToTarget, pitchToTarget);
    }

    public static float[] getCorrectRotation(Vec3d original, boolean flag, float value) {
        Vec3d vec = new Vec3d(mc.player.posX, mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight(), mc.player.posZ);
        double var4 = original.x - vec.x;
        double var6 = original.y - (mc.player.posY + mc.player.getEyeHeight() - value);
        double var8 = original.z - vec.z;
        double var10 = Math.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.toDegrees(Math.atan2(var8, var4)) - 90.0D) + (flag ? MathUtility.getRandomInRange(-2, 2) : 0);
        float var13 = (float)(-Math.toDegrees(Math.atan2(var6, var10))) + (flag ? MathUtility.getRandomInRange(-2, 2) : 0);
        var12 = mc.player.rotationYaw + GCDFixUtility.getFixedRotation(MathHelper.wrapDegrees(var12 - mc.player.rotationYaw));
        var13 = mc.player.rotationPitch + GCDFixUtility.getFixedRotation(MathHelper.wrapDegrees(var13 - mc.player.rotationPitch));
        var13 = MathHelper.clamp(var13, -90.0F, 90.0F);
        return new float[]{var12, var13};
    }
}
