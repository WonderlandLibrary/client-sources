/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ForwardingFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
@GwtIncompatible
public abstract class AbstractScheduledService
implements Service {
    private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
    private final AbstractService delegate = new ServiceDelegate(this, null);

    protected AbstractScheduledService() {
    }

    protected abstract void runOneIteration() throws Exception;

    protected void startUp() throws Exception {
    }

    protected void shutDown() throws Exception {
    }

    protected abstract Scheduler scheduler();

    protected ScheduledExecutorService executor() {
        class ThreadFactoryImpl
        implements ThreadFactory {
            final AbstractScheduledService this$0;

            ThreadFactoryImpl(AbstractScheduledService abstractScheduledService) {
                this.this$0 = abstractScheduledService;
            }

            @Override
            public Thread newThread(Runnable runnable) {
                return MoreExecutors.newThread(this.this$0.serviceName(), runnable);
            }
        }
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryImpl(this));
        this.addListener(new Service.Listener(this, scheduledExecutorService){
            final ScheduledExecutorService val$executor;
            final AbstractScheduledService this$0;
            {
                this.this$0 = abstractScheduledService;
                this.val$executor = scheduledExecutorService;
            }

            @Override
            public void terminated(Service.State state) {
                this.val$executor.shutdown();
            }

            @Override
            public void failed(Service.State state, Throwable throwable) {
                this.val$executor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return scheduledExecutorService;
    }

    protected String serviceName() {
        return this.getClass().getSimpleName();
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

    static Logger access$400() {
        return logger;
    }

    static AbstractService access$500(AbstractScheduledService abstractScheduledService) {
        return abstractScheduledService.delegate;
    }

    @Beta
    public static abstract class CustomScheduler
    extends Scheduler {
        public CustomScheduler() {
            super(null);
        }

        @Override
        final Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
            ReschedulableCallable reschedulableCallable = new ReschedulableCallable(this, abstractService, scheduledExecutorService, runnable);
            reschedulableCallable.reschedule();
            return reschedulableCallable;
        }

        protected abstract Schedule getNextSchedule() throws Exception;

        @Beta
        protected static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long l, TimeUnit timeUnit) {
                this.delay = l;
                this.unit = Preconditions.checkNotNull(timeUnit);
            }

            static long access$800(Schedule schedule) {
                return schedule.delay;
            }

            static TimeUnit access$900(Schedule schedule) {
                return schedule.unit;
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private class ReschedulableCallable
        extends ForwardingFuture<Void>
        implements Callable<Void> {
            private final Runnable wrappedRunnable;
            private final ScheduledExecutorService executor;
            private final AbstractService service;
            private final ReentrantLock lock;
            @GuardedBy(value="lock")
            private Future<Void> currentFuture;
            final CustomScheduler this$0;

            ReschedulableCallable(CustomScheduler customScheduler, AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                this.this$0 = customScheduler;
                this.lock = new ReentrantLock();
                this.wrappedRunnable = runnable;
                this.executor = scheduledExecutorService;
                this.service = abstractService;
            }

            @Override
            public Void call() throws Exception {
                this.wrappedRunnable.run();
                this.reschedule();
                return null;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void reschedule() {
                Schedule schedule;
                try {
                    schedule = this.this$0.getNextSchedule();
                } catch (Throwable throwable) {
                    this.service.notifyFailed(throwable);
                    return;
                }
                Throwable throwable = null;
                this.lock.lock();
                try {
                    if (this.currentFuture == null || !this.currentFuture.isCancelled()) {
                        this.currentFuture = this.executor.schedule(this, Schedule.access$800(schedule), Schedule.access$900(schedule));
                    }
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                } finally {
                    this.lock.unlock();
                }
                if (throwable != null) {
                    this.service.notifyFailed(throwable);
                }
            }

            @Override
            public boolean cancel(boolean bl) {
                this.lock.lock();
                try {
                    boolean bl2 = this.currentFuture.cancel(bl);
                    return bl2;
                } finally {
                    this.lock.unlock();
                }
            }

            @Override
            public boolean isCancelled() {
                this.lock.lock();
                try {
                    boolean bl = this.currentFuture.isCancelled();
                    return bl;
                } finally {
                    this.lock.unlock();
                }
            }

            @Override
            protected Future<Void> delegate() {
                throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
            }

            @Override
            protected Object delegate() {
                return this.delegate();
            }

            @Override
            public Object call() throws Exception {
                return this.call();
            }
        }
    }

    private final class ServiceDelegate
    extends AbstractService {
        private volatile Future<?> runningTask;
        private volatile ScheduledExecutorService executorService;
        private final ReentrantLock lock;
        private final Runnable task;
        final AbstractScheduledService this$0;

        private ServiceDelegate(AbstractScheduledService abstractScheduledService) {
            this.this$0 = abstractScheduledService;
            this.lock = new ReentrantLock();
            this.task = new Task(this);
        }

        @Override
        protected final void doStart() {
            this.executorService = MoreExecutors.renamingDecorator(this.this$0.executor(), new Supplier<String>(this){
                final ServiceDelegate this$1;
                {
                    this.this$1 = serviceDelegate;
                }

                @Override
                public String get() {
                    return this.this$1.this$0.serviceName() + " " + (Object)((Object)this.this$1.state());
                }

                @Override
                public Object get() {
                    return this.get();
                }
            });
            this.executorService.execute(new Runnable(this){
                final ServiceDelegate this$1;
                {
                    this.this$1 = serviceDelegate;
                }

                @Override
                public void run() {
                    ServiceDelegate.access$200(this.this$1).lock();
                    try {
                        this.this$1.this$0.startUp();
                        ServiceDelegate.access$302(this.this$1, this.this$1.this$0.scheduler().schedule(AbstractScheduledService.access$500(this.this$1.this$0), ServiceDelegate.access$600(this.this$1), ServiceDelegate.access$700(this.this$1)));
                        this.this$1.notifyStarted();
                    } catch (Throwable throwable) {
                        this.this$1.notifyFailed(throwable);
                        if (ServiceDelegate.access$300(this.this$1) != null) {
                            ServiceDelegate.access$300(this.this$1).cancel(false);
                        }
                    } finally {
                        ServiceDelegate.access$200(this.this$1).unlock();
                    }
                }
            });
        }

        @Override
        protected final void doStop() {
            this.runningTask.cancel(false);
            this.executorService.execute(new Runnable(this){
                final ServiceDelegate this$1;
                {
                    this.this$1 = serviceDelegate;
                }

                @Override
                public void run() {
                    try {
                        ServiceDelegate.access$200(this.this$1).lock();
                        try {
                            if (this.this$1.state() != Service.State.STOPPING) {
                                return;
                            }
                            this.this$1.this$0.shutDown();
                        } finally {
                            ServiceDelegate.access$200(this.this$1).unlock();
                        }
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

        ServiceDelegate(AbstractScheduledService abstractScheduledService, 1 var2_2) {
            this(abstractScheduledService);
        }

        static ReentrantLock access$200(ServiceDelegate serviceDelegate) {
            return serviceDelegate.lock;
        }

        static Future access$300(ServiceDelegate serviceDelegate) {
            return serviceDelegate.runningTask;
        }

        static Future access$302(ServiceDelegate serviceDelegate, Future future) {
            serviceDelegate.runningTask = future;
            return serviceDelegate.runningTask;
        }

        static ScheduledExecutorService access$600(ServiceDelegate serviceDelegate) {
            return serviceDelegate.executorService;
        }

        static Runnable access$700(ServiceDelegate serviceDelegate) {
            return serviceDelegate.task;
        }

        class Task
        implements Runnable {
            final ServiceDelegate this$1;

            Task(ServiceDelegate serviceDelegate) {
                this.this$1 = serviceDelegate;
            }

            @Override
            public void run() {
                ServiceDelegate.access$200(this.this$1).lock();
                try {
                    if (ServiceDelegate.access$300(this.this$1).isCancelled()) {
                        return;
                    }
                    this.this$1.this$0.runOneIteration();
                } catch (Throwable throwable) {
                    try {
                        this.this$1.this$0.shutDown();
                    } catch (Exception exception) {
                        AbstractScheduledService.access$400().log(Level.WARNING, "Error while attempting to shut down the service after failure.", exception);
                    }
                    this.this$1.notifyFailed(throwable);
                    ServiceDelegate.access$300(this.this$1).cancel(false);
                } finally {
                    ServiceDelegate.access$200(this.this$1).unlock();
                }
            }
        }
    }

    public static abstract class Scheduler {
        public static Scheduler newFixedDelaySchedule(long l, long l2, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(l2 > 0L, "delay must be > 0, found %s", l2);
            return new Scheduler(l, l2, timeUnit){
                final long val$initialDelay;
                final long val$delay;
                final TimeUnit val$unit;
                {
                    this.val$initialDelay = l;
                    this.val$delay = l2;
                    this.val$unit = timeUnit;
                    super(null);
                }

                @Override
                public Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleWithFixedDelay(runnable, this.val$initialDelay, this.val$delay, this.val$unit);
                }
            };
        }

        public static Scheduler newFixedRateSchedule(long l, long l2, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(l2 > 0L, "period must be > 0, found %s", l2);
            return new Scheduler(l, l2, timeUnit){
                final long val$initialDelay;
                final long val$period;
                final TimeUnit val$unit;
                {
                    this.val$initialDelay = l;
                    this.val$period = l2;
                    this.val$unit = timeUnit;
                    super(null);
                }

                @Override
                public Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleAtFixedRate(runnable, this.val$initialDelay, this.val$period, this.val$unit);
                }
            };
        }

        abstract Future<?> schedule(AbstractService var1, ScheduledExecutorService var2, Runnable var3);

        private Scheduler() {
        }

        Scheduler(1 var1_1) {
            this();
        }
    }
}

