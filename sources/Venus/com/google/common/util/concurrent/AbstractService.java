/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenerCallQueue;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

@Beta
@GwtIncompatible
public abstract class AbstractService
implements Service {
    private static final ListenerCallQueue.Callback<Service.Listener> STARTING_CALLBACK = new ListenerCallQueue.Callback<Service.Listener>("starting()"){

        @Override
        void call(Service.Listener listener) {
            listener.starting();
        }

        @Override
        void call(Object object) {
            this.call((Service.Listener)object);
        }
    };
    private static final ListenerCallQueue.Callback<Service.Listener> RUNNING_CALLBACK = new ListenerCallQueue.Callback<Service.Listener>("running()"){

        @Override
        void call(Service.Listener listener) {
            listener.running();
        }

        @Override
        void call(Object object) {
            this.call((Service.Listener)object);
        }
    };
    private static final ListenerCallQueue.Callback<Service.Listener> STOPPING_FROM_STARTING_CALLBACK = AbstractService.stoppingCallback(Service.State.STARTING);
    private static final ListenerCallQueue.Callback<Service.Listener> STOPPING_FROM_RUNNING_CALLBACK = AbstractService.stoppingCallback(Service.State.RUNNING);
    private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_NEW_CALLBACK = AbstractService.terminatedCallback(Service.State.NEW);
    private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_RUNNING_CALLBACK = AbstractService.terminatedCallback(Service.State.RUNNING);
    private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_STOPPING_CALLBACK = AbstractService.terminatedCallback(Service.State.STOPPING);
    private final Monitor monitor = new Monitor();
    private final Monitor.Guard isStartable = new IsStartableGuard(this);
    private final Monitor.Guard isStoppable = new IsStoppableGuard(this);
    private final Monitor.Guard hasReachedRunning = new HasReachedRunningGuard(this);
    private final Monitor.Guard isStopped = new IsStoppedGuard(this);
    @GuardedBy(value="monitor")
    private final List<ListenerCallQueue<Service.Listener>> listeners = Collections.synchronizedList(new ArrayList());
    @GuardedBy(value="monitor")
    private volatile StateSnapshot snapshot = new StateSnapshot(Service.State.NEW);

    private static ListenerCallQueue.Callback<Service.Listener> terminatedCallback(Service.State state) {
        return new ListenerCallQueue.Callback<Service.Listener>("terminated({from = " + (Object)((Object)state) + "})", state){
            final Service.State val$from;
            {
                this.val$from = state;
                super(string);
            }

            @Override
            void call(Service.Listener listener) {
                listener.terminated(this.val$from);
            }

            @Override
            void call(Object object) {
                this.call((Service.Listener)object);
            }
        };
    }

    private static ListenerCallQueue.Callback<Service.Listener> stoppingCallback(Service.State state) {
        return new ListenerCallQueue.Callback<Service.Listener>("stopping({from = " + (Object)((Object)state) + "})", state){
            final Service.State val$from;
            {
                this.val$from = state;
                super(string);
            }

            @Override
            void call(Service.Listener listener) {
                listener.stopping(this.val$from);
            }

            @Override
            void call(Object object) {
                this.call((Service.Listener)object);
            }
        };
    }

    protected AbstractService() {
    }

    protected abstract void doStart();

    protected abstract void doStop();

    @Override
    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (this.monitor.enterIf(this.isStartable)) {
            try {
                this.snapshot = new StateSnapshot(Service.State.STARTING);
                this.starting();
                this.doStart();
            } catch (Throwable throwable) {
                this.notifyFailed(throwable);
            } finally {
                this.monitor.leave();
                this.executeListeners();
            }
        } else {
            throw new IllegalStateException("Service " + this + " has already been started");
        }
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        if (!this.monitor.enterIf(this.isStoppable)) return this;
        try {
            Service.State state = this.state();
            switch (6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
                case 1: {
                    this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                    this.terminated(Service.State.NEW);
                    return this;
                }
                case 2: {
                    this.snapshot = new StateSnapshot(Service.State.STARTING, true, null);
                    this.stopping(Service.State.STARTING);
                    return this;
                }
                case 3: {
                    this.snapshot = new StateSnapshot(Service.State.STOPPING);
                    this.stopping(Service.State.RUNNING);
                    this.doStop();
                    return this;
                }
                case 4: 
                case 5: 
                case 6: {
                    throw new AssertionError((Object)("isStoppable is incorrectly implemented, saw: " + (Object)((Object)state)));
                }
                default: {
                    throw new AssertionError((Object)("Unexpected state: " + (Object)((Object)state)));
                }
            }
        } catch (Throwable throwable) {
            this.notifyFailed(throwable);
            return this;
        } finally {
            this.monitor.leave();
            this.executeListeners();
        }
    }

    @Override
    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            this.checkCurrentState(Service.State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void awaitRunning(long l, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, l, timeUnit)) {
            try {
                this.checkCurrentState(Service.State.RUNNING);
            } finally {
                this.monitor.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state.");
        }
    }

    @Override
    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            this.checkCurrentState(Service.State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void awaitTerminated(long l, TimeUnit timeUnit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, l, timeUnit)) {
            try {
                this.checkCurrentState(Service.State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. Current state: " + (Object)((Object)this.state()));
        }
    }

    @GuardedBy(value="monitor")
    private void checkCurrentState(Service.State state) {
        Service.State state2 = this.state();
        if (state2 != state) {
            if (state2 == Service.State.FAILED) {
                throw new IllegalStateException("Expected the service " + this + " to be " + (Object)((Object)state) + ", but the service has FAILED", this.failureCause());
            }
            throw new IllegalStateException("Expected the service " + this + " to be " + (Object)((Object)state) + ", but was " + (Object)((Object)state2));
        }
    }

    protected final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state != Service.State.STARTING) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStarted() when the service is " + (Object)((Object)this.snapshot.state));
                this.notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            if (this.snapshot.shutdownWhenStartupFinishes) {
                this.snapshot = new StateSnapshot(Service.State.STOPPING);
                this.doStop();
            } else {
                this.snapshot = new StateSnapshot(Service.State.RUNNING);
                this.running();
            }
        } finally {
            this.monitor.leave();
            this.executeListeners();
        }
    }

    protected final void notifyStopped() {
        this.monitor.enter();
        try {
            Service.State state = this.snapshot.state;
            if (state != Service.State.STOPPING && state != Service.State.RUNNING) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStopped() when the service is " + (Object)((Object)state));
                this.notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            this.snapshot = new StateSnapshot(Service.State.TERMINATED);
            this.terminated(state);
        } finally {
            this.monitor.leave();
            this.executeListeners();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final void notifyFailed(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        this.monitor.enter();
        try {
            Service.State state = this.state();
            switch (6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
                case 1: 
                case 5: {
                    throw new IllegalStateException("Failed while in state:" + (Object)((Object)state), throwable);
                }
                case 2: 
                case 3: 
                case 4: {
                    this.snapshot = new StateSnapshot(Service.State.FAILED, false, throwable);
                    this.failed(state, throwable);
                    return;
                }
                case 6: {
                    return;
                }
                default: {
                    throw new AssertionError((Object)("Unexpected state: " + (Object)((Object)state)));
                }
            }
        } finally {
            this.monitor.leave();
            this.executeListeners();
        }
    }

    @Override
    public final boolean isRunning() {
        return this.state() == Service.State.RUNNING;
    }

    @Override
    public final Service.State state() {
        return this.snapshot.externalState();
    }

    @Override
    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    @Override
    public final void addListener(Service.Listener listener, Executor executor) {
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.monitor.enter();
        try {
            if (!this.state().isTerminal()) {
                this.listeners.add(new ListenerCallQueue<Service.Listener>(listener, executor));
            }
        } finally {
            this.monitor.leave();
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + " [" + (Object)((Object)this.state()) + "]";
    }

    private void executeListeners() {
        if (!this.monitor.isOccupiedByCurrentThread()) {
            for (int i = 0; i < this.listeners.size(); ++i) {
                this.listeners.get(i).execute();
            }
        }
    }

    @GuardedBy(value="monitor")
    private void starting() {
        STARTING_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy(value="monitor")
    private void running() {
        RUNNING_CALLBACK.enqueueOn(this.listeners);
    }

    @GuardedBy(value="monitor")
    private void stopping(Service.State state) {
        if (state == Service.State.STARTING) {
            STOPPING_FROM_STARTING_CALLBACK.enqueueOn(this.listeners);
        } else if (state == Service.State.RUNNING) {
            STOPPING_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
        } else {
            throw new AssertionError();
        }
    }

    @GuardedBy(value="monitor")
    private void terminated(Service.State state) {
        switch (6.$SwitchMap$com$google$common$util$concurrent$Service$State[state.ordinal()]) {
            case 1: {
                TERMINATED_FROM_NEW_CALLBACK.enqueueOn(this.listeners);
                break;
            }
            case 3: {
                TERMINATED_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
                break;
            }
            case 4: {
                TERMINATED_FROM_STOPPING_CALLBACK.enqueueOn(this.listeners);
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
    }

    @GuardedBy(value="monitor")
    private void failed(Service.State state, Throwable throwable) {
        new ListenerCallQueue.Callback<Service.Listener>(this, "failed({from = " + (Object)((Object)state) + ", cause = " + throwable + "})", state, throwable){
            final Service.State val$from;
            final Throwable val$cause;
            final AbstractService this$0;
            {
                this.this$0 = abstractService;
                this.val$from = state;
                this.val$cause = throwable;
                super(string);
            }

            @Override
            void call(Service.Listener listener) {
                listener.failed(this.val$from, this.val$cause);
            }

            @Override
            void call(Object object) {
                this.call((Service.Listener)object);
            }
        }.enqueueOn(this.listeners);
    }

    static Monitor access$000(AbstractService abstractService) {
        return abstractService.monitor;
    }

    @Immutable
    private static final class StateSnapshot {
        final Service.State state;
        final boolean shutdownWhenStartupFinishes;
        @Nullable
        final Throwable failure;

        StateSnapshot(Service.State state) {
            this(state, false, null);
        }

        StateSnapshot(Service.State state, boolean bl, @Nullable Throwable throwable) {
            Preconditions.checkArgument(!bl || state == Service.State.STARTING, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", (Object)state);
            Preconditions.checkArgument(!(throwable != null ^ state == Service.State.FAILED), "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", (Object)state, (Object)throwable);
            this.state = state;
            this.shutdownWhenStartupFinishes = bl;
            this.failure = throwable;
        }

        Service.State externalState() {
            if (this.shutdownWhenStartupFinishes && this.state == Service.State.STARTING) {
                return Service.State.STOPPING;
            }
            return this.state;
        }

        Throwable failureCause() {
            Preconditions.checkState(this.state == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", (Object)this.state);
            return this.failure;
        }
    }

    private final class IsStoppedGuard
    extends Monitor.Guard {
        final AbstractService this$0;

        IsStoppedGuard(AbstractService abstractService) {
            this.this$0 = abstractService;
            super(AbstractService.access$000(abstractService));
        }

        @Override
        public boolean isSatisfied() {
            return this.this$0.state().isTerminal();
        }
    }

    private final class HasReachedRunningGuard
    extends Monitor.Guard {
        final AbstractService this$0;

        HasReachedRunningGuard(AbstractService abstractService) {
            this.this$0 = abstractService;
            super(AbstractService.access$000(abstractService));
        }

        @Override
        public boolean isSatisfied() {
            return this.this$0.state().compareTo(Service.State.RUNNING) >= 0;
        }
    }

    private final class IsStoppableGuard
    extends Monitor.Guard {
        final AbstractService this$0;

        IsStoppableGuard(AbstractService abstractService) {
            this.this$0 = abstractService;
            super(AbstractService.access$000(abstractService));
        }

        @Override
        public boolean isSatisfied() {
            return this.this$0.state().compareTo(Service.State.RUNNING) <= 0;
        }
    }

    private final class IsStartableGuard
    extends Monitor.Guard {
        final AbstractService this$0;

        IsStartableGuard(AbstractService abstractService) {
            this.this$0 = abstractService;
            super(AbstractService.access$000(abstractService));
        }

        @Override
        public boolean isSatisfied() {
            return this.this$0.state() == Service.State.NEW;
        }
    }
}

