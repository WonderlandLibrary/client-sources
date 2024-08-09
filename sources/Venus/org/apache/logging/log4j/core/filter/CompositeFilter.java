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

    private CompositeFilter(Filter[] filterArray) {
        this.filters = filterArray == null ? EMPTY_FILTERS : filterArray;
    }

    public CompositeFilter addFilter(Filter filter) {
        if (filter == null) {
            return this;
        }
        if (filter instanceof CompositeFilter) {
            int n = this.filters.length + ((CompositeFilter)filter).size();
            Filter[] filterArray = Arrays.copyOf(this.filters, n);
            int n2 = this.filters.length;
            Filter[] filterArray2 = ((CompositeFilter)filter).filters;
            int n3 = filterArray2.length;
            for (int i = 0; i < n3; ++i) {
                Filter filter2;
                filterArray[n2] = filter2 = filterArray2[i];
            }
            return new CompositeFilter(filterArray);
        }
        Filter[] filterArray = Arrays.copyOf(this.filters, this.filters.length + 1);
        filterArray[this.filters.length] = filter;
        return new CompositeFilter(filterArray);
    }

    public CompositeFilter removeFilter(Filter filter) {
        if (filter == null) {
            return this;
        }
        ArrayList<Filter> arrayList = new ArrayList<Filter>(Arrays.asList(this.filters));
        if (filter instanceof CompositeFilter) {
            for (Filter filter2 : ((CompositeFilter)filter).filters) {
                arrayList.remove(filter2);
            }
        } else {
            arrayList.remove(filter);
        }
        return new CompositeFilter(arrayList.toArray(new Filter[this.filters.length - 1]));
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
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        for (Filter filter : this.filters) {
            if (filter instanceof LifeCycle2) {
                ((LifeCycle2)((Object)filter)).stop(l, timeUnit);
                continue;
            }
            filter.stop();
        }
        this.setStopped();
        return false;
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
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object ... objectArray) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, objectArray);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4, object5);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4, object5, object6);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4, object5, object6, object7);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Object object, Throwable throwable) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, object, throwable);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logger, level, marker, message, throwable);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    @Override
    public Filter.Result filter(LogEvent logEvent) {
        Filter.Result result = Filter.Result.NEUTRAL;
        for (int i = 0; i < this.filters.length; ++i) {
            result = this.filters[i].filter(logEvent);
            if (result != Filter.Result.ACCEPT && result != Filter.Result.DENY) continue;
            return result;
        }
        return result;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.filters.length; ++i) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append('{');
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.filters[i].toString());
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append('}');
        }
        return stringBuilder.toString();
    }

    @PluginFactory
    public static CompositeFilter createFilters(@PluginElement(value="Filters") Filter[] filterArray) {
        return new CompositeFilter(filterArray);
    }
}

