/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.NameResolver;
import io.netty.resolver.SimpleNameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.List;

public final class CompositeNameResolver<T>
extends SimpleNameResolver<T> {
    private final NameResolver<T>[] resolvers;

    public CompositeNameResolver(EventExecutor eventExecutor, NameResolver<T> ... nameResolverArray) {
        super(eventExecutor);
        ObjectUtil.checkNotNull(nameResolverArray, "resolvers");
        for (int i = 0; i < nameResolverArray.length; ++i) {
            if (nameResolverArray[i] != null) continue;
            throw new NullPointerException("resolvers[" + i + ']');
        }
        if (nameResolverArray.length < 2) {
            throw new IllegalArgumentException("resolvers: " + Arrays.asList(nameResolverArray) + " (expected: at least 2 resolvers)");
        }
        this.resolvers = (NameResolver[])nameResolverArray.clone();
    }

    @Override
    protected void doResolve(String string, Promise<T> promise) throws Exception {
        this.doResolveRec(string, promise, 0, null);
    }

    private void doResolveRec(String string, Promise<T> promise, int n, Throwable throwable) throws Exception {
        if (n >= this.resolvers.length) {
            promise.setFailure(throwable);
        } else {
            NameResolver<T> nameResolver = this.resolvers[n];
            nameResolver.resolve(string).addListener(new FutureListener<T>(this, promise, string, n){
                final Promise val$promise;
                final String val$inetHost;
                final int val$resolverIndex;
                final CompositeNameResolver this$0;
                {
                    this.this$0 = compositeNameResolver;
                    this.val$promise = promise;
                    this.val$inetHost = string;
                    this.val$resolverIndex = n;
                }

                @Override
                public void operationComplete(Future<T> future) throws Exception {
                    if (future.isSuccess()) {
                        this.val$promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.access$000(this.this$0, this.val$inetHost, this.val$promise, this.val$resolverIndex + 1, future.cause());
                    }
                }
            });
        }
    }

    @Override
    protected void doResolveAll(String string, Promise<List<T>> promise) throws Exception {
        this.doResolveAllRec(string, promise, 0, null);
    }

    private void doResolveAllRec(String string, Promise<List<T>> promise, int n, Throwable throwable) throws Exception {
        if (n >= this.resolvers.length) {
            promise.setFailure(throwable);
        } else {
            NameResolver<T> nameResolver = this.resolvers[n];
            nameResolver.resolveAll(string).addListener(new FutureListener<List<T>>(this, promise, string, n){
                final Promise val$promise;
                final String val$inetHost;
                final int val$resolverIndex;
                final CompositeNameResolver this$0;
                {
                    this.this$0 = compositeNameResolver;
                    this.val$promise = promise;
                    this.val$inetHost = string;
                    this.val$resolverIndex = n;
                }

                @Override
                public void operationComplete(Future<List<T>> future) throws Exception {
                    if (future.isSuccess()) {
                        this.val$promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.access$100(this.this$0, this.val$inetHost, this.val$promise, this.val$resolverIndex + 1, future.cause());
                    }
                }
            });
        }
    }

    static void access$000(CompositeNameResolver compositeNameResolver, String string, Promise promise, int n, Throwable throwable) throws Exception {
        compositeNameResolver.doResolveRec(string, promise, n, throwable);
    }

    static void access$100(CompositeNameResolver compositeNameResolver, String string, Promise promise, int n, Throwable throwable) throws Exception {
        compositeNameResolver.doResolveAllRec(string, promise, n, throwable);
    }
}

