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

@Beta
@GwtIncompatible
public abstract class AbstractIdleService
implements Service {
    private final Supplier<String> threadNameSupplier = new ThreadNameSupplier(this, null);
    private final Service delegate = new DelegateService(this, null);

    protected AbstractIdleService() {
    }

    protected abstract void startUp() throws Exception;

    protected abstract void shutDown() throws Exception;

    protected Executor executor() {
        return new Executor(this){
            final AbstractIdleService this$0;
            {
                this.this$0 = abstractIdleService;
            }

            @Override
            public void execute(Runnable runnable) {
                MoreExecutors.newThread((String)AbstractIdleService.access$200(this.this$0).get(), runnable).start();
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

    static Supplier access$200(AbstractIdleService abstractIdleService) {
        return abstractIdleService.threadNameSupplier;
    }

    private final class DelegateService
    extends AbstractService {
        final AbstractIdleService this$0;

        private DelegateService(AbstractIdleService abstractIdleService) {
            this.this$0 = abstractIdleService;
        }

        @Override
        protected final void doStart() {
            MoreExecutors.renamingDecorator(this.this$0.executor(), (Supplier<String>)AbstractIdleService.access$200(this.this$0)).execute(new Runnable(this){
                final DelegateService this$1;
                {
                    this.this$1 = delegateService;
                }

                @Override
                public void run() {
                    try {
                        this.this$1.this$0.startUp();
                        this.this$1.notifyStarted();
                    } catch (Throwable throwable) {
                        this.this$1.notifyFailed(throwable);
                    }
                }
            });
        }

        @Override
        protected final void doStop() {
            MoreExecutors.renamingDecorator(this.this$0.executor(), (Supplier<String>)AbstractIdleService.access$200(this.this$0)).execute(new Runnable(this){
                final DelegateService this$1;
                {
                    this.this$1 = delegateService;
                }

                @Override
                public void run() {
                    try {
                        this.this$1.this$0.shutDown();
                        this.this$1.notifyStopped();
                    } catch (Throwable throwable) {
                        this.this$1.notifyFailed(throwable);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return this.this$0.toString();
        }

        DelegateService(AbstractIdleService abstractIdleService, 1 var2_2) {
            this(abstractIdleService);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ThreadNameSupplier
    implements Supplier<String> {
        final AbstractIdleService this$0;

        private ThreadNameSupplier(AbstractIdleService abstractIdleService) {
            this.this$0 = abstractIdleService;
        }

        @Override
        public String get() {
            return this.this$0.serviceName() + " " + (Object)((Object)this.this$0.state());
        }

        @Override
        public Object get() {
            return this.get();
        }

        ThreadNameSupplier(AbstractIdleService abstractIdleService, 1 var2_2) {
            this(abstractIdleService);
        }
    }
}

