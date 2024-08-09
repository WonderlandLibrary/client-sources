/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.filter.Filterable;
import org.apache.logging.log4j.util.PerformanceSensitive;

public class AppenderControl
extends AbstractFilterable {
    private final ThreadLocal<AppenderControl> recursive = new ThreadLocal();
    private final Appender appender;
    private final Level level;
    private final int intLevel;
    private final String appenderName;

    public AppenderControl(Appender appender, Level level, Filter filter) {
        super(filter);
        this.appender = appender;
        this.appenderName = appender.getName();
        this.level = level;
        this.intLevel = level == null ? Level.ALL.intLevel() : level.intLevel();
        this.start();
    }

    public String getAppenderName() {
        return this.appenderName;
    }

    public Appender getAppender() {
        return this.appender;
    }

    public void callAppender(LogEvent logEvent) {
        if (this.shouldSkip(logEvent)) {
            return;
        }
        this.callAppenderPreventRecursion(logEvent);
    }

    private boolean shouldSkip(LogEvent logEvent) {
        return this.isFilteredByAppenderControl(logEvent) || this.isFilteredByLevel(logEvent) || this.isRecursiveCall();
    }

    @PerformanceSensitive
    private boolean isFilteredByAppenderControl(LogEvent logEvent) {
        Filter filter = this.getFilter();
        return filter != null && Filter.Result.DENY == filter.filter(logEvent);
    }

    @PerformanceSensitive
    private boolean isFilteredByLevel(LogEvent logEvent) {
        return this.level != null && this.intLevel < logEvent.getLevel().intLevel();
    }

    @PerformanceSensitive
    private boolean isRecursiveCall() {
        if (this.recursive.get() != null) {
            this.appenderErrorHandlerMessage("Recursive call to appender ");
            return false;
        }
        return true;
    }

    private String appenderErrorHandlerMessage(String string) {
        String string2 = this.createErrorMsg(string);
        this.appender.getHandler().error(string2);
        return string2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void callAppenderPreventRecursion(LogEvent logEvent) {
        try {
            this.recursive.set(this);
            this.callAppender0(logEvent);
        } finally {
            this.recursive.set(null);
        }
    }

    private void callAppender0(LogEvent logEvent) {
        this.ensureAppenderStarted();
        if (!this.isFilteredByAppender(logEvent)) {
            this.tryCallAppender(logEvent);
        }
    }

    private void ensureAppenderStarted() {
        if (!this.appender.isStarted()) {
            this.handleError("Attempted to append to non-started appender ");
        }
    }

    private void handleError(String string) {
        String string2 = this.appenderErrorHandlerMessage(string);
        if (!this.appender.ignoreExceptions()) {
            throw new AppenderLoggingException(string2);
        }
    }

    private String createErrorMsg(String string) {
        return string + this.appender.getName();
    }

    private boolean isFilteredByAppender(LogEvent logEvent) {
        return this.appender instanceof Filterable && ((Filterable)((Object)this.appender)).isFiltered(logEvent);
    }

    private void tryCallAppender(LogEvent logEvent) {
        try {
            this.appender.append(logEvent);
        } catch (RuntimeException runtimeException) {
            this.handleAppenderError(runtimeException);
        } catch (Exception exception) {
            this.handleAppenderError(new AppenderLoggingException(exception));
        }
    }

    private void handleAppenderError(RuntimeException runtimeException) {
        this.appender.getHandler().error(this.createErrorMsg("An exception occurred processing Appender "), runtimeException);
        if (!this.appender.ignoreExceptions()) {
            throw runtimeException;
        }
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof AppenderControl)) {
            return true;
        }
        AppenderControl appenderControl = (AppenderControl)object;
        return Objects.equals(this.appenderName, appenderControl.appenderName);
    }

    public int hashCode() {
        return this.appenderName.hashCode();
    }

    public String toString() {
        return super.toString() + "[appender=" + this.appender + ", appenderName=" + this.appenderName + ", level=" + this.level + ", intLevel=" + this.intLevel + ", recursive=" + this.recursive + ", filter=" + this.getFilter() + "]";
    }
}

