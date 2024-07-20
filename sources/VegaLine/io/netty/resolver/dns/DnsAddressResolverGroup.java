/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.InetSocketAddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.resolver.dns.DnsNameResolverBuilder;
import io.netty.resolver.dns.DnsServerAddresses;
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
    private final ChannelFactory<? extends DatagramChannel> channelFactory;
    private final DnsServerAddresses nameServerAddresses;
    private final ConcurrentMap<String, Promise<InetAddress>> resolvesInProgress = PlatformDependent.newConcurrentHashMap();
    private final ConcurrentMap<String, Promise<List<InetAddress>>> resolveAllsInProgress = PlatformDependent.newConcurrentHashMap();

    public DnsAddressResolverGroup(Class<? extends DatagramChannel> channelType, DnsServerAddresses nameServerAddresses) {
        this(new ReflectiveChannelFactory<DatagramChannel>(channelType), nameServerAddresses);
    }

    public DnsAddressResolverGroup(ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddresses nameServerAddresses) {
        this.channelFactory = channelFactory;
        this.nameServerAddresses = nameServerAddresses;
    }

    @Override
    protected final AddressResolver<InetSocketAddress> newResolver(EventExecutor executor) throws Exception {
        if (!(executor instanceof EventLoop)) {
            throw new IllegalStateException("unsupported executor type: " + StringUtil.simpleClassName(executor) + " (expected: " + StringUtil.simpleClassName(EventLoop.class));
        }
        return this.newResolver((EventLoop)executor, this.channelFactory, this.nameServerAddresses);
    }

    @Deprecated
    protected AddressResolver<InetSocketAddress> newResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddresses nameServerAddresses) throws Exception {
        InflightNameResolver<InetAddress> resolver = new InflightNameResolver<InetAddress>(eventLoop, this.newNameResolver(eventLoop, channelFactory, nameServerAddresses), this.resolvesInProgress, this.resolveAllsInProgress);
        return this.newAddressResolver(eventLoop, resolver);
    }

    protected NameResolver<InetAddress> newNameResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddresses nameServerAddresses) throws Exception {
        return new DnsNameResolverBuilder(eventLoop).channelFactory(channelFactory).nameServerAddresses(nameServerAddresses).build();
    }

    protected AddressResolver<InetSocketAddress> newAddressResolver(EventLoop eventLoop, NameResolver<InetAddress> resolver) throws Exception {
        return new InetSocketAddressResolver((EventExecutor)eventLoop, resolver);
    }
}

