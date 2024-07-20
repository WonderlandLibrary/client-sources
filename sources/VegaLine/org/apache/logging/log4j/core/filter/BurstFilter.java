/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

@Plugin(name="BurstFilter", category="Core", elementType="filter", printObject=true)
public final class BurstFilter
extends AbstractFilter {
    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int DEFAULT_RATE = 10;
    private static final int DEFAULT_RATE_MULTIPLE = 100;
    private static final int HASH_SHIFT = 32;
    private final Level level;
    private final long burstInterval;
    private final DelayQueue<LogDelay> history = new DelayQueue();
    private final Queue<LogDelay> available = new ConcurrentLinkedQueue<LogDelay>();

    static LogDelay createLogDelay(long expireTime) {
        return new LogDelay(expireTime);
    }

    private BurstFilter(Level level, float rate, long maxBurst, Filter.Result onMatch, Filter.Result onMismatch) {
        super(onMatch, onMismatch);
        this.level = level;
        this.burstInterval = (long)(1.0E9f * ((float)maxBurst / rate));
        int i = 0;
        while ((long)i < maxBurst) {
            this.available.add(BurstFilter.createLogDelay(0L));
            ++i;
        }
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        return this.filter(event.getLevel());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.filter(level);
    }

    private Filter.Result filter(Level level) {
        if (this.level.isMoreSpecificThan(level)) {
            LogDelay delay = (LogDelay)this.history.poll();
            while (delay != null) {
                this.available.add(delay);
                delay = (LogDelay)this.history.poll();
            }
            delay = this.available.poll();
            if (delay != null) {
                delay.setDelay(this.burstInterval);
                this.history.add(delay);
                return this.onMatch;
            }
            return this.onMismatch;
        }
        return this.onMatch;
    }

    public int getAvailable() {
        return this.available.size();
    }

    public void clear() {
        for (LogDelay delay : this.history) {
            this.history.remove(delay);
            this.available.add(delay);
        }
    }

    @Override
    public String toString() {
        return "level=" + this.level.toString() + ", interval=" + this.burstInterval + ", max=" + this.history.size();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<BurstFilter> {
        @PluginBuilderAttribute
        private Level level = Level.WARN;
        @PluginBuilderAttribute
        private float rate = 10.0f;
        @PluginBuilderAttribute
        private long maxBurst;
        @PluginBuilderAttribute
        private Filter.Result onMatch = Filter.Result.NEUTRAL;
        @PluginBuilderAttribute
        private Filter.Result onMismatch = Filter.Result.DENY;

        public Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        public Builder setRate(float rate) {
            this.rate = rate;
            return this;
        }

        public Builder setMaxBurst(long maxBurst) {
            this.maxBurst = maxBurst;
            return this;
        }

        public Builder setOnMatch(Filter.Result onMatch) {
            this.onMatch = onMatch;
            return this;
        }

        public Builder setOnMismatch(Filter.Result onMismatch) {
            this.onMismatch = onMismatch;
            return this;
        }

        @Override
        public BurstFilter build() {
            if (this.rate <= 0.0f) {
                this.rate = 10.0f;
            }
            if (this.maxBurst <= 0L) {
                this.maxBurst = (long)(this.rate * 100.0f);
            }
            return new BurstFilter(this.level, this.rate, this.maxBurst, this.onMatch, this.onMismatch);
        }
    }

    private static class LogDelay
    implements Delayed {
        private long expireTime;

        LogDelay(long expireTime) {
            this.expireTime = expireTime;
        }

        public void setDelay(long delay) {
            this.expireTime = delay + System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.expireTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            long diff = this.expireTime - ((LogDelay)delayed).expireTime;
            return Long.signum(diff);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            LogDelay logDelay = (LogDelay)o;
            return this.expireTime == logDelay.expireTime;
        }

        public int hashCode() {
            return (int)(this.expireTime ^ this.expireTime >>> 32);
        }
    }
}

