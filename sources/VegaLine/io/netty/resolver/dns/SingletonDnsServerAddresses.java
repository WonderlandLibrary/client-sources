/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddresses;
import java.net.InetSocketAddress;

final class SingletonDnsServerAddresses
extends DnsServerAddresses {
    private final InetSocketAddress address;
    private final String strVal;
    private final DnsServerAddressStream stream = new DnsServerAddressStream(){

        @Override
        public InetSocketAddress next() {
            return SingletonDnsServerAddresses.this.address;
        }

        public String toString() {
            return SingletonDnsServerAddresses.this.toString();
        }
    };

    SingletonDnsServerAddresses(InetSocketAddress address) {
        this.address = address;
        this.strVal = new StringBuilder(32).append("singleton(").append(address).append(')').toString();
    }

    @Override
    public DnsServerAddressStream stream() {
        return this.stream;
    }

    public String toString() {
        return this.strVal;
    }
}

