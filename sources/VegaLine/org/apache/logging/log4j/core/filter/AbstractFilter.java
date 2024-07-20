/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public abstract class AbstractFilter
extends AbstractLifeCycle
implements Filter {
    protected final Filter.Result onMatch;
    protected final Filter.Result onMismatch;

    protected AbstractFilter() {
        this(null, null);
    }

    protected AbstractFilter(Filter.Result onMatch, Filter.Result onMismatch) {
        this.onMatch = onMatch == null ? Filter.Result.NEUTRAL : onMatch;
        this.onMismatch = onMismatch == null ? Filter.Result.DENY : onMismatch;
    }

    @Override
    protected boolean equalsImpl(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equalsImpl(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        AbstractFilter other = (AbstractFilter)obj;
        if (this.onMatch != other.onMatch) {
            return false;
        }
        return this.onMismatch == other.onMismatch;
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
        return this.filter(logger, level, marker, msg, new Object[]{p0});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3, p4});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3, p4, p5});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3, p4, p5, p6});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7, p8});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.filter(logger, level, marker, msg, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7, p8, p9});
    }

    @Override
    public final Filter.Result getOnMatch() {
        return this.onMatch;
    }

    @Override
    public final Filter.Result getOnMismatch() {
        return this.onMismatch;
    }

    @Override
    protected int hashCodeImpl() {
        int prime = 31;
        int result = super.hashCodeImpl();
        result = 31 * result + (this.onMatch == null ? 0 : this.onMatch.hashCode());
        result = 31 * result + (this.onMismatch == null ? 0 : this.onMismatch.hashCode());
        return result;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

