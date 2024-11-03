package dev.lvstrng.argon.event;

public class PriorityListener implements EventListener {
    private final EventListener listener;
    private final int priority;

    public PriorityListener(final EventListener listener) {
        this(listener, 0);
    }

    public PriorityListener(final EventListener listener, final int priority) {
        this.listener = listener;
        this.priority = priority;
    }

    public EventListener getListener() {
        return this.listener;
    }

    public int getPriority() {
        return this.priority;
    }
}
