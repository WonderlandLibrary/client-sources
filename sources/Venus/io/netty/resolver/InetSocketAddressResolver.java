/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.AbstractAddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class InetSocketAddressResolver
extends AbstractAddressResolver<InetSocketAddress> {
    final NameResolver<InetAddress> nameResolver;

    public InetSocketAddressResolver(EventExecutor eventExecutor, NameResolver<InetAddress> nameResolver) {
        super(eventExecutor, InetSocketAddress.class);
        this.nameResolver = nameResolver;
    }

    @Override
    protected boolean doIsResolved(InetSocketAddress inetSocketAddress) {
        return !inetSocketAddress.isUnresolved();
    }

    @Override
    protected void doResolve(InetSocketAddress inetSocketAddress, Promise<InetSocketAddress> promise) throws Exception {
        this.nameResolver.resolve(inetSocketAddress.getHostName()).addListener((GenericFutureListener<Future<InetAddress>>)new FutureListener<InetAddress>(this, promise, inetSocketAddress){
            final Promise val$promise;
            final InetSocketAddress val$unresolvedAddress;
            final InetSocketAddressResolver this$0;
            {
                this.this$0 = inetSocketAddressResolver;
                this.val$promise = promise;
                this.val$unresolvedAddress = inetSocketAddress;
            }

            @Override
            public void operationComplete(Future<InetAddress> future) throws Exception {
                if (future.isSuccess()) {
                    this.val$promise.setSuccess(new InetSocketAddress(future.getNow(), this.val$unresolvedAddress.getPort()));
                } else {
                    this.val$promise.setFailure(future.cause());
                }
            }
        });
    }

    @Override
    protected void doResolveAll(InetSocketAddress inetSocketAddress, Promise<List<InetSocketAddress>> promise) throws Exception {
        this.nameResolver.resolveAll(inetSocketAddress.getHostName()).addListener((GenericFutureListener<Future<List<InetAddress>>>)new FutureListener<List<InetAddress>>(this, inetSocketAddress, promise){
            final InetSocketAddress val$unresolvedAddress;
            final Promise val$promise;
            final InetSocketAddressResolver this$0;
            {
                this.this$0 = inetSocketAddressResolver;
                this.val$unresolvedAddress = inetSocketAddress;
                this.val$promise = promise;
            }

            @Override
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List<InetAddress> list = future.getNow();
                    ArrayList<InetSocketAddress> arrayList = new ArrayList<InetSocketAddress>(list.size());
                    for (InetAddress inetAddress : list) {
                        arrayList.add(new InetSocketAddress(inetAddress, this.val$unresolvedAddress.getPort()));
                    }
                    this.val$promise.setSuccess(arrayList);
                } else {
                    this.val$promise.setFailure(future.cause());
                }
            }
        });
    }

    @Override
    public void close() {
        this.nameResolver.close();
    }

    @Override
    protected void doResolveAll(SocketAddress socketAddress, Promise promise) throws Exception {
        this.doResolveAll((InetSocketAddress)socketAddress, (Promise<List<InetSocketAddress>>)promise);
    }

    @Override
    protected void doResolve(SocketAddress socketAddress, Promise promise) throws Exception {
        this.doResolve((InetSocketAddress)socketAddress, (Promise<InetSocketAddress>)promise);
    }

    @Override
    protected boolean doIsResolved(SocketAddress socketAddress) {
        return this.doIsResolved((InetSocketAddress)socketAddress);
    }
}

