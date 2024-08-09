/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class PromiseNotifier<V, F extends Future<V>>
implements GenericFutureListener<F> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PromiseNotifier.class);
    private final Promise<? super V>[] promises;
    private final boolean logNotifyFailure;

    @SafeVarargs
    public PromiseNotifier(Promise<? super V> ... promiseArray) {
        this(true, promiseArray);
    }

    @SafeVarargs
    public PromiseNotifier(boolean bl, Promise<? super V> ... promiseArray) {
        ObjectUtil.checkNotNull(promiseArray, "promises");
        for (Promise<? super V> promise : promiseArray) {
            if (promise != null) continue;
            throw new IllegalArgumentException("promises contains null Promise");
        }
        this.promises = (Promise[])promiseArray.clone();
        this.logNotifyFailure = bl;
    }

    @Override
    public void operationComplete(F f) throws Exception {
        InternalLogger internalLogger;
        InternalLogger internalLogger2 = internalLogger = this.logNotifyFailure ? logger : null;
        if (f.isSuccess()) {
            Object v = f.get();
            for (Promise<? super V> promise : this.promises) {
                PromiseNotificationUtil.trySuccess(promise, v, internalLogger);
            }
        } else if (f.isCancelled()) {
            for (Promise<? super V> promise : this.promises) {
                PromiseNotificationUtil.tryCancel(promise, internalLogger);
            }
        } else {
            Throwable throwable = f.cause();
            for (Promise<? super V> promise : this.promises) {
                PromiseNotificationUtil.tryFailure(promise, throwable, internalLogger);
            }
        }
    }
}

