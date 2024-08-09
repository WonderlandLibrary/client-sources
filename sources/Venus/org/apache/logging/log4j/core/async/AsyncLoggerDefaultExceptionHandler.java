/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.ExceptionHandler
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.ExceptionHandler;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;

public class AsyncLoggerDefaultExceptionHandler
implements ExceptionHandler<RingBufferLogEvent> {
    public void handleEventException(Throwable throwable, long l, RingBufferLogEvent ringBufferLogEvent) {
        StringBuilder stringBuilder = new StringBuilder(512);
        stringBuilder.append("AsyncLogger error handling event seq=").append(l).append(", value='");
        try {
            stringBuilder.append(ringBufferLogEvent);
        } catch (Exception exception) {
            stringBuilder.append("[ERROR calling ").append(ringBufferLogEvent.getClass()).append(".toString(): ");
            stringBuilder.append(exception).append("]");
        }
        stringBuilder.append("':");
        System.err.println(stringBuilder);
        throwable.printStackTrace();
    }

    public void handleOnStartException(Throwable throwable) {
        System.err.println("AsyncLogger error starting:");
        throwable.printStackTrace();
    }

    public void handleOnShutdownException(Throwable throwable) {
        System.err.println("AsyncLogger error shutting down:");
        throwable.printStackTrace();
    }

    public void handleEventException(Throwable throwable, long l, Object object) {
        this.handleEventException(throwable, l, (RingBufferLogEvent)object);
    }
}

