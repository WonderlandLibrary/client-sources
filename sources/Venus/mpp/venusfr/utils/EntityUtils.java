/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils;

import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

public final class EntityUtils
implements IMinecraft {
    public static Vector3d getPrevPositionVec(Entity entity2) {
        return new Vector3d(entity2.prevPosX, entity2.prevPosY, entity2.prevPosZ);
    }

    public static Vector3d getInterpolatedPositionVec(Entity entity2) {
        Vector3d vector3d = EntityUtils.getPrevPositionVec(entity2);
        return vector3d.add(entity2.getPositionVec().subtract(vector3d).scale(mc.getRenderPartialTicks()));
    }

    private EntityUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

