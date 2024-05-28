package arsenic.event.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }