/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.ObjectArrayIterator;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="filters", category="Core", printObject=true)
@PerformanceSensitive(value={"allocation"})
public final class CompositeFilter
extends AbstractLifeCycle
implements Iterable<Filter>,
Filter {
    private static final Filter[] EMPTY_FILTERS = new Filter[0];
    private final Filter[] filters;

    private CompositeFilter() {
        this.filters = EMPTY_FILTERS;
    }

    private CompositeFilter(Filter[] filters) {
        this.filters = filters == null ? EMPTY_FILTERS : filters;
    }

    public CompositeFilter addFilter(Filter filter) {
        if (filter == null) {
            return this;
        }
        if (filter instanceof CompositeFilter) {
            int size = this.filters.length + ((CompositeFilter)filter).size();
            Filter[] copy = Arrays.copyOf(this.filters, size);
            int index = this.filters.length;
            Filter[] arr$ = ((CompositeFilter)filter).filters;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                Filter currentFilter;
                copy[index] = currentFilter = arr$[i$];
            }
            return new CompositeFilter(copy);
        }
        Filter[] copy = Arrays.copyOf(this.filters, this.filters.length + 1);
        copy[this.filters.length] = filter;
        return new CompositeFilter(copy);
    }

    public CompositeFilter removeFilter(Filter filter) {
        if (filter == null) {
            return this;
        }
        ArrayList<Filter> filterList = new ArrayList<Filter>(Arrays.asList(this.filters));
        if (filter instanceof CompositeFilter) {
            for (Filter currentFilter : ((CompositeFilter)filter).filters) {
                filterList.remove(currentFilter);
            }
        } else {
            filterList.remove(filter);
        }
        return new CompositeFilter(filterList.toArray(new Filter[this.filters.length - 1]));
    }

    @Override
    public Iterator<Filter> iterator() {
        return new ObjectArrayIterator<Filter>(this.filters);
    }

    @Deprecated
    public List<Filter> getFilters() {
        return Arrays.asList(this.filters);
    }

    public Filter[] getFiltersArray() {
        return this.filters;
    }

    public boolean isEmpty() {
        return this.filters.length == 0;
    }

    public int size() {
        return this.filters.length;
    }

    @Override
    public void start() {
        this.setStarting();
        for (Filter filter : this.filters) {
            filter.start();
        }
        this.setStarted();
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        for (Filter filter : this.filters) {
            if (filter instanceof LifeCycle2) {
                ((LifeCycle2)((Object)filter)).stop(timeout, timeUnit);
                continue;
            }
            filter.stop();
        }
        this.setStopped();
        return true;
    }

    @Override
    public Filter.Result getOnMismatch() {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result getOnMatch() {
        return Filter.Result.NEUTRAL;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object ... params) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, params);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7, p8);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, t);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, msg, t);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(event);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.filters.length; ++i) {
            if (sb.length() == 0) {
                sb.append('{');
            } else {
                sb.append(", ");
            }
            sb.append(this.filters[i].toString());
        }
        if (sb.length() > 0) {
            sb.append('}');
        }
        return sb.toString();
    }

    @PluginFactory
    public static CompositeFilter createFilters(@PluginElement(value="Filters") Filter[] filters) {
        return new CompositeFilter(filters);
    }
}

