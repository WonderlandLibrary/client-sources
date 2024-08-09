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

    static LogDelay createLogDelay(long l) {
        return new LogDelay(l);
    }

    private BurstFilter(Level level, float f, long l, Filter.Result result, Filter.Result result2) {
        super(result, result2);
        this.level = level;
        this.burstInterval = (long)(1.0E9f * ((float)l / f));
        int n = 0;
        while ((long)n < l) {
            this.available.add(BurstFilter.createLogDelay(0L));
            ++n;
        }
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        return this.filter(logEvent.getLevel());
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.filter(level);
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.filter(level);
    }

    private Filter.Result filter(Level level) {
        if (this.level.isMoreSpecificThan(level)) {
            LogDelay logDelay = (LogDelay)this.history.poll();
            while (logDelay != null) {
                this.available.add(logDelay);
                logDelay = (LogDelay)this.history.poll();
            }
            logDelay = this.available.poll();
            if (logDelay != null) {
                logDelay.setDelay(this.burstInterval);
                this.history.add(logDelay);
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
        for (LogDelay logDelay : this.history) {
            this.history.remove(logDelay);
            this.available.add(logDelay);
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

    BurstFilter(Level level, float f, long l, Filter.Result result, Filter.Result result2, 1 var7_6) {
        this(level, f, l, result, result2);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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

        public Builder setRate(float f) {
            this.rate = f;
            return this;
        }

        public Builder setMaxBurst(long l) {
            this.maxBurst = l;
            return this;
        }

        public Builder setOnMatch(Filter.Result result) {
            this.onMatch = result;
            return this;
        }

        public Builder setOnMismatch(Filter.Result result) {
            this.onMismatch = result;
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
            return new BurstFilter(this.level, this.rate, this.maxBurst, this.onMatch, this.onMismatch, null);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }

    private static class LogDelay
    implements Delayed {
        private long expireTime;

        LogDelay(long l) {
            this.expireTime = l;
        }

        public void setDelay(long l) {
            this.expireTime = l + System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.expireTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            long l = this.expireTime - ((LogDelay)delayed).expireTime;
            return Long.signum(l);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            LogDelay logDelay = (LogDelay)object;
            return this.expireTime != logDelay.expireTime;
        }

        public int hashCode() {
            return (int)(this.expireTime ^ this.expireTime >>> 32);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Delayed)object);
        }
    }
}

