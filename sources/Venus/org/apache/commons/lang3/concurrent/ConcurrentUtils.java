/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;
import org.apache.commons.lang3.concurrent.ConcurrentRuntimeException;

public class ConcurrentUtils {
    private ConcurrentUtils() {
    }

    public static ConcurrentException extractCause(ExecutionException executionException) {
        if (executionException == null || executionException.getCause() == null) {
            return null;
        }
        ConcurrentUtils.throwCause(executionException);
        return new ConcurrentException(executionException.getMessage(), executionException.getCause());
    }

    public static ConcurrentRuntimeException extractCauseUnchecked(ExecutionException executionException) {
        if (executionException == null || executionException.getCause() == null) {
            return null;
        }
        ConcurrentUtils.throwCause(executionException);
        return new ConcurrentRuntimeException(executionException.getMessage(), executionException.getCause());
    }

    public static void handleCause(ExecutionException executionException) throws ConcurrentException {
        ConcurrentException concurrentException = ConcurrentUtils.extractCause(executionException);
        if (concurrentException != null) {
            throw concurrentException;
        }
    }

    public static void handleCauseUnchecked(ExecutionException executionException) {
        ConcurrentRuntimeException concurrentRuntimeException = ConcurrentUtils.extractCauseUnchecked(executionException);
        if (concurrentRuntimeException != null) {
            throw concurrentRuntimeException;
        }
    }

    static Throwable checkedException(Throwable throwable) {
        Validate.isTrue(throwable != null && !(throwable instanceof RuntimeException) && !(throwable instanceof Error), "Not a checked exception: " + throwable, new Object[0]);
        return throwable;
    }

    private static void throwCause(ExecutionException executionException) {
        if (executionException.getCause() instanceof RuntimeException) {
            throw (RuntimeException)executionException.getCause();
        }
        if (executionException.getCause() instanceof Error) {
            throw (Error)executionException.getCause();
        }
    }

    public static <T> T initialize(ConcurrentInitializer<T> concurrentInitializer) throws ConcurrentException {
        return concurrentInitializer != null ? (T)concurrentInitializer.get() : null;
    }

    public static <T> T initializeUnchecked(ConcurrentInitializer<T> concurrentInitializer) {
        try {
            return ConcurrentUtils.initialize(concurrentInitializer);
        } catch (ConcurrentException concurrentException) {
            throw new ConcurrentRuntimeException(concurrentException.getCause());
        }
    }

    public static <K, V> V putIfAbsent(ConcurrentMap<K, V> concurrentMap, K k, V v) {
        if (concurrentMap == null) {
            return null;
        }
        V v2 = concurrentMap.putIfAbsent(k, v);
        return v2 != null ? v2 : v;
    }

    public static <K, V> V createIfAbsent(ConcurrentMap<K, V> concurrentMap, K k, ConcurrentInitializer<V> concurrentInitializer) throws ConcurrentException {
        if (concurrentMap == null || concurrentInitializer == null) {
            return null;
        }
        Object v = concurrentMap.get(k);
        if (v == null) {
            return ConcurrentUtils.putIfAbsent(concurrentMap, k, concurrentInitializer.get());
        }
        return v;
    }

    public static <K, V> V createIfAbsentUnchecked(ConcurrentMap<K, V> concurrentMap, K k, ConcurrentInitializer<V> concurrentInitializer) {
        try {
            return ConcurrentUtils.createIfAbsent(concurrentMap, k, concurrentInitializer);
        } catch (ConcurrentException concurrentException) {
            throw new ConcurrentRuntimeException(concurrentException.getCause());
        }
    }

    public static <T> Future<T> constantFuture(T t) {
        return new ConstantFuture<T>(t);
    }

    static final class ConstantFuture<T>
    implements Future<T> {
        private final T value;

        ConstantFuture(T t) {
            this.value = t;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public T get() {
            return this.value;
        }

        @Override
        public T get(long l, TimeUnit timeUnit) {
            return this.value;
        }

        @Override
        public boolean isCancelled() {
            return true;
        }

        @Override
        public boolean cancel(boolean bl) {
            return true;
        }
    }
}

