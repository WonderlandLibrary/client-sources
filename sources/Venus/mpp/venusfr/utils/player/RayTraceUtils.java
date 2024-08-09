/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public final class RayTraceUtils
implements IMinecraft {
    public static boolean rayTraceSingleEntity(float f, float f2, double d, Entity entity2) {
        Vector3d vector3d = RayTraceUtils.mc.player.getEyePosition(1.0f);
        Vector3d vector3d2 = RayTraceUtils.mc.player.getVectorForRotation(f2, f);
        Vector3d vector3d3 = vector3d.add(vector3d2.scale(d));
        AxisAlignedBB axisAlignedBB = entity2.getBoundingBox();
        return axisAlignedBB.contains(vector3d) || axisAlignedBB.rayTrace(vector3d, vector3d3).isPresent();
    }

    public static boolean isHitBoxNotVisible(Vector3d vector3d) {
        RayTraceContext rayTraceContext = new RayTraceContext(RayTraceUtils.mc.player.getEyePosition(1.0f), vector3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, RayTraceUtils.mc.player);
        BlockRayTraceResult blockRayTraceResult = RayTraceUtils.mc.world.rayTraceBlocks(rayTraceContext);
        return blockRayTraceResult.getType() == RayTraceResult.Type.MISS;
    }

    private RayTraceUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

