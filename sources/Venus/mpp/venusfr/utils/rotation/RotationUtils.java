/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.rotation;

import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.VectorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

public final class RotationUtils
implements IMinecraft {
    public static Vector3d getClosestVec(Entity entity2) {
        Vector3d vector3d = RotationUtils.mc.player.getEyePosition(1.0f);
        return VectorUtils.getClosestVec(vector3d, entity2).subtract(vector3d);
    }

    public static double getStrictDistance(Entity entity2) {
        return RotationUtils.getClosestVec(entity2).length();
    }

    private RotationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

