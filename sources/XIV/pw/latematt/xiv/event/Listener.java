package pw.latematt.xiv.event;

/**
 * @author Matthew
 */
public interface Listener<T> {
    void onEventCalled(T event);
}
