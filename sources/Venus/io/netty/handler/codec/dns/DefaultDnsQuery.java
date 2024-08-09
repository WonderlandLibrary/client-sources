/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.AbstractDnsMessage;
import io.netty.handler.codec.dns.DnsMessage;
import io.netty.handler.codec.dns.DnsMessageUtil;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultDnsQuery
extends AbstractDnsMessage
implements DnsQuery {
    public DefaultDnsQuery(int n) {
        super(n);
    }

    public DefaultDnsQuery(int n, DnsOpCode dnsOpCode) {
        super(n, dnsOpCode);
    }

    @Override
    public DnsQuery setId(int n) {
        return (DnsQuery)super.setId(n);
    }

    @Override
    public DnsQuery setOpCode(DnsOpCode dnsOpCode) {
        return (DnsQuery)super.setOpCode(dnsOpCode);
    }

    @Override
    public DnsQuery setRecursionDesired(boolean bl) {
        return (DnsQuery)super.setRecursionDesired(bl);
    }

    @Override
    public DnsQuery setZ(int n) {
        return (DnsQuery)super.setZ(n);
    }

    @Override
    public DnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsQuery)super.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsQuery)super.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsQuery addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return (DnsQuery)super.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DnsQuery clear(DnsSection dnsSection) {
        return (DnsQuery)super.clear(dnsSection);
    }

    @Override
    public DnsQuery clear() {
        return (DnsQuery)super.clear();
    }

    @Override
    public DnsQuery touch() {
        return (DnsQuery)super.touch();
    }

    @Override
    public DnsQuery touch(Object object) {
        return (DnsQuery)super.touch(object);
    }

    @Override
    public DnsQuery retain() {
        return (DnsQuery)super.retain();
    }

    @Override
    public DnsQuery retain(int n) {
        return (DnsQuery)super.retain(n);
    }

    public String toString() {
        return DnsMessageUtil.appendQuery(new StringBuilder(128), this).toString();
    }

    @Override
    public DnsMessage retain(int n) {
        return this.retain(n);
    }

    @Override
    public DnsMessage retain() {
        return this.retain();
    }

    @Override
    public DnsMessage touch(Object object) {
        return this.touch(object);
    }

    @Override
    public DnsMessage touch() {
        return this.touch();
    }

    @Override
    public DnsMessage clear() {
        return this.clear();
    }

    @Override
    public DnsMessage clear(DnsSection dnsSection) {
        return this.clear(dnsSection);
    }

    @Override
    public DnsMessage addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return this.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DnsMessage addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return this.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsMessage setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return this.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsMessage setZ(int n) {
        return this.setZ(n);
    }

    @Override
    public DnsMessage setRecursionDesired(boolean bl) {
        return this.setRecursionDesired(bl);
    }

    @Override
    public DnsMessage setOpCode(DnsOpCode dnsOpCode) {
        return this.setOpCode(dnsOpCode);
    }

    @Override
    public DnsMessage setId(int n) {
        return this.setId(n);
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}

