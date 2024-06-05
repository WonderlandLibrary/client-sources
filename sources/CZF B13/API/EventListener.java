package gq.vapu.czfclient.API;

public interface EventListener<E extends Event> {
    void onEvent(E event);
}
