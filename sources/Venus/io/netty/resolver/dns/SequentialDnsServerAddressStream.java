/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import java.net.InetSocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class SequentialDnsServerAddressStream
implements DnsServerAddressStream {
    private final InetSocketAddress[] addresses;
    private int i;

    SequentialDnsServerAddressStream(InetSocketAddress[] inetSocketAddressArray, int n) {
        this.addresses = inetSocketAddressArray;
        this.i = n;
    }

    @Override
    public InetSocketAddress next() {
        int n = this.i;
        InetSocketAddress inetSocketAddress = this.addresses[n];
        this.i = ++n < this.addresses.length ? n : 0;
        return inetSocketAddress;
    }

    @Override
    public int size() {
        return this.addresses.length;
    }

    @Override
    public SequentialDnsServerAddressStream duplicate() {
        return new SequentialDnsServerAddressStream(this.addresses, this.i);
    }

    public String toString() {
        return SequentialDnsServerAddressStream.toString("sequential", this.i, this.addresses);
    }

    static String toString(String string, int n, InetSocketAddress[] inetSocketAddressArray) {
        StringBuilder stringBuilder = new StringBuilder(string.length() + 2 + inetSocketAddressArray.length * 16);
        stringBuilder.append(string).append("(index: ").append(n);
        stringBuilder.append(", addrs: (");
        for (InetSocketAddress inetSocketAddress : inetSocketAddressArray) {
            stringBuilder.append(inetSocketAddress).append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append("))");
        return stringBuilder.toString();
    }

    @Override
    public DnsServerAddressStream duplicate() {
        return this.duplicate();
    }
}

