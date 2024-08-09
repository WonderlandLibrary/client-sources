/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class BlockRayTraceResult
extends RayTraceResult {
    private final Direction face;
    private final BlockPos pos;
    private final boolean isMiss;
    private final boolean inside;

    public static BlockRayTraceResult createMiss(Vector3d vector3d, Direction direction, BlockPos blockPos) {
        return new BlockRayTraceResult(true, vector3d, direction, blockPos, false);
    }

    public BlockRayTraceResult(Vector3d vector3d, Direction direction, BlockPos blockPos, boolean bl) {
        this(false, vector3d, direction, blockPos, bl);
    }

    private BlockRayTraceResult(boolean bl, Vector3d vector3d, Direction direction, BlockPos blockPos, boolean bl2) {
        super(vector3d);
        this.isMiss = bl;
        this.face = direction;
        this.pos = blockPos;
        this.inside = bl2;
    }

    public BlockRayTraceResult withFace(Direction direction) {
        return new BlockRayTraceResult(this.isMiss, this.hitResult, direction, this.pos, this.inside);
    }

    public BlockRayTraceResult withPosition(BlockPos blockPos) {
        return new BlockRayTraceResult(this.isMiss, this.hitResult, this.face, blockPos, this.inside);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Direction getFace() {
        return this.face;
    }

    @Override
    public RayTraceResult.Type getType() {
        return this.isMiss ? RayTraceResult.Type.MISS : RayTraceResult.Type.BLOCK;
    }

    public boolean isInside() {
        return this.inside;
    }
}

