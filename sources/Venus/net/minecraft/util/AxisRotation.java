/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.Direction;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum AxisRotation {
    NONE{

        @Override
        public int getCoordinate(int n, int n2, int n3, Direction.Axis axis) {
            return axis.getCoordinate(n, n2, n3);
        }

        @Override
        public Direction.Axis rotate(Direction.Axis axis) {
            return axis;
        }

        @Override
        public AxisRotation reverse() {
            return this;
        }
    }
    ,
    FORWARD{

        @Override
        public int getCoordinate(int n, int n2, int n3, Direction.Axis axis) {
            return axis.getCoordinate(n3, n, n2);
        }

        @Override
        public Direction.Axis rotate(Direction.Axis axis) {
            return AXES[Math.floorMod(axis.ordinal() + 1, 3)];
        }

        @Override
        public AxisRotation reverse() {
            return BACKWARD;
        }
    }
    ,
    BACKWARD{

        @Override
        public int getCoordinate(int n, int n2, int n3, Direction.Axis axis) {
            return axis.getCoordinate(n2, n3, n);
        }

        @Override
        public Direction.Axis rotate(Direction.Axis axis) {
            return AXES[Math.floorMod(axis.ordinal() - 1, 3)];
        }

        @Override
        public AxisRotation reverse() {
            return FORWARD;
        }
    };

    public static final Direction.Axis[] AXES;
    public static final AxisRotation[] AXIS_ROTATIONS;

    public abstract int getCoordinate(int var1, int var2, int var3, Direction.Axis var4);

    public abstract Direction.Axis rotate(Direction.Axis var1);

    public abstract AxisRotation reverse();

    public static AxisRotation from(Direction.Axis axis, Direction.Axis axis2) {
        return AXIS_ROTATIONS[Math.floorMod(axis2.ordinal() - axis.ordinal(), 3)];
    }

    static {
        AXES = Direction.Axis.values();
        AXIS_ROTATIONS = AxisRotation.values();
    }
}

