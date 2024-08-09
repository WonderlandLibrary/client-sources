/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;

public final class PromiseNotificationUtil {
    private PromiseNotificationUtil() {
    }

    public static void tryCancel(Promise<?> promise, InternalLogger internalLogger) {
        if (!promise.cancel(false) && internalLogger != null) {
            Throwable throwable = promise.cause();
            if (throwable == null) {
                internalLogger.warn("Failed to cancel promise because it has succeeded already: {}", (Object)promise);
            } else {
                internalLogger.warn("Failed to cancel promise because it has failed already: {}, unnotified cause:", (Object)promise, (Object)throwable);
            }
        }
    }

    public static <V> void trySuccess(Promise<? super V> promise, V v, InternalLogger internalLogger) {
        if (!promise.trySuccess(v) && internalLogger != null) {
            Throwable throwable = promise.cause();
            if (throwable == null) {
                internalLogger.warn("Failed to mark a promise as success because it has succeeded already: {}", (Object)promise);
            } else {
                internalLogger.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause:", (Object)promise, (Object)throwable);
            }
        }
    }

    public static void tryFailure(Promise<?> promise, Throwable throwable, InternalLogger internalLogger) {
        if (!promise.tryFailure(throwable) && internalLogger != null) {
            Throwable throwable2 = promise.cause();
            if (throwable2 == null) {
                internalLogger.warn("Failed to mark a promise as failure because it has succeeded already: {}", (Object)promise, (Object)throwable);
            } else {
                internalLogger.warn("Failed to mark a promise as failure because it has failed already: {}, unnotified cause: {}", promise, ThrowableUtil.stackTraceToString(throwable2), throwable);
            }
        }
    }
}

