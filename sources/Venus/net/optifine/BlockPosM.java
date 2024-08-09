/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockPosM
extends BlockPos {
    private int mx;
    private int my;
    private int mz;
    private int level;
    private BlockPosM[] facings;
    private boolean needsUpdate;

    public BlockPosM() {
        this(0, 0, 0, 0);
    }

    public BlockPosM(int n, int n2, int n3) {
        this(n, n2, n3, 0);
    }

    public BlockPosM(double d, double d2, double d3) {
        this(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3));
    }

    public BlockPosM(int n, int n2, int n3, int n4) {
        super(0, 0, 0);
        this.mx = n;
        this.my = n2;
        this.mz = n3;
        this.level = n4;
    }

    @Override
    public int getX() {
        return this.mx;
    }

    @Override
    public int getY() {
        return this.my;
    }

    @Override
    public int getZ() {
        return this.mz;
    }

    public void setXyz(int n, int n2, int n3) {
        this.mx = n;
        this.my = n2;
        this.mz = n3;
        this.needsUpdate = true;
    }

    public void setXyz(double d, double d2, double d3) {
        this.setXyz(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3));
    }

    @Override
    public BlockPos offset(Direction direction) {
        int n;
        BlockPosM blockPosM;
        if (this.level <= 0) {
            return super.offset(direction, 1).toImmutable();
        }
        if (this.facings == null) {
            this.facings = new BlockPosM[Direction.VALUES.length];
        }
        if (this.needsUpdate) {
            this.update();
        }
        if ((blockPosM = this.facings[n = direction.getIndex()]) == null) {
            int n2 = this.mx + direction.getXOffset();
            int n3 = this.my + direction.getYOffset();
            int n4 = this.mz + direction.getZOffset();
            this.facings[n] = blockPosM = new BlockPosM(n2, n3, n4, this.level - 1);
        }
        return blockPosM;
    }

    @Override
    public BlockPos offset(Direction direction, int n) {
        return n == 1 ? this.offset(direction) : super.offset(direction, n).toImmutable();
    }

    public void setPosOffset(BlockPos blockPos, Direction direction) {
        this.mx = blockPos.getX() + direction.getXOffset();
        this.my = blockPos.getY() + direction.getYOffset();
        this.mz = blockPos.getZ() + direction.getZOffset();
    }

    public void setPosOffset(BlockPos blockPos, Direction direction, Direction direction2) {
        this.mx = blockPos.getX() + direction.getXOffset() + direction2.getXOffset();
        this.my = blockPos.getY() + direction.getYOffset() + direction2.getYOffset();
        this.mz = blockPos.getZ() + direction.getZOffset() + direction2.getZOffset();
    }

    private void update() {
        for (int i = 0; i < 6; ++i) {
            BlockPosM blockPosM = this.facings[i];
            if (blockPosM == null) continue;
            Direction direction = Direction.VALUES[i];
            int n = this.mx + direction.getXOffset();
            int n2 = this.my + direction.getYOffset();
            int n3 = this.mz + direction.getZOffset();
            blockPosM.setXyz(n, n2, n3);
        }
        this.needsUpdate = false;
    }

    @Override
    public BlockPos toImmutable() {
        return new BlockPos(this.mx, this.my, this.mz);
    }

    public static Iterable getAllInBoxMutable(BlockPos blockPos, BlockPos blockPos2) {
        BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
        BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
        return new Iterable(){
            final BlockPos val$blockpos;
            final BlockPos val$blockpos1;
            {
                this.val$blockpos = blockPos;
                this.val$blockpos1 = blockPos2;
            }

            public Iterator iterator() {
                return new AbstractIterator(this){
                    private BlockPosM posM;
                    final 1 this$0;
                    {
                        this.this$0 = var1_1;
                        this.posM = null;
                    }

                    protected Object computeNext() {
                        if (this.posM == null) {
                            this.posM = new BlockPosM(this.this$0.val$blockpos.getX(), this.this$0.val$blockpos.getY(), this.this$0.val$blockpos.getZ(), 3);
                            return this.posM;
                        }
                        if (this.posM.equals(this.this$0.val$blockpos1)) {
                            return (BlockPosM)this.endOfData();
                        }
                        int n = this.posM.getX();
                        int n2 = this.posM.getY();
                        int n3 = this.posM.getZ();
                        if (n < this.this$0.val$blockpos1.getX()) {
                            ++n;
                        } else if (n3 < this.this$0.val$blockpos1.getZ()) {
                            n = this.this$0.val$blockpos.getX();
                            ++n3;
                        } else if (n2 < this.this$0.val$blockpos1.getY()) {
                            n = this.this$0.val$blockpos.getX();
                            n3 = this.this$0.val$blockpos.getZ();
                            ++n2;
                        }
                        this.posM.setXyz(n, n2, n3);
                        return this.posM;
                    }
                };
            }
        };
    }

    @Override
    public Vector3i offset(Direction direction, int n) {
        return this.offset(direction, n);
    }
}

