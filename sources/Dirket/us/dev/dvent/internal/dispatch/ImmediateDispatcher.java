package us.dev.dvent.internal.dispatch;

import us.dev.dvent.DventBus;
import us.dev.dvent.Link;
import us.dev.dvent.dispatch.Dispatcher;
import us.dev.dvent.exception.ListenerExceptionContext;
import us.dev.dvent.exception.ListenerExceptionHandler;
import us.dev.dvent.filter.Filter;

import java.util.Iterator;
import java.util.concurrent.Executor;

/**
 * @author Foundry
 */
public final class ImmediateDispatcher implements Dispatcher {
    public static final ImmediateDispatcher INSTANCE = new ImmediateDispatcher();

    @Override
    public <T> void dispatch(T event, Iterator<Link<T>> listeners, Executor executor, ListenerExceptionHandler exceptionHandler) {
        OUTER: while (listeners.hasNext()) {
            Link<T> link = listeners.next();
            if (link.getFilters().length > 0) {
                for (Filter<T> f : link.getFilters()) {
                    if (!f.test(link, event)) continue OUTER;
                }
            }
            executor.execute(() -> {
                try {
                    link.invoke(event);
                } catch (Throwable e) {
                    exceptionHandler.handleException(new ListenerExceptionContext<>(e, event, link));
                }
            });
        }
    }
}
