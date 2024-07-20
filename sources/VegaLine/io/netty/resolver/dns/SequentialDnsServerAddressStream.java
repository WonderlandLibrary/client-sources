/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import java.net.InetSocketAddress;

final class SequentialDnsServerAddressStream
implements DnsServerAddressStream {
    private final InetSocketAddress[] addresses;
    private int i;

    SequentialDnsServerAddressStream(InetSocketAddress[] addresses, int startIdx) {
        this.addresses = addresses;
        this.i = startIdx;
    }

    @Override
    public InetSocketAddress next() {
        int i = this.i;
        InetSocketAddress next = this.addresses[i];
        this.i = ++i < this.addresses.length ? i : 0;
        return next;
    }

    public String toString() {
        return SequentialDnsServerAddressStream.toString("sequential", this.i, this.addresses);
    }

    static String toString(String type2, int index, InetSocketAddress[] addresses) {
        StringBuilder buf = new StringBuilder(type2.length() + 2 + addresses.length * 16);
        buf.append(type2).append("(index: ").append(index);
        buf.append(", addrs: (");
        for (InetSocketAddress a : addresses) {
            buf.append(a).append(", ");
        }
        buf.setLength(buf.length() - 2);
        buf.append("))");
        return buf.toString();
    }
}

