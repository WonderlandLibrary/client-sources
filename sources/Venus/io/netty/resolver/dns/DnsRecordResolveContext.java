/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsResolveContext;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.util.ReferenceCountUtil;
import java.net.UnknownHostException;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class DnsRecordResolveContext
extends DnsResolveContext<DnsRecord> {
    DnsRecordResolveContext(DnsNameResolver dnsNameResolver, DnsQuestion dnsQuestion, DnsRecord[] dnsRecordArray, DnsServerAddressStream dnsServerAddressStream) {
        this(dnsNameResolver, dnsQuestion.name(), dnsQuestion.dnsClass(), new DnsRecordType[]{dnsQuestion.type()}, dnsRecordArray, dnsServerAddressStream);
    }

    private DnsRecordResolveContext(DnsNameResolver dnsNameResolver, String string, int n, DnsRecordType[] dnsRecordTypeArray, DnsRecord[] dnsRecordArray, DnsServerAddressStream dnsServerAddressStream) {
        super(dnsNameResolver, string, n, dnsRecordTypeArray, dnsRecordArray, dnsServerAddressStream);
    }

    @Override
    DnsResolveContext<DnsRecord> newResolverContext(DnsNameResolver dnsNameResolver, String string, int n, DnsRecordType[] dnsRecordTypeArray, DnsRecord[] dnsRecordArray, DnsServerAddressStream dnsServerAddressStream) {
        return new DnsRecordResolveContext(dnsNameResolver, string, n, dnsRecordTypeArray, dnsRecordArray, dnsServerAddressStream);
    }

    @Override
    DnsRecord convertRecord(DnsRecord dnsRecord, String string, DnsRecord[] dnsRecordArray, EventLoop eventLoop) {
        return ReferenceCountUtil.retain(dnsRecord);
    }

    @Override
    List<DnsRecord> filterResults(List<DnsRecord> list) {
        return list;
    }

    @Override
    void cache(String string, DnsRecord[] dnsRecordArray, DnsRecord dnsRecord, DnsRecord dnsRecord2) {
    }

    @Override
    void cache(String string, DnsRecord[] dnsRecordArray, UnknownHostException unknownHostException) {
    }

    @Override
    void cache(String string, DnsRecord[] dnsRecordArray, DnsRecord dnsRecord, Object object) {
        this.cache(string, dnsRecordArray, dnsRecord, (DnsRecord)object);
    }

    @Override
    Object convertRecord(DnsRecord dnsRecord, String string, DnsRecord[] dnsRecordArray, EventLoop eventLoop) {
        return this.convertRecord(dnsRecord, string, dnsRecordArray, eventLoop);
    }
}

