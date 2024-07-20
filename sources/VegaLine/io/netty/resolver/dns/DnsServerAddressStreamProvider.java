/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;

public interface DnsServerAddressStreamProvider {
    public DnsServerAddressStream nameServerAddressStream(String var1);
}

