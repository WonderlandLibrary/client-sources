package none.event;

public interface EventListener<E extends Event> {
    void onEvent(E event);
}
