/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.memory;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.util.math.vector.Vector3d;

public class WalkTarget {
    private final IPosWrapper target;
    private final float speed;
    private final int distance;

    public WalkTarget(BlockPos blockPos, float f, int n) {
        this(new BlockPosWrapper(blockPos), f, n);
    }

    public WalkTarget(Vector3d vector3d, float f, int n) {
        this(new BlockPosWrapper(new BlockPos(vector3d)), f, n);
    }

    public WalkTarget(IPosWrapper iPosWrapper, float f, int n) {
        this.target = iPosWrapper;
        this.speed = f;
        this.distance = n;
    }

    public IPosWrapper getTarget() {
        return this.target;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getDistance() {
        return this.distance;
    }
}

