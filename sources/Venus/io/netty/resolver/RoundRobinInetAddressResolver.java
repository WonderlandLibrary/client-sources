/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.InetNameResolver;
import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundRobinInetAddressResolver
extends InetNameResolver {
    private final NameResolver<InetAddress> nameResolver;

    public RoundRobinInetAddressResolver(EventExecutor eventExecutor, NameResolver<InetAddress> nameResolver) {
        super(eventExecutor);
        this.nameResolver = nameResolver;
    }

    @Override
    protected void doResolve(String string, Promise<InetAddress> promise) throws Exception {
        this.nameResolver.resolveAll(string).addListener((GenericFutureListener<Future<List<InetAddress>>>)new FutureListener<List<InetAddress>>(this, promise, string){
            final Promise val$promise;
            final String val$inetHost;
            final RoundRobinInetAddressResolver this$0;
            {
                this.this$0 = roundRobinInetAddressResolver;
                this.val$promise = promise;
                this.val$inetHost = string;
            }

            @Override
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List<InetAddress> list = future.getNow();
                    int n = list.size();
                    if (n > 0) {
                        this.val$promise.setSuccess(list.get(RoundRobinInetAddressResolver.access$000(n)));
                    } else {
                        this.val$promise.setFailure(new UnknownHostException(this.val$inetHost));
                    }
                } else {
                    this.val$promise.setFailure(future.cause());
                }
            }
        });
    }

    @Override
    protected void doResolveAll(String string, Promise<List<InetAddress>> promise) throws Exception {
        this.nameResolver.resolveAll(string).addListener((GenericFutureListener<Future<List<InetAddress>>>)new FutureListener<List<InetAddress>>(this, promise){
            final Promise val$promise;
            final RoundRobinInetAddressResolver this$0;
            {
                this.this$0 = roundRobinInetAddressResolver;
                this.val$promise = promise;
            }

            @Override
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List<InetAddress> list = future.getNow();
                    if (!list.isEmpty()) {
                        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>(list);
                        Collections.rotate(arrayList, RoundRobinInetAddressResolver.access$000(list.size()));
                        this.val$promise.setSuccess(arrayList);
                    } else {
                        this.val$promise.setSuccess(list);
                    }
                } else {
                    this.val$promise.setFailure(future.cause());
                }
            }
        });
    }

    private static int randomIndex(int n) {
        return n == 1 ? 0 : PlatformDependent.threadLocalRandom().nextInt(n);
    }

    static int access$000(int n) {
        return RoundRobinInetAddressResolver.randomIndex(n);
    }
}

