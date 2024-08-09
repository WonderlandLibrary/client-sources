/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.concurrent.locks.LockSupport;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.Log4jThread;

public final class CoarseCachedClock
implements Clock {
    private static volatile CoarseCachedClock instance;
    private static final Object INSTANCE_LOCK;
    private volatile long millis = System.currentTimeMillis();
    private final Thread updater = new Log4jThread(this, "CoarseCachedClock Updater Thread"){
        final CoarseCachedClock this$0;
        {
            this.this$0 = coarseCachedClock;
            super(string);
        }

        @Override
        public void run() {
            while (true) {
                CoarseCachedClock.access$002(this.this$0, System.currentTimeMillis());
                LockSupport.parkNanos(1000000L);
            }
        }
    };

    private CoarseCachedClock() {
        this.updater.setDaemon(false);
        this.updater.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CoarseCachedClock instance() {
        CoarseCachedClock coarseCachedClock = instance;
        if (coarseCachedClock == null) {
            Object object = INSTANCE_LOCK;
            synchronized (object) {
                coarseCachedClock = instance;
                if (coarseCachedClock == null) {
                    instance = coarseCachedClock = new CoarseCachedClock();
                }
            }
        }
        return coarseCachedClock;
    }

    @Override
    public long currentTimeMillis() {
        return this.millis;
    }

    static long access$002(CoarseCachedClock coarseCachedClock, long l) {
        coarseCachedClock.millis = l;
        return coarseCachedClock.millis;
    }

    static {
        INSTANCE_LOCK = new Object();
    }
}

