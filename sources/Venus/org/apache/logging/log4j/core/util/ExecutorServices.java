/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public class ExecutorServices {
    private static final Logger LOGGER = StatusLogger.getLogger();

    public static boolean shutdown(ExecutorService executorService, long l, TimeUnit timeUnit, String string) {
        if (executorService == null || executorService.isTerminated()) {
            return false;
        }
        executorService.shutdown();
        if (l > 0L && timeUnit == null) {
            throw new IllegalArgumentException(String.format("%s can't shutdown %s when timeout = %,d and timeUnit = %s.", new Object[]{string, executorService, l, timeUnit}));
        }
        if (l > 0L) {
            try {
                if (!executorService.awaitTermination(l, timeUnit)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(l, timeUnit)) {
                        LOGGER.error("{} pool {} did not terminate after {} {}", (Object)string, (Object)executorService, (Object)l, (Object)timeUnit);
                    }
                    return false;
                }
            } catch (InterruptedException interruptedException) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } else {
            executorService.shutdown();
        }
        return false;
    }
}

