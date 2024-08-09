/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.SequentialDnsServerAddressStream;
import io.netty.util.internal.PlatformDependent;
import java.net.InetSocketAddress;
import java.util.Random;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ShuffledDnsServerAddressStream
implements DnsServerAddressStream {
    private final InetSocketAddress[] addresses;
    private int i;

    ShuffledDnsServerAddressStream(InetSocketAddress[] inetSocketAddressArray) {
        this.addresses = inetSocketAddressArray;
        this.shuffle();
    }

    private ShuffledDnsServerAddressStream(InetSocketAddress[] inetSocketAddressArray, int n) {
        this.addresses = inetSocketAddressArray;
        this.i = n;
    }

    private void shuffle() {
        InetSocketAddress[] inetSocketAddressArray = this.addresses;
        Random random2 = PlatformDependent.threadLocalRandom();
        for (int i = inetSocketAddressArray.length - 1; i >= 0; --i) {
            InetSocketAddress inetSocketAddress = inetSocketAddressArray[i];
            int n = random2.nextInt(i + 1);
            inetSocketAddressArray[i] = inetSocketAddressArray[n];
            inetSocketAddressArray[n] = inetSocketAddress;
        }
    }

    @Override
    public InetSocketAddress next() {
        int n = this.i;
        InetSocketAddress inetSocketAddress = this.addresses[n];
        if (++n < this.addresses.length) {
            this.i = n;
        } else {
            this.i = 0;
            this.shuffle();
        }
        return inetSocketAddress;
    }

    @Override
    public int size() {
        return this.addresses.length;
    }

    @Override
    public ShuffledDnsServerAddressStream duplicate() {
        return new ShuffledDnsServerAddressStream(this.addresses, this.i);
    }

    public String toString() {
        return SequentialDnsServerAddressStream.toString("shuffled", this.i, this.addresses);
    }

    @Override
    public DnsServerAddressStream duplicate() {
        return this.duplicate();
    }
}

