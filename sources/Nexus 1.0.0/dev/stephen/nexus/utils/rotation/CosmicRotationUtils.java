package dev.stephen.nexus.utils.rotation;

import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.mc.MoveUtils;
import lombok.Getter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CosmicRotationUtils implements Utils {
    private double xRandom = 0;
    private double yRandom = 0;
    private double zRandom = 0;
    private long lastNoiseRandom = System.currentTimeMillis();
    private double lastNoiseDeltaX = 0;
    private double lastNoiseDeltaY = 0;
    private double lastNoiseDeltaZ = 0;
    private final List<Box> boxHistory = new ArrayList<>(101);

    private boolean nearest = false;
    private double nearestAcc = 0.8;

    private boolean lazy = false;
    private double lazyAcc = 0.95;

    private boolean noise = false;
    private Pair<Float, Float> noiseRandom = new Pair<>(0.35F, 0.5F);
    private double noiseSpeed = 1;
    private long noiseDelay = 100;

    private boolean delay = false;
    private int delayTicks = 1;

    @Getter
    private Vec3d hitPos = Vec3d.ZERO;

    public void setNearest(boolean value, @Range(from = 0, to = 1) double acc) {
        this.nearestAcc = acc;
        this.nearest = value;
    }

    public void setLazy(boolean value, @Range(from = 0, to = 1) double acc) {
        this.lazyAcc = acc;
        this.lazy = value;
    }

    public void setNoise(boolean value, Pair<Float, Float> noiseRandom, double noiseSpeed, long noiseDelay) {
        this.noiseRandom = noiseRandom;
        this.noiseSpeed = noiseSpeed / 100;
        this.noiseDelay = noiseDelay;
        this.noise = value;
    }

    public void setDelay(boolean value, int delayTicks) {
        this.delayTicks = delayTicks;
        this.delay = value;
    }

    public @NotNull Pair<Float, Float> getRotation(@NotNull LivingEntity target) {
        Box targetBox = target.getBoundingBox();
        if (boxHistory.size() >= 101) {
            boxHistory.remove(boxHistory.size() - 1);
        }
        while (boxHistory.size() < 101) {
            boxHistory.add(0, targetBox);
        }

        float yaw, pitch;

        final double yDiff = target.getY() - mc.player.getY();
        Vec3d targetPosition;

        Box aimBox = delay ? boxHistory.get(delayTicks) : targetBox;
        if (nearest) {
            targetPosition = getNearestPoint(aimBox, mc.player.getEyePos());
            if (MoveUtils.isMoving() || MoveUtils.isMoving(target))
                targetPosition = targetPosition.add(randomizeDouble(nearestAcc - 1, 1 - nearestAcc) * 0.4, randomizeDouble(nearestAcc - 1, 1 - nearestAcc) * 0.4, randomizeDouble(nearestAcc - 1, 1 - nearestAcc) * 0.4);
        } else {
            targetPosition = new Vec3d((aimBox.maxX + aimBox.minX) / 2, aimBox.minY + target.getEyeHeight(target.getPose()) - 0.15, (aimBox.maxZ + aimBox.minZ) / 2);
        }


        if (yDiff >= 0 && lazy) {
            if (targetPosition.getY() - yDiff > target.getY()) {
                targetPosition = new Vec3d(targetPosition.getX(), targetPosition.getY() - yDiff, targetPosition.getZ());
            } else {
                targetPosition = new Vec3d(target.getX(), target.getY() + 0.2, target.getZ());
            }
            if (!target.isOnGround() && (MoveUtils.isMoving() || MoveUtils.isMoving(target)))
                targetPosition.y += randomizeDouble(lazyAcc - 1, 1 - lazyAcc) * 0.4;
        }

        if (noise) {
            if (System.currentTimeMillis() - lastNoiseRandom >= noiseDelay) {
                xRandom = random(noiseRandom.getLeft());
                yRandom = random(noiseRandom.getRight());
                zRandom = random(noiseRandom.getLeft());
                lastNoiseRandom = System.currentTimeMillis();
            }

            lastNoiseDeltaX = rotMove(xRandom, lastNoiseDeltaX, noiseSpeed);
            lastNoiseDeltaY = rotMove(yRandom, lastNoiseDeltaY, noiseSpeed);
            lastNoiseDeltaZ = rotMove(zRandom, lastNoiseDeltaZ, noiseSpeed);

            targetPosition.x = normal(targetBox.maxX, targetBox.minX, targetPosition.x + lastNoiseDeltaX);
            targetPosition.y = normal(targetBox.maxY, targetBox.minY, targetPosition.y + lastNoiseDeltaY);
            targetPosition.z = normal(targetBox.maxZ, targetBox.minZ, targetPosition.z + lastNoiseDeltaZ);
        }

        yaw = getYaw(targetPosition);
        pitch = getPitch(targetPosition);
        hitPos = targetPosition;

        return new Pair<>(yaw, pitch);
    }

    public static @NotNull Vec3d getNearestPoint(@NotNull Box from, @NotNull Vec3d to) {
        double pointX, pointY, pointZ;
        if (to.getX() >= from.maxX) {
            pointX = from.maxX;
        } else pointX = Math.max(to.getX(), from.minX);
        if (to.getY() >= from.maxY) {
            pointY = from.maxY;
        } else pointY = Math.max(to.getY(), from.minY);
        if (to.getZ() >= from.maxZ) {
            pointZ = from.maxZ;
        } else pointZ = Math.max(to.getZ(), from.minZ);

        return new Vec3d(pointX, pointY, pointZ);
    }

    private static float random(double multiple) {
        return (float) ((Math.random() - 0.5) * 2 * multiple);
    }

    public static double randomizeDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    private static double normal(double max, double min, double current) {
        if (current >= max) return max;
        return Math.max(current, min);
    }

    public static float rotMove(double target, double current, double diff) {
        return rotMoveNoRandom((float) target, (float) current, (float) diff);
    }

    public static float rotMoveNoRandom(float target, float current, float diff) {
        float delta;
        if (target > current) {
            float dist1 = target - current;
            float dist2 = current + 360 - target;
            if (dist1 > dist2) {
                delta = -current - 360 + target;
            } else {
                delta = dist1;
            }
        } else if (target < current) {
            float dist1 = current - target;
            float dist2 = target + 360 - current;
            if (dist1 > dist2) {
                delta = current + 360 + target;
            } else {
                delta = -dist1;
            }
        } else {
            return current;
        }

        delta = normalize(delta);

        if (Math.abs(delta) < 0.1 * Math.random() + 0.1) {
            return current;
        } else if (Math.abs(delta) <= diff) {
            return current + delta;
        } else {
            if (delta < 0) {
                return current - diff;
            } else if (delta > 0) {
                return current + diff;
            } else {
                return current;
            }
        }
    }

    public static boolean yawEquals(float yaw1, float yaw2) {
        return Math.abs(normalize(yaw1) - normalize(yaw2)) < 0.1;
    }

    public static boolean equals(@NotNull Vec2 rot1, @NotNull Vec2 rot2) {
        return yawEquals(rot1.x, rot2.x) && Math.abs(rot1.y - rot2.y) < 0.1;
    }

    public static float normalize(float yaw) {
        yaw %= 360.0F;
        if (yaw >= 180.0F) {
            yaw -= 360.0F;
        }
        if (yaw < -180.0F) {
            yaw += 360.0F;
        }

        return yaw;
    }

    public static float getYaw(@NotNull LivingEntity from, @NotNull Vec3d pos) {
        return from.getYaw() + MathUtils.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(pos.getZ() - from.getZ(), pos.getX() - from.getX())) - 90f - from.getYaw());
    }

    public static float getYaw(@NotNull Vec3d pos) {
        return getYaw(mc.player, pos);
    }

    public static float getPitch(@NotNull Vec3d pos) {
        return RotationUtils.calculate(pos)[1];
    }
}

class Vec2 {
    public static final Vec2 ZERO = new Vec2(0.0f, 0.0f);
    public static final Vec2 ONE = new Vec2(1.0f, 1.0f);
    public static final Vec2 UNIT_X = new Vec2(1.0f, 0.0f);
    public static final Vec2 NEG_UNIT_X = new Vec2(-1.0f, 0.0f);
    public static final Vec2 UNIT_Y = new Vec2(0.0f, 1.0f);
    public static final Vec2 NEG_UNIT_Y = new Vec2(0.0f, -1.0f);
    public static final Vec2 MAX = new Vec2(Float.MAX_VALUE, Float.MAX_VALUE);
    public static final Vec2 MIN = new Vec2(Float.MIN_VALUE, Float.MIN_VALUE);
    public float x;
    public float y;

    public Vec2(float f, float g) {
        this.x = f;
        this.y = g;
    }

    public Vec2 scale(float f) {
        return new Vec2(this.x * f, this.y * f);
    }

    public float dot(@NotNull Vec2 vec2) {
        return this.x * vec2.x + this.y * vec2.y;
    }

    public Vec2 add(@NotNull Vec2 vec2) {
        return new Vec2(this.x + vec2.x, this.y + vec2.y);
    }

    public Vec2 add(float f) {
        return new Vec2(this.x + f, this.y + f);
    }

    public Vec2 add(float x, float y) {
        return new Vec2(this.x + x, this.y + y);
    }

    public boolean equals(@NotNull Vec2 vec2) {
        return CosmicRotationUtils.yawEquals(this.x, vec2.x) && this.y == vec2.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec2 = (Vec2) o;
        return this.equals(vec2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vec2 normalized() {
        float f = MathUtils.sqrt_float(this.x * this.x + this.y * this.y);
        return f < 1.0E-4f ? ZERO : new Vec2(this.x / f, this.y / f);
    }

    public float length() {
        return MathUtils.sqrt_float(this.x * this.x + this.y * this.y);
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public float distanceToSqr(@NotNull Vec2 vec2) {
        float f = vec2.x - this.x;
        float g = vec2.y - this.y;
        return f * f + g * g;
    }

    public Vec2 negated() {
        return new Vec2(-this.x, -this.y);
    }
}