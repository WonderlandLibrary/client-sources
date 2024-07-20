/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.Inheritance
 *  javax.persistence.InheritanceType
 *  javax.persistence.MappedSuperclass
 *  javax.persistence.Transient
 */
package org.apache.logging.log4j.core.appender.db.jpa;

import java.util.Map;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.AbstractLogEvent;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class AbstractLogEventWrapperEntity
implements LogEvent {
    private static final long serialVersionUID = 1L;
    private final LogEvent wrappedEvent;

    protected AbstractLogEventWrapperEntity() {
        this(new NullLogEvent());
    }

    protected AbstractLogEventWrapperEntity(LogEvent wrappedEvent) {
        if (wrappedEvent == null) {
            throw new IllegalArgumentException("The wrapped event cannot be null.");
        }
        this.wrappedEvent = wrappedEvent;
    }

    @Override
    public LogEvent toImmutable() {
        return Log4jLogEvent.createMemento(this);
    }

    @Transient
    protected final LogEvent getWrappedEvent() {
        return this.wrappedEvent;
    }

    public void setLevel(Level level) {
    }

    public void setLoggerName(String loggerName) {
    }

    public void setSource(StackTraceElement source) {
    }

    public void setMessage(Message message) {
    }

    public void setMarker(Marker marker) {
    }

    public void setThreadId(long threadId) {
    }

    public void setThreadName(String threadName) {
    }

    public void setThreadPriority(int threadPriority) {
    }

    public void setNanoTime(long nanoTime) {
    }

    public void setTimeMillis(long millis) {
    }

    public void setThrown(Throwable throwable) {
    }

    public void setContextData(ReadOnlyStringMap contextData) {
    }

    public void setContextMap(Map<String, String> map) {
    }

    public void setContextStack(ThreadContext.ContextStack contextStack) {
    }

    public void setLoggerFqcn(String fqcn) {
    }

    @Override
    @Transient
    public final boolean isIncludeLocation() {
        return this.getWrappedEvent().isIncludeLocation();
    }

    @Override
    public final void setIncludeLocation(boolean locationRequired) {
        this.getWrappedEvent().setIncludeLocation(locationRequired);
    }

    @Override
    @Transient
    public final boolean isEndOfBatch() {
        return this.getWrappedEvent().isEndOfBatch();
    }

    @Override
    public final void setEndOfBatch(boolean endOfBatch) {
        this.getWrappedEvent().setEndOfBatch(endOfBatch);
    }

    @Override
    @Transient
    public ReadOnlyStringMap getContextData() {
        return this.getWrappedEvent().getContextData();
    }

    private static class NullLogEvent
    extends AbstractLogEvent {
        private static final long serialVersionUID = 1L;

        private NullLogEvent() {
        }
    }
}

