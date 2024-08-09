/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import java.util.List;

public final class MultiDnsServerAddressStreamProvider
implements DnsServerAddressStreamProvider {
    private final DnsServerAddressStreamProvider[] providers;

    public MultiDnsServerAddressStreamProvider(List<DnsServerAddressStreamProvider> list) {
        this.providers = list.toArray(new DnsServerAddressStreamProvider[0]);
    }

    public MultiDnsServerAddressStreamProvider(DnsServerAddressStreamProvider ... dnsServerAddressStreamProviderArray) {
        this.providers = (DnsServerAddressStreamProvider[])dnsServerAddressStreamProviderArray.clone();
    }

    @Override
    public DnsServerAddressStream nameServerAddressStream(String string) {
        for (DnsServerAddressStreamProvider dnsServerAddressStreamProvider : this.providers) {
            DnsServerAddressStream dnsServerAddressStream = dnsServerAddressStreamProvider.nameServerAddressStream(string);
            if (dnsServerAddressStream == null) continue;
            return dnsServerAddressStream;
        }
        return null;
    }
}

