/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.tsccm.AbstractConnPool;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.PoolEntryRequest;
import org.apache.http.impl.conn.tsccm.RouteSpecificPool;
import org.apache.http.impl.conn.tsccm.WaitingThread;
import org.apache.http.impl.conn.tsccm.WaitingThreadAborter;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
public class ConnPoolByRoute
extends AbstractConnPool {
    private final Log log = LogFactory.getLog(this.getClass());
    private final Lock poolLock;
    protected final ClientConnectionOperator operator;
    protected final ConnPerRoute connPerRoute;
    protected final Set<BasicPoolEntry> leasedConnections;
    protected final Queue<BasicPoolEntry> freeConnections;
    protected final Queue<WaitingThread> waitingThreads;
    protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
    private final long connTTL;
    private final TimeUnit connTTLTimeUnit;
    protected volatile boolean shutdown;
    protected volatile int maxTotalConnections;
    protected volatile int numConnections;

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, ConnPerRoute connPerRoute, int n) {
        this(clientConnectionOperator, connPerRoute, n, -1L, TimeUnit.MILLISECONDS);
    }

    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, ConnPerRoute connPerRoute, int n, long l, TimeUnit timeUnit) {
        Args.notNull(clientConnectionOperator, "Connection operator");
        Args.notNull(connPerRoute, "Connections per route");
        this.poolLock = ((AbstractConnPool)this).poolLock;
        this.leasedConnections = ((AbstractConnPool)this).leasedConnections;
        this.operator = clientConnectionOperator;
        this.connPerRoute = connPerRoute;
        this.maxTotalConnections = n;
        this.freeConnections = this.createFreeConnQueue();
        this.waitingThreads = this.createWaitingThreadQueue();
        this.routeToPool = this.createRouteToPoolMap();
        this.connTTL = l;
        this.connTTLTimeUnit = timeUnit;
    }

    protected Lock getLock() {
        return this.poolLock;
    }

    @Deprecated
    public ConnPoolByRoute(ClientConnectionOperator clientConnectionOperator, HttpParams httpParams) {
        this(clientConnectionOperator, ConnManagerParams.getMaxConnectionsPerRoute(httpParams), ConnManagerParams.getMaxTotalConnections(httpParams));
    }

    protected Queue<BasicPoolEntry> createFreeConnQueue() {
        return new LinkedList<BasicPoolEntry>();
    }

    protected Queue<WaitingThread> createWaitingThreadQueue() {
        return new LinkedList<WaitingThread>();
    }

    protected Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
        return new HashMap<HttpRoute, RouteSpecificPool>();
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute httpRoute) {
        return new RouteSpecificPool(httpRoute, this.connPerRoute);
    }

    protected WaitingThread newWaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        return new WaitingThread(condition, routeSpecificPool);
    }

    private void closeConnection(BasicPoolEntry basicPoolEntry) {
        OperatedClientConnection operatedClientConnection = basicPoolEntry.getConnection();
        if (operatedClientConnection != null) {
            try {
                operatedClientConnection.close();
            } catch (IOException iOException) {
                this.log.debug("I/O error closing connection", iOException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected RouteSpecificPool getRoutePool(HttpRoute httpRoute, boolean bl) {
        RouteSpecificPool routeSpecificPool = null;
        this.poolLock.lock();
        try {
            routeSpecificPool = this.routeToPool.get(httpRoute);
            if (routeSpecificPool == null && bl) {
                routeSpecificPool = this.newRouteSpecificPool(httpRoute);
                this.routeToPool.put(httpRoute, routeSpecificPool);
            }
        } finally {
            this.poolLock.unlock();
        }
        return routeSpecificPool;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getConnectionsInPool(HttpRoute httpRoute) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, true);
            int n = routeSpecificPool != null ? routeSpecificPool.getEntryCount() : 0;
            return n;
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getConnectionsInPool() {
        this.poolLock.lock();
        try {
            int n = this.numConnections;
            return n;
        } finally {
            this.poolLock.unlock();
        }
    }

    @Override
    public PoolEntryRequest requestPoolEntry(HttpRoute httpRoute, Object object) {
        WaitingThreadAborter waitingThreadAborter = new WaitingThreadAborter();
        return new PoolEntryRequest(this, waitingThreadAborter, httpRoute, object){
            final WaitingThreadAborter val$aborter;
            final HttpRoute val$route;
            final Object val$state;
            final ConnPoolByRoute this$0;
            {
                this.this$0 = connPoolByRoute;
                this.val$aborter = waitingThreadAborter;
                this.val$route = httpRoute;
                this.val$state = object;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void abortRequest() {
                ConnPoolByRoute.access$000(this.this$0).lock();
                try {
                    this.val$aborter.abort();
                } finally {
                    ConnPoolByRoute.access$000(this.this$0).unlock();
                }
            }

            @Override
            public BasicPoolEntry getPoolEntry(long l, TimeUnit timeUnit) throws InterruptedException, ConnectionPoolTimeoutException {
                return this.this$0.getEntryBlocking(this.val$route, this.val$state, l, timeUnit, this.val$aborter);
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected BasicPoolEntry getEntryBlocking(HttpRoute httpRoute, Object object, long l, TimeUnit timeUnit, WaitingThreadAborter waitingThreadAborter) throws ConnectionPoolTimeoutException, InterruptedException {
        Date date = null;
        if (l > 0L) {
            date = new Date(System.currentTimeMillis() + timeUnit.toMillis(l));
        }
        BasicPoolEntry basicPoolEntry = null;
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, false);
            WaitingThread waitingThread = null;
            while (basicPoolEntry == null) {
                boolean bl;
                Asserts.check(!this.shutdown, "Connection pool shut down");
                if (this.log.isDebugEnabled()) {
                    this.log.debug("[" + httpRoute + "] total kept alive: " + this.freeConnections.size() + ", total issued: " + this.leasedConnections.size() + ", total allocated: " + this.numConnections + " out of " + this.maxTotalConnections);
                }
                if ((basicPoolEntry = this.getFreeEntry(routeSpecificPool, object)) != null) {
                    break;
                }
                boolean bl2 = bl = routeSpecificPool.getCapacity() > 0;
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Available capacity: " + routeSpecificPool.getCapacity() + " out of " + routeSpecificPool.getMaxEntries() + " [" + httpRoute + "][" + object + "]");
                }
                if (bl && this.numConnections < this.maxTotalConnections) {
                    basicPoolEntry = this.createEntry(routeSpecificPool, this.operator);
                    continue;
                }
                if (bl && !this.freeConnections.isEmpty()) {
                    this.deleteLeastUsedEntry();
                    routeSpecificPool = this.getRoutePool(httpRoute, false);
                    basicPoolEntry = this.createEntry(routeSpecificPool, this.operator);
                    continue;
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Need to wait for connection [" + httpRoute + "][" + object + "]");
                }
                if (waitingThread == null) {
                    waitingThread = this.newWaitingThread(this.poolLock.newCondition(), routeSpecificPool);
                    waitingThreadAborter.setWaitingThread(waitingThread);
                }
                boolean bl3 = false;
                try {
                    routeSpecificPool.queueThread(waitingThread);
                    this.waitingThreads.add(waitingThread);
                    bl3 = waitingThread.await(date);
                } finally {
                    routeSpecificPool.removeThread(waitingThread);
                    this.waitingThreads.remove(waitingThread);
                }
                if (bl3 || date == null || date.getTime() > System.currentTimeMillis()) continue;
                throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
            }
        } finally {
            this.poolLock.unlock();
        }
        return basicPoolEntry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void freeEntry(BasicPoolEntry basicPoolEntry, boolean bl, long l, TimeUnit timeUnit) {
        HttpRoute httpRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Releasing connection [" + httpRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            if (this.shutdown) {
                this.closeConnection(basicPoolEntry);
                return;
            }
            this.leasedConnections.remove(basicPoolEntry);
            RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, false);
            if (bl && routeSpecificPool.getCapacity() >= 0) {
                if (this.log.isDebugEnabled()) {
                    String string = l > 0L ? "for " + l + " " + (Object)((Object)timeUnit) : "indefinitely";
                    this.log.debug("Pooling connection [" + httpRoute + "][" + basicPoolEntry.getState() + "]; keep alive " + string);
                }
                routeSpecificPool.freeEntry(basicPoolEntry);
                basicPoolEntry.updateExpiry(l, timeUnit);
                this.freeConnections.add(basicPoolEntry);
            } else {
                this.closeConnection(basicPoolEntry);
                routeSpecificPool.dropEntry();
                --this.numConnections;
            }
            this.notifyWaitingThread(routeSpecificPool);
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected BasicPoolEntry getFreeEntry(RouteSpecificPool routeSpecificPool, Object object) {
        BasicPoolEntry basicPoolEntry = null;
        this.poolLock.lock();
        try {
            boolean bl = false;
            while (!bl) {
                basicPoolEntry = routeSpecificPool.allocEntry(object);
                if (basicPoolEntry != null) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Getting free connection [" + routeSpecificPool.getRoute() + "][" + object + "]");
                    }
                    this.freeConnections.remove(basicPoolEntry);
                    if (basicPoolEntry.isExpired(System.currentTimeMillis())) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Closing expired free connection [" + routeSpecificPool.getRoute() + "][" + object + "]");
                        }
                        this.closeConnection(basicPoolEntry);
                        routeSpecificPool.dropEntry();
                        --this.numConnections;
                        continue;
                    }
                    this.leasedConnections.add(basicPoolEntry);
                    bl = true;
                    continue;
                }
                bl = true;
                if (!this.log.isDebugEnabled()) continue;
                this.log.debug("No free connections [" + routeSpecificPool.getRoute() + "][" + object + "]");
            }
        } finally {
            this.poolLock.unlock();
        }
        return basicPoolEntry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected BasicPoolEntry createEntry(RouteSpecificPool routeSpecificPool, ClientConnectionOperator clientConnectionOperator) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Creating new connection [" + routeSpecificPool.getRoute() + "]");
        }
        BasicPoolEntry basicPoolEntry = new BasicPoolEntry(clientConnectionOperator, routeSpecificPool.getRoute(), this.connTTL, this.connTTLTimeUnit);
        this.poolLock.lock();
        try {
            routeSpecificPool.createdEntry(basicPoolEntry);
            ++this.numConnections;
            this.leasedConnections.add(basicPoolEntry);
        } finally {
            this.poolLock.unlock();
        }
        return basicPoolEntry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void deleteEntry(BasicPoolEntry basicPoolEntry) {
        HttpRoute httpRoute = basicPoolEntry.getPlannedRoute();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Deleting connection [" + httpRoute + "][" + basicPoolEntry.getState() + "]");
        }
        this.poolLock.lock();
        try {
            this.closeConnection(basicPoolEntry);
            RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, false);
            routeSpecificPool.deleteEntry(basicPoolEntry);
            --this.numConnections;
            if (routeSpecificPool.isUnused()) {
                this.routeToPool.remove(httpRoute);
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void deleteLeastUsedEntry() {
        this.poolLock.lock();
        try {
            BasicPoolEntry basicPoolEntry = this.freeConnections.remove();
            if (basicPoolEntry != null) {
                this.deleteEntry(basicPoolEntry);
            } else if (this.log.isDebugEnabled()) {
                this.log.debug("No free connection to delete");
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void handleLostEntry(HttpRoute httpRoute) {
        this.poolLock.lock();
        try {
            RouteSpecificPool routeSpecificPool = this.getRoutePool(httpRoute, false);
            routeSpecificPool.dropEntry();
            if (routeSpecificPool.isUnused()) {
                this.routeToPool.remove(httpRoute);
            }
            --this.numConnections;
            this.notifyWaitingThread(routeSpecificPool);
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void notifyWaitingThread(RouteSpecificPool routeSpecificPool) {
        WaitingThread waitingThread = null;
        this.poolLock.lock();
        try {
            if (routeSpecificPool != null && routeSpecificPool.hasThread()) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Notifying thread waiting on pool [" + routeSpecificPool.getRoute() + "]");
                }
                waitingThread = routeSpecificPool.nextThread();
            } else if (!this.waitingThreads.isEmpty()) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Notifying thread waiting on any pool");
                }
                waitingThread = this.waitingThreads.remove();
            } else if (this.log.isDebugEnabled()) {
                this.log.debug("Notifying no-one, there are no waiting threads");
            }
            if (waitingThread != null) {
                waitingThread.wakeup();
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteClosedConnections() {
        this.poolLock.lock();
        try {
            Iterator iterator2 = this.freeConnections.iterator();
            while (iterator2.hasNext()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry)iterator2.next();
                if (basicPoolEntry.getConnection().isOpen()) continue;
                iterator2.remove();
                this.deleteEntry(basicPoolEntry);
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeIdleConnections(long l, TimeUnit timeUnit) {
        long l2;
        Args.notNull(timeUnit, "Time unit");
        long l3 = l2 = l > 0L ? l : 0L;
        if (this.log.isDebugEnabled()) {
            this.log.debug("Closing connections idle longer than " + l2 + " " + (Object)((Object)timeUnit));
        }
        long l4 = System.currentTimeMillis() - timeUnit.toMillis(l2);
        this.poolLock.lock();
        try {
            Iterator iterator2 = this.freeConnections.iterator();
            while (iterator2.hasNext()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry)iterator2.next();
                if (basicPoolEntry.getUpdated() > l4) continue;
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection last used @ " + new Date(basicPoolEntry.getUpdated()));
                }
                iterator2.remove();
                this.deleteEntry(basicPoolEntry);
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeExpiredConnections() {
        this.log.debug("Closing expired connections");
        long l = System.currentTimeMillis();
        this.poolLock.lock();
        try {
            Iterator iterator2 = this.freeConnections.iterator();
            while (iterator2.hasNext()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry)iterator2.next();
                if (!basicPoolEntry.isExpired(l)) continue;
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection expired @ " + new Date(basicPoolEntry.getExpiry()));
                }
                iterator2.remove();
                this.deleteEntry(basicPoolEntry);
            }
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        this.poolLock.lock();
        try {
            Object object;
            Object object2;
            if (this.shutdown) {
                return;
            }
            this.shutdown = true;
            Iterator<BasicPoolEntry> iterator2 = this.leasedConnections.iterator();
            while (iterator2.hasNext()) {
                object2 = iterator2.next();
                iterator2.remove();
                this.closeConnection((BasicPoolEntry)object2);
            }
            object2 = this.freeConnections.iterator();
            while (object2.hasNext()) {
                object = (BasicPoolEntry)object2.next();
                object2.remove();
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Closing connection [" + ((BasicPoolEntry)object).getPlannedRoute() + "][" + ((AbstractPoolEntry)object).getState() + "]");
                }
                this.closeConnection((BasicPoolEntry)object);
            }
            object = this.waitingThreads.iterator();
            while (object.hasNext()) {
                WaitingThread waitingThread = (WaitingThread)object.next();
                object.remove();
                waitingThread.wakeup();
            }
            this.routeToPool.clear();
        } finally {
            this.poolLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMaxTotalConnections(int n) {
        this.poolLock.lock();
        try {
            this.maxTotalConnections = n;
        } finally {
            this.poolLock.unlock();
        }
    }

    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }

    static Lock access$000(ConnPoolByRoute connPoolByRoute) {
        return connPoolByRoute.poolLock;
    }
}

