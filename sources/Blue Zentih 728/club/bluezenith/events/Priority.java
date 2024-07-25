package club.bluezenith.events;

public enum Priority {
    LOW(3),
    NORMAL(2),
    MODERATE(1),
    SEVERE(0),
    CRITICAL(-1);

    private final int val;

    Priority(int value) {
       val = value;
    }

    public int getPriority() {
        return val;
    }
}
