/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 *  com.lmax.disruptor.EventHandler
 *  com.lmax.disruptor.EventTranslator
 *  com.lmax.disruptor.ExceptionHandler
 *  com.lmax.disruptor.RingBuffer
 *  com.lmax.disruptor.TimeoutException
 *  com.lmax.disruptor.WaitStrategy
 *  com.lmax.disruptor.dsl.Disruptor
 *  com.lmax.disruptor.dsl.ProducerType
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicyFactory;
import org.apache.logging.log4j.core.async.DiscardingAsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.DisruptorUtil;
import org.apache.logging.log4j.core.async.EventRoute;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;
import org.apache.logging.log4j.core.async.RingBufferLogEventHandler;
import org.apache.logging.log4j.core.async.RingBufferLogEventTranslator;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;

class AsyncLoggerDisruptor
extends AbstractLifeCycle {
    private static final int SLEEP_MILLIS_BETWEEN_DRAIN_ATTEMPTS = 50;
    private static final int MAX_DRAIN_ATTEMPTS_BEFORE_SHUTDOWN = 200;
    private volatile Disruptor<RingBufferLogEvent> disruptor;
    private ExecutorService executor;
    private String contextName;
    private boolean useThreadLocalTranslator = true;
    private long backgroundThreadId;
    private AsyncQueueFullPolicy asyncQueueFullPolicy;
    private int ringBufferSize;

    AsyncLoggerDisruptor(String string) {
        this.contextName = string;
    }

    public String getContextName() {
        return this.contextName;
    }

    public void setContextName(String string) {
        this.contextName = string;
    }

    Disruptor<RingBufferLogEvent> getDisruptor() {
        return this.disruptor;
    }

    @Override
    public synchronized void start() {
        if (this.disruptor != null) {
            LOGGER.trace("[{}] AsyncLoggerDisruptor not starting new disruptor for this context, using existing object.", (Object)this.contextName);
            return;
        }
        LOGGER.trace("[{}] AsyncLoggerDisruptor creating new disruptor for this context.", (Object)this.contextName);
        this.ringBufferSize = DisruptorUtil.calculateRingBufferSize("AsyncLogger.RingBufferSize");
        WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy("AsyncLogger.WaitStrategy");
        this.executor = Executors.newSingleThreadExecutor(Log4jThreadFactory.createDaemonThreadFactory("AsyncLogger[" + this.contextName + "]"));
        this.backgroundThreadId = DisruptorUtil.getExecutorThreadId(this.executor);
        this.asyncQueueFullPolicy = AsyncQueueFullPolicyFactory.create();
        this.disruptor = new Disruptor((EventFactory)RingBufferLogEvent.FACTORY, this.ringBufferSize, (Executor)this.executor, ProducerType.MULTI, waitStrategy);
        ExceptionHandler<RingBufferLogEvent> exceptionHandler = DisruptorUtil.getAsyncLoggerExceptionHandler();
        this.disruptor.handleExceptionsWith(exceptionHandler);
        RingBufferLogEventHandler[] ringBufferLogEventHandlerArray = new RingBufferLogEventHandler[]{new RingBufferLogEventHandler()};
        this.disruptor.handleEventsWith((EventHandler[])ringBufferLogEventHandlerArray);
        LOGGER.debug("[{}] Starting AsyncLogger disruptor for this context with ringbufferSize={}, waitStrategy={}, exceptionHandler={}...", (Object)this.contextName, (Object)this.disruptor.getRingBuffer().getBufferSize(), (Object)waitStrategy.getClass().getSimpleName(), (Object)exceptionHandler);
        this.disruptor.start();
        LOGGER.trace("[{}] AsyncLoggers use a {} translator", (Object)this.contextName, (Object)(this.useThreadLocalTranslator ? "threadlocal" : "vararg"));
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        Disruptor<RingBufferLogEvent> disruptor = this.getDisruptor();
        if (disruptor == null) {
            LOGGER.trace("[{}] AsyncLoggerDisruptor: disruptor for this context already shut down.", (Object)this.contextName);
            return false;
        }
        this.setStopping();
        LOGGER.debug("[{}] AsyncLoggerDisruptor: shutting down disruptor for this context.", (Object)this.contextName);
        this.disruptor = null;
        for (int i = 0; AsyncLoggerDisruptor.hasBacklog(disruptor) && i < 200; ++i) {
            try {
                Thread.sleep(50L);
                continue;
            } catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        try {
            disruptor.shutdown(l, timeUnit);
        } catch (TimeoutException timeoutException) {
            disruptor.shutdown();
        }
        LOGGER.trace("[{}] AsyncLoggerDisruptor: shutting down disruptor executor.", (Object)this.contextName);
        ExecutorServices.shutdown(this.executor, l, timeUnit, this.toString());
        this.executor = null;
        if (DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy) > 0L) {
            LOGGER.trace("AsyncLoggerDisruptor: {} discarded {} events.", (Object)this.asyncQueueFullPolicy, (Object)DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy));
        }
        this.setStopped();
        return false;
    }

    private static boolean hasBacklog(Disruptor<?> disruptor) {
        RingBuffer ringBuffer = disruptor.getRingBuffer();
        return !ringBuffer.hasAvailableCapacity(ringBuffer.getBufferSize());
    }

    public RingBufferAdmin createRingBufferAdmin(String string) {
        RingBuffer ringBuffer = this.disruptor == null ? null : this.disruptor.getRingBuffer();
        return RingBufferAdmin.forAsyncLogger(ringBuffer, string);
    }

    EventRoute getEventRoute(Level level) {
        int n = this.remainingDisruptorCapacity();
        if (n < 0) {
            return EventRoute.DISCARD;
        }
        return this.asyncQueueFullPolicy.getRoute(this.backgroundThreadId, level);
    }

    private int remainingDisruptorCapacity() {
        Disruptor<RingBufferLogEvent> disruptor = this.disruptor;
        if (this.hasLog4jBeenShutDown(disruptor)) {
            return 1;
        }
        return (int)disruptor.getRingBuffer().remainingCapacity();
    }

    private boolean hasLog4jBeenShutDown(Disruptor<RingBufferLogEvent> disruptor) {
        if (disruptor == null) {
            LOGGER.warn("Ignoring log event after log4j was shut down");
            return false;
        }
        return true;
    }

    public boolean tryPublish(RingBufferLogEventTranslator ringBufferLogEventTranslator) {
        try {
            return this.disruptor.getRingBuffer().tryPublishEvent((EventTranslator)ringBufferLogEventTranslator);
        } catch (NullPointerException nullPointerException) {
            LOGGER.warn("[{}] Ignoring log event after log4j was shut down.", (Object)this.contextName);
            return true;
        }
    }

    void enqueueLogMessageInfo(RingBufferLogEventTranslator ringBufferLogEventTranslator) {
        try {
            this.disruptor.publishEvent((EventTranslator)ringBufferLogEventTranslator);
        } catch (NullPointerException nullPointerException) {
            LOGGER.warn("[{}] Ignoring log event after log4j was shut down.", (Object)this.contextName);
        }
    }

    public boolean isUseThreadLocals() {
        return this.useThreadLocalTranslator;
    }

    public void setUseThreadLocals(boolean bl) {
        this.useThreadLocalTranslator = bl;
        LOGGER.trace("[{}] AsyncLoggers have been modified to use a {} translator", (Object)this.contextName, (Object)(this.useThreadLocalTranslator ? "threadlocal" : "vararg"));
    }
}

