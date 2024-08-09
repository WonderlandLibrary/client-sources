/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.util.internal.ObjectUtil;

abstract class UniSequentialDnsServerAddressStreamProvider
implements DnsServerAddressStreamProvider {
    private final DnsServerAddresses addresses;

    UniSequentialDnsServerAddressStreamProvider(DnsServerAddresses dnsServerAddresses) {
        this.addresses = ObjectUtil.checkNotNull(dnsServerAddresses, "addresses");
    }

    @Override
    public final DnsServerAddressStream nameServerAddressStream(String string) {
        return this.addresses.stream();
    }
}

