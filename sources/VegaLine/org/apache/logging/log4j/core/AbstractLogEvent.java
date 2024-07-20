/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.util.Collections;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

public abstract class AbstractLogEvent
implements LogEvent {
    private static final long serialVersionUID = 1L;

    @Override
    public LogEvent toImmutable() {
        return this;
    }

    @Override
    public ReadOnlyStringMap getContextData() {
        return null;
    }

    @Override
    public Map<String, String> getContextMap() {
        return Collections.emptyMap();
    }

    @Override
    public ThreadContext.ContextStack getContextStack() {
        return ThreadContext.EMPTY_STACK;
    }

    @Override
    public Level getLevel() {
        return null;
    }

    @Override
    public String getLoggerFqcn() {
        return null;
    }

    @Override
    public String getLoggerName() {
        return null;
    }

    @Override
    public Marker getMarker() {
        return null;
    }

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public StackTraceElement getSource() {
        return null;
    }

    @Override
    public long getThreadId() {
        return 0L;
    }

    @Override
    public String getThreadName() {
        return null;
    }

    @Override
    public int getThreadPriority() {
        return 0;
    }

    @Override
    public Throwable getThrown() {
        return null;
    }

    @Override
    public ThrowableProxy getThrownProxy() {
        return null;
    }

    @Override
    public long getTimeMillis() {
        return 0L;
    }

    @Override
    public boolean isEndOfBatch() {
        return false;
    }

    @Override
    public boolean isIncludeLocation() {
        return false;
    }

    @Override
    public void setEndOfBatch(boolean endOfBatch) {
    }

    @Override
    public void setIncludeLocation(boolean locationRequired) {
    }

    @Override
    public long getNanoTime() {
        return 0L;
    }
}

