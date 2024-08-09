/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventTranslatorVararg
 *  com.lmax.disruptor.dsl.Disruptor
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerDisruptor;
import org.apache.logging.log4j.core.async.EventRoute;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;
import org.apache.logging.log4j.core.async.RingBufferLogEventTranslator;
import org.apache.logging.log4j.core.async.ThreadNameCachingStrategy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.ClockFactory;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.NanoClock;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;

public class AsyncLogger
extends Logger
implements EventTranslatorVararg<RingBufferLogEvent> {
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    private static final Clock CLOCK = ClockFactory.getClock();
    private static final ContextDataInjector CONTEXT_DATA_INJECTOR = ContextDataInjectorFactory.createInjector();
    private static final ThreadNameCachingStrategy THREAD_NAME_CACHING_STRATEGY = ThreadNameCachingStrategy.create();
    private final ThreadLocal<RingBufferLogEventTranslator> threadLocalTranslator = new ThreadLocal();
    private final AsyncLoggerDisruptor loggerDisruptor;
    private volatile boolean includeLocation;
    private volatile NanoClock nanoClock;

    public AsyncLogger(LoggerContext loggerContext, String string, MessageFactory messageFactory, AsyncLoggerDisruptor asyncLoggerDisruptor) {
        super(loggerContext, string, messageFactory);
        this.loggerDisruptor = asyncLoggerDisruptor;
        this.includeLocation = this.privateConfig.loggerConfig.isIncludeLocation();
        this.nanoClock = loggerContext.getConfiguration().getNanoClock();
    }

    @Override
    protected void updateConfiguration(Configuration configuration) {
        this.nanoClock = configuration.getNanoClock();
        this.includeLocation = configuration.getLoggerConfig(this.name).isIncludeLocation();
        super.updateConfiguration(configuration);
    }

    NanoClock getNanoClock() {
        return this.nanoClock;
    }

    private RingBufferLogEventTranslator getCachedTranslator() {
        RingBufferLogEventTranslator ringBufferLogEventTranslator = this.threadLocalTranslator.get();
        if (ringBufferLogEventTranslator == null) {
            ringBufferLogEventTranslator = new RingBufferLogEventTranslator();
            this.threadLocalTranslator.set(ringBufferLogEventTranslator);
        }
        return ringBufferLogEventTranslator;
    }

    @Override
    public void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable) {
        if (this.loggerDisruptor.isUseThreadLocals()) {
            this.logWithThreadLocalTranslator(string, level, marker, message, throwable);
        } else {
            this.logWithVarargTranslator(string, level, marker, message, throwable);
        }
    }

    private boolean isReused(Message message) {
        return message instanceof ReusableMessage;
    }

    private void logWithThreadLocalTranslator(String string, Level level, Marker marker, Message message, Throwable throwable) {
        RingBufferLogEventTranslator ringBufferLogEventTranslator = this.getCachedTranslator();
        this.initTranslator(ringBufferLogEventTranslator, string, level, marker, message, throwable);
        this.initTranslatorThreadValues(ringBufferLogEventTranslator);
        this.publish(ringBufferLogEventTranslator);
    }

    private void publish(RingBufferLogEventTranslator ringBufferLogEventTranslator) {
        if (!this.loggerDisruptor.tryPublish(ringBufferLogEventTranslator)) {
            this.handleRingBufferFull(ringBufferLogEventTranslator);
        }
    }

    private void handleRingBufferFull(RingBufferLogEventTranslator ringBufferLogEventTranslator) {
        EventRoute eventRoute = this.loggerDisruptor.getEventRoute(ringBufferLogEventTranslator.level);
        switch (1.$SwitchMap$org$apache$logging$log4j$core$async$EventRoute[eventRoute.ordinal()]) {
            case 1: {
                this.loggerDisruptor.enqueueLogMessageInfo(ringBufferLogEventTranslator);
                break;
            }
            case 2: {
                this.logMessageInCurrentThread(ringBufferLogEventTranslator.fqcn, ringBufferLogEventTranslator.level, ringBufferLogEventTranslator.marker, ringBufferLogEventTranslator.message, ringBufferLogEventTranslator.thrown);
                break;
            }
            case 3: {
                break;
            }
            default: {
                throw new IllegalStateException("Unknown EventRoute " + (Object)((Object)eventRoute));
            }
        }
    }

    private void initTranslator(RingBufferLogEventTranslator ringBufferLogEventTranslator, String string, Level level, Marker marker, Message message, Throwable throwable) {
        ringBufferLogEventTranslator.setBasicValues(this, this.name, marker, string, level, message, throwable, ThreadContext.getImmutableStack(), this.calcLocationIfRequested(string), CLOCK.currentTimeMillis(), this.nanoClock.nanoTime());
    }

    private void initTranslatorThreadValues(RingBufferLogEventTranslator ringBufferLogEventTranslator) {
        if (THREAD_NAME_CACHING_STRATEGY == ThreadNameCachingStrategy.UNCACHED) {
            ringBufferLogEventTranslator.updateThreadValues();
        }
    }

    private StackTraceElement calcLocationIfRequested(String string) {
        return this.includeLocation ? Log4jLogEvent.calcLocation(string) : null;
    }

    private void logWithVarargTranslator(String string, Level level, Marker marker, Message message, Throwable throwable) {
        Disruptor<RingBufferLogEvent> disruptor = this.loggerDisruptor.getDisruptor();
        if (disruptor == null) {
            LOGGER.error("Ignoring log event after Log4j has been shut down.");
            return;
        }
        if (!this.canFormatMessageInBackground(message) && !this.isReused(message)) {
            message.getFormattedMessage();
        }
        disruptor.getRingBuffer().publishEvent((EventTranslatorVararg)this, new Object[]{this, this.calcLocationIfRequested(string), string, level, marker, message, throwable});
    }

    private boolean canFormatMessageInBackground(Message message) {
        return Constants.FORMAT_MESSAGES_IN_BACKGROUND || message.getClass().isAnnotationPresent(AsynchronouslyFormattable.class);
    }

    public void translateTo(RingBufferLogEvent ringBufferLogEvent, long l, Object ... objectArray) {
        AsyncLogger asyncLogger = (AsyncLogger)objectArray[0];
        StackTraceElement stackTraceElement = (StackTraceElement)objectArray[5];
        String string = (String)objectArray[5];
        Level level = (Level)objectArray[5];
        Marker marker = (Marker)objectArray[5];
        Message message = (Message)objectArray[5];
        Throwable throwable = (Throwable)objectArray[5];
        ThreadContext.ContextStack contextStack = ThreadContext.getImmutableStack();
        Thread thread2 = Thread.currentThread();
        String string2 = THREAD_NAME_CACHING_STRATEGY.getThreadName();
        ringBufferLogEvent.setValues(asyncLogger, asyncLogger.getName(), marker, string, level, message, throwable, CONTEXT_DATA_INJECTOR.injectContextData(null, (StringMap)ringBufferLogEvent.getContextData()), contextStack, thread2.getId(), string2, thread2.getPriority(), stackTraceElement, CLOCK.currentTimeMillis(), this.nanoClock.nanoTime());
    }

    void logMessageInCurrentThread(String string, Level level, Marker marker, Message message, Throwable throwable) {
        ReliabilityStrategy reliabilityStrategy = this.privateConfig.loggerConfig.getReliabilityStrategy();
        reliabilityStrategy.log(this, this.getName(), string, marker, level, message, throwable);
    }

    public void actualAsyncLog(RingBufferLogEvent ringBufferLogEvent) {
        Object object;
        List<Property> list = this.privateConfig.loggerConfig.getPropertyList();
        if (list != null) {
            object = (StringMap)ringBufferLogEvent.getContextData();
            if (object.isFrozen()) {
                StringMap stringMap = ContextDataFactory.createContextData();
                stringMap.putAll((ReadOnlyStringMap)object);
                object = stringMap;
            }
            for (int i = 0; i < list.size(); ++i) {
                Property property = list.get(i);
                if (object.getValue(property.getName()) != null) continue;
                String string = property.isValueNeedsLookup() ? this.privateConfig.config.getStrSubstitutor().replace((LogEvent)ringBufferLogEvent, property.getValue()) : property.getValue();
                object.putValue(property.getName(), string);
            }
            ringBufferLogEvent.setContextData((StringMap)object);
        }
        object = this.privateConfig.loggerConfig.getReliabilityStrategy();
        object.log(this, ringBufferLogEvent);
    }

    public void translateTo(Object object, long l, Object[] objectArray) {
        this.translateTo((RingBufferLogEvent)object, l, objectArray);
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$logging$log4j$core$async$EventRoute = new int[EventRoute.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$async$EventRoute[EventRoute.ENQUEUE.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$async$EventRoute[EventRoute.SYNCHRONOUS.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$async$EventRoute[EventRoute.DISCARD.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

