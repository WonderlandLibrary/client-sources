/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import java.util.BitSet;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.IDoubleListMerger;
import net.minecraft.util.math.shapes.VoxelShapePart;

public final class BitSetVoxelShapePart
extends VoxelShapePart {
    private final BitSet bitSet;
    private int startX;
    private int startY;
    private int startZ;
    private int endX;
    private int endY;
    private int endZ;

    public BitSetVoxelShapePart(int n, int n2, int n3) {
        this(n, n2, n3, n, n2, n3, 0, 0, 0);
    }

    public BitSetVoxelShapePart(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        super(n, n2, n3);
        this.bitSet = new BitSet(n * n2 * n3);
        this.startX = n4;
        this.startY = n5;
        this.startZ = n6;
        this.endX = n7;
        this.endY = n8;
        this.endZ = n9;
    }

    public BitSetVoxelShapePart(VoxelShapePart voxelShapePart) {
        super(voxelShapePart.xSize, voxelShapePart.ySize, voxelShapePart.zSize);
        if (voxelShapePart instanceof BitSetVoxelShapePart) {
            this.bitSet = (BitSet)((BitSetVoxelShapePart)voxelShapePart).bitSet.clone();
        } else {
            this.bitSet = new BitSet(this.xSize * this.ySize * this.zSize);
            for (int i = 0; i < this.xSize; ++i) {
                for (int j = 0; j < this.ySize; ++j) {
                    for (int k = 0; k < this.zSize; ++k) {
                        if (!voxelShapePart.isFilled(i, j, k)) continue;
                        this.bitSet.set(this.getIndex(i, j, k));
                    }
                }
            }
        }
        this.startX = voxelShapePart.getStart(Direction.Axis.X);
        this.startY = voxelShapePart.getStart(Direction.Axis.Y);
        this.startZ = voxelShapePart.getStart(Direction.Axis.Z);
        this.endX = voxelShapePart.getEnd(Direction.Axis.X);
        this.endY = voxelShapePart.getEnd(Direction.Axis.Y);
        this.endZ = voxelShapePart.getEnd(Direction.Axis.Z);
    }

    protected int getIndex(int n, int n2, int n3) {
        return (n * this.ySize + n2) * this.zSize + n3;
    }

    @Override
    public boolean isFilled(int n, int n2, int n3) {
        return this.bitSet.get(this.getIndex(n, n2, n3));
    }

    @Override
    public void setFilled(int n, int n2, int n3, boolean bl, boolean bl2) {
        this.bitSet.set(this.getIndex(n, n2, n3), bl2);
        if (bl && bl2) {
            this.startX = Math.min(this.startX, n);
            this.startY = Math.min(this.startY, n2);
            this.startZ = Math.min(this.startZ, n3);
            this.endX = Math.max(this.endX, n + 1);
            this.endY = Math.max(this.endY, n2 + 1);
            this.endZ = Math.max(this.endZ, n3 + 1);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.bitSet.isEmpty();
    }

    @Override
    public int getStart(Direction.Axis axis) {
        return axis.getCoordinate(this.startX, this.startY, this.startZ);
    }

    @Override
    public int getEnd(Direction.Axis axis) {
        return axis.getCoordinate(this.endX, this.endY, this.endZ);
    }

    @Override
    protected boolean isZAxisLineFull(int n, int n2, int n3, int n4) {
        if (n3 >= 0 && n4 >= 0 && n >= 0) {
            if (n3 < this.xSize && n4 < this.ySize && n2 <= this.zSize) {
                return this.bitSet.nextClearBit(this.getIndex(n3, n4, n)) >= this.getIndex(n3, n4, n2);
            }
            return true;
        }
        return true;
    }

    @Override
    protected void setZAxisLine(int n, int n2, int n3, int n4, boolean bl) {
        this.bitSet.set(this.getIndex(n3, n4, n), this.getIndex(n3, n4, n2), bl);
    }

    static BitSetVoxelShapePart func_197852_a(VoxelShapePart voxelShapePart, VoxelShapePart voxelShapePart2, IDoubleListMerger iDoubleListMerger, IDoubleListMerger iDoubleListMerger2, IDoubleListMerger iDoubleListMerger3, IBooleanFunction iBooleanFunction) {
        BitSetVoxelShapePart bitSetVoxelShapePart = new BitSetVoxelShapePart(iDoubleListMerger.func_212435_a().size() - 1, iDoubleListMerger2.func_212435_a().size() - 1, iDoubleListMerger3.func_212435_a().size() - 1);
        int[] nArray = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        iDoubleListMerger.forMergedIndexes((arg_0, arg_1, arg_2) -> BitSetVoxelShapePart.lambda$func_197852_a$2(iDoubleListMerger2, iDoubleListMerger3, iBooleanFunction, voxelShapePart, voxelShapePart2, bitSetVoxelShapePart, nArray, arg_0, arg_1, arg_2));
        bitSetVoxelShapePart.startX = nArray[0];
        bitSetVoxelShapePart.startY = nArray[1];
        bitSetVoxelShapePart.startZ = nArray[2];
        bitSetVoxelShapePart.endX = nArray[3] + 1;
        bitSetVoxelShapePart.endY = nArray[4] + 1;
        bitSetVoxelShapePart.endZ = nArray[5] + 1;
        return bitSetVoxelShapePart;
    }

    private static boolean lambda$func_197852_a$2(IDoubleListMerger iDoubleListMerger, IDoubleListMerger iDoubleListMerger2, IBooleanFunction iBooleanFunction, VoxelShapePart voxelShapePart, VoxelShapePart voxelShapePart2, BitSetVoxelShapePart bitSetVoxelShapePart, int[] nArray, int n, int n2, int n3) {
        boolean[] blArray = new boolean[]{false};
        boolean bl = iDoubleListMerger.forMergedIndexes((arg_0, arg_1, arg_2) -> BitSetVoxelShapePart.lambda$func_197852_a$1(iDoubleListMerger2, iBooleanFunction, voxelShapePart, n, voxelShapePart2, n2, bitSetVoxelShapePart, n3, nArray, blArray, arg_0, arg_1, arg_2));
        if (blArray[0]) {
            nArray[0] = Math.min(nArray[0], n3);
            nArray[3] = Math.max(nArray[3], n3);
        }
        return bl;
    }

    private static boolean lambda$func_197852_a$1(IDoubleListMerger iDoubleListMerger, IBooleanFunction iBooleanFunction, VoxelShapePart voxelShapePart, int n, VoxelShapePart voxelShapePart2, int n2, BitSetVoxelShapePart bitSetVoxelShapePart, int n3, int[] nArray, boolean[] blArray, int n4, int n5, int n6) {
        boolean[] blArray2 = new boolean[]{false};
        boolean bl = iDoubleListMerger.forMergedIndexes((arg_0, arg_1, arg_2) -> BitSetVoxelShapePart.lambda$func_197852_a$0(iBooleanFunction, voxelShapePart, n, n4, voxelShapePart2, n2, n5, bitSetVoxelShapePart, n3, n6, nArray, blArray2, arg_0, arg_1, arg_2));
        if (blArray2[0]) {
            nArray[1] = Math.min(nArray[1], n6);
            nArray[4] = Math.max(nArray[4], n6);
            blArray[0] = true;
        }
        return bl;
    }

    private static boolean lambda$func_197852_a$0(IBooleanFunction iBooleanFunction, VoxelShapePart voxelShapePart, int n, int n2, VoxelShapePart voxelShapePart2, int n3, int n4, BitSetVoxelShapePart bitSetVoxelShapePart, int n5, int n6, int[] nArray, boolean[] blArray, int n7, int n8, int n9) {
        boolean bl = iBooleanFunction.apply(voxelShapePart.contains(n, n2, n7), voxelShapePart2.contains(n3, n4, n8));
        if (bl) {
            bitSetVoxelShapePart.bitSet.set(bitSetVoxelShapePart.getIndex(n5, n6, n9));
            nArray[2] = Math.min(nArray[2], n9);
            nArray[5] = Math.max(nArray[5], n9);
            blArray[0] = true;
        }
        return false;
    }
}

