/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class BlockPos
extends Vec3i {
    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
    private static final int field_177990_b;
    private static final int field_177991_c;
    private static final int field_177989_d;
    private static final int field_177987_f;
    private static final int field_177988_g;
    private static final long field_177994_h;
    private static final long field_177995_i;
    private static final long field_177993_j;
    private static final String __OBFID = "CL_00002334";

    static {
        field_177991_c = field_177990_b = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
        field_177989_d = 64 - field_177990_b - field_177991_c;
        field_177987_f = 0 + field_177991_c;
        field_177988_g = field_177987_f + field_177989_d;
        field_177994_h = (1L << field_177990_b) - 1L;
        field_177995_i = (1L << field_177989_d) - 1L;
        field_177993_j = (1L << field_177991_c) - 1L;
    }

    public BlockPos(int x2, int y2, int z2) {
        super(x2, y2, z2);
    }

    public BlockPos(double x2, double y2, double z2) {
        super(x2, y2, z2);
    }

    public BlockPos(Entity p_i46032_1_) {
        this(p_i46032_1_.posX, p_i46032_1_.posY, p_i46032_1_.posZ);
    }

    public BlockPos(Vec3 p_i46033_1_) {
        this(p_i46033_1_.xCoord, p_i46033_1_.yCoord, p_i46033_1_.zCoord);
    }

    public BlockPos(Vec3i p_i46034_1_) {
        this(p_i46034_1_.getX(), p_i46034_1_.getY(), p_i46034_1_.getZ());
    }

    public BlockPos add(double x2, double y2, double z2) {
        return new BlockPos((double)this.getX() + x2, (double)this.getY() + y2, (double)this.getZ() + z2);
    }

    public BlockPos add(int x2, int y2, int z2) {
        return new BlockPos(this.getX() + x2, this.getY() + y2, this.getZ() + z2);
    }

    public BlockPos add(Vec3i vec) {
        return new BlockPos(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }

    public BlockPos subtract(Vec3i vec) {
        return new BlockPos(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }

    public BlockPos multiply(int factor) {
        return new BlockPos(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }

    public BlockPos offsetUp() {
        return this.offsetUp(1);
    }

    public BlockPos offsetUp(int n2) {
        return this.offset(EnumFacing.UP, n2);
    }

    public BlockPos offsetDown() {
        return this.offsetDown(1);
    }

    public BlockPos offsetDown(int n2) {
        return this.offset(EnumFacing.DOWN, n2);
    }

    public BlockPos offsetNorth() {
        return this.offsetNorth(1);
    }

    public BlockPos offsetNorth(int n2) {
        return this.offset(EnumFacing.NORTH, n2);
    }

    public BlockPos offsetSouth() {
        return this.offsetSouth(1);
    }

    public BlockPos offsetSouth(int n2) {
        return this.offset(EnumFacing.SOUTH, n2);
    }

    public BlockPos offsetWest() {
        return this.offsetWest(1);
    }

    public BlockPos offsetWest(int n2) {
        return this.offset(EnumFacing.WEST, n2);
    }

    public BlockPos offsetEast() {
        return this.offsetEast(1);
    }

    public BlockPos offsetEast(int n2) {
        return this.offset(EnumFacing.EAST, n2);
    }

    public BlockPos offset(EnumFacing facing) {
        return this.offset(facing, 1);
    }

    public BlockPos offset(EnumFacing facing, int n2) {
        return new BlockPos(this.getX() + facing.getFrontOffsetX() * n2, this.getY() + facing.getFrontOffsetY() * n2, this.getZ() + facing.getFrontOffsetZ() * n2);
    }

    public BlockPos crossProductBP(Vec3i vec) {
        return new BlockPos(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public long toLong() {
        return ((long)this.getX() & field_177994_h) << field_177988_g | ((long)this.getY() & field_177995_i) << field_177987_f | ((long)this.getZ() & field_177993_j) << 0;
    }

    public static BlockPos fromLong(long serialized) {
        int var2 = (int)(serialized << 64 - field_177988_g - field_177990_b >> 64 - field_177990_b);
        int var3 = (int)(serialized << 64 - field_177987_f - field_177989_d >> 64 - field_177989_d);
        int var4 = (int)(serialized << 64 - field_177991_c >> 64 - field_177991_c);
        return new BlockPos(var2, var3, var4);
    }

    public static Iterable getAllInBox(BlockPos from, BlockPos to2) {
        final BlockPos var2 = new BlockPos(Math.min(from.getX(), to2.getX()), Math.min(from.getY(), to2.getY()), Math.min(from.getZ(), to2.getZ()));
        final BlockPos var3 = new BlockPos(Math.max(from.getX(), to2.getX()), Math.max(from.getY(), to2.getY()), Math.max(from.getZ(), to2.getZ()));
        return new Iterable(){
            private static final String __OBFID = "CL_00002333";

            public Iterator iterator() {
                return new AbstractIterator(){
                    private BlockPos lastReturned = null;
                    private static final String __OBFID = "CL_00002332";

                    protected BlockPos computeNext0() {
                        if (this.lastReturned == null) {
                            this.lastReturned = var2;
                            return this.lastReturned;
                        }
                        if (this.lastReturned.equals(var3)) {
                            return (BlockPos)this.endOfData();
                        }
                        int var1 = this.lastReturned.getX();
                        int var2x = this.lastReturned.getY();
                        int var3x = this.lastReturned.getZ();
                        if (var1 < var3.getX()) {
                            ++var1;
                        } else if (var2x < var3.getY()) {
                            var1 = var2.getX();
                            ++var2x;
                        } else if (var3x < var3.getZ()) {
                            var1 = var2.getX();
                            var2x = var2.getY();
                            ++var3x;
                        }
                        this.lastReturned = new BlockPos(var1, var2x, var3x);
                        return this.lastReturned;
                    }

                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                };
            }

        };
    }

    public static Iterable getAllInBoxMutable(BlockPos from, BlockPos to2) {
        final BlockPos var2 = new BlockPos(Math.min(from.getX(), to2.getX()), Math.min(from.getY(), to2.getY()), Math.min(from.getZ(), to2.getZ()));
        final BlockPos var3 = new BlockPos(Math.max(from.getX(), to2.getX()), Math.max(from.getY(), to2.getY()), Math.max(from.getZ(), to2.getZ()));
        return new Iterable(){
            private static final String __OBFID = "CL_00002331";

            public Iterator iterator() {
                return new AbstractIterator(){
                    private MutableBlockPos theBlockPos = null;
                    private static final String __OBFID = "CL_00002330";

                    protected MutableBlockPos computeNext0() {
                        if (this.theBlockPos == null) {
                            this.theBlockPos = new MutableBlockPos(var2.getX(), var2.getY(), var2.getZ(), null);
                            return this.theBlockPos;
                        }
                        if (this.theBlockPos.equals(var3)) {
                            return (MutableBlockPos)this.endOfData();
                        }
                        int var1 = this.theBlockPos.getX();
                        int var2xx = this.theBlockPos.getY();
                        int var3x = this.theBlockPos.getZ();
                        if (var1 < var3.getX()) {
                            ++var1;
                        } else if (var2xx < var3.getY()) {
                            var1 = var2.getX();
                            ++var2xx;
                        } else if (var3x < var3.getZ()) {
                            var1 = var2.getX();
                            var2xx = var2.getY();
                            ++var3x;
                        }
                        this.theBlockPos.x = var1;
                        this.theBlockPos.y = var2xx;
                        this.theBlockPos.z = var3x;
                        return this.theBlockPos;
                    }

                    protected Object computeNext() {
                        return this.computeNext0();
                    }
                };
            }

        };
    }

    @Override
    public Vec3i crossProduct(Vec3i vec) {
        return this.crossProductBP(vec);
    }

    public static final class MutableBlockPos
    extends BlockPos {
        public int x;
        public int y;
        public int z;
        private static final String __OBFID = "CL_00002329";

        private MutableBlockPos(int x_, int y_, int z_) {
            super(0, 0, 0);
            this.x = x_;
            this.y = y_;
            this.z = z_;
        }

        @Override
        public int getX() {
            return this.x;
        }

        @Override
        public int getY() {
            return this.y;
        }

        @Override
        public int getZ() {
            return this.z;
        }

        @Override
        public Vec3i crossProduct(Vec3i vec) {
            return super.crossProductBP(vec);
        }

        MutableBlockPos(int p_i46025_1_, int p_i46025_2_, int p_i46025_3_, Object p_i46025_4_) {
            this(p_i46025_1_, p_i46025_2_, p_i46025_3_);
        }
    }

}

