package us.dev.dvent;

import com.google.common.base.Preconditions;
import us.dev.dvent.dispatch.Dispatcher;
import us.dev.dvent.exception.ListenerExceptionContext;
import us.dev.dvent.exception.ListenerExceptionHandler;
import us.dev.dvent.internal.dispatch.ImmediateDispatcher;
import us.dev.dvent.internal.util.registry.ListenerRegistry;
import us.dev.dvent.internal.util.registry.StrongListenerRegistry;
import us.dev.dvent.internal.util.registry.WeakListenerRegistry;

import java.util.concurrent.Executor;

/**
 * @author Foundry
 */
public class DventBus<E> {
    private final String identifier;
    private final Dispatcher dispatcher;
    private final Executor executor;
    private final ListenerExceptionHandler exceptionHandler;
    private final ListenerRegistry<E> listenerRegistry;

    public DventBus() {
        this("default");
    }

    public DventBus(String identifier) {
        this(identifier, ImmediateDispatcher.INSTANCE, Runnable::run, ImmediateOutputHandler.INSTANCE, false);
    }

    private DventBus(String identifier, Dispatcher dispatcher, Executor executor, ListenerExceptionHandler exceptionHandler, boolean weakListeners) {
        this.identifier = identifier;
        this.dispatcher = dispatcher;
        this.executor = executor;
        this.exceptionHandler = exceptionHandler;
        this.listenerRegistry = weakListeners ? new WeakListenerRegistry<>() : new StrongListenerRegistry<>();
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public boolean register(Object parent, Class<?>... events) {
        return listenerRegistry.register(parent, events);
    }

    public boolean unregister(Object parent, Class<?>... events) {
        return listenerRegistry.unregister(parent, events);
    }

    public void call(E event) {
        listenerRegistry.findSubscribers(event).ifPresent(it -> dispatcher.dispatch(event, it, executor, exceptionHandler));
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    private static final class ImmediateOutputHandler implements ListenerExceptionHandler {
        static final ImmediateOutputHandler INSTANCE = new ImmediateOutputHandler();

        @Override
        public void handleException(ListenerExceptionContext<?> context) {
            System.err.println(createMessage(context));
            context.getCause().printStackTrace();
        }

        private static int getListenerExceptionLineNumber(ListenerExceptionContext<?> context) {
            for (StackTraceElement element : context.getCause().getStackTrace()) {
                if (element.getClassName().equals(context.getListener().getClass().getName()))
                    return element.getLineNumber();
            }
            return -1;
        }

        private static String createMessage(ListenerExceptionContext<?> context) {
            return "Exception " + context.getCause() + " thrown by link " + context.getListener()
                    + '(' + context.getListener().getEventClass().getSimpleName() + ')'
                    + " on line " + getListenerExceptionLineNumber(context)
                    + " while dispatching event " + context.getListener().getEventClass().getSimpleName();
        }
    }

    public static final class Builder<E> {
        private Dispatcher dispatcher;
        private Executor executor;
        private ListenerExceptionHandler exceptionHandler;
        private boolean weakListeners;

        public Builder<E> withDispatcher(Dispatcher dispatcher) {
            Preconditions.checkArgument(this.dispatcher == null, "Component \"%s\" already exists with value: %s", "Dispatcher", this.dispatcher.getClass().getSimpleName());
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder<E> withExecutor(Executor executor) {
            Preconditions.checkArgument(this.executor == null, "Component \"%s\" already exists with value: %s", "Executor", this.executor.getClass().getSimpleName());
            this.executor = executor;
            return this;
        }

        public Builder<E> withExceptionHandler(ListenerExceptionHandler exceptionHandler) {
            Preconditions.checkArgument(this.exceptionHandler == null, "Component \"%s\" already exists with value: %s", "ExceptionHandler", this.exceptionHandler.getClass().getSimpleName());
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        public Builder<E> weakListeners() {
            Preconditions.checkArgument(this.weakListeners, "Component \"%s\" already exists with value: %s", "WeakListeners", true);
            this.weakListeners = true;
            return this;
        }

        public DventBus<E> build() {
            return build("default");
        }

        public DventBus<E> build(String identifier) {
            if (dispatcher == null) dispatcher = ImmediateDispatcher.INSTANCE;
            if (executor == null) executor = Runnable::run;
            if (exceptionHandler == null) exceptionHandler = ImmediateOutputHandler.INSTANCE;
            return new DventBus<>(identifier, dispatcher, executor, exceptionHandler, weakListeners);
        }
    }
}
