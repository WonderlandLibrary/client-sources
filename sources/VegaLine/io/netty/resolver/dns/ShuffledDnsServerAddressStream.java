/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.SequentialDnsServerAddressStream;
import io.netty.util.internal.PlatformDependent;
import java.net.InetSocketAddress;
import java.util.Random;

final class ShuffledDnsServerAddressStream
implements DnsServerAddressStream {
    private final InetSocketAddress[] addresses;
    private int i;

    ShuffledDnsServerAddressStream(InetSocketAddress[] addresses) {
        this.addresses = (InetSocketAddress[])addresses.clone();
        this.shuffle();
    }

    private void shuffle() {
        InetSocketAddress[] addresses = this.addresses;
        Random r = PlatformDependent.threadLocalRandom();
        for (int i = addresses.length - 1; i >= 0; --i) {
            InetSocketAddress tmp = addresses[i];
            int j = r.nextInt(i + 1);
            addresses[i] = addresses[j];
            addresses[j] = tmp;
        }
    }

    @Override
    public InetSocketAddress next() {
        int i = this.i;
        InetSocketAddress next = this.addresses[i];
        if (++i < this.addresses.length) {
            this.i = i;
        } else {
            this.i = 0;
            this.shuffle();
        }
        return next;
    }

    public String toString() {
        return SequentialDnsServerAddressStream.toString("shuffled", this.i, this.addresses);
    }
}

