/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="ThresholdFilter", category="Core", elementType="filter", printObject=true)
@PerformanceSensitive(value={"allocation"})
public final class ThresholdFilter
extends AbstractFilter {
    private final Level level;

    private ThresholdFilter(Level level, Filter.Result onMatch, Filter.Result onMismatch) {
        super(onMatch, onMismatch);
        this.level = level;
    }

    @Override
    public Filter.Result filter(Logger logger, Level testLevel, Marker marker, String msg, Object ... params) {
        return this.filter(testLevel);
    }

    @Override
    public Filter.Result filter(Logger logger, Level testLevel, Marker marker, Object msg, Throwable t) {
        return this.filter(testLevel);
    }

    @Override
    public Filter.Result filter(Logger logger, Level testLevel, Marker marker, Message msg, Throwable t) {
        return this.filter(testLevel);
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        return this.filter(event.getLevel());
    }

    private Filter.Result filter(Level testLevel) {
        return testLevel.isMoreSpecificThan(this.level) ? this.onMatch : this.onMismatch;
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

    public Level getLevel() {
        return this.level;
    }

    @Override
    public String toString() {
        return this.level.toString();
    }

    @PluginFactory
    public static ThresholdFilter createFilter(@PluginAttribute(value="level") Level level, @PluginAttribute(value="onMatch") Filter.Result match, @PluginAttribute(value="onMismatch") Filter.Result mismatch) {
        Level actualLevel = level == null ? Level.ERROR : level;
        Filter.Result onMatch = match == null ? Filter.Result.NEUTRAL : match;
        Filter.Result onMismatch = mismatch == null ? Filter.Result.DENY : mismatch;
        return new ThresholdFilter(actualLevel, onMatch, onMismatch);
    }
}

