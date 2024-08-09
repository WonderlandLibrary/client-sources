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
    private final DnsServerAddressStream stream = new DnsServerAddressStream(this){
        final SingletonDnsServerAddresses this$0;
        {
            this.this$0 = singletonDnsServerAddresses;
        }

        @Override
        public InetSocketAddress next() {
            return SingletonDnsServerAddresses.access$000(this.this$0);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public DnsServerAddressStream duplicate() {
            return this;
        }

        public String toString() {
            return this.this$0.toString();
        }
    };

    SingletonDnsServerAddresses(InetSocketAddress inetSocketAddress) {
        this.address = inetSocketAddress;
    }

    @Override
    public DnsServerAddressStream stream() {
        return this.stream;
    }

    public String toString() {
        return "singleton(" + this.address + ")";
    }

    static InetSocketAddress access$000(SingletonDnsServerAddresses singletonDnsServerAddresses) {
        return singletonDnsServerAddresses.address;
    }
}

