/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;
import org.apache.commons.lang3.concurrent.CircuitBreakingException;

public class EventCountCircuitBreaker
extends AbstractCircuitBreaker<Integer> {
    private static final Map<AbstractCircuitBreaker.State, StateStrategy> STRATEGY_MAP = EventCountCircuitBreaker.createStrategyMap();
    private final AtomicReference<CheckIntervalData> checkIntervalData = new AtomicReference<CheckIntervalData>(new CheckIntervalData(0, 0L));
    private final int openingThreshold;
    private final long openingInterval;
    private final int closingThreshold;
    private final long closingInterval;

    public EventCountCircuitBreaker(int n, long l, TimeUnit timeUnit, int n2, long l2, TimeUnit timeUnit2) {
        this.openingThreshold = n;
        this.openingInterval = timeUnit.toNanos(l);
        this.closingThreshold = n2;
        this.closingInterval = timeUnit2.toNanos(l2);
    }

    public EventCountCircuitBreaker(int n, long l, TimeUnit timeUnit, int n2) {
        this(n, l, timeUnit, n2, l, timeUnit);
    }

    public EventCountCircuitBreaker(int n, long l, TimeUnit timeUnit) {
        this(n, l, timeUnit, n);
    }

    public int getOpeningThreshold() {
        return this.openingThreshold;
    }

    public long getOpeningInterval() {
        return this.openingInterval;
    }

    public int getClosingThreshold() {
        return this.closingThreshold;
    }

    public long getClosingInterval() {
        return this.closingInterval;
    }

    @Override
    public boolean checkState() {
        return this.performStateCheck(0);
    }

    @Override
    public boolean incrementAndCheckState(Integer n) throws CircuitBreakingException {
        return this.performStateCheck(1);
    }

    public boolean incrementAndCheckState() {
        return this.incrementAndCheckState(1);
    }

    @Override
    public void open() {
        super.open();
        this.checkIntervalData.set(new CheckIntervalData(0, this.now()));
    }

    @Override
    public void close() {
        super.close();
        this.checkIntervalData.set(new CheckIntervalData(0, this.now()));
    }

    private boolean performStateCheck(int n) {
        long l;
        AbstractCircuitBreaker.State state;
        CheckIntervalData checkIntervalData;
        CheckIntervalData checkIntervalData2;
        do {
            l = this.now();
            state = (AbstractCircuitBreaker.State)((Object)this.state.get());
        } while (!this.updateCheckIntervalData(checkIntervalData2 = this.checkIntervalData.get(), checkIntervalData = this.nextCheckIntervalData(n, checkIntervalData2, state, l)));
        if (EventCountCircuitBreaker.stateStrategy(state).isStateTransition(this, checkIntervalData2, checkIntervalData)) {
            state = state.oppositeState();
            this.changeStateAndStartNewCheckInterval(state);
        }
        return !EventCountCircuitBreaker.isOpen(state);
    }

    private boolean updateCheckIntervalData(CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2) {
        return checkIntervalData == checkIntervalData2 || this.checkIntervalData.compareAndSet(checkIntervalData, checkIntervalData2);
    }

    private void changeStateAndStartNewCheckInterval(AbstractCircuitBreaker.State state) {
        this.changeState(state);
        this.checkIntervalData.set(new CheckIntervalData(0, this.now()));
    }

    private CheckIntervalData nextCheckIntervalData(int n, CheckIntervalData checkIntervalData, AbstractCircuitBreaker.State state, long l) {
        CheckIntervalData checkIntervalData2 = EventCountCircuitBreaker.stateStrategy(state).isCheckIntervalFinished(this, checkIntervalData, l) ? new CheckIntervalData(n, l) : checkIntervalData.increment(n);
        return checkIntervalData2;
    }

    long now() {
        return System.nanoTime();
    }

    private static StateStrategy stateStrategy(AbstractCircuitBreaker.State state) {
        StateStrategy stateStrategy = STRATEGY_MAP.get((Object)state);
        return stateStrategy;
    }

    private static Map<AbstractCircuitBreaker.State, StateStrategy> createStrategyMap() {
        EnumMap<AbstractCircuitBreaker.State, StateStrategy> enumMap = new EnumMap<AbstractCircuitBreaker.State, StateStrategy>(AbstractCircuitBreaker.State.class);
        enumMap.put(AbstractCircuitBreaker.State.CLOSED, new StateStrategyClosed(null));
        enumMap.put(AbstractCircuitBreaker.State.OPEN, new StateStrategyOpen(null));
        return enumMap;
    }

    @Override
    public boolean incrementAndCheckState(Object object) {
        return this.incrementAndCheckState((Integer)object);
    }

    private static class StateStrategyOpen
    extends StateStrategy {
        private StateStrategyOpen() {
            super(null);
        }

        @Override
        public boolean isStateTransition(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2) {
            return checkIntervalData2.getCheckIntervalStart() != checkIntervalData.getCheckIntervalStart() && checkIntervalData.getEventCount() < eventCountCircuitBreaker.getClosingThreshold();
        }

        @Override
        protected long fetchCheckInterval(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getClosingInterval();
        }

        StateStrategyOpen(1 var1_1) {
            this();
        }
    }

    private static class StateStrategyClosed
    extends StateStrategy {
        private StateStrategyClosed() {
            super(null);
        }

        @Override
        public boolean isStateTransition(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, CheckIntervalData checkIntervalData2) {
            return checkIntervalData2.getEventCount() > eventCountCircuitBreaker.getOpeningThreshold();
        }

        @Override
        protected long fetchCheckInterval(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getOpeningInterval();
        }

        StateStrategyClosed(1 var1_1) {
            this();
        }
    }

    private static abstract class StateStrategy {
        private StateStrategy() {
        }

        public boolean isCheckIntervalFinished(EventCountCircuitBreaker eventCountCircuitBreaker, CheckIntervalData checkIntervalData, long l) {
            return l - checkIntervalData.getCheckIntervalStart() > this.fetchCheckInterval(eventCountCircuitBreaker);
        }

        public abstract boolean isStateTransition(EventCountCircuitBreaker var1, CheckIntervalData var2, CheckIntervalData var3);

        protected abstract long fetchCheckInterval(EventCountCircuitBreaker var1);

        StateStrategy(1 var1_1) {
            this();
        }
    }

    private static class CheckIntervalData {
        private final int eventCount;
        private final long checkIntervalStart;

        public CheckIntervalData(int n, long l) {
            this.eventCount = n;
            this.checkIntervalStart = l;
        }

        public int getEventCount() {
            return this.eventCount;
        }

        public long getCheckIntervalStart() {
            return this.checkIntervalStart;
        }

        public CheckIntervalData increment(int n) {
            return n != 0 ? new CheckIntervalData(this.getEventCount() + n, this.getCheckIntervalStart()) : this;
        }
    }
}

