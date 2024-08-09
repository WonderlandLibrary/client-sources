/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.HttpPoolEntry;
import org.apache.http.pool.AbstractConnPool;
import org.apache.http.pool.ConnFactory;
import org.apache.http.pool.PoolEntry;

@Deprecated
class HttpConnPool
extends AbstractConnPool<HttpRoute, OperatedClientConnection, HttpPoolEntry> {
    private static final AtomicLong COUNTER = new AtomicLong();
    private final Log log;
    private final long timeToLive;
    private final TimeUnit timeUnit;

    public HttpConnPool(Log log2, ClientConnectionOperator clientConnectionOperator, int n, int n2, long l, TimeUnit timeUnit) {
        super(new InternalConnFactory(clientConnectionOperator), n, n2);
        this.log = log2;
        this.timeToLive = l;
        this.timeUnit = timeUnit;
    }

    @Override
    protected HttpPoolEntry createEntry(HttpRoute httpRoute, OperatedClientConnection operatedClientConnection) {
        String string = Long.toString(COUNTER.getAndIncrement());
        return new HttpPoolEntry(this.log, string, httpRoute, operatedClientConnection, this.timeToLive, this.timeUnit);
    }

    @Override
    protected PoolEntry createEntry(Object object, Object object2) {
        return this.createEntry((HttpRoute)object, (OperatedClientConnection)object2);
    }

    static class InternalConnFactory
    implements ConnFactory<HttpRoute, OperatedClientConnection> {
        private final ClientConnectionOperator connOperator;

        InternalConnFactory(ClientConnectionOperator clientConnectionOperator) {
            this.connOperator = clientConnectionOperator;
        }

        @Override
        public OperatedClientConnection create(HttpRoute httpRoute) throws IOException {
            return this.connOperator.createConnection();
        }

        @Override
        public Object create(Object object) throws IOException {
            return this.create((HttpRoute)object);
        }
    }
}

