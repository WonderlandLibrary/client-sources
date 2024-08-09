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

    protected AbstractFilter(Filter.Result result, Filter.Result result2) {
        this.onMatch = result == null ? Filter.Result.NEUTRAL : result;
        this.onMismatch = result2 == null ? Filter.Result.DENY : result2;
    }

    @Override
    protected boolean equalsImpl(Object object) {
        if (this == object) {
            return false;
        }
        if (!super.equalsImpl(object)) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        AbstractFilter abstractFilter = (AbstractFilter)object;
        if (this.onMatch != abstractFilter.onMatch) {
            return true;
        }
        return this.onMismatch != abstractFilter.onMismatch;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        return this.filter(logger, level, marker, string, new Object[]{object});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4, object5});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4, object5, object6});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4, object5, object6, object7});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4, object5, object6, object7, object8});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4, object5, object6, object7, object8, object9});
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.filter(logger, level, marker, string, new Object[]{object, object2, object3, object4, object5, object6, object7, object8, object9, object10});
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
        int n = 31;
        int n2 = super.hashCodeImpl();
        n2 = 31 * n2 + (this.onMatch == null ? 0 : this.onMatch.hashCode());
        n2 = 31 * n2 + (this.onMismatch == null ? 0 : this.onMismatch.hashCode());
        return n2;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

