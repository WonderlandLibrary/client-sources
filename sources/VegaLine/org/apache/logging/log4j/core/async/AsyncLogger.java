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

    public AsyncLogger(LoggerContext context, String name, MessageFactory messageFactory, AsyncLoggerDisruptor loggerDisruptor) {
        super(context, name, messageFactory);
        this.loggerDisruptor = loggerDisruptor;
        this.includeLocation = this.privateConfig.loggerConfig.isIncludeLocation();
        this.nanoClock = context.getConfiguration().getNanoClock();
    }

    @Override
    protected void updateConfiguration(Configuration newConfig) {
        this.nanoClock = newConfig.getNanoClock();
        this.includeLocation = newConfig.getLoggerConfig(this.name).isIncludeLocation();
        super.updateConfiguration(newConfig);
    }

    NanoClock getNanoClock() {
        return this.nanoClock;
    }

    private RingBufferLogEventTranslator getCachedTranslator() {
        RingBufferLogEventTranslator result = this.threadLocalTranslator.get();
        if (result == null) {
            result = new RingBufferLogEventTranslator();
            this.threadLocalTranslator.set(result);
        }
        return result;
    }

    @Override
    public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
        if (this.loggerDisruptor.isUseThreadLocals()) {
            this.logWithThreadLocalTranslator(fqcn, level, marker, message, thrown);
        } else {
            this.logWithVarargTranslator(fqcn, level, marker, message, thrown);
        }
    }

    private boolean isReused(Message message) {
        return message instanceof ReusableMessage;
    }

    private void logWithThreadLocalTranslator(String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
        RingBufferLogEventTranslator translator = this.getCachedTranslator();
        this.initTranslator(translator, fqcn, level, marker, message, thrown);
        this.initTranslatorThreadValues(translator);
        this.publish(translator);
    }

    private void publish(RingBufferLogEventTranslator translator) {
        if (!this.loggerDisruptor.tryPublish(translator)) {
            this.handleRingBufferFull(translator);
        }
    }

    private void handleRingBufferFull(RingBufferLogEventTranslator translator) {
        EventRoute eventRoute = this.loggerDisruptor.getEventRoute(translator.level);
        switch (eventRoute) {
            case ENQUEUE: {
                this.loggerDisruptor.enqueueLogMessageInfo(translator);
                break;
            }
            case SYNCHRONOUS: {
                this.logMessageInCurrentThread(translator.fqcn, translator.level, translator.marker, translator.message, translator.thrown);
                break;
            }
            case DISCARD: {
                break;
            }
            default: {
                throw new IllegalStateException("Unknown EventRoute " + (Object)((Object)eventRoute));
            }
        }
    }

    private void initTranslator(RingBufferLogEventTranslator translator, String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
        translator.setBasicValues(this, this.name, marker, fqcn, level, message, thrown, ThreadContext.getImmutableStack(), this.calcLocationIfRequested(fqcn), CLOCK.currentTimeMillis(), this.nanoClock.nanoTime());
    }

    private void initTranslatorThreadValues(RingBufferLogEventTranslator translator) {
        if (THREAD_NAME_CACHING_STRATEGY == ThreadNameCachingStrategy.UNCACHED) {
            translator.updateThreadValues();
        }
    }

    private StackTraceElement calcLocationIfRequested(String fqcn) {
        return this.includeLocation ? Log4jLogEvent.calcLocation(fqcn) : null;
    }

    private void logWithVarargTranslator(String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
        Disruptor<RingBufferLogEvent> disruptor = this.loggerDisruptor.getDisruptor();
        if (disruptor == null) {
            LOGGER.error("Ignoring log event after Log4j has been shut down.");
            return;
        }
        if (!this.canFormatMessageInBackground(message) && !this.isReused(message)) {
            message.getFormattedMessage();
        }
        disruptor.getRingBuffer().publishEvent((EventTranslatorVararg)this, new Object[]{this, this.calcLocationIfRequested(fqcn), fqcn, level, marker, message, thrown});
    }

    private boolean canFormatMessageInBackground(Message message) {
        return Constants.FORMAT_MESSAGES_IN_BACKGROUND || message.getClass().isAnnotationPresent(AsynchronouslyFormattable.class);
    }

    public void translateTo(RingBufferLogEvent event, long sequence, Object ... args) {
        AsyncLogger asyncLogger = (AsyncLogger)args[0];
        StackTraceElement location = (StackTraceElement)args[1];
        String fqcn = (String)args[2];
        Level level = (Level)args[3];
        Marker marker = (Marker)args[4];
        Message message = (Message)args[5];
        Throwable thrown = (Throwable)args[6];
        ThreadContext.ContextStack contextStack = ThreadContext.getImmutableStack();
        Thread currentThread = Thread.currentThread();
        String threadName = THREAD_NAME_CACHING_STRATEGY.getThreadName();
        event.setValues(asyncLogger, asyncLogger.getName(), marker, fqcn, level, message, thrown, CONTEXT_DATA_INJECTOR.injectContextData(null, (StringMap)event.getContextData()), contextStack, currentThread.getId(), threadName, currentThread.getPriority(), location, CLOCK.currentTimeMillis(), this.nanoClock.nanoTime());
    }

    void logMessageInCurrentThread(String fqcn, Level level, Marker marker, Message message, Throwable thrown) {
        ReliabilityStrategy strategy = this.privateConfig.loggerConfig.getReliabilityStrategy();
        strategy.log(this, this.getName(), fqcn, marker, level, message, thrown);
    }

    public void actualAsyncLog(RingBufferLogEvent event) {
        List<Property> properties = this.privateConfig.loggerConfig.getPropertyList();
        if (properties != null) {
            StringMap contextData = (StringMap)event.getContextData();
            if (contextData.isFrozen()) {
                StringMap temp = ContextDataFactory.createContextData();
                temp.putAll(contextData);
                contextData = temp;
            }
            for (int i = 0; i < properties.size(); ++i) {
                Property prop = properties.get(i);
                if (contextData.getValue(prop.getName()) != null) continue;
                String value = prop.isValueNeedsLookup() ? this.privateConfig.config.getStrSubstitutor().replace((LogEvent)event, prop.getValue()) : prop.getValue();
                contextData.putValue(prop.getName(), value);
            }
            event.setContextData(contextData);
        }
        ReliabilityStrategy strategy = this.privateConfig.loggerConfig.getReliabilityStrategy();
        strategy.log(this, event);
    }
}

