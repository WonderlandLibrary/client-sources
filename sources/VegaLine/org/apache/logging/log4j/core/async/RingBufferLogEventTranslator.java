/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventTranslator
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.EventTranslator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.async.AsyncLogger;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.StringMap;

public class RingBufferLogEventTranslator
implements EventTranslator<RingBufferLogEvent> {
    private final ContextDataInjector injector = ContextDataInjectorFactory.createInjector();
    private AsyncLogger asyncLogger;
    private String loggerName;
    protected Marker marker;
    protected String fqcn;
    protected Level level;
    protected Message message;
    protected Throwable thrown;
    private ThreadContext.ContextStack contextStack;
    private long threadId = Thread.currentThread().getId();
    private String threadName = Thread.currentThread().getName();
    private int threadPriority = Thread.currentThread().getPriority();
    private StackTraceElement location;
    private long currentTimeMillis;
    private long nanoTime;

    public void translateTo(RingBufferLogEvent event, long sequence) {
        event.setValues(this.asyncLogger, this.loggerName, this.marker, this.fqcn, this.level, this.message, this.thrown, this.injector.injectContextData(null, (StringMap)event.getContextData()), this.contextStack, this.threadId, this.threadName, this.threadPriority, this.location, this.currentTimeMillis, this.nanoTime);
        this.clear();
    }

    private void clear() {
        this.setBasicValues(null, null, null, null, null, null, null, null, null, 0L, 0L);
    }

    public void setBasicValues(AsyncLogger anAsyncLogger, String aLoggerName, Marker aMarker, String theFqcn, Level aLevel, Message msg, Throwable aThrowable, ThreadContext.ContextStack aContextStack, StackTraceElement aLocation, long aCurrentTimeMillis, long aNanoTime) {
        this.asyncLogger = anAsyncLogger;
        this.loggerName = aLoggerName;
        this.marker = aMarker;
        this.fqcn = theFqcn;
        this.level = aLevel;
        this.message = msg;
        this.thrown = aThrowable;
        this.contextStack = aContextStack;
        this.location = aLocation;
        this.currentTimeMillis = aCurrentTimeMillis;
        this.nanoTime = aNanoTime;
    }

    public void updateThreadValues() {
        Thread currentThread = Thread.currentThread();
        this.threadId = currentThread.getId();
        this.threadName = currentThread.getName();
        this.threadPriority = currentThread.getPriority();
    }
}

