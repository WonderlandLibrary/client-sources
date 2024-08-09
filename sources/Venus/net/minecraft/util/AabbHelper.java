/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class AabbHelper {
    public static AxisAlignedBB func_227019_a_(AxisAlignedBB axisAlignedBB, Direction direction, double d) {
        double d2 = d * (double)direction.getAxisDirection().getOffset();
        double d3 = Math.min(d2, 0.0);
        double d4 = Math.max(d2, 0.0);
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                return new AxisAlignedBB(axisAlignedBB.minX + d3, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.minX + d4, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            }
            case 2: {
                return new AxisAlignedBB(axisAlignedBB.maxX + d3, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX + d4, axisAlignedBB.maxY, axisAlignedBB.maxZ);
            }
            case 3: {
                return new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY + d3, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.minY + d4, axisAlignedBB.maxZ);
            }
            default: {
                return new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.maxY + d3, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY + d4, axisAlignedBB.maxZ);
            }
            case 5: {
                return new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ + d3, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ + d4);
            }
            case 6: 
        }
        return new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ + d3, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ + d4);
    }
}

