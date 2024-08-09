/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public final class MathUtil
implements IMinecraft {
    public static double interpolate(double d, double d2, double d3) {
        return d2 + (d - d2) * d3;
    }

    public static boolean isHovered(float f, float f2, float f3, float f4, float f5, float f6) {
        return f > f3 && f < f3 + f5 && f2 > f4 && f2 < f4 + f6;
    }

    public static Vector2f rotationToVec(Vector3d vector3d) {
        Vector3d vector3d2 = MathUtil.mc.player.getEyePosition(1.0f);
        double d = vector3d != null ? vector3d.x - vector3d2.x : 0.0;
        double d2 = vector3d != null ? vector3d.y - (MathUtil.mc.player.getPosY() + (double)MathUtil.mc.player.getEyeHeight() + 0.5) : 0.0;
        double d3 = vector3d != null ? vector3d.z - vector3d2.z : 0.0;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)(Math.toDegrees(Math.atan2(d3, d)) - 90.0);
        float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
        f = MathUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(f - MathUtil.mc.player.rotationYaw);
        f2 = MathUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(f2 - MathUtil.mc.player.rotationPitch);
        f2 = MathHelper.clamp(f2, -90.0f, 90.0f);
        return new Vector2f(f, f2);
    }

    public static Vector2f rotationToEntity(Entity entity2) {
        Vector3d vector3d = entity2.getPositionVec().subtract(Minecraft.getInstance().player.getPositionVec());
        double d = Math.hypot(vector3d.x, vector3d.z);
        return new Vector2f((float)Math.toDegrees(Math.atan2(vector3d.z, vector3d.x)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vector3d.y, d))));
    }

    public static Vector2f rotationToVec(Vector2f vector2f, Vector3d vector3d) {
        double d = vector3d.x - MathUtil.mc.player.getPosX();
        double d2 = vector3d.y - MathUtil.mc.player.getEyePosition((float)1.0f).y;
        double d3 = vector3d.z - MathUtil.mc.player.getPosZ();
        double d4 = Math.sqrt(Math.pow(d, 2.0) + Math.pow(d3, 2.0));
        float f = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(d3, d)) - 90.0);
        float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
        float f3 = MathHelper.wrapDegrees(f - vector2f.x);
        float f4 = f2 - vector2f.y;
        if (Math.abs(f3) > 180.0f) {
            f3 -= Math.signum(f3) * 360.0f;
        }
        return new Vector2f(f3, f4);
    }

    public static double round(double d, double d2) {
        double d3 = (double)Math.round(d / d2) * d2;
        BigDecimal bigDecimal = new BigDecimal(d3);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double distance(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        return Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
    }

    public static double distance(double d, double d2, double d3, double d4) {
        double d5 = d - d3;
        double d6 = d2 - d4;
        return Math.sqrt(d5 * d5 + d6 * d6);
    }

    public static double deltaTime() {
        return MathUtil.mc.debugFPS > 0 ? 1.0 / (double)MathUtil.mc.debugFPS : 1.0;
    }

    public static float fast(float f, float f2, float f3) {
        return (1.0f - MathHelper.clamp((float)(MathUtil.deltaTime() * (double)f3), 0.0f, 1.0f)) * f + MathHelper.clamp((float)(MathUtil.deltaTime() * (double)f3), 0.0f, 1.0f) * f2;
    }

    public static Vector3d interpolate(Vector3d vector3d, Vector3d vector3d2, float f) {
        return new Vector3d(MathUtil.interpolate(vector3d.getX(), vector3d2.getX(), (double)f), MathUtil.interpolate(vector3d.getY(), vector3d2.getY(), (double)f), MathUtil.interpolate(vector3d.getZ(), vector3d2.getZ(), (double)f));
    }

    public static Vector3d fast(Vector3d vector3d, Vector3d vector3d2, float f) {
        return new Vector3d(MathUtil.fast((float)vector3d.getX(), (float)vector3d2.getX(), f), MathUtil.fast((float)vector3d.getY(), (float)vector3d2.getY(), f), MathUtil.fast((float)vector3d.getZ(), (float)vector3d2.getZ(), f));
    }

    public static float lerp(float f, float f2, float f3) {
        return (float)((double)f + (double)(f2 - f) * MathHelper.clamp(MathUtil.deltaTime() * (double)f3, 0.0, 1.0));
    }

    public static double lerp(double d, double d2, double d3) {
        return d + (d2 - d) * MathHelper.clamp(MathUtil.deltaTime() * d3, 0.0, 1.0);
    }

    public static float random(float f, float f2) {
        return (float)(Math.random() * (double)(f2 - f) + (double)f);
    }

    public static float randomizeFloat(float f, float f2) {
        return (float)(Math.random() * (double)(f2 - f)) + f;
    }

    private MathUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

