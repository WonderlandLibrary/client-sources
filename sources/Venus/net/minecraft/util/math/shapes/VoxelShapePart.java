/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import net.minecraft.util.AxisRotation;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;

public abstract class VoxelShapePart {
    private static final Direction.Axis[] AXIS_VALUES = Direction.Axis.values();
    protected final int xSize;
    protected final int ySize;
    protected final int zSize;

    protected VoxelShapePart(int n, int n2, int n3) {
        this.xSize = n;
        this.ySize = n2;
        this.zSize = n3;
    }

    public boolean containsWithRotation(AxisRotation axisRotation, int n, int n2, int n3) {
        return this.contains(axisRotation.getCoordinate(n, n2, n3, Direction.Axis.X), axisRotation.getCoordinate(n, n2, n3, Direction.Axis.Y), axisRotation.getCoordinate(n, n2, n3, Direction.Axis.Z));
    }

    public boolean contains(int n, int n2, int n3) {
        if (n >= 0 && n2 >= 0 && n3 >= 0) {
            return n < this.xSize && n2 < this.ySize && n3 < this.zSize ? this.isFilled(n, n2, n3) : false;
        }
        return true;
    }

    public boolean isFilledWithRotation(AxisRotation axisRotation, int n, int n2, int n3) {
        return this.isFilled(axisRotation.getCoordinate(n, n2, n3, Direction.Axis.X), axisRotation.getCoordinate(n, n2, n3, Direction.Axis.Y), axisRotation.getCoordinate(n, n2, n3, Direction.Axis.Z));
    }

    public abstract boolean isFilled(int var1, int var2, int var3);

    public abstract void setFilled(int var1, int var2, int var3, boolean var4, boolean var5);

    public boolean isEmpty() {
        for (Direction.Axis axis : AXIS_VALUES) {
            if (this.getStart(axis) < this.getEnd(axis)) continue;
            return false;
        }
        return true;
    }

    public abstract int getStart(Direction.Axis var1);

    public abstract int getEnd(Direction.Axis var1);

    public int lastFilled(Direction.Axis axis, int n, int n2) {
        if (n >= 0 && n2 >= 0) {
            Direction.Axis axis2 = AxisRotation.FORWARD.rotate(axis);
            Direction.Axis axis3 = AxisRotation.BACKWARD.rotate(axis);
            if (n < this.getSize(axis2) && n2 < this.getSize(axis3)) {
                int n3 = this.getSize(axis);
                AxisRotation axisRotation = AxisRotation.from(Direction.Axis.X, axis);
                for (int i = n3 - 1; i >= 0; --i) {
                    if (!this.isFilledWithRotation(axisRotation, i, n, n2)) continue;
                    return i + 1;
                }
                return 1;
            }
            return 1;
        }
        return 1;
    }

    public int getSize(Direction.Axis axis) {
        return axis.getCoordinate(this.xSize, this.ySize, this.zSize);
    }

    public int getXSize() {
        return this.getSize(Direction.Axis.X);
    }

    public int getYSize() {
        return this.getSize(Direction.Axis.Y);
    }

    public int getZSize() {
        return this.getSize(Direction.Axis.Z);
    }

    public void forEachEdge(ILineConsumer iLineConsumer, boolean bl) {
        this.forEachEdgeOnAxis(iLineConsumer, AxisRotation.NONE, bl);
        this.forEachEdgeOnAxis(iLineConsumer, AxisRotation.FORWARD, bl);
        this.forEachEdgeOnAxis(iLineConsumer, AxisRotation.BACKWARD, bl);
    }

    private void forEachEdgeOnAxis(ILineConsumer iLineConsumer, AxisRotation axisRotation, boolean bl) {
        AxisRotation axisRotation2 = axisRotation.reverse();
        int n = this.getSize(axisRotation2.rotate(Direction.Axis.X));
        int n2 = this.getSize(axisRotation2.rotate(Direction.Axis.Y));
        int n3 = this.getSize(axisRotation2.rotate(Direction.Axis.Z));
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= n2; ++j) {
                int n4 = -1;
                for (int k = 0; k <= n3; ++k) {
                    int n5 = 0;
                    int n6 = 0;
                    for (int i2 = 0; i2 <= 1; ++i2) {
                        for (int i3 = 0; i3 <= 1; ++i3) {
                            if (!this.containsWithRotation(axisRotation2, i + i2 - 1, j + i3 - 1, k)) continue;
                            ++n5;
                            n6 ^= i2 ^ i3;
                        }
                    }
                    if (n5 == 1 || n5 == 3 || n5 == 2 && !(n6 & true)) {
                        if (bl) {
                            if (n4 != -1) continue;
                            n4 = k;
                            continue;
                        }
                        iLineConsumer.consume(axisRotation2.getCoordinate(i, j, k, Direction.Axis.X), axisRotation2.getCoordinate(i, j, k, Direction.Axis.Y), axisRotation2.getCoordinate(i, j, k, Direction.Axis.Z), axisRotation2.getCoordinate(i, j, k + 1, Direction.Axis.X), axisRotation2.getCoordinate(i, j, k + 1, Direction.Axis.Y), axisRotation2.getCoordinate(i, j, k + 1, Direction.Axis.Z));
                        continue;
                    }
                    if (n4 == -1) continue;
                    iLineConsumer.consume(axisRotation2.getCoordinate(i, j, n4, Direction.Axis.X), axisRotation2.getCoordinate(i, j, n4, Direction.Axis.Y), axisRotation2.getCoordinate(i, j, n4, Direction.Axis.Z), axisRotation2.getCoordinate(i, j, k, Direction.Axis.X), axisRotation2.getCoordinate(i, j, k, Direction.Axis.Y), axisRotation2.getCoordinate(i, j, k, Direction.Axis.Z));
                    n4 = -1;
                }
            }
        }
    }

    protected boolean isZAxisLineFull(int n, int n2, int n3, int n4) {
        for (int i = n; i < n2; ++i) {
            if (this.contains(n3, n4, i)) continue;
            return true;
        }
        return false;
    }

    protected void setZAxisLine(int n, int n2, int n3, int n4, boolean bl) {
        for (int i = n; i < n2; ++i) {
            this.setFilled(n3, n4, i, false, bl);
        }
    }

    protected boolean isXZRectangleFull(int n, int n2, int n3, int n4, int n5) {
        for (int i = n; i < n2; ++i) {
            if (this.isZAxisLineFull(n3, n4, i, n5)) continue;
            return true;
        }
        return false;
    }

    public void forEachBox(ILineConsumer iLineConsumer, boolean bl) {
        BitSetVoxelShapePart bitSetVoxelShapePart = new BitSetVoxelShapePart(this);
        for (int i = 0; i <= this.xSize; ++i) {
            for (int j = 0; j <= this.ySize; ++j) {
                int n = -1;
                for (int k = 0; k <= this.zSize; ++k) {
                    int n2;
                    if (bitSetVoxelShapePart.contains(i, j, k)) {
                        if (bl) {
                            if (n != -1) continue;
                            n = k;
                            continue;
                        }
                        iLineConsumer.consume(i, j, k, i + 1, j + 1, k + 1);
                        continue;
                    }
                    if (n == -1) continue;
                    int n3 = i;
                    int n4 = i;
                    int n5 = j;
                    int n6 = j;
                    ((VoxelShapePart)bitSetVoxelShapePart).setZAxisLine(n, k, i, j, true);
                    while (((VoxelShapePart)bitSetVoxelShapePart).isZAxisLineFull(n, k, n3 - 1, n5)) {
                        ((VoxelShapePart)bitSetVoxelShapePart).setZAxisLine(n, k, n3 - 1, n5, true);
                        --n3;
                    }
                    while (((VoxelShapePart)bitSetVoxelShapePart).isZAxisLineFull(n, k, n4 + 1, n5)) {
                        ((VoxelShapePart)bitSetVoxelShapePart).setZAxisLine(n, k, n4 + 1, n5, true);
                        ++n4;
                    }
                    while (bitSetVoxelShapePart.isXZRectangleFull(n3, n4 + 1, n, k, n5 - 1)) {
                        for (n2 = n3; n2 <= n4; ++n2) {
                            ((VoxelShapePart)bitSetVoxelShapePart).setZAxisLine(n, k, n2, n5 - 1, true);
                        }
                        --n5;
                    }
                    while (bitSetVoxelShapePart.isXZRectangleFull(n3, n4 + 1, n, k, n6 + 1)) {
                        for (n2 = n3; n2 <= n4; ++n2) {
                            ((VoxelShapePart)bitSetVoxelShapePart).setZAxisLine(n, k, n2, n6 + 1, true);
                        }
                        ++n6;
                    }
                    iLineConsumer.consume(n3, n5, n, n4 + 1, n6 + 1, k);
                    n = -1;
                }
            }
        }
    }

    public void forEachFace(IFaceConsumer iFaceConsumer) {
        this.forEachFaceOnAxis(iFaceConsumer, AxisRotation.NONE);
        this.forEachFaceOnAxis(iFaceConsumer, AxisRotation.FORWARD);
        this.forEachFaceOnAxis(iFaceConsumer, AxisRotation.BACKWARD);
    }

    private void forEachFaceOnAxis(IFaceConsumer iFaceConsumer, AxisRotation axisRotation) {
        AxisRotation axisRotation2 = axisRotation.reverse();
        Direction.Axis axis = axisRotation2.rotate(Direction.Axis.Z);
        int n = this.getSize(axisRotation2.rotate(Direction.Axis.X));
        int n2 = this.getSize(axisRotation2.rotate(Direction.Axis.Y));
        int n3 = this.getSize(axis);
        Direction direction = Direction.getFacingFromAxisDirection(axis, Direction.AxisDirection.NEGATIVE);
        Direction direction2 = Direction.getFacingFromAxisDirection(axis, Direction.AxisDirection.POSITIVE);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                boolean bl = false;
                for (int k = 0; k <= n3; ++k) {
                    boolean bl2;
                    boolean bl3 = bl2 = k != n3 && this.isFilledWithRotation(axisRotation2, i, j, k);
                    if (!bl && bl2) {
                        iFaceConsumer.consume(direction, axisRotation2.getCoordinate(i, j, k, Direction.Axis.X), axisRotation2.getCoordinate(i, j, k, Direction.Axis.Y), axisRotation2.getCoordinate(i, j, k, Direction.Axis.Z));
                    }
                    if (bl && !bl2) {
                        iFaceConsumer.consume(direction2, axisRotation2.getCoordinate(i, j, k - 1, Direction.Axis.X), axisRotation2.getCoordinate(i, j, k - 1, Direction.Axis.Y), axisRotation2.getCoordinate(i, j, k - 1, Direction.Axis.Z));
                    }
                    bl = bl2;
                }
            }
        }
    }

    public static interface ILineConsumer {
        public void consume(int var1, int var2, int var3, int var4, int var5, int var6);
    }

    public static interface IFaceConsumer {
        public void consume(Direction var1, int var2, int var3, int var4);
    }
}

