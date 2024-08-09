/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.tsccm.BasicPoolEntry;
import org.apache.http.impl.conn.tsccm.WaitingThread;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.LangUtils;

@Deprecated
public class RouteSpecificPool {
    private final Log log = LogFactory.getLog(this.getClass());
    protected final HttpRoute route;
    protected final int maxEntries;
    protected final ConnPerRoute connPerRoute;
    protected final LinkedList<BasicPoolEntry> freeEntries;
    protected final Queue<WaitingThread> waitingThreads;
    protected int numEntries;

    @Deprecated
    public RouteSpecificPool(HttpRoute httpRoute, int n) {
        this.route = httpRoute;
        this.maxEntries = n;
        this.connPerRoute = new ConnPerRoute(this){
            final RouteSpecificPool this$0;
            {
                this.this$0 = routeSpecificPool;
            }

            @Override
            public int getMaxForRoute(HttpRoute httpRoute) {
                return this.this$0.maxEntries;
            }
        };
        this.freeEntries = new LinkedList();
        this.waitingThreads = new LinkedList<WaitingThread>();
        this.numEntries = 0;
    }

    public RouteSpecificPool(HttpRoute httpRoute, ConnPerRoute connPerRoute) {
        this.route = httpRoute;
        this.connPerRoute = connPerRoute;
        this.maxEntries = connPerRoute.getMaxForRoute(httpRoute);
        this.freeEntries = new LinkedList();
        this.waitingThreads = new LinkedList<WaitingThread>();
        this.numEntries = 0;
    }

    public final HttpRoute getRoute() {
        return this.route;
    }

    public final int getMaxEntries() {
        return this.maxEntries;
    }

    public boolean isUnused() {
        return this.numEntries < 1 && this.waitingThreads.isEmpty();
    }

    public int getCapacity() {
        return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
    }

    public final int getEntryCount() {
        return this.numEntries;
    }

    public BasicPoolEntry allocEntry(Object object) {
        Object object2;
        Object object3;
        if (!this.freeEntries.isEmpty()) {
            object3 = this.freeEntries.listIterator(this.freeEntries.size());
            while (object3.hasPrevious()) {
                object2 = object3.previous();
                if (((AbstractPoolEntry)object2).getState() != null && !LangUtils.equals(object, ((AbstractPoolEntry)object2).getState())) continue;
                object3.remove();
                return object2;
            }
        }
        if (this.getCapacity() == 0 && !this.freeEntries.isEmpty()) {
            object3 = this.freeEntries.remove();
            ((BasicPoolEntry)object3).shutdownEntry();
            object2 = ((BasicPoolEntry)object3).getConnection();
            try {
                object2.close();
            } catch (IOException iOException) {
                this.log.debug("I/O error closing connection", iOException);
            }
            return object3;
        }
        return null;
    }

    public void freeEntry(BasicPoolEntry basicPoolEntry) {
        if (this.numEntries < 1) {
            throw new IllegalStateException("No entry created for this pool. " + this.route);
        }
        if (this.numEntries <= this.freeEntries.size()) {
            throw new IllegalStateException("No entry allocated from this pool. " + this.route);
        }
        this.freeEntries.add(basicPoolEntry);
    }

    public void createdEntry(BasicPoolEntry basicPoolEntry) {
        Args.check(this.route.equals(basicPoolEntry.getPlannedRoute()), "Entry not planned for this pool");
        ++this.numEntries;
    }

    public boolean deleteEntry(BasicPoolEntry basicPoolEntry) {
        boolean bl = this.freeEntries.remove(basicPoolEntry);
        if (bl) {
            --this.numEntries;
        }
        return bl;
    }

    public void dropEntry() {
        Asserts.check(this.numEntries > 0, "There is no entry that could be dropped");
        --this.numEntries;
    }

    public void queueThread(WaitingThread waitingThread) {
        Args.notNull(waitingThread, "Waiting thread");
        this.waitingThreads.add(waitingThread);
    }

    public boolean hasThread() {
        return !this.waitingThreads.isEmpty();
    }

    public WaitingThread nextThread() {
        return this.waitingThreads.peek();
    }

    public void removeThread(WaitingThread waitingThread) {
        if (waitingThread == null) {
            return;
        }
        this.waitingThreads.remove(waitingThread);
    }
}

