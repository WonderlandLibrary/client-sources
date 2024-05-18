package us.dev.dvent.exception;

import us.dev.dvent.Link;

/**
 * @author Foundry
 */
public class ListenerExceptionContext<T> {
    private final Throwable cause;
    private final T event;
    private final Link<T> listener;

    public ListenerExceptionContext(Throwable cause, T event, Link<T> listener) {
        this.cause = cause;
        this.event = event;
        this.listener = listener;
    }

    public Throwable getCause() {
        return cause;
    }

    public T getEvent() {
        return event;
    }

    public Link<T> getListener() {
        return listener;
    }
}
