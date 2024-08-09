/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.resolver.dns.DnsAddressDecoder;
import io.netty.resolver.dns.DnsCache;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsResolveContext;
import io.netty.resolver.dns.DnsServerAddressStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class DnsAddressResolveContext
extends DnsResolveContext<InetAddress> {
    private final DnsCache resolveCache;

    DnsAddressResolveContext(DnsNameResolver dnsNameResolver, String string, DnsRecord[] dnsRecordArray, DnsServerAddressStream dnsServerAddressStream, DnsCache dnsCache) {
        super(dnsNameResolver, string, 1, dnsNameResolver.resolveRecordTypes(), dnsRecordArray, dnsServerAddressStream);
        this.resolveCache = dnsCache;
    }

    @Override
    DnsResolveContext<InetAddress> newResolverContext(DnsNameResolver dnsNameResolver, String string, int n, DnsRecordType[] dnsRecordTypeArray, DnsRecord[] dnsRecordArray, DnsServerAddressStream dnsServerAddressStream) {
        return new DnsAddressResolveContext(dnsNameResolver, string, dnsRecordArray, dnsServerAddressStream, this.resolveCache);
    }

    @Override
    InetAddress convertRecord(DnsRecord dnsRecord, String string, DnsRecord[] dnsRecordArray, EventLoop eventLoop) {
        return DnsAddressDecoder.decodeAddress(dnsRecord, string, this.parent.isDecodeIdn());
    }

    @Override
    List<InetAddress> filterResults(List<InetAddress> list) {
        Class<? extends InetAddress> clazz = this.parent.preferredAddressType().addressType();
        int n = list.size();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            InetAddress inetAddress = list.get(i);
            if (!clazz.isInstance(inetAddress)) continue;
            ++n2;
        }
        if (n2 == n || n2 == 0) {
            return list;
        }
        ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>(n2);
        for (int i = 0; i < n; ++i) {
            InetAddress inetAddress = list.get(i);
            if (!clazz.isInstance(inetAddress)) continue;
            arrayList.add(inetAddress);
        }
        return arrayList;
    }

    @Override
    void cache(String string, DnsRecord[] dnsRecordArray, DnsRecord dnsRecord, InetAddress inetAddress) {
        this.resolveCache.cache(string, dnsRecordArray, inetAddress, dnsRecord.timeToLive(), this.parent.ch.eventLoop());
    }

    @Override
    void cache(String string, DnsRecord[] dnsRecordArray, UnknownHostException unknownHostException) {
        this.resolveCache.cache(string, dnsRecordArray, unknownHostException, this.parent.ch.eventLoop());
    }

    @Override
    void cache(String string, DnsRecord[] dnsRecordArray, DnsRecord dnsRecord, Object object) {
        this.cache(string, dnsRecordArray, dnsRecord, (InetAddress)object);
    }

    @Override
    Object convertRecord(DnsRecord dnsRecord, String string, DnsRecord[] dnsRecordArray, EventLoop eventLoop) {
        return this.convertRecord(dnsRecord, string, dnsRecordArray, eventLoop);
    }
}

