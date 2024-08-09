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

    DefaultDnsServerAddresses(String string, InetSocketAddress[] inetSocketAddressArray) {
        this.addresses = inetSocketAddressArray;
        StringBuilder stringBuilder = new StringBuilder(string.length() + 2 + inetSocketAddressArray.length * 16);
        stringBuilder.append(string).append('(');
        for (InetSocketAddress inetSocketAddress : inetSocketAddressArray) {
            stringBuilder.append(inetSocketAddress).append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(')');
        this.strVal = stringBuilder.toString();
    }

    public String toString() {
        return this.strVal;
    }
}

