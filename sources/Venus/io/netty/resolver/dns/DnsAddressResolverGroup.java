/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.InetSocketAddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.resolver.dns.DnsNameResolverBuilder;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.InflightNameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class DnsAddressResolverGroup
extends AddressResolverGroup<InetSocketAddress> {
    private final DnsNameResolverBuilder dnsResolverBuilder;
    private final ConcurrentMap<String, Promise<InetAddress>> resolvesInProgress = PlatformDependent.newConcurrentHashMap();
    private final ConcurrentMap<String, Promise<List<InetAddress>>> resolveAllsInProgress = PlatformDependent.newConcurrentHashMap();

    public DnsAddressResolverGroup(DnsNameResolverBuilder dnsNameResolverBuilder) {
        this.dnsResolverBuilder = dnsNameResolverBuilder.copy();
    }

    public DnsAddressResolverGroup(Class<? extends DatagramChannel> clazz, DnsServerAddressStreamProvider dnsServerAddressStreamProvider) {
        this(new DnsNameResolverBuilder());
        this.dnsResolverBuilder.channelType(clazz).nameServerProvider(dnsServerAddressStreamProvider);
    }

    public DnsAddressResolverGroup(ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddressStreamProvider dnsServerAddressStreamProvider) {
        this(new DnsNameResolverBuilder());
        this.dnsResolverBuilder.channelFactory(channelFactory).nameServerProvider(dnsServerAddressStreamProvider);
    }

    @Override
    protected final AddressResolver<InetSocketAddress> newResolver(EventExecutor eventExecutor) throws Exception {
        if (!(eventExecutor instanceof EventLoop)) {
            throw new IllegalStateException("unsupported executor type: " + StringUtil.simpleClassName(eventExecutor) + " (expected: " + StringUtil.simpleClassName(EventLoop.class));
        }
        return this.newResolver((EventLoop)eventExecutor, this.dnsResolverBuilder.channelFactory(), this.dnsResolverBuilder.nameServerProvider());
    }

    @Deprecated
    protected AddressResolver<InetSocketAddress> newResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddressStreamProvider dnsServerAddressStreamProvider) throws Exception {
        InflightNameResolver<InetAddress> inflightNameResolver = new InflightNameResolver<InetAddress>(eventLoop, this.newNameResolver(eventLoop, channelFactory, dnsServerAddressStreamProvider), this.resolvesInProgress, this.resolveAllsInProgress);
        return this.newAddressResolver(eventLoop, inflightNameResolver);
    }

    protected NameResolver<InetAddress> newNameResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddressStreamProvider dnsServerAddressStreamProvider) throws Exception {
        return this.dnsResolverBuilder.eventLoop(eventLoop).channelFactory(channelFactory).nameServerProvider(dnsServerAddressStreamProvider).build();
    }

    protected AddressResolver<InetSocketAddress> newAddressResolver(EventLoop eventLoop, NameResolver<InetAddress> nameResolver) throws Exception {
        return new InetSocketAddressResolver((EventExecutor)eventLoop, nameResolver);
    }
}

