/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;

public abstract class AbstractDatabaseManager
extends AbstractManager
implements Flushable {
    private final ArrayList<LogEvent> buffer;
    private final int bufferSize;
    private boolean running = false;

    protected AbstractDatabaseManager(String string, int n) {
        super(null, string);
        this.bufferSize = n;
        this.buffer = new ArrayList(n + 1);
    }

    protected abstract void startupInternal() throws Exception;

    public final synchronized void startup() {
        if (!this.isRunning()) {
            try {
                this.startupInternal();
                this.running = true;
            } catch (Exception exception) {
                this.logError("Could not perform database startup operations", exception);
            }
        }
    }

    protected abstract boolean shutdownInternal() throws Exception;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized boolean shutdown() {
        boolean bl = true;
        this.flush();
        if (this.isRunning()) {
            try {
                bl &= this.shutdownInternal();
            } catch (Exception exception) {
                this.logWarn("Caught exception while performing database shutdown operations", exception);
                bl = false;
            } finally {
                this.running = false;
            }
        }
        return bl;
    }

    public final boolean isRunning() {
        return this.running;
    }

    protected abstract void connectAndStart();

    protected abstract void writeInternal(LogEvent var1);

    protected abstract boolean commitAndClose();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized void flush() {
        if (this.isRunning() && this.buffer.size() > 0) {
            this.connectAndStart();
            try {
                for (LogEvent logEvent : this.buffer) {
                    this.writeInternal(logEvent);
                }
            } finally {
                this.commitAndClose();
                this.buffer.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void write(LogEvent logEvent) {
        if (this.bufferSize > 0) {
            this.buffer.add(logEvent);
            if (this.buffer.size() >= this.bufferSize || logEvent.isEndOfBatch()) {
                this.flush();
            }
        } else {
            this.connectAndStart();
            try {
                this.writeInternal(logEvent);
            } finally {
                this.commitAndClose();
            }
        }
    }

    @Override
    public final boolean releaseSub(long l, TimeUnit timeUnit) {
        return this.shutdown();
    }

    public final String toString() {
        return this.getName();
    }

    protected static <M extends AbstractDatabaseManager, T extends AbstractFactoryData> M getManager(String string, T t, ManagerFactory<M, T> managerFactory) {
        return (M)((AbstractDatabaseManager)AbstractManager.getManager(string, managerFactory, t));
    }

    protected static abstract class AbstractFactoryData {
        private final int bufferSize;

        protected AbstractFactoryData(int n) {
            this.bufferSize = n;
        }

        public int getBufferSize() {
            return this.bufferSize;
        }
    }
}

