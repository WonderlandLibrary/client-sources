/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.ExceptionHandler
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.ExceptionHandler;
import org.apache.logging.log4j.core.async.AsyncLoggerConfigDisruptor;

public class AsyncLoggerConfigDefaultExceptionHandler
implements ExceptionHandler<AsyncLoggerConfigDisruptor.Log4jEventWrapper> {
    public void handleEventException(Throwable throwable, long sequence, AsyncLoggerConfigDisruptor.Log4jEventWrapper event) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("AsyncLogger error handling event seq=").append(sequence).append(", value='");
        try {
            sb.append(event);
        } catch (Exception ignored) {
            sb.append("[ERROR calling ").append(event.getClass()).append(".toString(): ");
            sb.append(ignored).append("]");
        }
        sb.append("':");
        System.err.println(sb);
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
}

