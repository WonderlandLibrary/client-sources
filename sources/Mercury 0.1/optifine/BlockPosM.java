/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockPosM
extends BlockPos {
    private int mx;
    private int my;
    private int mz;
    private int level;
    private BlockPosM[] facings;
    private boolean needsUpdate;

    public BlockPosM(int x2, int y2, int z2) {
        this(x2, y2, z2, 0);
    }

    public BlockPosM(double xIn, double yIn, double zIn) {
        this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
    }

    public BlockPosM(int x2, int y2, int z2, int level) {
        super(0, 0, 0);
        this.mx = x2;
        this.my = y2;
        this.mz = z2;
        this.level = level;
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

    public void setXyz(int x2, int y2, int z2) {
        this.mx = x2;
        this.my = y2;
        this.mz = z2;
        this.needsUpdate = true;
    }

    public void setXyz(double xIn, double yIn, double zIn) {
        this.setXyz(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
    }

    @Override
    public BlockPos offset(EnumFacing facing) {
        int index;
        BlockPosM bpm;
        if (this.level <= 0) {
            return super.offset(facing, 1);
        }
        if (this.facings == null) {
            this.facings = new BlockPosM[EnumFacing.VALUES.length];
        }
        if (this.needsUpdate) {
            this.update();
        }
        if ((bpm = this.facings[index = facing.getIndex()]) == null) {
            int nx2 = this.mx + facing.getFrontOffsetX();
            int ny = this.my + facing.getFrontOffsetY();
            int nz2 = this.mz + facing.getFrontOffsetZ();
            this.facings[index] = bpm = new BlockPosM(nx2, ny, nz2, this.level - 1);
        }
        return bpm;
    }

    @Override
    public BlockPos offset(EnumFacing facing, int n2) {
        return n2 == 1 ? this.offset(facing) : super.offset(facing, n2);
    }

    private void update() {
        for (int i2 = 0; i2 < 6; ++i2) {
            BlockPosM bpm = this.facings[i2];
            if (bpm == null) continue;
            EnumFacing facing = EnumFacing.VALUES[i2];
            int nx2 = this.mx + facing.getFrontOffsetX();
            int ny = this.my + facing.getFrontOffsetY();
            int nz2 = this.mz + facing.getFrontOffsetZ();
            bpm.setXyz(nx2, ny, nz2);
        }
        this.needsUpdate = false;
    }

    public static Iterable getAllInBoxMutable(BlockPos from, BlockPos to2) {
        final BlockPos posFrom = new BlockPos(Math.min(from.getX(), to2.getX()), Math.min(from.getY(), to2.getY()), Math.min(from.getZ(), to2.getZ()));
        final BlockPos posTo = new BlockPos(Math.max(from.getX(), to2.getX()), Math.max(from.getY(), to2.getY()), Math.max(from.getZ(), to2.getZ()));
        return new Iterable(){

            public Iterator iterator() {
                return new AbstractIterator(){
                    private BlockPosM theBlockPosM = null;

                    protected BlockPosM computeNext0() {
                        if (this.theBlockPosM == null) {
                            this.theBlockPosM = new BlockPosM(posFrom.getX(), posFrom.getY(), posFrom.getZ(), 3);
                            return this.theBlockPosM;
                        }
                        if (this.theBlockPosM.equals(posTo)) {
                            return (BlockPosM)this.endOfData();
                        }
                        int bx2 = this.theBlockPosM.getX();
                        int by = this.theBlockPosM.getY();
                        int bz2 = this.theBlockPosM.getZ();
                        if (bx2 < posTo.getX()) {
                            ++bx2;
                        } else if (by < posTo.getY()) {
                            bx2 = posFrom.getX();
                            ++by;
                        } else if (bz2 < posTo.getZ()) {
                            bx2 = posFrom.getX();
                            by = posFrom.getY();
                            ++bz2;
                        }
                        this.theBlockPosM.setXyz(bx2, by, bz2);
                        return this.theBlockPosM;
                    }

                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                };
            }

        };
    }

    public BlockPos getImmutable() {
        return new BlockPos(this.getX(), this.getY(), this.getZ());
    }

}

