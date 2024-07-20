/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.InetNameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.SocketUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class DefaultNameResolver
extends InetNameResolver {
    public DefaultNameResolver(EventExecutor executor) {
        super(executor);
    }

    @Override
    protected void doResolve(String inetHost, Promise<InetAddress> promise) throws Exception {
        try {
            promise.setSuccess(SocketUtils.addressByName(inetHost));
        } catch (UnknownHostException e) {
            promise.setFailure(e);
        }
    }

    @Override
    protected void doResolveAll(String inetHost, Promise<List<InetAddress>> promise) throws Exception {
        try {
            promise.setSuccess(Arrays.asList(SocketUtils.allAddressesByName(inetHost)));
        } catch (UnknownHostException e) {
            promise.setFailure(e);
        }
    }
}

