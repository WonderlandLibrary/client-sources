/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import java.net.InetSocketAddress;

public interface DnsServerAddressStream {
    public InetSocketAddress next();

    public int size();

    public DnsServerAddressStream duplicate();
}

