/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.resolver.dns.DnsQueryContext;
import io.netty.util.NetUtil;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.PlatformDependent;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

final class DnsQueryContextManager {
    final Map<InetSocketAddress, IntObjectMap<DnsQueryContext>> map = new HashMap<InetSocketAddress, IntObjectMap<DnsQueryContext>>();

    DnsQueryContextManager() {
    }

    int add(DnsQueryContext dnsQueryContext) {
        IntObjectMap<DnsQueryContext> intObjectMap = this.getOrCreateContextMap(dnsQueryContext.nameServerAddr());
        int n = PlatformDependent.threadLocalRandom().nextInt(65535) + 1;
        int n2 = 131070;
        int n3 = 0;
        IntObjectMap<DnsQueryContext> intObjectMap2 = intObjectMap;
        synchronized (intObjectMap2) {
            do {
                if (!intObjectMap.containsKey(n)) {
                    intObjectMap.put(n, dnsQueryContext);
                    return n;
                }
                n = n + 1 & 0xFFFF;
            } while (++n3 < 131070);
            throw new IllegalStateException("query ID space exhausted: " + dnsQueryContext.question());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    DnsQueryContext get(InetSocketAddress inetSocketAddress, int n) {
        DnsQueryContext dnsQueryContext;
        IntObjectMap<DnsQueryContext> intObjectMap = this.getContextMap(inetSocketAddress);
        if (intObjectMap != null) {
            IntObjectMap<DnsQueryContext> intObjectMap2 = intObjectMap;
            synchronized (intObjectMap2) {
                dnsQueryContext = intObjectMap.get(n);
            }
        } else {
            dnsQueryContext = null;
        }
        return dnsQueryContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    DnsQueryContext remove(InetSocketAddress inetSocketAddress, int n) {
        IntObjectMap<DnsQueryContext> intObjectMap = this.getContextMap(inetSocketAddress);
        if (intObjectMap == null) {
            return null;
        }
        IntObjectMap<DnsQueryContext> intObjectMap2 = intObjectMap;
        synchronized (intObjectMap2) {
            return intObjectMap.remove(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IntObjectMap<DnsQueryContext> getContextMap(InetSocketAddress inetSocketAddress) {
        Map<InetSocketAddress, IntObjectMap<DnsQueryContext>> map = this.map;
        synchronized (map) {
            return this.map.get(inetSocketAddress);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IntObjectMap<DnsQueryContext> getOrCreateContextMap(InetSocketAddress inetSocketAddress) {
        Map<InetSocketAddress, IntObjectMap<DnsQueryContext>> map = this.map;
        synchronized (map) {
            IntObjectMap<DnsQueryContext> intObjectMap = this.map.get(inetSocketAddress);
            if (intObjectMap != null) {
                return intObjectMap;
            }
            IntObjectHashMap<DnsQueryContext> intObjectHashMap = new IntObjectHashMap<DnsQueryContext>();
            InetAddress inetAddress = inetSocketAddress.getAddress();
            int n = inetSocketAddress.getPort();
            this.map.put(inetSocketAddress, intObjectHashMap);
            if (inetAddress instanceof Inet4Address) {
                Inet4Address inet4Address = (Inet4Address)inetAddress;
                if (inet4Address.isLoopbackAddress()) {
                    this.map.put(new InetSocketAddress(NetUtil.LOCALHOST6, n), intObjectHashMap);
                } else {
                    this.map.put(new InetSocketAddress(DnsQueryContextManager.toCompactAddress(inet4Address), n), intObjectHashMap);
                }
            } else if (inetAddress instanceof Inet6Address) {
                Inet6Address inet6Address = (Inet6Address)inetAddress;
                if (inet6Address.isLoopbackAddress()) {
                    this.map.put(new InetSocketAddress(NetUtil.LOCALHOST4, n), intObjectHashMap);
                } else if (inet6Address.isIPv4CompatibleAddress()) {
                    this.map.put(new InetSocketAddress(DnsQueryContextManager.toIPv4Address(inet6Address), n), intObjectHashMap);
                }
            }
            return intObjectHashMap;
        }
    }

    private static Inet6Address toCompactAddress(Inet4Address inet4Address) {
        byte[] byArray = inet4Address.getAddress();
        byte[] byArray2 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, byArray[0], byArray[1], byArray[2], byArray[3]};
        try {
            return (Inet6Address)InetAddress.getByAddress(byArray2);
        } catch (UnknownHostException unknownHostException) {
            throw new Error(unknownHostException);
        }
    }

    private static Inet4Address toIPv4Address(Inet6Address inet6Address) {
        byte[] byArray = inet6Address.getAddress();
        byte[] byArray2 = new byte[]{byArray[12], byArray[13], byArray[14], byArray[15]};
        try {
            return (Inet4Address)InetAddress.getByAddress(byArray2);
        } catch (UnknownHostException unknownHostException) {
            throw new Error(unknownHostException);
        }
    }
}

