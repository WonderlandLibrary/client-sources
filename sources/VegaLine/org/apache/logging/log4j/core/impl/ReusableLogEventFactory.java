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
    public LogEvent createEvent(String loggerName, Marker marker, String fqcn, Level level, Message message, List<Property> properties, Throwable t) {
        MutableLogEvent result = mutableLogEventThreadLocal.get();
        if (result == null || result.reserved) {
            boolean initThreadLocal = result == null;
            result = new MutableLogEvent();
            result.setThreadId(Thread.currentThread().getId());
            result.setThreadName(Thread.currentThread().getName());
            result.setThreadPriority(Thread.currentThread().getPriority());
            if (initThreadLocal) {
                mutableLogEventThreadLocal.set(result);
            }
        }
        result.reserved = true;
        result.clear();
        result.setLoggerName(loggerName);
        result.setMarker(marker);
        result.setLoggerFqcn(fqcn);
        result.setLevel(level == null ? Level.OFF : level);
        result.setMessage(message);
        result.setThrown(t);
        result.setContextData(this.injector.injectContextData(properties, (StringMap)result.getContextData()));
        result.setContextStack(ThreadContext.getDepth() == 0 ? ThreadContext.EMPTY_STACK : ThreadContext.cloneStack());
        result.setTimeMillis(message instanceof TimestampMessage ? ((TimestampMessage)((Object)message)).getTimestamp() : CLOCK.currentTimeMillis());
        result.setNanoTime(Log4jLogEvent.getNanoClock().nanoTime());
        if (THREAD_NAME_CACHING_STRATEGY == ThreadNameCachingStrategy.UNCACHED) {
            result.setThreadName(Thread.currentThread().getName());
            result.setThreadPriority(Thread.currentThread().getPriority());
        }
        return result;
    }

    public static void release(LogEvent logEvent) {
        if (logEvent instanceof MutableLogEvent) {
            ((MutableLogEvent)logEvent).reserved = false;
        }
    }
}

