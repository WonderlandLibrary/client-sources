/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.AbstractDnsMessage;
import io.netty.handler.codec.dns.DnsMessage;
import io.netty.handler.codec.dns.DnsMessageUtil;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultDnsResponse
extends AbstractDnsMessage
implements DnsResponse {
    private boolean authoritativeAnswer;
    private boolean truncated;
    private boolean recursionAvailable;
    private DnsResponseCode code;

    public DefaultDnsResponse(int n) {
        this(n, DnsOpCode.QUERY, DnsResponseCode.NOERROR);
    }

    public DefaultDnsResponse(int n, DnsOpCode dnsOpCode) {
        this(n, dnsOpCode, DnsResponseCode.NOERROR);
    }

    public DefaultDnsResponse(int n, DnsOpCode dnsOpCode, DnsResponseCode dnsResponseCode) {
        super(n, dnsOpCode);
        this.setCode(dnsResponseCode);
    }

    @Override
    public boolean isAuthoritativeAnswer() {
        return this.authoritativeAnswer;
    }

    @Override
    public DnsResponse setAuthoritativeAnswer(boolean bl) {
        this.authoritativeAnswer = bl;
        return this;
    }

    @Override
    public boolean isTruncated() {
        return this.truncated;
    }

    @Override
    public DnsResponse setTruncated(boolean bl) {
        this.truncated = bl;
        return this;
    }

    @Override
    public boolean isRecursionAvailable() {
        return this.recursionAvailable;
    }

    @Override
    public DnsResponse setRecursionAvailable(boolean bl) {
        this.recursionAvailable = bl;
        return this;
    }

    @Override
    public DnsResponseCode code() {
        return this.code;
    }

    @Override
    public DnsResponse setCode(DnsResponseCode dnsResponseCode) {
        this.code = ObjectUtil.checkNotNull(dnsResponseCode, "code");
        return this;
    }

    @Override
    public DnsResponse setId(int n) {
        return (DnsResponse)super.setId(n);
    }

    @Override
    public DnsResponse setOpCode(DnsOpCode dnsOpCode) {
        return (DnsResponse)super.setOpCode(dnsOpCode);
    }

    @Override
    public DnsResponse setRecursionDesired(boolean bl) {
        return (DnsResponse)super.setRecursionDesired(bl);
    }

    @Override
    public DnsResponse setZ(int n) {
        return (DnsResponse)super.setZ(n);
    }

    @Override
    public DnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsResponse)super.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DnsResponse)super.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsResponse addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return (DnsResponse)super.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DnsResponse clear(DnsSection dnsSection) {
        return (DnsResponse)super.clear(dnsSection);
    }

    @Override
    public DnsResponse clear() {
        return (DnsResponse)super.clear();
    }

    @Override
    public DnsResponse touch() {
        return (DnsResponse)super.touch();
    }

    @Override
    public DnsResponse touch(Object object) {
        return (DnsResponse)super.touch(object);
    }

    @Override
    public DnsResponse retain() {
        return (DnsResponse)super.retain();
    }

    @Override
    public DnsResponse retain(int n) {
        return (DnsResponse)super.retain(n);
    }

    public String toString() {
        return DnsMessageUtil.appendResponse(new StringBuilder(128), this).toString();
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

