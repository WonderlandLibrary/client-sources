package us.dev.dvent.internal.dispatch;

import us.dev.dvent.Link;
import us.dev.dvent.dispatch.Dispatcher;
import us.dev.dvent.exception.ListenerExceptionContext;
import us.dev.dvent.exception.ListenerExceptionHandler;
import us.dev.dvent.filter.Filter;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * @author Foundry
 */
public class PerThreadQueuedDispatcher implements Dispatcher {

    private final ThreadLocal<Queue<DispatchAction<?>>> dispatchQueue = ThreadLocal.withInitial(ArrayDeque::new);

    private final ThreadLocal<Boolean> dispatchStatus = ThreadLocal.withInitial(() -> false);

    @Override
    public <T> void dispatch(T event, Iterator<Link<T>> listeners, Executor executor, ListenerExceptionHandler exceptionHandler) {
        final Queue<DispatchAction<?>> queueForThread = dispatchQueue.get();
        queueForThread.offer(new DispatchAction<>(event, listeners));

        if (!dispatchStatus.get()) {
            dispatchStatus.set(true);
            try {
                DispatchAction<?> nextAction;
                while ((nextAction = queueForThread.poll()) != null) {
                    nextAction.dispatch(executor, exceptionHandler);
                }
            } finally {
                dispatchStatus.remove();
                dispatchQueue.remove();
            }
        }
    }

    private static final class DispatchAction<T> {
        private final T event;
        private final Iterator<Link<T>> listeners;

        private DispatchAction(T event, Iterator<Link<T>> listeners) {
            this.event = event;
            this.listeners = listeners;
        }

        void dispatch(Executor executor, ListenerExceptionHandler exceptionHandler) {
            OUTER: while (listeners.hasNext()) {
                final Link<T> link = listeners.next();
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
}
