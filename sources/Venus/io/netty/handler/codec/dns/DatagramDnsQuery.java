/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.channel.AddressedEnvelope;
import io.netty.handler.codec.dns.DefaultDnsQuery;
import io.netty.handler.codec.dns.DnsMessage;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.ReferenceCounted;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DatagramDnsQuery
extends DefaultDnsQuery
implements AddressedEnvelope<DatagramDnsQuery, InetSocketAddress> {
    private final InetSocketAddress sender;
    private final InetSocketAddress recipient;

    public DatagramDnsQuery(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int n) {
        this(inetSocketAddress, inetSocketAddress2, n, DnsOpCode.QUERY);
    }

    public DatagramDnsQuery(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, int n, DnsOpCode dnsOpCode) {
        super(n, dnsOpCode);
        if (inetSocketAddress2 == null && inetSocketAddress == null) {
            throw new NullPointerException("recipient and sender");
        }
        this.sender = inetSocketAddress;
        this.recipient = inetSocketAddress2;
    }

    @Override
    public DatagramDnsQuery content() {
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
    public DatagramDnsQuery setId(int n) {
        return (DatagramDnsQuery)super.setId(n);
    }

    @Override
    public DatagramDnsQuery setOpCode(DnsOpCode dnsOpCode) {
        return (DatagramDnsQuery)super.setOpCode(dnsOpCode);
    }

    @Override
    public DatagramDnsQuery setRecursionDesired(boolean bl) {
        return (DatagramDnsQuery)super.setRecursionDesired(bl);
    }

    @Override
    public DatagramDnsQuery setZ(int n) {
        return (DatagramDnsQuery)super.setZ(n);
    }

    @Override
    public DatagramDnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsQuery)super.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DatagramDnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return (DatagramDnsQuery)super.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DatagramDnsQuery addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return (DatagramDnsQuery)super.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DatagramDnsQuery clear(DnsSection dnsSection) {
        return (DatagramDnsQuery)super.clear(dnsSection);
    }

    @Override
    public DatagramDnsQuery clear() {
        return (DatagramDnsQuery)super.clear();
    }

    @Override
    public DatagramDnsQuery touch() {
        return (DatagramDnsQuery)super.touch();
    }

    @Override
    public DatagramDnsQuery touch(Object object) {
        return (DatagramDnsQuery)super.touch(object);
    }

    @Override
    public DatagramDnsQuery retain() {
        return (DatagramDnsQuery)super.retain();
    }

    @Override
    public DatagramDnsQuery retain(int n) {
        return (DatagramDnsQuery)super.retain(n);
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
    public DnsQuery retain(int n) {
        return this.retain(n);
    }

    @Override
    public DnsQuery retain() {
        return this.retain();
    }

    @Override
    public DnsQuery touch(Object object) {
        return this.touch(object);
    }

    @Override
    public DnsQuery touch() {
        return this.touch();
    }

    @Override
    public DnsQuery clear() {
        return this.clear();
    }

    @Override
    public DnsQuery clear(DnsSection dnsSection) {
        return this.clear(dnsSection);
    }

    @Override
    public DnsQuery addRecord(DnsSection dnsSection, int n, DnsRecord dnsRecord) {
        return this.addRecord(dnsSection, n, dnsRecord);
    }

    @Override
    public DnsQuery addRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return this.addRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsQuery setRecord(DnsSection dnsSection, DnsRecord dnsRecord) {
        return this.setRecord(dnsSection, dnsRecord);
    }

    @Override
    public DnsQuery setZ(int n) {
        return this.setZ(n);
    }

    @Override
    public DnsQuery setRecursionDesired(boolean bl) {
        return this.setRecursionDesired(bl);
    }

    @Override
    public DnsQuery setOpCode(DnsOpCode dnsOpCode) {
        return this.setOpCode(dnsOpCode);
    }

    @Override
    public DnsQuery setId(int n) {
        return this.setId(n);
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

