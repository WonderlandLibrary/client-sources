/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.channel.AddressedEnvelope;
import io.netty.handler.codec.dns.DefaultDnsResponse;
import io.netty.handler.codec.dns.DnsMessage;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.ReferenceCounted;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DatagramDnsResponse
extends DefaultDnsResponse
implements AddressedEnvelope<DatagramDnsResponse, InetSocketAddress> {
    private final InetSocketAddress sender;
    private final InetSocketAddress recipient;

    public DatagramDnsResponse(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int n) {
        this(inetSocketAddress, inetSocketAddress2, n, DnsOpCode.QUERY, DnsResponseCode.NOERROR);
    }

    public DatagramDnsResponse(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int n, DnsOpCode dnsOpCode) {
        this(inetSocketAddress, inetSocketAddress2, n, dnsOpCode, DnsResponseCode.NOERROR);
    }

    public DatagramDnsResponse(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int n, DnsOpCode dnsOpCode, DnsResponseCode dnsResponseCode) {
        super(n, dnsOpCode, dnsResponseCode);
        if (inetSocketAddress2 == null && inetSocketAddress == null) {
            throw new NullPointerException("recipient and sender");
        }
        this.sender = inetSocketAddress;
        this.recipient = inetSocketAddress2;
    }

    @Override
    public DatagramDnsResponse content() {
        return this;
    }

    @Override
    public InetSocketAddress sender() {
        return this.sender;
    }

    @Override
    public InetSocketAddress recipient() {
        return this.recipient;
    }

    @Override
    public DatagramDnsResponse setAuthoritativeAnswer(boolean bl) {
        return (DatagramDnsResponse)super.setAuthoritativeAnswer(bl);
    }

    @Override
    public DatagramDnsResponse setTruncated(boolean bl) {
        return (DatagramDnsResponse)super.setTruncated(bl);
    }

    @Override
    public DatagramDnsResponse setRecursionAvailable(boolean bl) {
        return (DatagramDnsResponse)super.setRecursionAvailable(bl);
    }

    @Override
    public DatagramDnsResponse setCode(DnsResponseCode dnsResponseCode) {
        return (DatagramDnsResponse)super.setCode(dnsResponseCode);
    }

    @Override
    public DatagramDnsResponse setId(int n) {
        return (DatagramDnsResponse)super.setId(n);
    }

    @Override
    public DatagramDnsResponse setOpCode(DnsOpCode dnsOpCode) {
        return (DatagramDnsResponse)super.setOpCode(dnsOpCode);
    }

    @Override
    public DatagramDnsResponse setRecursionDesired(boolean bl) {
        return (DatagramDnsResponse)super.setRecursionDesired(bl);
    }

    @Override
    public DatagramDnsResponse setZ(int n) {
        return (DatagramDnsResponse)super.setZ(n);
    }

    @Override
    public DatagramDnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsResponse)super.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DatagramDnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsResponse)super.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DatagramDnsResponse addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return (DatagramDnsResponse)super.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DatagramDnsResponse clear(DnsSection dnsSection) {
        return (DatagramDnsResponse)super.clear(dnsSection);
    }

    @Override
    public DatagramDnsResponse clear() {
        return (DatagramDnsResponse)super.clear();
    }

    @Override
    public DatagramDnsResponse touch() {
        return (DatagramDnsResponse)super.touch();
    }

    @Override
    public DatagramDnsResponse touch(Object object) {
        return (DatagramDnsResponse)super.touch(object);
    }

    @Override
    public DatagramDnsResponse retain() {
        return (DatagramDnsResponse)super.retain();
    }

    @Override
    public DatagramDnsResponse retain(int n) {
        return (DatagramDnsResponse)super.retain(n);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!super.equals(object)) {
            return true;
        }
        if (!(object instanceof AddressedEnvelope)) {
            return true;
        }
        AddressedEnvelope addressedEnvelope = (AddressedEnvelope)object;
        if (this.sender() == null ? addressedEnvelope.sender() != null : !this.sender().equals(addressedEnvelope.sender())) {
            return true;
        }
        return this.recipient() == null ? addressedEnvelope.recipient() != null : !this.recipient().equals(addressedEnvelope.recipient());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        if (this.sender() != null) {
            n = n * 31 + this.sender().hashCode();
        }
        if (this.recipient() != null) {
            n = n * 31 + this.recipient().hashCode();
        }
        return n;
    }

    @Override
    public DnsResponse retain(int n) {
        return this.retain(n);
    }

    @Override
    public DnsResponse retain() {
        return this.retain();
    }

    @Override
    public DnsResponse touch(Object object) {
        return this.touch(object);
    }

    @Override
    public DnsResponse touch() {
        return this.touch();
    }

    @Override
    public DnsResponse clear() {
        return this.clear();
    }

    @Override
    public DnsResponse clear(DnsSection dnsSection) {
        return this.clear(dnsSection);
    }

    @Override
    public DnsResponse addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return this.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DnsResponse addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return this.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsResponse setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return this.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsResponse setZ(int n) {
        return this.setZ(n);
    }

    @Override
    public DnsResponse setRecursionDesired(boolean bl) {
        return this.setRecursionDesired(bl);
    }

    @Override
    public DnsResponse setOpCode(DnsOpCode dnsOpCode) {
        return this.setOpCode(dnsOpCode);
    }

    @Override
    public DnsResponse setId(int n) {
        return this.setId(n);
    }

    @Override
    public DnsResponse setCode(DnsResponseCode dnsResponseCode) {
        return this.setCode(dnsResponseCode);
    }

    @Override
    public DnsResponse setRecursionAvailable(boolean bl) {
        return this.setRecursionAvailable(bl);
    }

    @Override
    public DnsResponse setTruncated(boolean bl) {
        return this.setTruncated(bl);
    }

    @Override
    public DnsResponse setAuthoritativeAnswer(boolean bl) {
        return this.setAuthoritativeAnswer(bl);
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

    @Override
    public AddressedEnvelope touch(Object object) {
        return this.touch(object);
    }

    @Override
    public AddressedEnvelope touch() {
        return this.touch();
    }

    @Override
    public AddressedEnvelope retain(int n) {
        return this.retain(n);
    }

    @Override
    public AddressedEnvelope retain() {
        return this.retain();
    }

    @Override
    public SocketAddress recipient() {
        return this.recipient();
    }

    @Override
    public SocketAddress sender() {
        return this.sender();
    }

    @Override
    public Object content() {
        return this.content();
    }
}

