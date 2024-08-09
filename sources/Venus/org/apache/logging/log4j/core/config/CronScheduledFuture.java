/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CronScheduledFuture<V>
implements ScheduledFuture<V> {
    private volatile FutureData futureData;

    public CronScheduledFuture(ScheduledFuture<V> scheduledFuture, Date date) {
        this.futureData = new FutureData(this, scheduledFuture, date);
    }

    public Date getFireTime() {
        return FutureData.access$000(this.futureData);
    }

    void reset(ScheduledFuture<?> scheduledFuture, Date date) {
        this.futureData = new FutureData(this, scheduledFuture, date);
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return FutureData.access$100(this.futureData).getDelay(timeUnit);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return FutureData.access$100(this.futureData).compareTo(delayed);
    }

    @Override
    public boolean cancel(boolean bl) {
        return FutureData.access$100(this.futureData).cancel(bl);
    }

    @Override
    public boolean isCancelled() {
        return FutureData.access$100(this.futureData).isCancelled();
    }

    @Override
    public boolean isDone() {
        return FutureData.access$100(this.futureData).isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return FutureData.access$100(this.futureData).get();
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return FutureData.access$100(this.futureData).get(l, timeUnit);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Delayed)object);
    }

    private class FutureData {
        private final ScheduledFuture<?> scheduledFuture;
        private final Date runDate;
        final CronScheduledFuture this$0;

        FutureData(CronScheduledFuture cronScheduledFuture, ScheduledFuture<?> scheduledFuture, Date date) {
            this.this$0 = cronScheduledFuture;
            this.scheduledFuture = scheduledFuture;
            this.runDate = date;
        }

        static Date access$000(FutureData futureData) {
            return futureData.runDate;
        }

        static ScheduledFuture access$100(FutureData futureData) {
            return futureData.scheduledFuture;
        }
    }
}

