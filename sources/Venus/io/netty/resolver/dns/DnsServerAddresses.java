/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DefaultDnsServerAddressStreamProvider;
import io.netty.resolver.dns.DefaultDnsServerAddresses;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.RotationalDnsServerAddresses;
import io.netty.resolver.dns.SequentialDnsServerAddressStream;
import io.netty.resolver.dns.ShuffledDnsServerAddressStream;
import io.netty.resolver.dns.SingletonDnsServerAddresses;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DnsServerAddresses {
    @Deprecated
    public static List<InetSocketAddress> defaultAddressList() {
        return DefaultDnsServerAddressStreamProvider.defaultAddressList();
    }

    @Deprecated
    public static DnsServerAddresses defaultAddresses() {
        return DefaultDnsServerAddressStreamProvider.defaultAddresses();
    }

    public static DnsServerAddresses sequential(Iterable<? extends InetSocketAddress> iterable) {
        return DnsServerAddresses.sequential0(DnsServerAddresses.sanitize(iterable));
    }

    public static DnsServerAddresses sequential(InetSocketAddress ... inetSocketAddressArray) {
        return DnsServerAddresses.sequential0(DnsServerAddresses.sanitize(inetSocketAddressArray));
    }

    private static DnsServerAddresses sequential0(InetSocketAddress ... inetSocketAddressArray) {
        if (inetSocketAddressArray.length == 1) {
            return DnsServerAddresses.singleton(inetSocketAddressArray[0]);
        }
        return new DefaultDnsServerAddresses("sequential", inetSocketAddressArray){

            @Override
            public DnsServerAddressStream stream() {
                return new SequentialDnsServerAddressStream(this.addresses, 0);
            }
        };
    }

    public static DnsServerAddresses shuffled(Iterable<? extends InetSocketAddress> iterable) {
        return DnsServerAddresses.shuffled0(DnsServerAddresses.sanitize(iterable));
    }

    public static DnsServerAddresses shuffled(InetSocketAddress ... inetSocketAddressArray) {
        return DnsServerAddresses.shuffled0(DnsServerAddresses.sanitize(inetSocketAddressArray));
    }

    private static DnsServerAddresses shuffled0(InetSocketAddress[] inetSocketAddressArray) {
        if (inetSocketAddressArray.length == 1) {
            return DnsServerAddresses.singleton(inetSocketAddressArray[0]);
        }
        return new DefaultDnsServerAddresses("shuffled", inetSocketAddressArray){

            @Override
            public DnsServerAddressStream stream() {
                return new ShuffledDnsServerAddressStream(this.addresses);
            }
        };
    }

    public static DnsServerAddresses rotational(Iterable<? extends InetSocketAddress> iterable) {
        return DnsServerAddresses.rotational0(DnsServerAddresses.sanitize(iterable));
    }

    public static DnsServerAddresses rotational(InetSocketAddress ... inetSocketAddressArray) {
        return DnsServerAddresses.rotational0(DnsServerAddresses.sanitize(inetSocketAddressArray));
    }

    private static DnsServerAddresses rotational0(InetSocketAddress[] inetSocketAddressArray) {
        if (inetSocketAddressArray.length == 1) {
            return DnsServerAddresses.singleton(inetSocketAddressArray[0]);
        }
        return new RotationalDnsServerAddresses(inetSocketAddressArray);
    }

    public static DnsServerAddresses singleton(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress == null) {
            throw new NullPointerException("address");
        }
        if (inetSocketAddress.isUnresolved()) {
            throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
        }
        return new SingletonDnsServerAddresses(inetSocketAddress);
    }

    private static InetSocketAddress[] sanitize(Iterable<? extends InetSocketAddress> iterable) {
        if (iterable == null) {
            throw new NullPointerException("addresses");
        }
        ArrayList<InetSocketAddress> arrayList = iterable instanceof Collection ? new ArrayList(((Collection)iterable).size()) : new ArrayList<InetSocketAddress>(4);
        for (InetSocketAddress inetSocketAddress : iterable) {
            if (inetSocketAddress == null) break;
            if (inetSocketAddress.isUnresolved()) {
                throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
            }
            arrayList.add(inetSocketAddress);
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("empty addresses");
        }
        return arrayList.toArray(new InetSocketAddress[arrayList.size()]);
    }

    private static InetSocketAddress[] sanitize(InetSocketAddress[] inetSocketAddressArray) {
        if (inetSocketAddressArray == null) {
            throw new NullPointerException("addresses");
        }
        ArrayList<InetSocketAddress> arrayList = new ArrayList<InetSocketAddress>(inetSocketAddressArray.length);
        for (InetSocketAddress inetSocketAddress : inetSocketAddressArray) {
            if (inetSocketAddress == null) break;
            if (inetSocketAddress.isUnresolved()) {
                throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
            }
            arrayList.add(inetSocketAddress);
        }
        if (arrayList.isEmpty()) {
            return DefaultDnsServerAddressStreamProvider.defaultAddressArray();
        }
        return arrayList.toArray(new InetSocketAddress[arrayList.size()]);
    }

    public abstract DnsServerAddressStream stream();
}

