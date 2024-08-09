/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

final class InflightNameResolver<T>
implements NameResolver<T> {
    private final EventExecutor executor;
    private final NameResolver<T> delegate;
    private final ConcurrentMap<String, Promise<T>> resolvesInProgress;
    private final ConcurrentMap<String, Promise<List<T>>> resolveAllsInProgress;

    InflightNameResolver(EventExecutor eventExecutor, NameResolver<T> nameResolver, ConcurrentMap<String, Promise<T>> concurrentMap, ConcurrentMap<String, Promise<List<T>>> concurrentMap2) {
        this.executor = ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.delegate = ObjectUtil.checkNotNull(nameResolver, "delegate");
        this.resolvesInProgress = ObjectUtil.checkNotNull(concurrentMap, "resolvesInProgress");
        this.resolveAllsInProgress = ObjectUtil.checkNotNull(concurrentMap2, "resolveAllsInProgress");
    }

    @Override
    public Future<T> resolve(String string) {
        return this.resolve(string, this.executor.newPromise());
    }

    @Override
    public Future<List<T>> resolveAll(String string) {
        return this.resolveAll(string, this.executor.newPromise());
    }

    @Override
    public void close() {
        this.delegate.close();
    }

    @Override
    public Promise<T> resolve(String string, Promise<T> promise) {
        return this.resolve(this.resolvesInProgress, string, promise, false);
    }

    @Override
    public Promise<List<T>> resolveAll(String string, Promise<List<T>> promise) {
        return this.resolve(this.resolveAllsInProgress, string, promise, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private <U> Promise<U> resolve(ConcurrentMap<String, Promise<U>> concurrentMap, String string, Promise<U> promise, boolean bl) {
        block10: {
            Promise promise2 = concurrentMap.putIfAbsent(string, promise);
            if (promise2 != null) {
                if (promise2.isDone()) {
                    InflightNameResolver.transferResult(promise2, promise);
                    return promise;
                } else {
                    promise2.addListener(new FutureListener<U>(this, promise){
                        final Promise val$promise;
                        final InflightNameResolver this$0;
                        {
                            this.this$0 = inflightNameResolver;
                            this.val$promise = promise;
                        }

                        @Override
                        public void operationComplete(Future<U> future) throws Exception {
                            InflightNameResolver.access$000(future, this.val$promise);
                        }
                    });
                }
                return promise;
            }
            try {
                if (bl) {
                    Promise promise3 = promise;
                    this.delegate.resolveAll(string, promise3);
                    break block10;
                }
                Promise promise4 = promise;
                this.delegate.resolve(string, promise4);
            } catch (Throwable throwable) {
                if (promise.isDone()) {
                    concurrentMap.remove(string);
                    throw throwable;
                } else {
                    promise.addListener(new FutureListener<U>(this, concurrentMap, string){
                        final ConcurrentMap val$resolveMap;
                        final String val$inetHost;
                        final InflightNameResolver this$0;
                        {
                            this.this$0 = inflightNameResolver;
                            this.val$resolveMap = concurrentMap;
                            this.val$inetHost = string;
                        }

                        @Override
                        public void operationComplete(Future<U> future) throws Exception {
                            this.val$resolveMap.remove(this.val$inetHost);
                        }
                    });
                }
                throw throwable;
            }
        }
        if (promise.isDone()) {
            concurrentMap.remove(string);
            return promise;
        } else {
            promise.addListener(new /* invalid duplicate definition of identical inner class */);
        }
        return promise;
    }

    private static <T> void transferResult(Future<T> future, Promise<T> promise) {
        if (future.isSuccess()) {
            promise.trySuccess(future.getNow());
        } else {
            promise.tryFailure(future.cause());
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.delegate + ')';
    }

    @Override
    public Future resolveAll(String string, Promise promise) {
        return this.resolveAll(string, promise);
    }

    @Override
    public Future resolve(String string, Promise promise) {
        return this.resolve(string, promise);
    }

    static void access$000(Future future, Promise promise) {
        InflightNameResolver.transferResult(future, promise);
    }
}

