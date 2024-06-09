package us.dev.dvent.dispatch;

import us.dev.dvent.Link;
import us.dev.dvent.exception.ListenerExceptionHandler;

import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * @author Foundry
 */
@FunctionalInterface
public interface Dispatcher {
    <T> void dispatch(T event, Iterator<Link<T>> listeners, Executor executor, ListenerExceptionHandler exceptionHandler);
}
