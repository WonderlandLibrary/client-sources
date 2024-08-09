/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.PoolArena;
import io.netty.buffer.PoolChunk;
import io.netty.buffer.PoolChunkListMetric;
import io.netty.buffer.PoolChunkMetric;
import io.netty.buffer.PooledByteBuf;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

final class PoolChunkList<T>
implements PoolChunkListMetric {
    private static final Iterator<PoolChunkMetric> EMPTY_METRICS;
    private final PoolArena<T> arena;
    private final PoolChunkList<T> nextList;
    private final int minUsage;
    private final int maxUsage;
    private final int maxCapacity;
    private PoolChunk<T> head;
    private PoolChunkList<T> prevList;
    static final boolean $assertionsDisabled;

    PoolChunkList(PoolArena<T> poolArena, PoolChunkList<T> poolChunkList, int n, int n2, int n3) {
        if (!$assertionsDisabled && n > n2) {
            throw new AssertionError();
        }
        this.arena = poolArena;
        this.nextList = poolChunkList;
        this.minUsage = n;
        this.maxUsage = n2;
        this.maxCapacity = PoolChunkList.calculateMaxCapacity(n, n3);
    }

    private static int calculateMaxCapacity(int n, int n2) {
        if ((n = PoolChunkList.minUsage0(n)) == 100) {
            return 1;
        }
        return (int)((long)n2 * (100L - (long)n) / 100L);
    }

    void prevList(PoolChunkList<T> poolChunkList) {
        if (!$assertionsDisabled && this.prevList != null) {
            throw new AssertionError();
        }
        this.prevList = poolChunkList;
    }

    boolean allocate(PooledByteBuf<T> pooledByteBuf, int n, int n2) {
        long l;
        if (this.head == null || n2 > this.maxCapacity) {
            return true;
        }
        PoolChunk<T> poolChunk = this.head;
        while ((l = poolChunk.allocate(n2)) < 0L) {
            poolChunk = poolChunk.next;
            if (poolChunk != null) continue;
            return true;
        }
        poolChunk.initBuf(pooledByteBuf, l, n);
        if (poolChunk.usage() >= this.maxUsage) {
            this.remove(poolChunk);
            this.nextList.add(poolChunk);
        }
        return false;
    }

    boolean free(PoolChunk<T> poolChunk, long l) {
        poolChunk.free(l);
        if (poolChunk.usage() < this.minUsage) {
            this.remove(poolChunk);
            return this.move0(poolChunk);
        }
        return false;
    }

    private boolean move(PoolChunk<T> poolChunk) {
        if (!$assertionsDisabled && poolChunk.usage() >= this.maxUsage) {
            throw new AssertionError();
        }
        if (poolChunk.usage() < this.minUsage) {
            return this.move0(poolChunk);
        }
        this.add0(poolChunk);
        return false;
    }

    private boolean move0(PoolChunk<T> poolChunk) {
        if (this.prevList == null) {
            if (!$assertionsDisabled && poolChunk.usage() != 0) {
                throw new AssertionError();
            }
            return true;
        }
        return super.move(poolChunk);
    }

    void add(PoolChunk<T> poolChunk) {
        if (poolChunk.usage() >= this.maxUsage) {
            this.nextList.add(poolChunk);
            return;
        }
        this.add0(poolChunk);
    }

    void add0(PoolChunk<T> poolChunk) {
        poolChunk.parent = this;
        if (this.head == null) {
            this.head = poolChunk;
            poolChunk.prev = null;
            poolChunk.next = null;
        } else {
            poolChunk.prev = null;
            poolChunk.next = this.head;
            this.head.prev = poolChunk;
            this.head = poolChunk;
        }
    }

    private void remove(PoolChunk<T> poolChunk) {
        if (poolChunk == this.head) {
            this.head = poolChunk.next;
            if (this.head != null) {
                this.head.prev = null;
            }
        } else {
            PoolChunk poolChunk2;
            poolChunk.prev.next = poolChunk2 = poolChunk.next;
            if (poolChunk2 != null) {
                poolChunk2.prev = poolChunk.prev;
            }
        }
    }

    @Override
    public int minUsage() {
        return PoolChunkList.minUsage0(this.minUsage);
    }

    @Override
    public int maxUsage() {
        return Math.min(this.maxUsage, 100);
    }

    private static int minUsage0(int n) {
        return Math.max(1, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Iterator<PoolChunkMetric> iterator() {
        PoolArena<T> poolArena = this.arena;
        synchronized (poolArena) {
            if (this.head == null) {
                return EMPTY_METRICS;
            }
            ArrayList<PoolChunk<T>> arrayList = new ArrayList<PoolChunk<T>>();
            PoolChunk<T> poolChunk = this.head;
            do {
                arrayList.add(poolChunk);
            } while ((poolChunk = poolChunk.next) != null);
            return arrayList.iterator();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        PoolArena<T> poolArena = this.arena;
        synchronized (poolArena) {
            if (this.head == null) {
                return "none";
            }
            PoolChunk<T> poolChunk = this.head;
            while (true) {
                stringBuilder.append(poolChunk);
                poolChunk = poolChunk.next;
                if (poolChunk == null) break;
                stringBuilder.append(StringUtil.NEWLINE);
            }
        }
        return stringBuilder.toString();
    }

    void destroy(PoolArena<T> poolArena) {
        PoolChunk<T> poolChunk = this.head;
        while (poolChunk != null) {
            poolArena.destroyChunk(poolChunk);
            poolChunk = poolChunk.next;
        }
        this.head = null;
    }

    static {
        $assertionsDisabled = !PoolChunkList.class.desiredAssertionStatus();
        EMPTY_METRICS = Collections.emptyList().iterator();
    }
}

