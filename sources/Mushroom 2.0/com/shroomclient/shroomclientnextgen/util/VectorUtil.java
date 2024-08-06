package com.shroomclient.shroomclientnextgen.util;

import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class VectorUtil {

    public static Vec3i toVec3i(Vec3d v) {
        return new Vec3i((int) v.x, (int) v.y, (int) v.z);
    }

    // TODO This is probably not completely accurate
    public static ArrayList<BlockPos> getPosWithinDistance(
        Vec3d src,
        double distance
    ) {
        int mD = (int) Math.ceil(distance);
        double mdSq = Math.pow(distance, 2);

        ArrayList<BlockPos> o = new ArrayList<>();
        for (int dX = -1 * mD; dX <= mD; dX++) {
            for (int dY = -1 * mD; dY <= mD; dY++) {
                for (int dZ = -1 * mD; dZ <= mD; dZ++) {
                    BlockPos b = new BlockPos(toVec3i(src.add(dX, dY, dZ)));
                    if (
                        b.getSquaredDistanceFromCenter(src.x, src.y, src.z) <=
                        mdSq
                    ) o.add(b);
                }
            }
        }

        return o;
    }

    public static ArrayList<BlockPos> getAdjacent(
        BlockPos to,
        boolean diagonal,
        boolean ignoreUnder
    ) {
        ArrayList<BlockPos> o = new ArrayList<>();

        if (diagonal) {
            for (int dX = -1; dX <= 1; dX++) {
                for (int dY = ignoreUnder ? 0 : -1; dY <= 1; dY++) {
                    for (int dZ = -1; dZ <= 1; dZ++) {
                        if (!(dX == 0 && dY == 0 && dZ == 0)) o.add(
                            to.add(dX, dY, dZ)
                        );
                    }
                }
            }
        } else {
            o.add(to.add(-1, 0, 0));
            o.add(to.add(1, 0, 0));
            o.add(to.add(0, 0, -1));
            o.add(to.add(0, 0, 1));
            if (!ignoreUnder) o.add(to.add(0, -1, 0));
            o.add(to.add(0, 1, 0));
        }

        return o;
    }

    public static Vec3d getVectorForRotation(float pitch, float yaw) {
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3d((f2 * f3), f4, (f * f3));
    }
}
