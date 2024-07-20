/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DefaultDnsServerAddresses;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.RotationalDnsServerAddresses;
import io.netty.resolver.dns.SequentialDnsServerAddressStream;
import io.netty.resolver.dns.ShuffledDnsServerAddressStream;
import io.netty.resolver.dns.SingletonDnsServerAddresses;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class DnsServerAddresses {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DnsServerAddresses.class);
    private static final List<InetSocketAddress> DEFAULT_NAME_SERVER_LIST;
    private static final InetSocketAddress[] DEFAULT_NAME_SERVER_ARRAY;
    private static final DnsServerAddresses DEFAULT_NAME_SERVERS;
    static final int DNS_PORT = 53;

    public static List<InetSocketAddress> defaultAddressList() {
        return DEFAULT_NAME_SERVER_LIST;
    }

    public static DnsServerAddresses defaultAddresses() {
        return DEFAULT_NAME_SERVERS;
    }

    public static DnsServerAddresses sequential(Iterable<? extends InetSocketAddress> addresses) {
        return DnsServerAddresses.sequential0(DnsServerAddresses.sanitize(addresses));
    }

    public static DnsServerAddresses sequential(InetSocketAddress ... addresses) {
        return DnsServerAddresses.sequential0(DnsServerAddresses.sanitize(addresses));
    }

    private static DnsServerAddresses sequential0(InetSocketAddress ... addresses) {
        if (addresses.length == 1) {
            return DnsServerAddresses.singleton(addresses[0]);
        }
        return new DefaultDnsServerAddresses("sequential", addresses){

            @Override
            public DnsServerAddressStream stream() {
                return new SequentialDnsServerAddressStream(this.addresses, 0);
            }
        };
    }

    public static DnsServerAddresses shuffled(Iterable<? extends InetSocketAddress> addresses) {
        return DnsServerAddresses.shuffled0(DnsServerAddresses.sanitize(addresses));
    }

    public static DnsServerAddresses shuffled(InetSocketAddress ... addresses) {
        return DnsServerAddresses.shuffled0(DnsServerAddresses.sanitize(addresses));
    }

    private static DnsServerAddresses shuffled0(InetSocketAddress[] addresses) {
        if (addresses.length == 1) {
            return DnsServerAddresses.singleton(addresses[0]);
        }
        return new DefaultDnsServerAddresses("shuffled", addresses){

            @Override
            public DnsServerAddressStream stream() {
                return new ShuffledDnsServerAddressStream(this.addresses);
            }
        };
    }

    public static DnsServerAddresses rotational(Iterable<? extends InetSocketAddress> addresses) {
        return DnsServerAddresses.rotational0(DnsServerAddresses.sanitize(addresses));
    }

    public static DnsServerAddresses rotational(InetSocketAddress ... addresses) {
        return DnsServerAddresses.rotational0(DnsServerAddresses.sanitize(addresses));
    }

    private static DnsServerAddresses rotational0(InetSocketAddress[] addresses) {
        if (addresses.length == 1) {
            return DnsServerAddresses.singleton(addresses[0]);
        }
        return new RotationalDnsServerAddresses(addresses);
    }

    public static DnsServerAddresses singleton(InetSocketAddress address) {
        if (address == null) {
            throw new NullPointerException("address");
        }
        if (address.isUnresolved()) {
            throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + address);
        }
        return new SingletonDnsServerAddresses(address);
    }

    private static InetSocketAddress[] sanitize(Iterable<? extends InetSocketAddress> addresses) {
        if (addresses == null) {
            throw new NullPointerException("addresses");
        }
        ArrayList<InetSocketAddress> list = addresses instanceof Collection ? new ArrayList(((Collection)addresses).size()) : new ArrayList<InetSocketAddress>(4);
        for (InetSocketAddress inetSocketAddress : addresses) {
            if (inetSocketAddress == null) break;
            if (inetSocketAddress.isUnresolved()) {
                throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + inetSocketAddress);
            }
            list.add(inetSocketAddress);
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("empty addresses");
        }
        return list.toArray(new InetSocketAddress[list.size()]);
    }

    private static InetSocketAddress[] sanitize(InetSocketAddress[] addresses) {
        if (addresses == null) {
            throw new NullPointerException("addresses");
        }
        ArrayList<InetSocketAddress> list = new ArrayList<InetSocketAddress>(addresses.length);
        for (InetSocketAddress a : addresses) {
            if (a == null) break;
            if (a.isUnresolved()) {
                throw new IllegalArgumentException("cannot use an unresolved DNS server address: " + a);
            }
            list.add(a);
        }
        if (list.isEmpty()) {
            return DEFAULT_NAME_SERVER_ARRAY;
        }
        return list.toArray(new InetSocketAddress[list.size()]);
    }

    public abstract DnsServerAddressStream stream();

    static {
        ArrayList<InetSocketAddress> defaultNameServers = new ArrayList<InetSocketAddress>(2);
        try {
            Class<?> configClass = Class.forName("sun.net.dns.ResolverConfiguration");
            Method open = configClass.getMethod("open", new Class[0]);
            Method nameservers = configClass.getMethod("nameservers", new Class[0]);
            Object instance = open.invoke(null, new Object[0]);
            List list = (List)nameservers.invoke(instance, new Object[0]);
            for (String a : list) {
                if (a == null) continue;
                defaultNameServers.add(new InetSocketAddress(SocketUtils.addressByName(a), 53));
            }
        } catch (Exception exception) {
            // empty catch block
        }
        if (!defaultNameServers.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Default DNS servers: {} (sun.net.dns.ResolverConfiguration)", (Object)defaultNameServers);
            }
        } else {
            Collections.addAll(defaultNameServers, SocketUtils.socketAddress("8.8.8.8", 53), SocketUtils.socketAddress("8.8.4.4", 53));
            if (logger.isWarnEnabled()) {
                logger.warn("Default DNS servers: {} (Google Public DNS as a fallback)", (Object)defaultNameServers);
            }
        }
        DEFAULT_NAME_SERVER_LIST = Collections.unmodifiableList(defaultNameServers);
        DEFAULT_NAME_SERVER_ARRAY = defaultNameServers.toArray(new InetSocketAddress[defaultNameServers.size()]);
        DEFAULT_NAME_SERVERS = DnsServerAddresses.sequential(DEFAULT_NAME_SERVER_ARRAY);
    }
}

