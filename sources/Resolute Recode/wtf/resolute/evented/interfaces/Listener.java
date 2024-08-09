package wtf.resolute.evented.interfaces;

public interface Listener<T> {
    void handle(T event);

}
