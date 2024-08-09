/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.math.vector.Vector4f;

public enum Direction implements IStringSerializable
{
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vector3i(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vector3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vector3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vector3i(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vector3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vector3i(1, 0, 0));

    private final int index;
    private final int opposite;
    private final int horizontalIndex;
    private final String name;
    private final Axis axis;
    private final AxisDirection axisDirection;
    private final Vector3i directionVec;
    public static final Direction[] VALUES;
    private static final Map<String, Direction> NAME_LOOKUP;
    public static final Direction[] BY_INDEX;
    private static final Direction[] BY_HORIZONTAL_INDEX;
    private static final Long2ObjectMap<Direction> BY_LONG;

    private Direction(int n2, int n3, int n4, String string2, AxisDirection axisDirection, Axis axis, Vector3i vector3i) {
        this.index = n2;
        this.horizontalIndex = n4;
        this.opposite = n3;
        this.name = string2;
        this.axis = axis;
        this.axisDirection = axisDirection;
        this.directionVec = vector3i;
    }

    public static Direction[] getFacingDirections(Entity entity2) {
        Direction direction;
        float f = entity2.getPitch(1.0f) * ((float)Math.PI / 180);
        float f2 = -entity2.getYaw(1.0f) * ((float)Math.PI / 180);
        float f3 = MathHelper.sin(f);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f2);
        float f6 = MathHelper.cos(f2);
        boolean bl = f5 > 0.0f;
        boolean bl2 = f3 < 0.0f;
        boolean bl3 = f6 > 0.0f;
        float f7 = bl ? f5 : -f5;
        float f8 = bl2 ? -f3 : f3;
        float f9 = bl3 ? f6 : -f6;
        float f10 = f7 * f4;
        float f11 = f9 * f4;
        Direction direction2 = bl ? EAST : WEST;
        Direction direction3 = bl2 ? UP : DOWN;
        Direction direction4 = direction = bl3 ? SOUTH : NORTH;
        if (f7 > f9) {
            if (f8 > f10) {
                return Direction.compose(direction3, direction2, direction);
            }
            return f11 > f8 ? Direction.compose(direction2, direction, direction3) : Direction.compose(direction2, direction3, direction);
        }
        if (f8 > f11) {
            return Direction.compose(direction3, direction, direction2);
        }
        return f10 > f8 ? Direction.compose(direction, direction2, direction3) : Direction.compose(direction, direction3, direction2);
    }

    private static Direction[] compose(Direction direction, Direction direction2, Direction direction3) {
        return new Direction[]{direction, direction2, direction3, direction3.getOpposite(), direction2.getOpposite(), direction.getOpposite()};
    }

    public static Direction rotateFace(Matrix4f matrix4f, Direction direction) {
        Vector3i vector3i = direction.getDirectionVec();
        Vector4f vector4f = new Vector4f(vector3i.getX(), vector3i.getY(), vector3i.getZ(), 0.0f);
        vector4f.transform(matrix4f);
        return Direction.getFacingFromVector(vector4f.getX(), vector4f.getY(), vector4f.getZ());
    }

    public Quaternion getRotation() {
        Quaternion quaternion = Vector3f.XP.rotationDegrees(90.0f);
        switch (this) {
            case DOWN: {
                return Vector3f.XP.rotationDegrees(180.0f);
            }
            case UP: {
                return Quaternion.ONE.copy();
            }
            case NORTH: {
                quaternion.multiply(Vector3f.ZP.rotationDegrees(180.0f));
                return quaternion;
            }
            case SOUTH: {
                return quaternion;
            }
            case WEST: {
                quaternion.multiply(Vector3f.ZP.rotationDegrees(90.0f));
                return quaternion;
            }
        }
        quaternion.multiply(Vector3f.ZP.rotationDegrees(-90.0f));
        return quaternion;
    }

    public int getIndex() {
        return this.index;
    }

    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    public AxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public Direction getOpposite() {
        return VALUES[this.opposite];
    }

    public Direction rotateY() {
        switch (this) {
            case NORTH: {
                return EAST;
            }
            case SOUTH: {
                return WEST;
            }
            case WEST: {
                return NORTH;
            }
            case EAST: {
                return SOUTH;
            }
        }
        throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
    }

    public Direction rotateYCCW() {
        switch (this) {
            case NORTH: {
                return WEST;
            }
            case SOUTH: {
                return EAST;
            }
            case WEST: {
                return SOUTH;
            }
            case EAST: {
                return NORTH;
            }
        }
        throw new IllegalStateException("Unable to get CCW facing of " + this);
    }

    public int getXOffset() {
        return this.directionVec.getX();
    }

    public int getYOffset() {
        return this.directionVec.getY();
    }

    public int getZOffset() {
        return this.directionVec.getZ();
    }

    public Vector3f toVector3f() {
        return new Vector3f(this.getXOffset(), this.getYOffset(), this.getZOffset());
    }

    public String getName2() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    @Nullable
    public static Direction byName(@Nullable String string) {
        return string == null ? null : NAME_LOOKUP.get(string.toLowerCase(Locale.ROOT));
    }

    public static Direction byIndex(int n) {
        return BY_INDEX[MathHelper.abs(n % BY_INDEX.length)];
    }

    public static Direction byHorizontalIndex(int n) {
        return BY_HORIZONTAL_INDEX[MathHelper.abs(n % BY_HORIZONTAL_INDEX.length)];
    }

    @Nullable
    public static Direction byLong(int n, int n2, int n3) {
        return (Direction)BY_LONG.get(BlockPos.pack(n, n2, n3));
    }

    public static Direction fromAngle(double d) {
        return Direction.byHorizontalIndex(MathHelper.floor(d / 90.0 + 0.5) & 3);
    }

    public static Direction getFacingFromAxisDirection(Axis axis, AxisDirection axisDirection) {
        switch (axis) {
            case X: {
                return axisDirection == AxisDirection.POSITIVE ? EAST : WEST;
            }
            case Y: {
                return axisDirection == AxisDirection.POSITIVE ? UP : DOWN;
            }
        }
        return axisDirection == AxisDirection.POSITIVE ? SOUTH : NORTH;
    }

    public float getHorizontalAngle() {
        return (this.horizontalIndex & 3) * 90;
    }

    public static Direction getRandomDirection(Random random2) {
        return Util.getRandomObject(VALUES, random2);
    }

    public static Direction getFacingFromVector(double d, double d2, double d3) {
        return Direction.getFacingFromVector((float)d, (float)d2, (float)d3);
    }

    public static Direction getFacingFromVector(float f, float f2, float f3) {
        Direction direction = NORTH;
        float f4 = Float.MIN_VALUE;
        for (Direction direction2 : VALUES) {
            float f5 = f * (float)direction2.directionVec.getX() + f2 * (float)direction2.directionVec.getY() + f3 * (float)direction2.directionVec.getZ();
            if (!(f5 > f4)) continue;
            f4 = f5;
            direction = direction2;
        }
        return direction;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getString() {
        return this.name;
    }

    public static Direction getFacingFromAxis(AxisDirection axisDirection, Axis axis) {
        for (Direction direction : VALUES) {
            if (direction.getAxisDirection() != axisDirection || direction.getAxis() != axis) continue;
            return direction;
        }
        throw new IllegalArgumentException("No such direction: " + axisDirection + " " + axis);
    }

    public Vector3i getDirectionVec() {
        return this.directionVec;
    }

    public boolean hasOrientation(float f) {
        float f2 = f * ((float)Math.PI / 180);
        float f3 = -MathHelper.sin(f2);
        float f4 = MathHelper.cos(f2);
        return (float)this.directionVec.getX() * f3 + (float)this.directionVec.getZ() * f4 > 0.0f;
    }

    private static Direction lambda$static$8(Direction direction, Direction direction2) {
        throw new IllegalArgumentException("Duplicate keys");
    }

    private static Direction lambda$static$7(Direction direction) {
        return direction;
    }

    private static Long lambda$static$6(Direction direction) {
        return new BlockPos(direction.getDirectionVec()).toLong();
    }

    private static Direction[] lambda$static$5(int n) {
        return new Direction[n];
    }

    private static int lambda$static$4(Direction direction) {
        return direction.horizontalIndex;
    }

    private static boolean lambda$static$3(Direction direction) {
        return direction.getAxis().isHorizontal();
    }

    private static Direction[] lambda$static$2(int n) {
        return new Direction[n];
    }

    private static int lambda$static$1(Direction direction) {
        return direction.index;
    }

    private static Direction lambda$static$0(Direction direction) {
        return direction;
    }

    static {
        VALUES = Direction.values();
        NAME_LOOKUP = Arrays.stream(VALUES).collect(Collectors.toMap(Direction::getName2, Direction::lambda$static$0));
        BY_INDEX = (Direction[])Arrays.stream(VALUES).sorted(Comparator.comparingInt(Direction::lambda$static$1)).toArray(Direction::lambda$static$2);
        BY_HORIZONTAL_INDEX = (Direction[])Arrays.stream(VALUES).filter(Direction::lambda$static$3).sorted(Comparator.comparingInt(Direction::lambda$static$4)).toArray(Direction::lambda$static$5);
        BY_LONG = Arrays.stream(VALUES).collect(Collectors.toMap(Direction::lambda$static$6, Direction::lambda$static$7, Direction::lambda$static$8, Long2ObjectOpenHashMap::new));
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static enum Axis implements IStringSerializable,
    Predicate<Direction>
    {
        X("x"){

            @Override
            public int getCoordinate(int n, int n2, int n3) {
                return n;
            }

            @Override
            public double getCoordinate(double d, double d2, double d3) {
                return d;
            }

            @Override
            public boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        }
        ,
        Y("y"){

            @Override
            public int getCoordinate(int n, int n2, int n3) {
                return n2;
            }

            @Override
            public double getCoordinate(double d, double d2, double d3) {
                return d2;
            }

            @Override
            public boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        }
        ,
        Z("z"){

            @Override
            public int getCoordinate(int n, int n2, int n3) {
                return n3;
            }

            @Override
            public double getCoordinate(double d, double d2, double d3) {
                return d3;
            }

            @Override
            public boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        };

        private static final Axis[] VALUES;
        public static final Codec<Axis> CODEC;
        private static final Map<String, Axis> NAME_LOOKUP;
        private final String name;

        private Axis(String string2) {
            this.name = string2;
        }

        @Nullable
        public static Axis byName(String string) {
            return NAME_LOOKUP.get(string.toLowerCase(Locale.ROOT));
        }

        public String getName2() {
            return this.name;
        }

        public boolean isVertical() {
            return this == Y;
        }

        public boolean isHorizontal() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.name;
        }

        public static Axis getRandomAxis(Random random2) {
            return Util.getRandomObject(VALUES, random2);
        }

        @Override
        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis() == this;
        }

        public Plane getPlane() {
            switch (this) {
                case X: 
                case Z: {
                    return Plane.HORIZONTAL;
                }
                case Y: {
                    return Plane.VERTICAL;
                }
            }
            throw new Error("Someone's been tampering with the universe!");
        }

        @Override
        public String getString() {
            return this.name;
        }

        public abstract int getCoordinate(int var1, int var2, int var3);

        public abstract double getCoordinate(double var1, double var3, double var5);

        @Override
        public boolean test(@Nullable Object object) {
            return this.test((Direction)object);
        }

        private static Axis lambda$static$0(Axis axis) {
            return axis;
        }

        static {
            VALUES = Axis.values();
            CODEC = IStringSerializable.createEnumCodec(Axis::values, Axis::byName);
            NAME_LOOKUP = Arrays.stream(VALUES).collect(Collectors.toMap(Axis::getName2, Axis::lambda$static$0));
        }
    }

    public static enum AxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int offset;
        private final String description;

        private AxisDirection(int n2, String string2) {
            this.offset = n2;
            this.description = string2;
        }

        public int getOffset() {
            return this.offset;
        }

        public String toString() {
            return this.description;
        }

        public AxisDirection inverted() {
            return this == POSITIVE ? NEGATIVE : POSITIVE;
        }
    }

    public static enum Plane implements Iterable<Direction>,
    Predicate<Direction>
    {
        HORIZONTAL(new Direction[]{NORTH, EAST, SOUTH, WEST}, new Axis[]{Axis.X, Axis.Z}),
        VERTICAL(new Direction[]{UP, DOWN}, new Axis[]{Axis.Y});

        private final Direction[] facingValues;
        private final Axis[] axisValues;

        private Plane(Direction[] directionArray, Axis[] axisArray) {
            this.facingValues = directionArray;
            this.axisValues = axisArray;
        }

        public Direction random(Random random2) {
            return Util.getRandomObject(this.facingValues, random2);
        }

        public Axis func_244803_b(Random random2) {
            return Util.getRandomObject(this.axisValues, random2);
        }

        @Override
        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis().getPlane() == this;
        }

        @Override
        public Iterator<Direction> iterator() {
            return Iterators.forArray(this.facingValues);
        }

        public Stream<Direction> getDirectionValues() {
            return Arrays.stream(this.facingValues);
        }

        @Override
        public boolean test(@Nullable Object object) {
            return this.test((Direction)object);
        }
    }
}

