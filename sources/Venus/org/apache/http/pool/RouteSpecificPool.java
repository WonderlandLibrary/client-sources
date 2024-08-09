/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.pool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Future;
import org.apache.http.pool.PoolEntry;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

abstract class RouteSpecificPool<T, C, E extends PoolEntry<T, C>> {
    private final T route;
    private final Set<E> leased;
    private final LinkedList<E> available;
    private final LinkedList<Future<E>> pending;

    RouteSpecificPool(T t) {
        this.route = t;
        this.leased = new HashSet();
        this.available = new LinkedList();
        this.pending = new LinkedList();
    }

    protected abstract E createEntry(C var1);

    public final T getRoute() {
        return this.route;
    }

    public int getLeasedCount() {
        return this.leased.size();
    }

    public int getPendingCount() {
        return this.pending.size();
    }

    public int getAvailableCount() {
        return this.available.size();
    }

    public int getAllocatedCount() {
        return this.available.size() + this.leased.size();
    }

    public E getFree(Object object) {
        if (!this.available.isEmpty()) {
            PoolEntry poolEntry;
            Iterator iterator2;
            if (object != null) {
                iterator2 = this.available.iterator();
                while (iterator2.hasNext()) {
                    poolEntry = (PoolEntry)iterator2.next();
                    if (!object.equals(poolEntry.getState())) continue;
                    iterator2.remove();
                    this.leased.add(poolEntry);
                    return (E)poolEntry;
                }
            }
            iterator2 = this.available.iterator();
            while (iterator2.hasNext()) {
                poolEntry = (PoolEntry)iterator2.next();
                if (poolEntry.getState() != null) continue;
                iterator2.remove();
                this.leased.add(poolEntry);
                return (E)poolEntry;
            }
        }
        return null;
    }

    public E getLastUsed() {
        return (E)(this.available.isEmpty() ? null : (PoolEntry)this.available.getLast());
    }

    public boolean remove(E e) {
        Args.notNull(e, "Pool entry");
        return !this.available.remove(e) && !this.leased.remove(e);
    }

    public void free(E e, boolean bl) {
        Args.notNull(e, "Pool entry");
        boolean bl2 = this.leased.remove(e);
        Asserts.check(bl2, "Entry %s has not been leased from this pool", e);
        if (bl) {
            this.available.addFirst(e);
        }
    }

    public E add(C c) {
        E e = this.createEntry(c);
        this.leased.add(e);
        return e;
    }

    public void queue(Future<E> future) {
        if (future == null) {
            return;
        }
        this.pending.add(future);
    }

    public Future<E> nextPending() {
        return this.pending.poll();
    }

    public void unqueue(Future<E> future) {
        if (future == null) {
            return;
        }
        this.pending.remove(future);
    }

    public void shutdown() {
        for (Future object : this.pending) {
            object.cancel(true);
        }
        this.pending.clear();
        for (PoolEntry poolEntry : this.available) {
            poolEntry.close();
        }
        this.available.clear();
        for (PoolEntry poolEntry : this.leased) {
            poolEntry.close();
        }
        this.leased.clear();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[route: ");
        stringBuilder.append(this.route);
        stringBuilder.append("][leased: ");
        stringBuilder.append(this.leased.size());
        stringBuilder.append("][available: ");
        stringBuilder.append(this.available.size());
        stringBuilder.append("][pending: ");
        stringBuilder.append(this.pending.size());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

