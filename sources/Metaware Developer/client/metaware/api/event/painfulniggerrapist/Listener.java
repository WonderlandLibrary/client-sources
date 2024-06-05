package client.metaware.api.event.painfulniggerrapist;

@FunctionalInterface
public interface Listener<Event> {
    void call(Event event);
}