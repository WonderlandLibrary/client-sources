/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public abstract class SimpleNameResolver<T>
implements NameResolver<T> {
    private final EventExecutor executor;

    protected SimpleNameResolver(EventExecutor eventExecutor) {
        this.executor = ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    protected EventExecutor executor() {
        return this.executor;
    }

    @Override
    public final Future<T> resolve(String string) {
        Promise promise = this.executor().newPromise();
        return this.resolve(string, promise);
    }

    @Override
    public Future<T> resolve(String string, Promise<T> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            this.doResolve(string, promise);
            return promise;
        } catch (Exception exception) {
            return promise.setFailure(exception);
        }
    }

    @Override
    public final Future<List<T>> resolveAll(String string) {
        Promise<List<T>> promise = this.executor().newPromise();
        return this.resolveAll(string, promise);
    }

    @Override
    public Future<List<T>> resolveAll(String string, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            this.doResolveAll(string, promise);
            return promise;
        } catch (Exception exception) {
            return promise.setFailure(exception);
        }
    }

    protected abstract void doResolve(String var1, Promise<T> var2) throws Exception;

    protected abstract void doResolveAll(String var1, Promise<List<T>> var2) throws Exception;

    @Override
    public void close() {
    }
}

