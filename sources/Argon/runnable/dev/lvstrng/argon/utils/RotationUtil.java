// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.interfaces.IVec3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class RotationUtil {
    public static Vec3d getCameraPos() {
        return RenderUtil.getCameraPos();
    }

    public static BlockPos method481() {
        return Argon.mc.getBlockEntityRenderDispatcher().camera.getBlockPos();
    }

    public static BlockPos method482() {
        return new BlockPos((int) getCameraPos().x, (int) getCameraPos().y, (int) getCameraPos().z);
    }

    public static Vec3d method483(final float yaw, final float pitch) {
        final float n = pitch * 0.017453292f;
        final float n2 = -yaw * 0.017453292f;
        final float cos = MathHelper.cos(n2);
        final float sin = MathHelper.sin(n2);
        final float cos2 = MathHelper.cos(n);
        return (Vec3d) new Vec3d(sin * cos2, -MathHelper.sin(n), cos * cos2);
    }

    public static Vec3d method484(final PlayerEntity player) {
        return method483(player.getYaw(), player.getPitch());
    }

    public static Rotation absRotation(final Rotation rotation1, final Rotation rotation2) {
        return new Rotation(Math.abs(Math.max(rotation1.getYaw(), rotation2.getYaw()) - Math.min(rotation1.getYaw(), rotation2.getYaw())), Math.abs(Math.max(rotation1.getPitch(), rotation2.getPitch()) - Math.min(rotation1.getPitch(), rotation2.getPitch())));
    }

    public static Rotation method486(final Rotation from, final Rotation to, final double speed) {
        return new Rotation(MathHelper.lerpAngleDegrees((float) speed, (float) from.getYaw(), (float) to.getYaw()), MathHelper.lerpAngleDegrees((float) speed, (float) from.getPitch(), (float) to.getPitch()));
    }

    public static double abs(final Rotation rotation1, final Rotation rotation2) {
        final Rotation rotation = absRotation(rotation1, rotation2);
        return rotation.getYaw() + rotation.getPitch();
    }

    public static Vec3d method489() {
        return method484(Argon.mc.player);
    }

    public static Rotation method490(final Entity entity, final Vec3d vec) {
        final double x = vec.x - entity.getX();
        final double y = vec.y - entity.getY();
        final double y2 = vec.z - entity.getZ();
        return new Rotation(MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(y2, x)) - 90.0), -MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(y, MathHelper.sqrt((float) (x * x + y2 * y2))))));
    }

    public static double method491(final Rotation rotation) {
        final double n = MathHelper.wrapDegrees(Argon.mc.player.getYaw());
        final double n2 = MathHelper.wrapDegrees(Argon.mc.player.getPitch());
        final double wrapDegrees = MathHelper.wrapDegrees(n - rotation.getYaw());
        final double wrapDegrees2 = MathHelper.wrapDegrees(n2 - rotation.getPitch());
        return Math.sqrt(wrapDegrees * wrapDegrees + wrapDegrees2 * wrapDegrees2);
    }

    private static boolean canAttack(final boolean b, final Entity entity) {
        return !entity.isSpectator() && entity.canHit() && entity.isInvisible() && !b;
    }
}