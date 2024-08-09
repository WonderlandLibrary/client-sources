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

    public void translateTo(RingBufferLogEvent ringBufferLogEvent, long l) {
        ringBufferLogEvent.setValues(this.asyncLogger, this.loggerName, this.marker, this.fqcn, this.level, this.message, this.thrown, this.injector.injectContextData(null, (StringMap)ringBufferLogEvent.getContextData()), this.contextStack, this.threadId, this.threadName, this.threadPriority, this.location, this.currentTimeMillis, this.nanoTime);
        this.clear();
    }

    private void clear() {
        this.setBasicValues(null, null, null, null, null, null, null, null, null, 0L, 0L);
    }

    public void setBasicValues(AsyncLogger asyncLogger, String string, Marker marker, String string2, Level level, Message message, Throwable throwable, ThreadContext.ContextStack contextStack, StackTraceElement stackTraceElement, long l, long l2) {
        this.asyncLogger = asyncLogger;
        this.loggerName = string;
        this.marker = marker;
        this.fqcn = string2;
        this.level = level;
        this.message = message;
        this.thrown = throwable;
        this.contextStack = contextStack;
        this.location = stackTraceElement;
        this.currentTimeMillis = l;
        this.nanoTime = l2;
    }

    public void updateThreadValues() {
        Thread thread2 = Thread.currentThread();
        this.threadId = thread2.getId();
        this.threadName = thread2.getName();
        this.threadPriority = thread2.getPriority();
    }

    public void translateTo(Object object, long l) {
        this.translateTo((RingBufferLogEvent)object, l);
    }
}

