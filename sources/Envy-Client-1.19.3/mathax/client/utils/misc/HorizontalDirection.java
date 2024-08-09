package mathax.client.utils.misc;

public enum HorizontalDirection {
    South("South or Z+", "South", "Z+", false, 0, 0, 1),
    South_East("South East or Z+ X+", "South East", "Z+ X+", true, -45, 1, 1),
    West("West or X-", "West", "X-", false, 90, -1, 0),
    North_West("North West or Z- X-", "North West", "Z- X-", true, 135, -1, -1),
    North("North or Z-", "North", "Z-", false, 180, 0, -1),
    North_East("North East or Z- X+", "North East", "Z- X+", true, -135, 1, -1),
    East("East or X+", "East", "X+", false, -90, 1, 0),
    South_West("South West or Z+ X-","South West", "Z+ X-", true, 45, -1, 1);

    private final String title;
    public final String name;
    public final String axis;
    public final boolean diagonal;
    public final float yaw;
    public final int offsetX, offsetZ;

    HorizontalDirection(String title, String name, String axis, boolean diagonal, float yaw, int offsetX, int offsetZ) {
        this.title = title;
        this.name = name;
        this.axis = axis;
        this.diagonal = diagonal;
        this.yaw = yaw;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
    }

    public HorizontalDirection opposite() {
        return switch (this) {
            case South -> North;
            case South_East -> North_West;
            case West -> East;
            case North_West -> South_East;
            case North -> South;
            case North_East -> South_West;
            case East -> West;
            case South_West -> North_East;
        };
    }

    public HorizontalDirection rotateLeft() {
        return switch (this) {
            case South -> South_East;
            case South_East -> East;
            case East -> North_East;
            case North_East -> North;
            case North -> North_West;
            case North_West -> West;
            case West -> South_West;
            case South_West -> South;
        };
    }

    public HorizontalDirection rotateLeftSkipOne() {
        return switch (this) {
            case South -> East;
            case East -> North;
            case North -> West;
            case West -> South;
            case South_East -> North_East;
            case North_East -> North_West;
            case North_West -> South_West;
            case South_West -> South_East;
        };
    }

    public HorizontalDirection rotateRight() {
        return switch (this) {
            case South -> South_West;
            case South_West -> West;
            case West -> North_West;
            case North_West -> North;
            case North -> North_East;
            case North_East -> East;
            case East -> South_East;
            case South_East -> South;
        };
    }

    public static HorizontalDirection get(float yaw) {
        yaw = yaw % 360;
        if (yaw < 0) yaw += 360;

        if (yaw >= 337.5 || yaw < 22.5) return South;
        else if (yaw >= 22.5 && yaw < 67.5) return South_West;
        else if (yaw >= 67.5 && yaw < 112.5) return West;
        else if (yaw >= 112.5 && yaw < 157.5) return North_West;
        else if (yaw >= 157.5 && yaw < 202.5) return North;
        else if (yaw >= 202.5 && yaw < 247.5) return North_East;
        else if (yaw >= 247.5 && yaw < 292.5) return East;
        else if (yaw >= 292.5 && yaw < 337.5) return South_East;

        return South;
    }

    @Override
    public String toString() {
        return title;
    }
}
