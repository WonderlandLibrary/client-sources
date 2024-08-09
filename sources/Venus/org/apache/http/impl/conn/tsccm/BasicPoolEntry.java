/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPoolEntryRef;
import org.apache.http.util.Args;

@Deprecated
public class BasicPoolEntry
extends AbstractPoolEntry {
    private final long created;
    private long updated;
    private final long validUntil;
    private long expiry;

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, ReferenceQueue<Object> referenceQueue) {
        super(clientConnectionOperator, httpRoute);
        Args.notNull(httpRoute, "HTTP route");
        this.created = System.currentTimeMillis();
        this.expiry = this.validUntil = Long.MAX_VALUE;
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute) {
        this(clientConnectionOperator, httpRoute, -1L, TimeUnit.MILLISECONDS);
    }

    public BasicPoolEntry(ClientConnectionOperator clientConnectionOperator, HttpRoute httpRoute, long l, TimeUnit timeUnit) {
        super(clientConnectionOperator, httpRoute);
        Args.notNull(httpRoute, "HTTP route");
        this.created = System.currentTimeMillis();
        this.validUntil = l > 0L ? this.created + timeUnit.toMillis(l) : Long.MAX_VALUE;
        this.expiry = this.validUntil;
    }

    protected final OperatedClientConnection getConnection() {
        return this.connection;
    }

    protected final HttpRoute getPlannedRoute() {
        return this.route;
    }

    protected final BasicPoolEntryRef getWeakRef() {
        return null;
    }

    @Override
    protected void shutdownEntry() {
        super.shutdownEntry();
    }

    public long getCreated() {
        return this.created;
    }

    public long getUpdated() {
        return this.updated;
    }

    public long getExpiry() {
        return this.expiry;
    }

    public long getValidUntil() {
        return this.validUntil;
    }

    public void updateExpiry(long l, TimeUnit timeUnit) {
        this.updated = System.currentTimeMillis();
        long l2 = l > 0L ? this.updated + timeUnit.toMillis(l) : Long.MAX_VALUE;
        this.expiry = Math.min(this.validUntil, l2);
    }

    public boolean isExpired(long l) {
        return l >= this.expiry;
    }
}

