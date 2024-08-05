package fr.dog.event;

public abstract class Event {
    private final EventStage stage;

    protected Event(final EventStage stage) {
        this.stage = stage;
    }

    protected Event() {
        this.stage = EventStage.NONE;
    }

    public EventStage getStage() {
        return stage;
    }
}
