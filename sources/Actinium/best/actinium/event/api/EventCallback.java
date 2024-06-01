package best.actinium.event.api;

@FunctionalInterface
public interface EventCallback<Event> {
    void invoke(Event event);
}