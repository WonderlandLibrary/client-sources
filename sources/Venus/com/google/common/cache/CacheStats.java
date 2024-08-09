/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

@GwtCompatible
public final class CacheStats {
    private final long hitCount;
    private final long missCount;
    private final long loadSuccessCount;
    private final long loadExceptionCount;
    private final long totalLoadTime;
    private final long evictionCount;

    public CacheStats(long l, long l2, long l3, long l4, long l5, long l6) {
        Preconditions.checkArgument(l >= 0L);
        Preconditions.checkArgument(l2 >= 0L);
        Preconditions.checkArgument(l3 >= 0L);
        Preconditions.checkArgument(l4 >= 0L);
        Preconditions.checkArgument(l5 >= 0L);
        Preconditions.checkArgument(l6 >= 0L);
        this.hitCount = l;
        this.missCount = l2;
        this.loadSuccessCount = l3;
        this.loadExceptionCount = l4;
        this.totalLoadTime = l5;
        this.evictionCount = l6;
    }

    public long requestCount() {
        return this.hitCount + this.missCount;
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long l = this.requestCount();
        return l == 0L ? 1.0 : (double)this.hitCount / (double)l;
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long l = this.requestCount();
        return l == 0L ? 0.0 : (double)this.missCount / (double)l;
    }

    public long loadCount() {
        return this.loadSuccessCount + this.loadExceptionCount;
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }

    public double loadExceptionRate() {
        long l = this.loadSuccessCount + this.loadExceptionCount;
        return l == 0L ? 0.0 : (double)this.loadExceptionCount / (double)l;
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }

    public double averageLoadPenalty() {
        long l = this.loadSuccessCount + this.loadExceptionCount;
        return l == 0L ? 0.0 : (double)this.totalLoadTime / (double)l;
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public CacheStats minus(CacheStats cacheStats) {
        return new CacheStats(Math.max(0L, this.hitCount - cacheStats.hitCount), Math.max(0L, this.missCount - cacheStats.missCount), Math.max(0L, this.loadSuccessCount - cacheStats.loadSuccessCount), Math.max(0L, this.loadExceptionCount - cacheStats.loadExceptionCount), Math.max(0L, this.totalLoadTime - cacheStats.totalLoadTime), Math.max(0L, this.evictionCount - cacheStats.evictionCount));
    }

    public CacheStats plus(CacheStats cacheStats) {
        return new CacheStats(this.hitCount + cacheStats.hitCount, this.missCount + cacheStats.missCount, this.loadSuccessCount + cacheStats.loadSuccessCount, this.loadExceptionCount + cacheStats.loadExceptionCount, this.totalLoadTime + cacheStats.totalLoadTime, this.evictionCount + cacheStats.evictionCount);
    }

    public int hashCode() {
        return Objects.hashCode(this.hitCount, this.missCount, this.loadSuccessCount, this.loadExceptionCount, this.totalLoadTime, this.evictionCount);
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof CacheStats) {
            CacheStats cacheStats = (CacheStats)object;
            return this.hitCount == cacheStats.hitCount && this.missCount == cacheStats.missCount && this.loadSuccessCount == cacheStats.loadSuccessCount && this.loadExceptionCount == cacheStats.loadExceptionCount && this.totalLoadTime == cacheStats.totalLoadTime && this.evictionCount == cacheStats.evictionCount;
        }
        return true;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }
}

