/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DefaultDnsServerAddresses;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.SequentialDnsServerAddressStream;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class RotationalDnsServerAddresses
extends DefaultDnsServerAddresses {
    private static final AtomicIntegerFieldUpdater<RotationalDnsServerAddresses> startIdxUpdater = AtomicIntegerFieldUpdater.newUpdater(RotationalDnsServerAddresses.class, "startIdx");
    private volatile int startIdx;

    RotationalDnsServerAddresses(InetSocketAddress[] inetSocketAddressArray) {
        super("rotational", inetSocketAddressArray);
    }

    @Override
    public DnsServerAddressStream stream() {
        int n;
        int n2;
        do {
            if ((n = (n2 = this.startIdx) + 1) < this.addresses.length) continue;
            n = 0;
        } while (!startIdxUpdater.compareAndSet(this, n2, n));
        return new SequentialDnsServerAddressStream(this.addresses, n2);
    }
}

