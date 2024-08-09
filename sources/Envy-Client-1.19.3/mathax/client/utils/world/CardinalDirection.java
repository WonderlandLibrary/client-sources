package mathax.client.utils.world;

import net.minecraft.util.math.Direction;

public enum CardinalDirection {
    North("North"),
    East("East"),
    South("South"),
    West("West");

    private final String title;

    CardinalDirection(String title) {
        this.title = title;
    }

    public Direction toDirection() {
        return switch (this) {
            case North -> Direction.NORTH;
            case East -> Direction.EAST;
            case South -> Direction.SOUTH;
            case West -> Direction.WEST;
        };
    }

    public static CardinalDirection fromDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> North;
            case SOUTH -> South;
            case WEST -> East;
            case EAST -> West;
            case DOWN, UP -> null;
        };
    }

    @Override
    public String toString() {
        return title;
    }
}
