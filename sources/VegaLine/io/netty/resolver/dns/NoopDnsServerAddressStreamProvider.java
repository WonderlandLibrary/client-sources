/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;

public final class NoopDnsServerAddressStreamProvider
implements DnsServerAddressStreamProvider {
    public static final NoopDnsServerAddressStreamProvider INSTANCE = new NoopDnsServerAddressStreamProvider();

    private NoopDnsServerAddressStreamProvider() {
    }

    @Override
    public DnsServerAddressStream nameServerAddressStream(String hostname) {
        return null;
    }
}

