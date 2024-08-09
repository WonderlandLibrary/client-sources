/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.util.math.vector.Vector3d;

public class BlockPosWrapper
implements IPosWrapper {
    private final BlockPos pos;
    private final Vector3d centerPos;

    public BlockPosWrapper(BlockPos blockPos) {
        this.pos = blockPos;
        this.centerPos = Vector3d.copyCentered(blockPos);
    }

    @Override
    public Vector3d getPos() {
        return this.centerPos;
    }

    @Override
    public BlockPos getBlockPos() {
        return this.pos;
    }

    @Override
    public boolean isVisibleTo(LivingEntity livingEntity) {
        return false;
    }

    public String toString() {
        return "BlockPosTracker{blockPos=" + this.pos + ", centerPosition=" + this.centerPos + "}";
    }
}

