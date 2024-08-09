/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ShulkerAABBHelper {
    public static AxisAlignedBB getOpenedCollisionBox(BlockPos blockPos, Direction direction) {
        return VoxelShapes.fullCube().getBoundingBox().expand(0.5f * (float)direction.getXOffset(), 0.5f * (float)direction.getYOffset(), 0.5f * (float)direction.getZOffset()).contract(direction.getXOffset(), direction.getYOffset(), direction.getZOffset()).offset(blockPos.offset(direction));
    }
}

