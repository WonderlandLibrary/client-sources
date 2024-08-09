/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.AddressResolver;
import io.netty.resolver.InetSocketAddressResolver;
import io.netty.resolver.SimpleNameResolver;
import io.netty.util.concurrent.EventExecutor;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public abstract class InetNameResolver
extends SimpleNameResolver<InetAddress> {
    private volatile AddressResolver<InetSocketAddress> addressResolver;

    protected InetNameResolver(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public AddressResolver<InetSocketAddress> asAddressResolver() {
        InetSocketAddressResolver inetSocketAddressResolver = this.addressResolver;
        if (inetSocketAddressResolver == null) {
            InetNameResolver inetNameResolver = this;
            synchronized (inetNameResolver) {
                inetSocketAddressResolver = this.addressResolver;
                if (inetSocketAddressResolver == null) {
                    this.addressResolver = inetSocketAddressResolver = new InetSocketAddressResolver(this.executor(), this);
                }
            }
        }
        return inetSocketAddressResolver;
    }
}

