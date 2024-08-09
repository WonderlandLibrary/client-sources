/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.util.math.BlockPos;
import net.optifine.BlockPosM;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IteratorAxis
implements Iterator<BlockPos> {
    private double yDelta;
    private double zDelta;
    private int xStart;
    private int xEnd;
    private double yStart;
    private double yEnd;
    private double zStart;
    private double zEnd;
    private int xNext;
    private double yNext;
    private double zNext;
    private BlockPosM pos = new BlockPosM(0, 0, 0);
    private boolean hasNext = false;

    public IteratorAxis(BlockPos blockPos, BlockPos blockPos2, double d, double d2) {
        this.yDelta = d;
        this.zDelta = d2;
        this.xStart = blockPos.getX();
        this.xEnd = blockPos2.getX();
        this.yStart = blockPos.getY();
        this.yEnd = (double)blockPos2.getY() - 0.5;
        this.zStart = blockPos.getZ();
        this.zEnd = (double)blockPos2.getZ() - 0.5;
        this.xNext = this.xStart;
        this.yNext = this.yStart;
        this.zNext = this.zStart;
        this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public BlockPos next() {
        if (!this.hasNext) {
            throw new NoSuchElementException();
        }
        this.pos.setXyz((double)this.xNext, this.yNext, this.zNext);
        this.nextPos();
        this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
        return this.pos;
    }

    private void nextPos() {
        this.zNext += 1.0;
        if (!(this.zNext < this.zEnd)) {
            this.zNext = this.zStart;
            this.yNext += 1.0;
            if (!(this.yNext < this.yEnd)) {
                this.yNext = this.yStart;
                this.yStart += this.yDelta;
                this.yEnd += this.yDelta;
                this.yNext = this.yStart;
                this.zStart += this.zDelta;
                this.zEnd += this.zDelta;
                this.zNext = this.zStart;
                ++this.xNext;
                if (this.xNext >= this.xEnd) {
                    // empty if block
                }
            }
        }
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not implemented");
    }

    public static void main(String[] stringArray) throws Exception {
        BlockPos blockPos = new BlockPos(-2, 10, 20);
        BlockPos blockPos2 = new BlockPos(2, 12, 22);
        double d = -0.5;
        double d2 = 0.5;
        IteratorAxis iteratorAxis = new IteratorAxis(blockPos, blockPos2, d, d2);
        System.out.println("Start: " + blockPos + ", end: " + blockPos2 + ", yDelta: " + d + ", zDelta: " + d2);
        while (iteratorAxis.hasNext()) {
            BlockPos blockPos3 = iteratorAxis.next();
            System.out.println("" + blockPos3);
        }
    }

    @Override
    public Object next() {
        return this.next();
    }
}

