/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.CPoolEntry;
import org.apache.http.pool.AbstractConnPool;
import org.apache.http.pool.ConnFactory;
import org.apache.http.pool.PoolEntry;
import org.apache.http.pool.PoolEntryCallback;

@Contract(threading=ThreadingBehavior.SAFE)
class CPool
extends AbstractConnPool<HttpRoute, ManagedHttpClientConnection, CPoolEntry> {
    private static final AtomicLong COUNTER = new AtomicLong();
    private final Log log = LogFactory.getLog(CPool.class);
    private final long timeToLive;
    private final TimeUnit timeUnit;

    public CPool(ConnFactory<HttpRoute, ManagedHttpClientConnection> connFactory, int n, int n2, long l, TimeUnit timeUnit) {
        super(connFactory, n, n2);
        this.timeToLive = l;
        this.timeUnit = timeUnit;
    }

    @Override
    protected CPoolEntry createEntry(HttpRoute httpRoute, ManagedHttpClientConnection managedHttpClientConnection) {
        String string = Long.toString(COUNTER.getAndIncrement());
        return new CPoolEntry(this.log, string, httpRoute, managedHttpClientConnection, this.timeToLive, this.timeUnit);
    }

    @Override
    protected boolean validate(CPoolEntry cPoolEntry) {
        return !((ManagedHttpClientConnection)cPoolEntry.getConnection()).isStale();
    }

    @Override
    protected void enumAvailable(PoolEntryCallback<HttpRoute, ManagedHttpClientConnection> poolEntryCallback) {
        super.enumAvailable(poolEntryCallback);
    }

    @Override
    protected void enumLeased(PoolEntryCallback<HttpRoute, ManagedHttpClientConnection> poolEntryCallback) {
        super.enumLeased(poolEntryCallback);
    }

    @Override
    protected boolean validate(PoolEntry poolEntry) {
        return this.validate((CPoolEntry)poolEntry);
    }

    @Override
    protected PoolEntry createEntry(Object object, Object object2) {
        return this.createEntry((HttpRoute)object, (ManagedHttpClientConnection)object2);
    }
}

