/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddresses;
import java.net.InetSocketAddress;

abstract class DefaultDnsServerAddresses
extends DnsServerAddresses {
    protected final InetSocketAddress[] addresses;
    private final String strVal;

    DefaultDnsServerAddresses(String type2, InetSocketAddress[] addresses) {
        this.addresses = addresses;
        StringBuilder buf = new StringBuilder(type2.length() + 2 + addresses.length * 16);
        buf.append(type2).append('(');
        for (InetSocketAddress a : addresses) {
            buf.append(a).append(", ");
        }
        buf.setLength(buf.length() - 2);
        buf.append(')');
        this.strVal = buf.toString();
    }

    public String toString() {
        return this.strVal;
    }
}

