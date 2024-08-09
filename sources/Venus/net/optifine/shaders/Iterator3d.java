/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.optifine.BlockPosM;
import net.optifine.shaders.IteratorAxis;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Iterator3d
implements Iterator<BlockPos> {
    private IteratorAxis iteratorAxis;
    private BlockPosM blockPos = new BlockPosM(0, 0, 0);
    private int axis = 0;
    private int kX;
    private int kY;
    private int kZ;
    private static final int AXIS_X = 0;
    private static final int AXIS_Y = 1;
    private static final int AXIS_Z = 2;

    public Iterator3d(BlockPos blockPos, BlockPos blockPos2, int n, int n2) {
        boolean bl = blockPos.getX() > blockPos2.getX();
        boolean bl2 = blockPos.getY() > blockPos2.getY();
        boolean bl3 = blockPos.getZ() > blockPos2.getZ();
        blockPos = this.reverseCoord(blockPos, bl, bl2, bl3);
        blockPos2 = this.reverseCoord(blockPos2, bl, bl2, bl3);
        this.kX = bl ? -1 : 1;
        this.kY = bl2 ? -1 : 1;
        this.kZ = bl3 ? -1 : 1;
        Vector3d vector3d = new Vector3d(blockPos2.getX() - blockPos.getX(), blockPos2.getY() - blockPos.getY(), blockPos2.getZ() - blockPos.getZ());
        Vector3d vector3d2 = vector3d.normalize();
        Vector3d vector3d3 = new Vector3d(1.0, 0.0, 0.0);
        double d = vector3d2.dotProduct(vector3d3);
        double d2 = Math.abs(d);
        Vector3d vector3d4 = new Vector3d(0.0, 1.0, 0.0);
        double d3 = vector3d2.dotProduct(vector3d4);
        double d4 = Math.abs(d3);
        Vector3d vector3d5 = new Vector3d(0.0, 0.0, 1.0);
        double d5 = vector3d2.dotProduct(vector3d5);
        double d6 = Math.abs(d5);
        if (d6 >= d4 && d6 >= d2) {
            this.axis = 2;
            BlockPos blockPos3 = new BlockPos(blockPos.getZ(), blockPos.getY() - n, blockPos.getX() - n2);
            BlockPos blockPos4 = new BlockPos(blockPos2.getZ(), blockPos.getY() + n + 1, blockPos.getX() + n2 + 1);
            int n3 = blockPos2.getZ() - blockPos.getZ();
            double d7 = (double)(blockPos2.getY() - blockPos.getY()) / (1.0 * (double)n3);
            double d8 = (double)(blockPos2.getX() - blockPos.getX()) / (1.0 * (double)n3);
            this.iteratorAxis = new IteratorAxis(blockPos3, blockPos4, d7, d8);
        } else if (d4 >= d2 && d4 >= d6) {
            this.axis = 1;
            BlockPos blockPos5 = new BlockPos(blockPos.getY(), blockPos.getX() - n, blockPos.getZ() - n2);
            BlockPos blockPos6 = new BlockPos(blockPos2.getY(), blockPos.getX() + n + 1, blockPos.getZ() + n2 + 1);
            int n4 = blockPos2.getY() - blockPos.getY();
            double d9 = (double)(blockPos2.getX() - blockPos.getX()) / (1.0 * (double)n4);
            double d10 = (double)(blockPos2.getZ() - blockPos.getZ()) / (1.0 * (double)n4);
            this.iteratorAxis = new IteratorAxis(blockPos5, blockPos6, d9, d10);
        } else {
            this.axis = 0;
            BlockPos blockPos7 = new BlockPos(blockPos.getX(), blockPos.getY() - n, blockPos.getZ() - n2);
            BlockPos blockPos8 = new BlockPos(blockPos2.getX(), blockPos.getY() + n + 1, blockPos.getZ() + n2 + 1);
            int n5 = blockPos2.getX() - blockPos.getX();
            double d11 = (double)(blockPos2.getY() - blockPos.getY()) / (1.0 * (double)n5);
            double d12 = (double)(blockPos2.getZ() - blockPos.getZ()) / (1.0 * (double)n5);
            this.iteratorAxis = new IteratorAxis(blockPos7, blockPos8, d11, d12);
        }
    }

    private BlockPos reverseCoord(BlockPos blockPos, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            blockPos = new BlockPos(-blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
        if (bl2) {
            blockPos = new BlockPos(blockPos.getX(), -blockPos.getY(), blockPos.getZ());
        }
        if (bl3) {
            blockPos = new BlockPos(blockPos.getX(), blockPos.getY(), -blockPos.getZ());
        }
        return blockPos;
    }

    @Override
    public boolean hasNext() {
        return this.iteratorAxis.hasNext();
    }

    @Override
    public BlockPos next() {
        BlockPos blockPos = this.iteratorAxis.next();
        switch (this.axis) {
            case 0: {
                this.blockPos.setXyz(blockPos.getX() * this.kX, blockPos.getY() * this.kY, blockPos.getZ() * this.kZ);
                return this.blockPos;
            }
            case 1: {
                this.blockPos.setXyz(blockPos.getY() * this.kX, blockPos.getX() * this.kY, blockPos.getZ() * this.kZ);
                return this.blockPos;
            }
            case 2: {
                this.blockPos.setXyz(blockPos.getZ() * this.kX, blockPos.getY() * this.kY, blockPos.getX() * this.kZ);
                return this.blockPos;
            }
        }
        this.blockPos.setXyz(blockPos.getX() * this.kX, blockPos.getY() * this.kY, blockPos.getZ() * this.kZ);
        return this.blockPos;
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not supported");
    }

    public static void main(String[] stringArray) {
        BlockPos blockPos = new BlockPos(10, 20, 30);
        BlockPos blockPos2 = new BlockPos(30, 40, 20);
        Iterator3d iterator3d = new Iterator3d(blockPos, blockPos2, 1, 1);
        while (iterator3d.hasNext()) {
            BlockPos blockPos3 = iterator3d.next();
            System.out.println("" + blockPos3);
        }
    }

    @Override
    public Object next() {
        return this.next();
    }
}

