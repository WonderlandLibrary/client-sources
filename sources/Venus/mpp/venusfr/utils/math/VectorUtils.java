/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public final class VectorUtils {
    public static Vector3d getClosestVec(Vector3d vector3d, AxisAlignedBB axisAlignedBB) {
        return new Vector3d(MathHelper.clamp(vector3d.getX(), axisAlignedBB.minX, axisAlignedBB.maxX), MathHelper.clamp(vector3d.getY(), axisAlignedBB.minY, axisAlignedBB.maxY), MathHelper.clamp(vector3d.getZ(), axisAlignedBB.minZ, axisAlignedBB.maxZ));
    }

    public static Vector3d getClosestVec(Vector3d vector3d, Entity entity2) {
        return VectorUtils.getClosestVec(vector3d, entity2.getBoundingBox());
    }

    private VectorUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

