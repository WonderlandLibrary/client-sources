package io.github.liticane.electron.event;

public enum EventPriority {
    //Priority of the events
    LOWEST(4),
    LOWER(3),
    DEFAULT(2),
    HIGHER(1),
    HIGHEST(0);

    private final int priority;

    EventPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
