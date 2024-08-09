/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 *  com.lmax.disruptor.EventHandler
 *  com.lmax.disruptor.EventTranslatorTwoArg
 *  com.lmax.disruptor.ExceptionHandler
 *  com.lmax.disruptor.RingBuffer
 *  com.lmax.disruptor.Sequence
 *  com.lmax.disruptor.SequenceReportingEventHandler
 *  com.lmax.disruptor.WaitStrategy
 *  com.lmax.disruptor.dsl.Disruptor
 *  com.lmax.disruptor.dsl.ProducerType
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDelegate;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicyFactory;
import org.apache.logging.log4j.core.async.DiscardingAsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.DisruptorUtil;
import org.apache.logging.log4j.core.async.EventRoute;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.impl.ReusableLogEventFactory;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;
import org.apache.logging.log4j.message.ReusableMessage;

public class AsyncLoggerConfigDisruptor
extends AbstractLifeCycle
implements AsyncLoggerConfigDelegate {
    private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 200;
    private static final int SLEEP_MILLIS_BETWEEN_DRAIN_ATTEMPTS = 50;
    private static final EventFactory<Log4jEventWrapper> FACTORY = new EventFactory<Log4jEventWrapper>(){

        public Log4jEventWrapper newInstance() {
            return new Log4jEventWrapper();
        }

        public Object newInstance() {
            return this.newInstance();
        }
    };
    private static final EventFactory<Log4jEventWrapper> MUTABLE_FACTORY = new EventFactory<Log4jEventWrapper>(){

        public Log4jEventWrapper newInstance() {
            return new Log4jEventWrapper(new MutableLogEvent());
        }

        public Object newInstance() {
            return this.newInstance();
        }
    };
    private static final EventTranslatorTwoArg<Log4jEventWrapper, LogEvent, AsyncLoggerConfig> TRANSLATOR = new EventTranslatorTwoArg<Log4jEventWrapper, LogEvent, AsyncLoggerConfig>(){

        public void translateTo(Log4jEventWrapper log4jEventWrapper, long l, LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig) {
            Log4jEventWrapper.access$002(log4jEventWrapper, logEvent);
            Log4jEventWrapper.access$102(log4jEventWrapper, asyncLoggerConfig);
        }

        public void translateTo(Object object, long l, Object object2, Object object3) {
            this.translateTo((Log4jEventWrapper)object, l, (LogEvent)object2, (AsyncLoggerConfig)object3);
        }
    };
    private static final EventTranslatorTwoArg<Log4jEventWrapper, LogEvent, AsyncLoggerConfig> MUTABLE_TRANSLATOR = new EventTranslatorTwoArg<Log4jEventWrapper, LogEvent, AsyncLoggerConfig>(){

        public void translateTo(Log4jEventWrapper log4jEventWrapper, long l, LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig) {
            ((MutableLogEvent)Log4jEventWrapper.access$000(log4jEventWrapper)).initFrom(logEvent);
            Log4jEventWrapper.access$102(log4jEventWrapper, asyncLoggerConfig);
        }

        public void translateTo(Object object, long l, Object object2, Object object3) {
            this.translateTo((Log4jEventWrapper)object, l, (LogEvent)object2, (AsyncLoggerConfig)object3);
        }
    };
    private static final ThreadFactory THREAD_FACTORY = Log4jThreadFactory.createDaemonThreadFactory("AsyncLoggerConfig");
    private int ringBufferSize;
    private AsyncQueueFullPolicy asyncQueueFullPolicy;
    private Boolean mutable = Boolean.FALSE;
    private volatile Disruptor<Log4jEventWrapper> disruptor;
    private ExecutorService executor;
    private long backgroundThreadId;
    private EventFactory<Log4jEventWrapper> factory;
    private EventTranslatorTwoArg<Log4jEventWrapper, LogEvent, AsyncLoggerConfig> translator;

    @Override
    public void setLogEventFactory(LogEventFactory logEventFactory) {
        this.mutable = this.mutable != false || logEventFactory instanceof ReusableLogEventFactory;
    }

    @Override
    public synchronized void start() {
        if (this.disruptor != null) {
            LOGGER.trace("AsyncLoggerConfigDisruptor not starting new disruptor for this configuration, using existing object.");
            return;
        }
        LOGGER.trace("AsyncLoggerConfigDisruptor creating new disruptor for this configuration.");
        this.ringBufferSize = DisruptorUtil.calculateRingBufferSize("AsyncLoggerConfig.RingBufferSize");
        WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy("AsyncLoggerConfig.WaitStrategy");
        this.executor = Executors.newSingleThreadExecutor(THREAD_FACTORY);
        this.backgroundThreadId = DisruptorUtil.getExecutorThreadId(this.executor);
        this.asyncQueueFullPolicy = AsyncQueueFullPolicyFactory.create();
        this.translator = this.mutable != false ? MUTABLE_TRANSLATOR : TRANSLATOR;
        this.factory = this.mutable != false ? MUTABLE_FACTORY : FACTORY;
        this.disruptor = new Disruptor(this.factory, this.ringBufferSize, (Executor)this.executor, ProducerType.MULTI, waitStrategy);
        ExceptionHandler<Log4jEventWrapper> exceptionHandler = DisruptorUtil.getAsyncLoggerConfigExceptionHandler();
        this.disruptor.handleExceptionsWith(exceptionHandler);
        Log4jEventWrapperHandler[] log4jEventWrapperHandlerArray = new Log4jEventWrapperHandler[]{new Log4jEventWrapperHandler(null)};
        this.disruptor.handleEventsWith((EventHandler[])log4jEventWrapperHandlerArray);
        LOGGER.debug("Starting AsyncLoggerConfig disruptor for this configuration with ringbufferSize={}, waitStrategy={}, exceptionHandler={}...", (Object)this.disruptor.getRingBuffer().getBufferSize(), (Object)waitStrategy.getClass().getSimpleName(), (Object)exceptionHandler);
        this.disruptor.start();
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        Disruptor<Log4jEventWrapper> disruptor = this.disruptor;
        if (disruptor == null) {
            LOGGER.trace("AsyncLoggerConfigDisruptor: disruptor for this configuration already shut down.");
            return false;
        }
        this.setStopping();
        LOGGER.trace("AsyncLoggerConfigDisruptor: shutting down disruptor for this configuration.");
        this.disruptor = null;
        for (int i = 0; AsyncLoggerConfigDisruptor.hasBacklog(disruptor) && i < 200; ++i) {
            try {
                Thread.sleep(50L);
                continue;
            } catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        disruptor.shutdown();
        LOGGER.trace("AsyncLoggerConfigDisruptor: shutting down disruptor executor for this configuration.");
        ExecutorServices.shutdown(this.executor, l, timeUnit, this.toString());
        this.executor = null;
        if (DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy) > 0L) {
            LOGGER.trace("AsyncLoggerConfigDisruptor: {} discarded {} events.", (Object)this.asyncQueueFullPolicy, (Object)DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy));
        }
        this.setStopped();
        return false;
    }

    private static boolean hasBacklog(Disruptor<?> disruptor) {
        RingBuffer ringBuffer = disruptor.getRingBuffer();
        return !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize());
    }

    @Override
    public EventRoute getEventRoute(Level level) {
        int n = this.remainingDisruptorCapacity();
        if (n < 0) {
            return EventRoute.DISCARD;
        }
        return this.asyncQueueFullPolicy.getRoute(this.backgroundThreadId, level);
    }

    private int remainingDisruptorCapacity() {
        Disruptor<Log4jEventWrapper> disruptor = this.disruptor;
        if (this.hasLog4jBeenShutDown(disruptor)) {
            return 1;
        }
        return (int)disruptor.getRingBuffer().remainingCapacity();
    }

    private boolean hasLog4jBeenShutDown(Disruptor<Log4jEventWrapper> disruptor) {
        if (disruptor == null) {
            LOGGER.warn("Ignoring log event after log4j was shut down");
            return false;
        }
        return true;
    }

    @Override
    public void enqueueEvent(LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig) {
        try {
            LogEvent logEvent2 = this.prepareEvent(logEvent);
            this.enqueue(logEvent2, asyncLoggerConfig);
        } catch (NullPointerException nullPointerException) {
            LOGGER.warn("Ignoring log event after log4j was shut down.");
        }
    }

    private LogEvent prepareEvent(LogEvent logEvent) {
        LogEvent logEvent2 = this.ensureImmutable(logEvent);
        if (logEvent2 instanceof Log4jLogEvent && logEvent2.getMessage() instanceof ReusableMessage) {
            ((Log4jLogEvent)logEvent2).makeMessageImmutable();
        }
        return logEvent2;
    }

    private void enqueue(LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig) {
        this.disruptor.getRingBuffer().publishEvent(this.translator, (Object)logEvent, (Object)asyncLoggerConfig);
    }

    @Override
    public boolean tryEnqueue(LogEvent logEvent, AsyncLoggerConfig asyncLoggerConfig) {
        LogEvent logEvent2 = this.prepareEvent(logEvent);
        return this.disruptor.getRingBuffer().tryPublishEvent(this.translator, (Object)logEvent2, (Object)asyncLoggerConfig);
    }

    private LogEvent ensureImmutable(LogEvent logEvent) {
        LogEvent logEvent2 = logEvent;
        if (logEvent instanceof RingBufferLogEvent) {
            logEvent2 = ((RingBufferLogEvent)logEvent).createMemento();
        }
        return logEvent2;
    }

    @Override
    public RingBufferAdmin createRingBufferAdmin(String string, String string2) {
        return RingBufferAdmin.forAsyncLoggerConfig(this.disruptor.getRingBuffer(), string, string2);
    }

    private static class Log4jEventWrapperHandler
    implements SequenceReportingEventHandler<Log4jEventWrapper> {
        private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
        private Sequence sequenceCallback;
        private int counter;

        private Log4jEventWrapperHandler() {
        }

        public void setSequenceCallback(Sequence sequence) {
            this.sequenceCallback = sequence;
        }

        public void onEvent(Log4jEventWrapper log4jEventWrapper, long l, boolean bl) throws Exception {
            Log4jEventWrapper.access$000(log4jEventWrapper).setEndOfBatch(bl);
            Log4jEventWrapper.access$100(log4jEventWrapper).asyncCallAppenders(Log4jEventWrapper.access$000(log4jEventWrapper));
            log4jEventWrapper.clear();
            this.notifyIntermediateProgress(l);
        }

        private void notifyIntermediateProgress(long l) {
            if (++this.counter > 50) {
                this.sequenceCallback.set(l);
                this.counter = 0;
            }
        }

        public void onEvent(Object object, long l, boolean bl) throws Exception {
            this.onEvent((Log4jEventWrapper)object, l, bl);
        }

        Log4jEventWrapperHandler(1 var1_1) {
            this();
        }
    }

    public static class Log4jEventWrapper {
        private AsyncLoggerConfig loggerConfig;
        private LogEvent event;

        public Log4jEventWrapper() {
        }

        public Log4jEventWrapper(MutableLogEvent mutableLogEvent) {
            this.event = mutableLogEvent;
        }

        public void clear() {
            this.loggerConfig = null;
            if (this.event instanceof MutableLogEvent) {
                ((MutableLogEvent)this.event).clear();
            } else {
                this.event = null;
            }
        }

        public String toString() {
            return String.valueOf(this.event);
        }

        static LogEvent access$000(Log4jEventWrapper log4jEventWrapper) {
            return log4jEventWrapper.event;
        }

        static AsyncLoggerConfig access$100(Log4jEventWrapper log4jEventWrapper) {
            return log4jEventWrapper.loggerConfig;
        }

        static LogEvent access$002(Log4jEventWrapper log4jEventWrapper, LogEvent logEvent) {
            log4jEventWrapper.event = logEvent;
            return log4jEventWrapper.event;
        }

        static AsyncLoggerConfig access$102(Log4jEventWrapper log4jEventWrapper, AsyncLoggerConfig asyncLoggerConfig) {
            log4jEventWrapper.loggerConfig = asyncLoggerConfig;
            return log4jEventWrapper.loggerConfig;
        }
    }
}

