/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ListenerCallQueue;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
@GwtIncompatible
public final class ServiceManager {
    private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
    private static final ListenerCallQueue.Callback<Listener> HEALTHY_CALLBACK = new ListenerCallQueue.Callback<Listener>("healthy()"){

        @Override
        void call(Listener listener) {
            listener.healthy();
        }

        @Override
        void call(Object object) {
            this.call((Listener)object);
        }
    };
    private static final ListenerCallQueue.Callback<Listener> STOPPED_CALLBACK = new ListenerCallQueue.Callback<Listener>("stopped()"){

        @Override
        void call(Listener listener) {
            listener.stopped();
        }

        @Override
        void call(Object object) {
            this.call((Listener)object);
        }
    };
    private final ServiceManagerState state;
    private final ImmutableList<Service> services;

    public ServiceManager(Iterable<? extends Service> iterable) {
        ImmutableList<Service> immutableList = ImmutableList.copyOf(iterable);
        if (immutableList.isEmpty()) {
            logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning(null));
            immutableList = ImmutableList.of(new NoOpService(null));
        }
        this.state = new ServiceManagerState(immutableList);
        this.services = immutableList;
        WeakReference<ServiceManagerState> weakReference = new WeakReference<ServiceManagerState>(this.state);
        for (Service service : immutableList) {
            service.addListener(new ServiceListener(service, weakReference), MoreExecutors.directExecutor());
            Preconditions.checkArgument(service.state() == Service.State.NEW, "Can only manage NEW services, %s", (Object)service);
        }
        this.state.markReady();
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.addListener(listener, executor);
    }

    public void addListener(Listener listener) {
        this.state.addListener(listener, MoreExecutors.directExecutor());
    }

    @CanIgnoreReturnValue
    public ServiceManager startAsync() {
        for (Service service : this.services) {
            Service.State state = service.state();
            Preconditions.checkState(state == Service.State.NEW, "Service %s is %s, cannot start it.", (Object)service, (Object)state);
        }
        for (Service service : this.services) {
            try {
                this.state.tryStartTiming(service);
                service.startAsync();
            } catch (IllegalStateException illegalStateException) {
                logger.log(Level.WARNING, "Unable to start Service " + service, illegalStateException);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.state.awaitHealthy();
    }

    public void awaitHealthy(long l, TimeUnit timeUnit) throws TimeoutException {
        this.state.awaitHealthy(l, timeUnit);
    }

    @CanIgnoreReturnValue
    public ServiceManager stopAsync() {
        for (Service service : this.services) {
            service.stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.state.awaitStopped();
    }

    public void awaitStopped(long l, TimeUnit timeUnit) throws TimeoutException {
        this.state.awaitStopped(l, timeUnit);
    }

    public boolean isHealthy() {
        for (Service service : this.services) {
            if (service.isRunning()) continue;
            return true;
        }
        return false;
    }

    public ImmutableMultimap<Service.State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.startupTimes();
    }

    public String toString() {
        return MoreObjects.toStringHelper(ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }

    static Logger access$200() {
        return logger;
    }

    static ListenerCallQueue.Callback access$300() {
        return STOPPED_CALLBACK;
    }

    static ListenerCallQueue.Callback access$400() {
        return HEALTHY_CALLBACK;
    }

    private static final class EmptyServiceManagerWarning
    extends Throwable {
        private EmptyServiceManagerWarning() {
        }

        EmptyServiceManagerWarning(1 var1_1) {
            this();
        }
    }

    private static final class NoOpService
    extends AbstractService {
        private NoOpService() {
        }

        @Override
        protected void doStart() {
            this.notifyStarted();
        }

        @Override
        protected void doStop() {
            this.notifyStopped();
        }

        NoOpService(1 var1_1) {
            this();
        }
    }

    private static final class ServiceListener
    extends Service.Listener {
        final Service service;
        final WeakReference<ServiceManagerState> state;

        ServiceListener(Service service, WeakReference<ServiceManagerState> weakReference) {
            this.service = service;
            this.state = weakReference;
        }

        @Override
        public void starting() {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.access$200().log(Level.FINE, "Starting {0}.", this.service);
                }
            }
        }

        @Override
        public void running() {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
            }
        }

        @Override
        public void stopping(Service.State state) {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                serviceManagerState.transitionService(this.service, state, Service.State.STOPPING);
            }
        }

        @Override
        public void terminated(Service.State state) {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.access$200().log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, state});
                }
                serviceManagerState.transitionService(this.service, state, Service.State.TERMINATED);
            }
        }

        @Override
        public void failed(Service.State state, Throwable throwable) {
            ServiceManagerState serviceManagerState = (ServiceManagerState)this.state.get();
            if (serviceManagerState != null) {
                boolean bl;
                boolean bl2 = bl = !(this.service instanceof NoOpService);
                if (bl) {
                    ServiceManager.access$200().log(Level.SEVERE, "Service " + this.service + " has failed in the " + (Object)((Object)state) + " state.", throwable);
                }
                serviceManagerState.transitionService(this.service, state, Service.State.FAILED);
            }
        }
    }

    private static final class ServiceManagerState {
        final Monitor monitor = new Monitor();
        @GuardedBy(value="monitor")
        final SetMultimap<Service.State, Service> servicesByState = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
        @GuardedBy(value="monitor")
        final Multiset<Service.State> states = this.servicesByState.keys();
        @GuardedBy(value="monitor")
        final Map<Service, Stopwatch> startupTimers = Maps.newIdentityHashMap();
        @GuardedBy(value="monitor")
        boolean ready;
        @GuardedBy(value="monitor")
        boolean transitioned;
        final int numberOfServices;
        final Monitor.Guard awaitHealthGuard = new AwaitHealthGuard(this);
        final Monitor.Guard stoppedGuard = new StoppedGuard(this);
        @GuardedBy(value="monitor")
        final List<ListenerCallQueue<Listener>> listeners = Collections.synchronizedList(new ArrayList());

        ServiceManagerState(ImmutableCollection<Service> immutableCollection) {
            this.numberOfServices = immutableCollection.size();
            this.servicesByState.putAll(Service.State.NEW, immutableCollection);
        }

        void tryStartTiming(Service service) {
            this.monitor.enter();
            try {
                Stopwatch stopwatch = this.startupTimers.get(service);
                if (stopwatch == null) {
                    this.startupTimers.put(service, Stopwatch.createStarted());
                }
            } finally {
                this.monitor.leave();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void markReady() {
            block5: {
                this.monitor.enter();
                try {
                    if (!this.transitioned) {
                        this.ready = true;
                        break block5;
                    }
                    ArrayList<Service> arrayList = Lists.newArrayList();
                    for (Service service : this.servicesByState().values()) {
                        if (service.state() == Service.State.NEW) continue;
                        arrayList.add(service);
                    }
                    throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + arrayList);
                } finally {
                    this.monitor.leave();
                }
            }
        }

        void addListener(Listener listener, Executor executor) {
            Preconditions.checkNotNull(listener, "listener");
            Preconditions.checkNotNull(executor, "executor");
            this.monitor.enter();
            try {
                if (!this.stoppedGuard.isSatisfied()) {
                    this.listeners.add(new ListenerCallQueue<Listener>(listener, executor));
                }
            } finally {
                this.monitor.leave();
            }
        }

        void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            try {
                this.checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void awaitHealthy(long l, TimeUnit timeUnit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.awaitHealthGuard, l, timeUnit)) {
                    throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
                }
                this.checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void awaitStopped(long l, TimeUnit timeUnit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, l, timeUnit)) {
                    throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
                }
            } finally {
                this.monitor.leave();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        ImmutableMultimap<Service.State, Service> servicesByState() {
            ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                for (Map.Entry entry : this.servicesByState.entries()) {
                    if (entry.getValue() instanceof NoOpService) continue;
                    builder.put(entry);
                }
            } finally {
                this.monitor.leave();
            }
            return builder.build();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        ImmutableMap<Service, Long> startupTimes() {
            ArrayList<Map.Entry<Service, Long>> arrayList;
            this.monitor.enter();
            try {
                arrayList = Lists.newArrayListWithCapacity(this.startupTimers.size());
                for (Map.Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
                    Service service = entry.getKey();
                    Stopwatch stopwatch = entry.getValue();
                    if (stopwatch.isRunning() || service instanceof NoOpService) continue;
                    arrayList.add(Maps.immutableEntry(service, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
                }
            } finally {
                this.monitor.leave();
            }
            Collections.sort(arrayList, Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>(this){
                final ServiceManagerState this$0;
                {
                    this.this$0 = serviceManagerState;
                }

                @Override
                public Long apply(Map.Entry<Service, Long> entry) {
                    return entry.getValue();
                }

                @Override
                public Object apply(Object object) {
                    return this.apply((Map.Entry)object);
                }
            }));
            return ImmutableMap.copyOf(arrayList);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void transitionService(Service service, Service.State state, Service.State state2) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(state != state2);
            this.monitor.enter();
            try {
                this.transitioned = true;
                if (!this.ready) {
                    return;
                }
                Preconditions.checkState(this.servicesByState.remove((Object)state, service), "Service %s not at the expected location in the state map %s", (Object)service, (Object)state);
                Preconditions.checkState(this.servicesByState.put(state2, service), "Service %s in the state map unexpectedly at %s", (Object)service, (Object)state2);
                Stopwatch stopwatch = this.startupTimers.get(service);
                if (stopwatch == null) {
                    stopwatch = Stopwatch.createStarted();
                    this.startupTimers.put(service, stopwatch);
                }
                if (state2.compareTo(Service.State.RUNNING) >= 0 && stopwatch.isRunning()) {
                    stopwatch.stop();
                    if (!(service instanceof NoOpService)) {
                        ServiceManager.access$200().log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
                    }
                }
                if (state2 == Service.State.FAILED) {
                    this.fireFailedListeners(service);
                }
                if (this.states.count((Object)Service.State.RUNNING) == this.numberOfServices) {
                    this.fireHealthyListeners();
                } else if (this.states.count((Object)Service.State.TERMINATED) + this.states.count((Object)Service.State.FAILED) == this.numberOfServices) {
                    this.fireStoppedListeners();
                }
            } finally {
                this.monitor.leave();
                this.executeListeners();
            }
        }

        @GuardedBy(value="monitor")
        void fireStoppedListeners() {
            ServiceManager.access$300().enqueueOn(this.listeners);
        }

        @GuardedBy(value="monitor")
        void fireHealthyListeners() {
            ServiceManager.access$400().enqueueOn(this.listeners);
        }

        @GuardedBy(value="monitor")
        void fireFailedListeners(Service service) {
            new ListenerCallQueue.Callback<Listener>(this, "failed({service=" + service + "})", service){
                final Service val$service;
                final ServiceManagerState this$0;
                {
                    this.this$0 = serviceManagerState;
                    this.val$service = service;
                    super(string);
                }

                @Override
                void call(Listener listener) {
                    listener.failure(this.val$service);
                }

                @Override
                void call(Object object) {
                    this.call((Listener)object);
                }
            }.enqueueOn(this.listeners);
        }

        void executeListeners() {
            Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            for (int i = 0; i < this.listeners.size(); ++i) {
                this.listeners.get(i).execute();
            }
        }

        @GuardedBy(value="monitor")
        void checkHealthy() {
            if (this.states.count((Object)Service.State.RUNNING) != this.numberOfServices) {
                IllegalStateException illegalStateException = new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
                throw illegalStateException;
            }
        }

        final class StoppedGuard
        extends Monitor.Guard {
            final ServiceManagerState this$0;

            StoppedGuard(ServiceManagerState serviceManagerState) {
                this.this$0 = serviceManagerState;
                super(serviceManagerState.monitor);
            }

            @Override
            public boolean isSatisfied() {
                return this.this$0.states.count((Object)Service.State.TERMINATED) + this.this$0.states.count((Object)Service.State.FAILED) == this.this$0.numberOfServices;
            }
        }

        final class AwaitHealthGuard
        extends Monitor.Guard {
            final ServiceManagerState this$0;

            AwaitHealthGuard(ServiceManagerState serviceManagerState) {
                this.this$0 = serviceManagerState;
                super(serviceManagerState.monitor);
            }

            @Override
            public boolean isSatisfied() {
                return this.this$0.states.count((Object)Service.State.RUNNING) == this.this$0.numberOfServices || this.this$0.states.contains((Object)Service.State.STOPPING) || this.this$0.states.contains((Object)Service.State.TERMINATED) || this.this$0.states.contains((Object)Service.State.FAILED);
            }
        }
    }

    @Beta
    public static abstract class Listener {
        public void healthy() {
        }

        public void stopped() {
        }

        public void failure(Service service) {
        }
    }
}

