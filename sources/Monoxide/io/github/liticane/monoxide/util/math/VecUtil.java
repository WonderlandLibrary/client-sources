package io.github.liticane.monoxide.util.math;

import io.github.liticane.monoxide.util.interfaces.Methods;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class VecUtil implements Methods {

    public static Vec3 getNearestPointBB(Vec3 eye, AxisAlignedBB box) {
        double[] origin = { eye.xCoord, eye.yCoord, eye.zCoord };
        double[] destMins = { box.minX, box.minY, box.minZ };
        double[] destMaxs = { box.maxX, box.maxY, box.maxZ };

        for (int i = 0; i < 3; i++) {
            if (origin[i] > destMaxs[i]) {
                origin[i] = destMaxs[i];
            } else if (origin[i] < destMins[i]) {
                origin[i] = destMins[i];
            }
        }

        return new Vec3(origin[0], origin[1], origin[2]);
    }

}
