/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn.tsccm;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import org.apache.http.impl.conn.tsccm.RouteSpecificPool;
import org.apache.http.util.Args;

@Deprecated
public class WaitingThread {
    private final Condition cond;
    private final RouteSpecificPool pool;
    private Thread waiter;
    private boolean aborted;

    public WaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        Args.notNull(condition, "Condition");
        this.cond = condition;
        this.pool = routeSpecificPool;
    }

    public final Condition getCondition() {
        return this.cond;
    }

    public final RouteSpecificPool getPool() {
        return this.pool;
    }

    public final Thread getThread() {
        return this.waiter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean await(Date date) throws InterruptedException {
        if (this.waiter != null) {
            throw new IllegalStateException("A thread is already waiting on this object.\ncaller: " + Thread.currentThread() + "\nwaiter: " + this.waiter);
        }
        if (this.aborted) {
            throw new InterruptedException("Operation interrupted");
        }
        this.waiter = Thread.currentThread();
        boolean bl = false;
        try {
            if (date != null) {
                bl = this.cond.awaitUntil(date);
            } else {
                this.cond.await();
                bl = true;
            }
            if (this.aborted) {
                throw new InterruptedException("Operation interrupted");
            }
        } finally {
            this.waiter = null;
        }
        return bl;
    }

    public void wakeup() {
        if (this.waiter == null) {
            throw new IllegalStateException("Nobody waiting on this object.");
        }
        this.cond.signalAll();
    }

    public void interrupt() {
        this.aborted = true;
        this.cond.signalAll();
    }
}

