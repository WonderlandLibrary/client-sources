/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.vector.Orientation;

public enum Mirror {
    NONE(Orientation.IDENTITY),
    LEFT_RIGHT(Orientation.INVERT_Z),
    FRONT_BACK(Orientation.INVERT_X);

    private final Orientation orientation;

    private Mirror(Orientation orientation) {
        this.orientation = orientation;
    }

    public int mirrorRotation(int n, int n2) {
        int n3 = n2 / 2;
        int n4 = n > n3 ? n - n2 : n;
        switch (1.$SwitchMap$net$minecraft$util$Mirror[this.ordinal()]) {
            case 1: {
                return (n2 - n4) % n2;
            }
            case 2: {
                return (n3 - n4 + n2) % n2;
            }
        }
        return n;
    }

    public Rotation toRotation(Direction direction) {
        Direction.Axis axis = direction.getAxis();
        return !(this == LEFT_RIGHT && axis == Direction.Axis.Z || this == FRONT_BACK && axis == Direction.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
    }

    public Direction mirror(Direction direction) {
        if (this == FRONT_BACK && direction.getAxis() == Direction.Axis.X) {
            return direction.getOpposite();
        }
        return this == LEFT_RIGHT && direction.getAxis() == Direction.Axis.Z ? direction.getOpposite() : direction;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }
}

