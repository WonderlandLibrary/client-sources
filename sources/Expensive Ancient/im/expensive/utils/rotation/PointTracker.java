package im.expensive.utils.rotation;

import com.viaversion.viaversion.libs.kyori.adventure.util.Listenable;
import im.expensive.utils.client.IMinecraft;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

import static net.minecraft.util.math.MathHelper.clamp;

public class PointTracker implements IMinecraft {
    private final SecureRandom random = new SecureRandom();
    private final Vector3d currentOffset = Vector3d.ZERO;

    public Point calculateVectorToTarget(Entity target, double attackRange) {
        double eyePositionYOffset = mc.player.getEyePosition(1).y - target.getPosY();
        double maxOffset = target.getHeight() * (RotationUtils.getDistanceEyePos(target) / attackRange);
        double clampedYOffset = clamp(eyePositionYOffset, 0, maxOffset);

        Vector3d vector3d = target.getPositionVec().add(0, clampedYOffset, 0);
        AxisAlignedBB box = target.getBoundingBox();
        return new Point(vector3d, box);
    }

    public Vector3d getSpot(LivingEntity entity, float attackRange) {
        Point point = calculateVectorToTarget(entity, attackRange);
        Vector3d toPoint = point.getToPoint();
        AxisAlignedBB box = point.getBox();
        Vector3d spot = RotationUtils.raytraceBox(toPoint, box, Math.pow(attackRange, 2));

        if (spot == null) {
            spot = toPoint;
        }
        return spot;
    }

    private Vector3d updateGaussianOffset() {
        Vector3d nextOffset = currentOffset.add(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
        return nextOffset.mul(0.05, 0.05, 0.05);
    }

    @Value
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Point {
        Vector3d toPoint;
        AxisAlignedBB box;
    }
}
