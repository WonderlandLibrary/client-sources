/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.status.StatusLogger;

public class AbstractLifeCycle
implements LifeCycle2 {
    public static final int DEFAULT_STOP_TIMEOUT = 0;
    public static final TimeUnit DEFAULT_STOP_TIMEUNIT = TimeUnit.MILLISECONDS;
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private volatile LifeCycle.State state = LifeCycle.State.INITIALIZED;

    protected static Logger getStatusLogger() {
        return LOGGER;
    }

    protected boolean equalsImpl(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        LifeCycle lifeCycle = (LifeCycle)object;
        return this.state != lifeCycle.getState();
    }

    @Override
    public LifeCycle.State getState() {
        return this.state;
    }

    protected int hashCodeImpl() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.state == null ? 0 : this.state.hashCode());
        return n2;
    }

    public boolean isInitialized() {
        return this.state == LifeCycle.State.INITIALIZED;
    }

    @Override
    public boolean isStarted() {
        return this.state == LifeCycle.State.STARTED;
    }

    public boolean isStarting() {
        return this.state == LifeCycle.State.STARTING;
    }

    @Override
    public boolean isStopped() {
        return this.state == LifeCycle.State.STOPPED;
    }

    public boolean isStopping() {
        return this.state == LifeCycle.State.STOPPING;
    }

    protected void setStarted() {
        this.setState(LifeCycle.State.STARTED);
    }

    protected void setStarting() {
        this.setState(LifeCycle.State.STARTING);
    }

    protected void setState(LifeCycle.State state) {
        this.state = state;
    }

    protected void setStopped() {
        this.setState(LifeCycle.State.STOPPED);
    }

    protected void setStopping() {
        this.setState(LifeCycle.State.STOPPING);
    }

    @Override
    public void initialize() {
        this.state = LifeCycle.State.INITIALIZED;
    }

    @Override
    public void start() {
        this.setStarted();
    }

    @Override
    public void stop() {
        this.stop(0L, DEFAULT_STOP_TIMEUNIT);
    }

    protected boolean stop(Future<?> future) {
        boolean bl = true;
        if (future != null) {
            if (future.isCancelled() || future.isDone()) {
                return false;
            }
            bl = future.cancel(true);
        }
        return bl;
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.state = LifeCycle.State.STOPPED;
        return false;
    }
}

