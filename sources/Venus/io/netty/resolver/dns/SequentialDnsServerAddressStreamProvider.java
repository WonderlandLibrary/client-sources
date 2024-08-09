/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.resolver.dns.UniSequentialDnsServerAddressStreamProvider;
import java.net.InetSocketAddress;

public final class SequentialDnsServerAddressStreamProvider
extends UniSequentialDnsServerAddressStreamProvider {
    public SequentialDnsServerAddressStreamProvider(InetSocketAddress ... inetSocketAddressArray) {
        super(DnsServerAddresses.sequential(inetSocketAddressArray));
    }

    public SequentialDnsServerAddressStreamProvider(Iterable<? extends InetSocketAddress> iterable) {
        super(DnsServerAddresses.sequential(iterable));
    }
}

