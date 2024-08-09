/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.async.ThreadNameCachingStrategy;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.ClockFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.TimestampMessage;
import org.apache.logging.log4j.util.StringMap;

public class ReusableLogEventFactory
implements LogEventFactory {
    private static final ThreadNameCachingStrategy THREAD_NAME_CACHING_STRATEGY = ThreadNameCachingStrategy.create();
    private static final Clock CLOCK = ClockFactory.getClock();
    private static ThreadLocal<MutableLogEvent> mutableLogEventThreadLocal = new ThreadLocal();
    private final ContextDataInjector injector = ContextDataInjectorFactory.createInjector();

    @Override
    public LogEvent createEvent(String string, Marker marker, String string2, Level level, Message message, List<Property> list, Throwable throwable) {
        MutableLogEvent mutableLogEvent = mutableLogEventThreadLocal.get();
        if (mutableLogEvent == null || mutableLogEvent.reserved) {
            boolean bl = mutableLogEvent == null;
            mutableLogEvent = new MutableLogEvent();
            mutableLogEvent.setThreadId(Thread.currentThread().getId());
            mutableLogEvent.setThreadName(Thread.currentThread().getName());
            mutableLogEvent.setThreadPriority(Thread.currentThread().getPriority());
            if (bl) {
                mutableLogEventThreadLocal.set(mutableLogEvent);
            }
        }
        mutableLogEvent.reserved = true;
        mutableLogEvent.clear();
        mutableLogEvent.setLoggerName(string);
        mutableLogEvent.setMarker(marker);
        mutableLogEvent.setLoggerFqcn(string2);
        mutableLogEvent.setLevel(level == null ? Level.OFF : level);
        mutableLogEvent.setMessage(message);
        mutableLogEvent.setThrown(throwable);
        mutableLogEvent.setContextData(this.injector.injectContextData(list, (StringMap)mutableLogEvent.getContextData()));
        mutableLogEvent.setContextStack(ThreadContext.getDepth() == 0 ? ThreadContext.EMPTY_STACK : ThreadContext.cloneStack());
        mutableLogEvent.setTimeMillis(message instanceof TimestampMessage ? ((TimestampMessage)((Object)message)).getTimestamp() : CLOCK.currentTimeMillis());
        mutableLogEvent.setNanoTime(Log4jLogEvent.getNanoClock().nanoTime());
        if (THREAD_NAME_CACHING_STRATEGY == ThreadNameCachingStrategy.UNCACHED) {
            mutableLogEvent.setThreadName(Thread.currentThread().getName());
            mutableLogEvent.setThreadPriority(Thread.currentThread().getPriority());
        }
        return mutableLogEvent;
    }

    public static void release(LogEvent logEvent) {
        if (logEvent instanceof MutableLogEvent) {
            ((MutableLogEvent)logEvent).reserved = false;
        }
    }
}

