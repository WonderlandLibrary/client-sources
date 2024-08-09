package im.expensive.utils.math;

import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import static net.minecraft.util.math.MathHelper.clamp;

@UtilityClass
public class VectorUtils {

    public Vector3d getClosestVec(Vector3d vec, AxisAlignedBB AABB) {
        return new Vector3d(
                clamp(vec.getX(), AABB.minX, AABB.maxX),
                clamp(vec.getY(), AABB.minY, AABB.maxY),
                clamp(vec.getZ(), AABB.minZ, AABB.maxZ)
        );
    }

    public Vector3d getClosestVec(Vector3d vec, Entity entity) {
        return getClosestVec(vec, entity.getBoundingBox());
    }
}
