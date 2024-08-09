/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.Closeables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class Closer
implements Closeable {
    private static final Suppressor SUPPRESSOR = SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE;
    @VisibleForTesting
    final Suppressor suppressor;
    private final Deque<Closeable> stack = new ArrayDeque<Closeable>(4);
    private Throwable thrown;

    public static Closer create() {
        return new Closer(SUPPRESSOR);
    }

    @VisibleForTesting
    Closer(Suppressor suppressor) {
        this.suppressor = Preconditions.checkNotNull(suppressor);
    }

    @CanIgnoreReturnValue
    public <C extends Closeable> C register(@Nullable C c) {
        if (c != null) {
            this.stack.addFirst(c);
        }
        return c;
    }

    public RuntimeException rethrow(Throwable throwable) throws IOException {
        Preconditions.checkNotNull(throwable);
        this.thrown = throwable;
        Throwables.propagateIfPossible(throwable, IOException.class);
        throw new RuntimeException(throwable);
    }

    public <X extends Exception> RuntimeException rethrow(Throwable throwable, Class<X> clazz) throws IOException, X {
        Preconditions.checkNotNull(throwable);
        this.thrown = throwable;
        Throwables.propagateIfPossible(throwable, IOException.class);
        Throwables.propagateIfPossible(throwable, clazz);
        throw new RuntimeException(throwable);
    }

    public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable throwable, Class<X1> clazz, Class<X2> clazz2) throws IOException, X1, X2 {
        Preconditions.checkNotNull(throwable);
        this.thrown = throwable;
        Throwables.propagateIfPossible(throwable, IOException.class);
        Throwables.propagateIfPossible(throwable, clazz, clazz2);
        throw new RuntimeException(throwable);
    }

    @Override
    public void close() throws IOException {
        Throwable throwable = this.thrown;
        while (!this.stack.isEmpty()) {
            Closeable closeable = this.stack.removeFirst();
            try {
                closeable.close();
            } catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                    continue;
                }
                this.suppressor.suppress(closeable, throwable, throwable2);
            }
        }
        if (this.thrown == null && throwable != null) {
            Throwables.propagateIfPossible(throwable, IOException.class);
            throw new AssertionError((Object)throwable);
        }
    }

    @VisibleForTesting
    static final class SuppressingSuppressor
    implements Suppressor {
        static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
        static final Method addSuppressed = SuppressingSuppressor.getAddSuppressed();

        SuppressingSuppressor() {
        }

        static boolean isAvailable() {
            return addSuppressed != null;
        }

        private static Method getAddSuppressed() {
            try {
                return Throwable.class.getMethod("addSuppressed", Throwable.class);
            } catch (Throwable throwable) {
                return null;
            }
        }

        @Override
        public void suppress(Closeable closeable, Throwable throwable, Throwable throwable2) {
            if (throwable == throwable2) {
                return;
            }
            try {
                addSuppressed.invoke(throwable, throwable2);
            } catch (Throwable throwable3) {
                LoggingSuppressor.INSTANCE.suppress(closeable, throwable, throwable2);
            }
        }
    }

    @VisibleForTesting
    static final class LoggingSuppressor
    implements Suppressor {
        static final LoggingSuppressor INSTANCE = new LoggingSuppressor();

        LoggingSuppressor() {
        }

        @Override
        public void suppress(Closeable closeable, Throwable throwable, Throwable throwable2) {
            Closeables.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, throwable2);
        }
    }

    @VisibleForTesting
    static interface Suppressor {
        public void suppress(Closeable var1, Throwable var2, Throwable var3);
    }
}

