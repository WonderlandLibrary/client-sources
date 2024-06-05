package net.minecraft.util;

import net.minecraft.entity.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockPos extends Vec3i
{
    public static final BlockPos ORIGIN;
    private static final int field_177990_b;
    private static final int field_177991_c;
    private static final int field_177989_d;
    private static final int field_177987_f;
    private static final int field_177988_g;
    private static final long field_177994_h;
    private static final long field_177995_i;
    private static final long field_177993_j;
    
    public BlockPos(final int x, final int y, final int z) {
        super(x, y, z);
    }
    
    public BlockPos(final double x, final double y, final double z) {
        super(x, y, z);
    }
    
    public BlockPos(final Entity p_i46032_1_) {
        this(p_i46032_1_.posX, p_i46032_1_.posY, p_i46032_1_.posZ);
    }
    
    public BlockPos(final Vec3 p_i46033_1_) {
        this(p_i46033_1_.xCoord, p_i46033_1_.yCoord, p_i46033_1_.zCoord);
    }
    
    public BlockPos(final Vec3i p_i46034_1_) {
        this(p_i46034_1_.getX(), p_i46034_1_.getY(), p_i46034_1_.getZ());
    }
    
    public static BlockPos fromLong(final long serialized) {
        final int var2 = (int)(serialized << 64 - BlockPos.field_177988_g - BlockPos.field_177990_b >> 64 - BlockPos.field_177990_b);
        final int var3 = (int)(serialized << 64 - BlockPos.field_177987_f - BlockPos.field_177989_d >> 64 - BlockPos.field_177989_d);
        final int var4 = (int)(serialized << 64 - BlockPos.field_177991_c >> 64 - BlockPos.field_177991_c);
        return new BlockPos(var2, var3, var4);
    }
    
    public static Iterable<BlockPos> getAllInBox(final BlockPos from, final BlockPos to) {
        final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos blockpos2 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable<BlockPos>() {
            @Override
            public Iterator<BlockPos> iterator() {
                return (Iterator<BlockPos>)new AbstractIterator<BlockPos>() {
                    private BlockPos lastReturned = null;
                    
                    protected BlockPos computeNext() {
                        if (this.lastReturned == null) {
                            return this.lastReturned = blockpos;
                        }
                        if (this.lastReturned.equals(blockpos2)) {
                            return (BlockPos)this.endOfData();
                        }
                        int i = this.lastReturned.getX();
                        int j = this.lastReturned.getY();
                        int k = this.lastReturned.getZ();
                        if (i < blockpos2.getX()) {
                            ++i;
                        }
                        else if (j < blockpos2.getY()) {
                            i = blockpos.getX();
                            ++j;
                        }
                        else if (k < blockpos2.getZ()) {
                            i = blockpos.getX();
                            j = blockpos.getY();
                            ++k;
                        }
                        return this.lastReturned = new BlockPos(i, j, k);
                    }
                };
            }
        };
    }
    
    public static Iterable getAllInBoxMutable(final BlockPos from, final BlockPos to) {
        final BlockPos var2 = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
        final BlockPos var3 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return (Iterator)new AbstractIterator() {
                    private MutableBlockPos theBlockPos = null;
                    
                    protected MutableBlockPos computeNext0() {
                        if (this.theBlockPos == null) {
                            return this.theBlockPos = new MutableBlockPos(var2.getX(), var2.getY(), var2.getZ(), null);
                        }
                        if (this.theBlockPos.equals(var3)) {
                            return (MutableBlockPos)this.endOfData();
                        }
                        int var1 = this.theBlockPos.getX();
                        int var2xx = this.theBlockPos.getY();
                        int var3x = this.theBlockPos.getZ();
                        if (var1 < var3.getX()) {
                            ++var1;
                        }
                        else if (var2xx < var3.getY()) {
                            var1 = var2.getX();
                            ++var2xx;
                        }
                        else if (var3x < var3.getZ()) {
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
    
    public BlockPos add(final double x, final double y, final double z) {
        return new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }
    
    public BlockPos add(final int x, final int y, final int z) {
        return new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }
    
    public BlockPos add(final Vec3i vec) {
        return new BlockPos(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }
    
    public BlockPos subtract(final Vec3i vec) {
        return new BlockPos(this.getX() - vec.getX(), this.getY() - vec.getY(), this.getZ() - vec.getZ());
    }
    
    public BlockPos multiply(final int factor) {
        return new BlockPos(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }
    
    public BlockPos offsetUp() {
        return this.offsetUp(1);
    }
    
    public BlockPos offsetUp(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public BlockPos offsetDown() {
        return this.offsetDown(1);
    }
    
    public BlockPos offsetDown(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public BlockPos offsetNorth() {
        return this.offsetNorth(1);
    }
    
    public BlockPos offsetNorth(final int n) {
        return this.offset(EnumFacing.NORTH, n);
    }
    
    public BlockPos offsetSouth() {
        return this.offsetSouth(1);
    }
    
    public BlockPos offsetSouth(final int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }
    
    public BlockPos offsetWest() {
        return this.offsetWest(1);
    }
    
    public BlockPos offsetWest(final int n) {
        return this.offset(EnumFacing.WEST, n);
    }
    
    public BlockPos offsetEast() {
        return this.offsetEast(1);
    }
    
    public BlockPos offsetEast(final int n) {
        return this.offset(EnumFacing.EAST, n);
    }
    
    public BlockPos offset(final EnumFacing facing) {
        return this.offset(facing, 1);
    }
    
    public BlockPos offset(final EnumFacing facing, final int n) {
        return new BlockPos(this.getX() + facing.getFrontOffsetX() * n, this.getY() + facing.getFrontOffsetY() * n, this.getZ() + facing.getFrontOffsetZ() * n);
    }
    
    public BlockPos crossProductBP(final Vec3i vec) {
        return new BlockPos(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }
    
    public long toLong() {
        return ((long)this.getX() & BlockPos.field_177994_h) << BlockPos.field_177988_g | ((long)this.getY() & BlockPos.field_177995_i) << BlockPos.field_177987_f | ((long)this.getZ() & BlockPos.field_177993_j) << 0;
    }
    
    @Override
    public Vec3i crossProduct(final Vec3i vec) {
        return this.crossProductBP(vec);
    }
    
    static {
        ORIGIN = new BlockPos(0, 0, 0);
        field_177990_b = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
        field_177991_c = BlockPos.field_177990_b;
        field_177989_d = 64 - BlockPos.field_177990_b - BlockPos.field_177991_c;
        field_177987_f = 0 + BlockPos.field_177991_c;
        field_177988_g = BlockPos.field_177987_f + BlockPos.field_177989_d;
        field_177994_h = (1L << BlockPos.field_177990_b) - 1L;
        field_177995_i = (1L << BlockPos.field_177989_d) - 1L;
        field_177993_j = (1L << BlockPos.field_177991_c) - 1L;
    }
    
    public static final class MutableBlockPos extends BlockPos
    {
        public int x;
        public int y;
        public int z;
        
        private MutableBlockPos(final int x_, final int y_, final int z_) {
            super(0, 0, 0);
            this.x = x_;
            this.y = y_;
            this.z = z_;
        }
        
        MutableBlockPos(final int p_i46025_1_, final int p_i46025_2_, final int p_i46025_3_, final Object p_i46025_4_) {
            this(p_i46025_1_, p_i46025_2_, p_i46025_3_);
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
        public Vec3i crossProduct(final Vec3i vec) {
            return super.crossProductBP(vec);
        }
    }
}
