/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.pool;

import java.util.concurrent.TimeUnit;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public abstract class PoolEntry<T, C> {
    private final String id;
    private final T route;
    private final C conn;
    private final long created;
    private final long validityDeadline;
    private long updated;
    private long expiry;
    private volatile Object state;

    public PoolEntry(String string, T t, C c, long l, TimeUnit timeUnit) {
        long l2;
        Args.notNull(t, "Route");
        Args.notNull(c, "Connection");
        Args.notNull(timeUnit, "Time unit");
        this.id = string;
        this.route = t;
        this.conn = c;
        this.updated = this.created = System.currentTimeMillis();
        this.validityDeadline = l > 0L ? ((l2 = this.created + timeUnit.toMillis(l)) > 0L ? l2 : Long.MAX_VALUE) : Long.MAX_VALUE;
        this.expiry = this.validityDeadline;
    }

    public PoolEntry(String string, T t, C c) {
        this(string, t, c, 0L, TimeUnit.MILLISECONDS);
    }

    public String getId() {
        return this.id;
    }

    public T getRoute() {
        return this.route;
    }

    public C getConnection() {
        return this.conn;
    }

    public long getCreated() {
        return this.created;
    }

    public long getValidityDeadline() {
        return this.validityDeadline;
    }

    @Deprecated
    public long getValidUnit() {
        return this.validityDeadline;
    }

    public Object getState() {
        return this.state;
    }

    public void setState(Object object) {
        this.state = object;
    }

    public synchronized long getUpdated() {
        return this.updated;
    }

    public synchronized long getExpiry() {
        return this.expiry;
    }

    public synchronized void updateExpiry(long l, TimeUnit timeUnit) {
        Args.notNull(timeUnit, "Time unit");
        this.updated = System.currentTimeMillis();
        long l2 = l > 0L ? this.updated + timeUnit.toMillis(l) : Long.MAX_VALUE;
        this.expiry = Math.min(l2, this.validityDeadline);
    }

    public synchronized boolean isExpired(long l) {
        return l >= this.expiry;
    }

    public abstract void close();

    public abstract boolean isClosed();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[id:");
        stringBuilder.append(this.id);
        stringBuilder.append("][route:");
        stringBuilder.append(this.route);
        stringBuilder.append("][state:");
        stringBuilder.append(this.state);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

