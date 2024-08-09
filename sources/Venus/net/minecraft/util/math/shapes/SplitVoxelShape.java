/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.DoubleRangeList;
import net.minecraft.util.math.shapes.PartSplitVoxelShape;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapePart;

public class SplitVoxelShape
extends VoxelShape {
    private final VoxelShape shape;
    private final Direction.Axis axis;
    private static final DoubleList field_223415_d = new DoubleRangeList(1);

    public SplitVoxelShape(VoxelShape voxelShape, Direction.Axis axis, int n) {
        super(SplitVoxelShape.makeShapePart(voxelShape.part, axis, n));
        this.shape = voxelShape;
        this.axis = axis;
    }

    private static VoxelShapePart makeShapePart(VoxelShapePart voxelShapePart, Direction.Axis axis, int n) {
        return new PartSplitVoxelShape(voxelShapePart, axis.getCoordinate(n, 0, 0), axis.getCoordinate(0, n, 0), axis.getCoordinate(0, 0, n), axis.getCoordinate(n + 1, voxelShapePart.xSize, voxelShapePart.xSize), axis.getCoordinate(voxelShapePart.ySize, n + 1, voxelShapePart.ySize), axis.getCoordinate(voxelShapePart.zSize, voxelShapePart.zSize, n + 1));
    }

    @Override
    protected DoubleList getValues(Direction.Axis axis) {
        return axis == this.axis ? field_223415_d : this.shape.getValues(axis);
    }
}

