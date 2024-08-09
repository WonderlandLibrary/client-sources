/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.resolver.RoundRobinInetAddressResolver;
import io.netty.resolver.dns.DnsAddressResolverGroup;
import io.netty.resolver.dns.DnsNameResolverBuilder;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class RoundRobinDnsAddressResolverGroup
extends DnsAddressResolverGroup {
    public RoundRobinDnsAddressResolverGroup(DnsNameResolverBuilder dnsNameResolverBuilder) {
        super(dnsNameResolverBuilder);
    }

    public RoundRobinDnsAddressResolverGroup(Class<? extends DatagramChannel> clazz, DnsServerAddressStreamProvider dnsServerAddressStreamProvider) {
        super(clazz, dnsServerAddressStreamProvider);
    }

    public RoundRobinDnsAddressResolverGroup(ChannelFactory<? extends DatagramChannel> channelFactory, DnsServerAddressStreamProvider dnsServerAddressStreamProvider) {
        super(channelFactory, dnsServerAddressStreamProvider);
    }

    @Override
    protected final AddressResolver<InetSocketAddress> newAddressResolver(EventLoop eventLoop, NameResolver<InetAddress> nameResolver) throws Exception {
        return new RoundRobinInetAddressResolver(eventLoop, nameResolver).asAddressResolver();
    }
}

