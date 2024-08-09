/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
@GwtIncompatible
public abstract class AbstractExecutionThreadService
implements Service {
    private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service delegate = new AbstractService(this){
        final AbstractExecutionThreadService this$0;
        {
            this.this$0 = abstractExecutionThreadService;
        }

        @Override
        protected final void doStart() {
            Executor executor = MoreExecutors.renamingDecorator(this.this$0.executor(), new Supplier<String>(this){
                final 1 this$1;
                {
                    this.this$1 = var1_1;
                }

                @Override
                public String get() {
                    return this.this$1.this$0.serviceName();
                }

                @Override
                public Object get() {
                    return this.get();
                }
            });
            executor.execute(new Runnable(this){
                final 1 this$1;
                {
                    this.this$1 = var1_1;
                }

                @Override
                public void run() {
                    try {
                        this.this$1.this$0.startUp();
                        this.this$1.notifyStarted();
                        if (this.this$1.isRunning()) {
                            try {
                                this.this$1.this$0.run();
                            } catch (Throwable throwable) {
                                try {
                                    this.this$1.this$0.shutDown();
                                } catch (Exception exception) {
                                    AbstractExecutionThreadService.access$000().log(Level.WARNING, "Error while attempting to shut down the service after failure.", exception);
                                }
                                this.this$1.notifyFailed(throwable);
                                return;
                            }
                        }
                        this.this$1.this$0.shutDown();
                        this.this$1.notifyStopped();
                    } catch (Throwable throwable) {
                        this.this$1.notifyFailed(throwable);
                    }
                }
            });
        }

        @Override
        protected void doStop() {
            this.this$0.triggerShutdown();
        }

        @Override
        public String toString() {
            return this.this$0.toString();
        }
    };

    protected AbstractExecutionThreadService() {
    }

    protected void startUp() throws Exception {
    }

    protected abstract void run() throws Exception;

    protected void shutDown() throws Exception {
    }

    protected void triggerShutdown() {
    }

    protected Executor executor() {
        return new Executor(this){
            final AbstractExecutionThreadService this$0;
            {
                this.this$0 = abstractExecutionThreadService;
            }

            @Override
            public void execute(Runnable runnable) {
                MoreExecutors.newThread(this.this$0.serviceName(), runnable).start();
            }
        };
    }

    public String toString() {
        return this.serviceName() + " [" + (Object)((Object)this.state()) + "]";
    }

    @Override
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    @Override
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    @Override
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override
    public final void awaitRunning(long l, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(l, timeUnit);
    }

    @Override
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override
    public final void awaitTerminated(long l, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(l, timeUnit);
    }

    protected String serviceName() {
        return this.getClass().getSimpleName();
    }

    static Logger access$000() {
        return logger;
    }
}

