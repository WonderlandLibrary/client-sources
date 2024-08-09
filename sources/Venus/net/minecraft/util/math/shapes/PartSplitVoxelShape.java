/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShapePart;

public final class PartSplitVoxelShape
extends VoxelShapePart {
    private final VoxelShapePart part;
    private final int startX;
    private final int startY;
    private final int startZ;
    private final int endX;
    private final int endY;
    private final int endZ;

    protected PartSplitVoxelShape(VoxelShapePart voxelShapePart, int n, int n2, int n3, int n4, int n5, int n6) {
        super(n4 - n, n5 - n2, n6 - n3);
        this.part = voxelShapePart;
        this.startX = n;
        this.startY = n2;
        this.startZ = n3;
        this.endX = n4;
        this.endY = n5;
        this.endZ = n6;
    }

    @Override
    public boolean isFilled(int n, int n2, int n3) {
        return this.part.isFilled(this.startX + n, this.startY + n2, this.startZ + n3);
    }

    @Override
    public void setFilled(int n, int n2, int n3, boolean bl, boolean bl2) {
        this.part.setFilled(this.startX + n, this.startY + n2, this.startZ + n3, bl, bl2);
    }

    @Override
    public int getStart(Direction.Axis axis) {
        return Math.max(0, this.part.getStart(axis) - axis.getCoordinate(this.startX, this.startY, this.startZ));
    }

    @Override
    public int getEnd(Direction.Axis axis) {
        return Math.min(axis.getCoordinate(this.endX, this.endY, this.endZ), this.part.getEnd(axis) - axis.getCoordinate(this.startX, this.startY, this.startZ));
    }
}

