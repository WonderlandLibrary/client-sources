/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapePart;

public final class VoxelShapeArray
extends VoxelShape {
    private final DoubleList xPoints;
    private final DoubleList yPoints;
    private final DoubleList zPoints;

    protected VoxelShapeArray(VoxelShapePart voxelShapePart, double[] dArray, double[] dArray2, double[] dArray3) {
        this(voxelShapePart, DoubleArrayList.wrap(Arrays.copyOf(dArray, voxelShapePart.getXSize() + 1)), DoubleArrayList.wrap(Arrays.copyOf(dArray2, voxelShapePart.getYSize() + 1)), DoubleArrayList.wrap(Arrays.copyOf(dArray3, voxelShapePart.getZSize() + 1)));
    }

    VoxelShapeArray(VoxelShapePart voxelShapePart, DoubleList doubleList, DoubleList doubleList2, DoubleList doubleList3) {
        super(voxelShapePart);
        int n = voxelShapePart.getXSize() + 1;
        int n2 = voxelShapePart.getYSize() + 1;
        int n3 = voxelShapePart.getZSize() + 1;
        if (n != doubleList.size() || n2 != doubleList2.size() || n3 != doubleList3.size()) {
            throw Util.pauseDevMode(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape."));
        }
        this.xPoints = doubleList;
        this.yPoints = doubleList2;
        this.zPoints = doubleList3;
    }

    @Override
    protected DoubleList getValues(Direction.Axis axis) {
        switch (1.$SwitchMap$net$minecraft$util$Direction$Axis[axis.ordinal()]) {
            case 1: {
                return this.xPoints;
            }
            case 2: {
                return this.yPoints;
            }
            case 3: {
                return this.zPoints;
            }
        }
        throw new IllegalArgumentException();
    }
}

