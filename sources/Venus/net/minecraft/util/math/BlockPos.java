/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.concurrent.Immutable;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.AxisRotation;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Immutable
public class BlockPos
extends Vector3i {
    public static final Codec<BlockPos> CODEC = Codec.INT_STREAM.comapFlatMap(BlockPos::lambda$static$1, BlockPos::lambda$static$2).stable();
    private static final Logger LOGGER = LogManager.getLogger();
    public static final BlockPos ZERO = new BlockPos(0, 0, 0);
    private static final int NUM_X_BITS;
    private static final int NUM_Z_BITS;
    private static final int NUM_Y_BITS;
    private static final long X_MASK;
    private static final long Y_MASK;
    private static final long Z_MASK;
    private static final int INVERSE_START_BITS_Z;
    private static final int INVERSE_START_BITS_X;

    public BlockPos(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public BlockPos(double d, double d2, double d3) {
        super(d, d2, d3);
    }

    public BlockPos(Vector3d vector3d) {
        this(vector3d.x, vector3d.y, vector3d.z);
    }

    public BlockPos(IPosition iPosition) {
        this(iPosition.getX(), iPosition.getY(), iPosition.getZ());
    }

    public BlockPos(Vector3i vector3i) {
        this(vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public static long offset(long l, Direction direction) {
        return BlockPos.offset(l, direction.getXOffset(), direction.getYOffset(), direction.getZOffset());
    }

    public static long offset(long l, int n, int n2, int n3) {
        return BlockPos.pack(BlockPos.unpackX(l) + n, BlockPos.unpackY(l) + n2, BlockPos.unpackZ(l) + n3);
    }

    public static int unpackX(long l) {
        return (int)(l << 64 - INVERSE_START_BITS_X - NUM_X_BITS >> 64 - NUM_X_BITS);
    }

    public static int unpackY(long l) {
        return (int)(l << 64 - NUM_Y_BITS >> 64 - NUM_Y_BITS);
    }

    public static int unpackZ(long l) {
        return (int)(l << 64 - INVERSE_START_BITS_Z - NUM_Z_BITS >> 64 - NUM_Z_BITS);
    }

    public static BlockPos fromLong(long l) {
        return new BlockPos(BlockPos.unpackX(l), BlockPos.unpackY(l), BlockPos.unpackZ(l));
    }

    public long toLong() {
        return BlockPos.pack(this.getX(), this.getY(), this.getZ());
    }

    public static long pack(int n, int n2, int n3) {
        long l = 0L;
        l |= ((long)n & X_MASK) << INVERSE_START_BITS_X;
        return (l |= ((long)n2 & Y_MASK) << 0) | ((long)n3 & Z_MASK) << INVERSE_START_BITS_Z;
    }

    public static long atSectionBottomY(long l) {
        return l & 0xFFFFFFFFFFFFFFF0L;
    }

    public BlockPos add(double d, double d2, double d3) {
        return d == 0.0 && d2 == 0.0 && d3 == 0.0 ? this : new BlockPos((double)this.getX() + d, (double)this.getY() + d2, (double)this.getZ() + d3);
    }

    public BlockPos add(int n, int n2, int n3) {
        return n == 0 && n2 == 0 && n3 == 0 ? this : new BlockPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
    }

    public BlockPos add(Vector3i vector3i) {
        return this.add(vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public BlockPos subtract(Vector3i vector3i) {
        return this.add(-vector3i.getX(), -vector3i.getY(), -vector3i.getZ());
    }

    @Override
    public BlockPos up() {
        return this.offset(Direction.UP);
    }

    @Override
    public BlockPos up(int n) {
        return this.offset(Direction.UP, n);
    }

    @Override
    public BlockPos down() {
        return this.offset(Direction.DOWN);
    }

    @Override
    public BlockPos down(int n) {
        return this.offset(Direction.DOWN, n);
    }

    public BlockPos north() {
        return this.offset(Direction.NORTH);
    }

    public BlockPos north(int n) {
        return this.offset(Direction.NORTH, n);
    }

    public BlockPos south() {
        return this.offset(Direction.SOUTH);
    }

    public BlockPos south(int n) {
        return this.offset(Direction.SOUTH, n);
    }

    public BlockPos west() {
        return this.offset(Direction.WEST);
    }

    public BlockPos west(int n) {
        return this.offset(Direction.WEST, n);
    }

    public BlockPos east() {
        return this.offset(Direction.EAST);
    }

    public BlockPos east(int n) {
        return this.offset(Direction.EAST, n);
    }

    public BlockPos offset(Direction direction) {
        return new BlockPos(this.getX() + direction.getXOffset(), this.getY() + direction.getYOffset(), this.getZ() + direction.getZOffset());
    }

    @Override
    public BlockPos offset(Direction direction, int n) {
        return n == 0 ? this : new BlockPos(this.getX() + direction.getXOffset() * n, this.getY() + direction.getYOffset() * n, this.getZ() + direction.getZOffset() * n);
    }

    public BlockPos func_241872_a(Direction.Axis axis, int n) {
        if (n == 0) {
            return this;
        }
        int n2 = axis == Direction.Axis.X ? n : 0;
        int n3 = axis == Direction.Axis.Y ? n : 0;
        int n4 = axis == Direction.Axis.Z ? n : 0;
        return new BlockPos(this.getX() + n2, this.getY() + n3, this.getZ() + n4);
    }

    public BlockPos rotate(Rotation rotation) {
        switch (rotation) {
            default: {
                return this;
            }
            case CLOCKWISE_90: {
                return new BlockPos(-this.getZ(), this.getY(), this.getX());
            }
            case CLOCKWISE_180: {
                return new BlockPos(-this.getX(), this.getY(), -this.getZ());
            }
            case COUNTERCLOCKWISE_90: 
        }
        return new BlockPos(this.getZ(), this.getY(), -this.getX());
    }

    @Override
    public BlockPos crossProduct(Vector3i vector3i) {
        return new BlockPos(this.getY() * vector3i.getZ() - this.getZ() * vector3i.getY(), this.getZ() * vector3i.getX() - this.getX() * vector3i.getZ(), this.getX() * vector3i.getY() - this.getY() * vector3i.getX());
    }

    public BlockPos toImmutable() {
        return this;
    }

    public Mutable toMutable() {
        return new Mutable(this.getX(), this.getY(), this.getZ());
    }

    public static Iterable<BlockPos> getRandomPositions(Random random2, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8 = n5 - n2 + 1;
        int n9 = n6 - n3 + 1;
        int n10 = n7 - n4 + 1;
        return () -> BlockPos.lambda$getRandomPositions$3(n, n2, random2, n8, n3, n9, n4, n10);
    }

    public static Iterable<BlockPos> getProximitySortedBoxPositionsIterator(BlockPos blockPos, int n, int n2, int n3) {
        int n4 = n + n2 + n3;
        int n5 = blockPos.getX();
        int n6 = blockPos.getY();
        int n7 = blockPos.getZ();
        return () -> BlockPos.lambda$getProximitySortedBoxPositionsIterator$4(n7, n4, n, n2, n3, n5, n6);
    }

    public static Optional<BlockPos> getClosestMatchingPosition(BlockPos blockPos, int n, int n2, Predicate<BlockPos> predicate) {
        return BlockPos.getProximitySortedBoxPositions(blockPos, n, n2, n).filter(predicate).findFirst();
    }

    public static Stream<BlockPos> getProximitySortedBoxPositions(BlockPos blockPos, int n, int n2, int n3) {
        return StreamSupport.stream(BlockPos.getProximitySortedBoxPositionsIterator(blockPos, n, n2, n3).spliterator(), false);
    }

    public static Iterable<BlockPos> getAllInBoxMutable(BlockPos blockPos, BlockPos blockPos2) {
        return BlockPos.getAllInBoxMutable(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()), Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
    }

    public static Stream<BlockPos> getAllInBox(BlockPos blockPos, BlockPos blockPos2) {
        return StreamSupport.stream(BlockPos.getAllInBoxMutable(blockPos, blockPos2).spliterator(), false);
    }

    public static Stream<BlockPos> getAllInBox(MutableBoundingBox mutableBoundingBox) {
        return BlockPos.getAllInBox(Math.min(mutableBoundingBox.minX, mutableBoundingBox.maxX), Math.min(mutableBoundingBox.minY, mutableBoundingBox.maxY), Math.min(mutableBoundingBox.minZ, mutableBoundingBox.maxZ), Math.max(mutableBoundingBox.minX, mutableBoundingBox.maxX), Math.max(mutableBoundingBox.minY, mutableBoundingBox.maxY), Math.max(mutableBoundingBox.minZ, mutableBoundingBox.maxZ));
    }

    public static Stream<BlockPos> getAllInBox(AxisAlignedBB axisAlignedBB) {
        return BlockPos.getAllInBox(MathHelper.floor(axisAlignedBB.minX), MathHelper.floor(axisAlignedBB.minY), MathHelper.floor(axisAlignedBB.minZ), MathHelper.floor(axisAlignedBB.maxX), MathHelper.floor(axisAlignedBB.maxY), MathHelper.floor(axisAlignedBB.maxZ));
    }

    public static Stream<BlockPos> getAllInBox(int n, int n2, int n3, int n4, int n5, int n6) {
        return StreamSupport.stream(BlockPos.getAllInBoxMutable(n, n2, n3, n4, n5, n6).spliterator(), false);
    }

    public static Iterable<BlockPos> getAllInBoxMutable(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n4 - n + 1;
        int n8 = n5 - n2 + 1;
        int n9 = n6 - n3 + 1;
        int n10 = n7 * n8 * n9;
        return () -> BlockPos.lambda$getAllInBoxMutable$5(n10, n7, n8, n, n2, n3);
    }

    public static Iterable<Mutable> func_243514_a(BlockPos blockPos, int n, Direction direction, Direction direction2) {
        Validate.validState(direction.getAxis() != direction2.getAxis(), "The two directions cannot be on the same axis", new Object[0]);
        return () -> BlockPos.lambda$func_243514_a$6(direction, direction2, blockPos, n);
    }

    @Override
    public Vector3i crossProduct(Vector3i vector3i) {
        return this.crossProduct(vector3i);
    }

    @Override
    public Vector3i offset(Direction direction, int n) {
        return this.offset(direction, n);
    }

    @Override
    public Vector3i down(int n) {
        return this.down(n);
    }

    @Override
    public Vector3i down() {
        return this.down();
    }

    @Override
    public Vector3i up(int n) {
        return this.up(n);
    }

    @Override
    public Vector3i up() {
        return this.up();
    }

    private static Iterator lambda$func_243514_a$6(Direction direction, Direction direction2, BlockPos blockPos, int n) {
        return new AbstractIterator<Mutable>(direction, direction2, blockPos, n){
            private final Direction[] field_243520_e;
            private final Mutable field_243521_f;
            private final int field_243522_g;
            private int field_243523_h;
            private int field_243524_i;
            private int field_243525_j;
            private int field_243526_k;
            private int field_243527_l;
            private int field_243528_m;
            final Direction val$p_243514_2_;
            final Direction val$p_243514_3_;
            final BlockPos val$p_243514_0_;
            final int val$p_243514_1_;
            {
                this.val$p_243514_2_ = direction;
                this.val$p_243514_3_ = direction2;
                this.val$p_243514_0_ = blockPos;
                this.val$p_243514_1_ = n;
                this.field_243520_e = new Direction[]{this.val$p_243514_2_, this.val$p_243514_3_, this.val$p_243514_2_.getOpposite(), this.val$p_243514_3_.getOpposite()};
                this.field_243521_f = this.val$p_243514_0_.toMutable().move(this.val$p_243514_3_);
                this.field_243522_g = 4 * this.val$p_243514_1_;
                this.field_243523_h = -1;
                this.field_243526_k = this.field_243521_f.getX();
                this.field_243527_l = this.field_243521_f.getY();
                this.field_243528_m = this.field_243521_f.getZ();
            }

            @Override
            protected Mutable computeNext() {
                this.field_243521_f.setPos(this.field_243526_k, this.field_243527_l, this.field_243528_m).move(this.field_243520_e[(this.field_243523_h + 4) % 4]);
                this.field_243526_k = this.field_243521_f.getX();
                this.field_243527_l = this.field_243521_f.getY();
                this.field_243528_m = this.field_243521_f.getZ();
                if (this.field_243525_j >= this.field_243524_i) {
                    if (this.field_243523_h >= this.field_243522_g) {
                        return (Mutable)this.endOfData();
                    }
                    ++this.field_243523_h;
                    this.field_243525_j = 0;
                    this.field_243524_i = this.field_243523_h / 2 + 1;
                }
                ++this.field_243525_j;
                return this.field_243521_f;
            }

            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
        };
    }

    private static Iterator lambda$getAllInBoxMutable$5(int n, int n2, int n3, int n4, int n5, int n6) {
        return new AbstractIterator<BlockPos>(n, n2, n3, n4, n5, n6){
            private final Mutable mutablePos;
            private int totalAmount;
            final int val$l;
            final int val$i;
            final int val$j;
            final int val$x1;
            final int val$y1;
            final int val$z1;
            {
                this.val$l = n;
                this.val$i = n2;
                this.val$j = n3;
                this.val$x1 = n4;
                this.val$y1 = n5;
                this.val$z1 = n6;
                this.mutablePos = new Mutable();
            }

            @Override
            protected BlockPos computeNext() {
                if (this.totalAmount == this.val$l) {
                    return (BlockPos)this.endOfData();
                }
                int n = this.totalAmount % this.val$i;
                int n2 = this.totalAmount / this.val$i;
                int n3 = n2 % this.val$j;
                int n4 = n2 / this.val$j;
                ++this.totalAmount;
                return this.mutablePos.setPos(this.val$x1 + n, this.val$y1 + n3, this.val$z1 + n4);
            }

            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
        };
    }

    private static Iterator lambda$getProximitySortedBoxPositionsIterator$4(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        return new AbstractIterator<BlockPos>(n, n2, n3, n4, n5, n6, n7){
            private final Mutable coordinateIterator;
            private int field_239604_i_;
            private int field_239605_j_;
            private int field_239606_k_;
            private int field_239607_l_;
            private int field_239608_m_;
            private boolean field_239609_n_;
            final int val$l;
            final int val$i;
            final int val$xWidth;
            final int val$yHeight;
            final int val$zWidth;
            final int val$j;
            final int val$k;
            {
                this.val$l = n;
                this.val$i = n2;
                this.val$xWidth = n3;
                this.val$yHeight = n4;
                this.val$zWidth = n5;
                this.val$j = n6;
                this.val$k = n7;
                this.coordinateIterator = new Mutable();
            }

            @Override
            protected BlockPos computeNext() {
                if (this.field_239609_n_) {
                    this.field_239609_n_ = false;
                    this.coordinateIterator.setZ(this.val$l - (this.coordinateIterator.getZ() - this.val$l));
                    return this.coordinateIterator;
                }
                Mutable mutable = null;
                while (mutable == null) {
                    if (this.field_239608_m_ > this.field_239606_k_) {
                        ++this.field_239607_l_;
                        if (this.field_239607_l_ > this.field_239605_j_) {
                            ++this.field_239604_i_;
                            if (this.field_239604_i_ > this.val$i) {
                                return (BlockPos)this.endOfData();
                            }
                            this.field_239605_j_ = Math.min(this.val$xWidth, this.field_239604_i_);
                            this.field_239607_l_ = -this.field_239605_j_;
                        }
                        this.field_239606_k_ = Math.min(this.val$yHeight, this.field_239604_i_ - Math.abs(this.field_239607_l_));
                        this.field_239608_m_ = -this.field_239606_k_;
                    }
                    int n = this.field_239607_l_;
                    int n2 = this.field_239608_m_;
                    int n3 = this.field_239604_i_ - Math.abs(n) - Math.abs(n2);
                    if (n3 <= this.val$zWidth) {
                        this.field_239609_n_ = n3 != 0;
                        mutable = this.coordinateIterator.setPos(this.val$j + n, this.val$k + n2, this.val$l + n3);
                    }
                    ++this.field_239608_m_;
                }
                return mutable;
            }

            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
        };
    }

    private static Iterator lambda$getRandomPositions$3(int n, int n2, Random random2, int n3, int n4, int n5, int n6, int n7) {
        return new AbstractIterator<BlockPos>(n, n2, random2, n3, n4, n5, n6, n7){
            final Mutable pos;
            int remainingAmount;
            final int val$amount;
            final int val$minX;
            final Random val$rand;
            final int val$i;
            final int val$minY;
            final int val$j;
            final int val$minZ;
            final int val$k;
            {
                this.val$amount = n;
                this.val$minX = n2;
                this.val$rand = random2;
                this.val$i = n3;
                this.val$minY = n4;
                this.val$j = n5;
                this.val$minZ = n6;
                this.val$k = n7;
                this.pos = new Mutable();
                this.remainingAmount = this.val$amount;
            }

            @Override
            protected BlockPos computeNext() {
                if (this.remainingAmount <= 0) {
                    return (BlockPos)this.endOfData();
                }
                Mutable mutable = this.pos.setPos(this.val$minX + this.val$rand.nextInt(this.val$i), this.val$minY + this.val$rand.nextInt(this.val$j), this.val$minZ + this.val$rand.nextInt(this.val$k));
                --this.remainingAmount;
                return mutable;
            }

            @Override
            protected Object computeNext() {
                return this.computeNext();
            }
        };
    }

    private static IntStream lambda$static$2(BlockPos blockPos) {
        return IntStream.of(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    private static DataResult lambda$static$1(IntStream intStream) {
        return Util.validateIntStreamSize(intStream, 3).map(BlockPos::lambda$static$0);
    }

    private static BlockPos lambda$static$0(int[] nArray) {
        return new BlockPos(nArray[0], nArray[1], nArray[2]);
    }

    static {
        NUM_Z_BITS = NUM_X_BITS = 1 + MathHelper.log2(MathHelper.smallestEncompassingPowerOfTwo(30000000));
        NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
        X_MASK = (1L << NUM_X_BITS) - 1L;
        Y_MASK = (1L << NUM_Y_BITS) - 1L;
        Z_MASK = (1L << NUM_Z_BITS) - 1L;
        INVERSE_START_BITS_Z = NUM_Y_BITS;
        INVERSE_START_BITS_X = NUM_Y_BITS + NUM_Z_BITS;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Mutable
    extends BlockPos {
        public Mutable() {
            this(0, 0, 0);
        }

        public Mutable(int n, int n2, int n3) {
            super(n, n2, n3);
        }

        public Mutable(double d, double d2, double d3) {
            this(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3));
        }

        @Override
        public BlockPos add(double d, double d2, double d3) {
            return super.add(d, d2, d3).toImmutable();
        }

        @Override
        public BlockPos add(int n, int n2, int n3) {
            return super.add(n, n2, n3).toImmutable();
        }

        @Override
        public BlockPos offset(Direction direction, int n) {
            return super.offset(direction, n).toImmutable();
        }

        @Override
        public BlockPos func_241872_a(Direction.Axis axis, int n) {
            return super.func_241872_a(axis, n).toImmutable();
        }

        @Override
        public BlockPos rotate(Rotation rotation) {
            return super.rotate(rotation).toImmutable();
        }

        public Mutable setPos(int n, int n2, int n3) {
            this.setX(n);
            this.setY(n2);
            this.setZ(n3);
            return this;
        }

        public Mutable setPos(double d, double d2, double d3) {
            return this.setPos(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3));
        }

        public Mutable setPos(Vector3i vector3i) {
            return this.setPos(vector3i.getX(), vector3i.getY(), vector3i.getZ());
        }

        public Mutable setPos(long l) {
            return this.setPos(Mutable.unpackX(l), Mutable.unpackY(l), Mutable.unpackZ(l));
        }

        public Mutable setPos(AxisRotation axisRotation, int n, int n2, int n3) {
            return this.setPos(axisRotation.getCoordinate(n, n2, n3, Direction.Axis.X), axisRotation.getCoordinate(n, n2, n3, Direction.Axis.Y), axisRotation.getCoordinate(n, n2, n3, Direction.Axis.Z));
        }

        public Mutable setAndMove(Vector3i vector3i, Direction direction) {
            return this.setPos(vector3i.getX() + direction.getXOffset(), vector3i.getY() + direction.getYOffset(), vector3i.getZ() + direction.getZOffset());
        }

        public Mutable setAndOffset(Vector3i vector3i, int n, int n2, int n3) {
            return this.setPos(vector3i.getX() + n, vector3i.getY() + n2, vector3i.getZ() + n3);
        }

        public Mutable move(Direction direction) {
            return this.move(direction, 1);
        }

        public Mutable move(Direction direction, int n) {
            return this.setPos(this.getX() + direction.getXOffset() * n, this.getY() + direction.getYOffset() * n, this.getZ() + direction.getZOffset() * n);
        }

        public Mutable move(int n, int n2, int n3) {
            return this.setPos(this.getX() + n, this.getY() + n2, this.getZ() + n3);
        }

        public Mutable func_243531_h(Vector3i vector3i) {
            return this.setPos(this.getX() + vector3i.getX(), this.getY() + vector3i.getY(), this.getZ() + vector3i.getZ());
        }

        public Mutable clampAxisCoordinate(Direction.Axis axis, int n, int n2) {
            switch (axis) {
                case X: {
                    return this.setPos(MathHelper.clamp(this.getX(), n, n2), this.getY(), this.getZ());
                }
                case Y: {
                    return this.setPos(this.getX(), MathHelper.clamp(this.getY(), n, n2), this.getZ());
                }
                case Z: {
                    return this.setPos(this.getX(), this.getY(), MathHelper.clamp(this.getZ(), n, n2));
                }
            }
            throw new IllegalStateException("Unable to clamp axis " + axis);
        }

        @Override
        public void setX(int n) {
            super.setX(n);
        }

        @Override
        public void setY(int n) {
            super.setY(n);
        }

        @Override
        public void setZ(int n) {
            super.setZ(n);
        }

        @Override
        public BlockPos toImmutable() {
            return new BlockPos(this);
        }

        @Override
        public Vector3i crossProduct(Vector3i vector3i) {
            return super.crossProduct(vector3i);
        }

        @Override
        public Vector3i offset(Direction direction, int n) {
            return this.offset(direction, n);
        }

        @Override
        public Vector3i down(int n) {
            return super.down(n);
        }

        @Override
        public Vector3i down() {
            return super.down();
        }

        @Override
        public Vector3i up(int n) {
            return super.up(n);
        }

        @Override
        public Vector3i up() {
            return super.up();
        }
    }
}

