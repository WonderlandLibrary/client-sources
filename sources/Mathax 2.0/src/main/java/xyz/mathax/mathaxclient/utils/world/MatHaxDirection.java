package xyz.mathax.mathaxclient.utils.world;

import net.minecraft.util.math.Direction;

public class MatHaxDirection {
    public static final byte UP = 1 << 1;
    public static final byte DOWN = 1 << 2;
    public static final byte NORTH = 1 << 3;
    public static final byte SOUTH = 1 << 4;
    public static final byte WEST = 1 << 5;
    public static final byte EAST = 1 << 6;

    public static byte get(Direction direction) {
        return switch (direction) {
            case UP    -> UP;
            case DOWN  -> DOWN;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST  -> WEST;
            case EAST  -> EAST;
        };
    }

    public static boolean is(int direction, byte b) {
        return (direction & b) == b;
    }

    public static boolean isNot(int direction, byte b) {
        return (direction & b) != b;
    }
}
