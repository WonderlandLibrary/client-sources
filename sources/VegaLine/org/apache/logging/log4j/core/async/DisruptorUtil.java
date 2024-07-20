/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.BlockingWaitStrategy
 *  com.lmax.disruptor.BusySpinWaitStrategy
 *  com.lmax.disruptor.ExceptionHandler
 *  com.lmax.disruptor.SleepingWaitStrategy
 *  com.lmax.disruptor.TimeoutBlockingWaitStrategy
 *  com.lmax.disruptor.WaitStrategy
 *  com.lmax.disruptor.YieldingWaitStrategy
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDefaultExceptionHandler;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDisruptor;
import org.apache.logging.log4j.core.async.AsyncLoggerDefaultExceptionHandler;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.Integers;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

final class DisruptorUtil {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final int RINGBUFFER_MIN_SIZE = 128;
    private static final int RINGBUFFER_DEFAULT_SIZE = 262144;
    private static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4096;

    private DisruptorUtil() {
    }

    static long getTimeout(String propertyName, long defaultTimeout) {
        return PropertiesUtil.getProperties().getLongProperty(propertyName, defaultTimeout);
    }

    static WaitStrategy createWaitStrategy(String propertyName) {
        String key = propertyName.startsWith("AsyncLogger.") ? "AsyncLogger.Timeout" : "AsyncLoggerConfig.Timeout";
        long timeoutMillis = DisruptorUtil.getTimeout(key, 10L);
        return DisruptorUtil.createWaitStrategy(propertyName, timeoutMillis);
    }

    static WaitStrategy createWaitStrategy(String propertyName, long timeoutMillis) {
        String strategyUp;
        String strategy = PropertiesUtil.getProperties().getStringProperty(propertyName, "TIMEOUT");
        LOGGER.trace("property {}={}", (Object)propertyName, (Object)strategy);
        switch (strategyUp = strategy.toUpperCase(Locale.ROOT)) {
            case "SLEEP": {
                return new SleepingWaitStrategy();
            }
            case "YIELD": {
                return new YieldingWaitStrategy();
            }
            case "BLOCK": {
                return new BlockingWaitStrategy();
            }
            case "BUSYSPIN": {
                return new BusySpinWaitStrategy();
            }
            case "TIMEOUT": {
                return new TimeoutBlockingWaitStrategy(timeoutMillis, TimeUnit.MILLISECONDS);
            }
        }
        return new TimeoutBlockingWaitStrategy(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    static int calculateRingBufferSize(String propertyName) {
        int ringBufferSize = Constants.ENABLE_THREADLOCALS ? 4096 : 262144;
        String userPreferredRBSize = PropertiesUtil.getProperties().getStringProperty(propertyName, String.valueOf(ringBufferSize));
        try {
            int size = Integer.parseInt(userPreferredRBSize);
            if (size < 128) {
                size = 128;
                LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", (Object)userPreferredRBSize, (Object)128);
            }
            ringBufferSize = size;
        } catch (Exception ex) {
            LOGGER.warn("Invalid RingBufferSize {}, using default size {}.", (Object)userPreferredRBSize, (Object)ringBufferSize);
        }
        return Integers.ceilingNextPowerOfTwo(ringBufferSize);
    }

    static ExceptionHandler<RingBufferLogEvent> getAsyncLoggerExceptionHandler() {
        String cls = PropertiesUtil.getProperties().getStringProperty("AsyncLogger.ExceptionHandler");
        if (cls == null) {
            return new AsyncLoggerDefaultExceptionHandler();
        }
        try {
            Class<?> klass = LoaderUtil.loadClass(cls);
            return (ExceptionHandler)klass.newInstance();
        } catch (Exception ignored) {
            LOGGER.debug("Invalid AsyncLogger.ExceptionHandler value: error creating {}: ", (Object)cls, (Object)ignored);
            return new AsyncLoggerDefaultExceptionHandler();
        }
    }

    static ExceptionHandler<AsyncLoggerConfigDisruptor.Log4jEventWrapper> getAsyncLoggerConfigExceptionHandler() {
        String cls = PropertiesUtil.getProperties().getStringProperty("AsyncLoggerConfig.ExceptionHandler");
        if (cls == null) {
            return new AsyncLoggerConfigDefaultExceptionHandler();
        }
        try {
            Class<?> klass = LoaderUtil.loadClass(cls);
            return (ExceptionHandler)klass.newInstance();
        } catch (Exception ignored) {
            LOGGER.debug("Invalid AsyncLoggerConfig.ExceptionHandler value: error creating {}: ", (Object)cls, (Object)ignored);
            return new AsyncLoggerConfigDefaultExceptionHandler();
        }
    }

    public static long getExecutorThreadId(ExecutorService executor) {
        Future<Long> result = executor.submit(new Callable<Long>(){

            @Override
            public Long call() {
                return Thread.currentThread().getId();
            }
        });
        try {
            return result.get();
        } catch (Exception ex) {
            String msg = "Could not obtain executor thread Id. Giving up to avoid the risk of application deadlock.";
            throw new IllegalStateException("Could not obtain executor thread Id. Giving up to avoid the risk of application deadlock.", ex);
        }
    }
}

