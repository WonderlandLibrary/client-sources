/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.dns.AbstractDnsRecord;
import io.netty.handler.codec.dns.DnsMessageUtil;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultDnsRawRecord
extends AbstractDnsRecord
implements DnsRawRecord {
    private final ByteBuf content;

    public DefaultDnsRawRecord(String string, DnsRecordType dnsRecordType, long l, ByteBuf byteBuf) {
        this(string, dnsRecordType, 1, l, byteBuf);
    }

    public DefaultDnsRawRecord(String string, DnsRecordType dnsRecordType, int n, long l, ByteBuf byteBuf) {
        super(string, dnsRecordType, n, l);
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
    }

    @Override
    public ByteBuf content() {
        return this.content;
    }

    @Override
    public DnsRawRecord copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public DnsRawRecord duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public DnsRawRecord retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public DnsRawRecord replace(ByteBuf byteBuf) {
        return new DefaultDnsRawRecord(this.name(), this.type(), this.dnsClass(), this.timeToLive(), byteBuf);
    }

    @Override
    public int refCnt() {
        return this.content().refCnt();
    }

    @Override
    public DnsRawRecord retain() {
        this.content().retain();
        return this;
    }

    @Override
    public DnsRawRecord retain(int n) {
        this.content().retain(n);
        return this;
    }

    @Override
    public boolean release() {
        return this.content().release();
    }

    @Override
    public boolean release(int n) {
        return this.content().release(n);
    }

    @Override
    public DnsRawRecord touch() {
        this.content().touch();
        return this;
    }

    @Override
    public DnsRawRecord touch(Object object) {
        this.content().touch(object);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('(');
        DnsRecordType dnsRecordType = this.type();
        if (dnsRecordType != DnsRecordType.OPT) {
            stringBuilder.append(this.name().isEmpty() ? "<root>" : this.name()).append(' ').append(this.timeToLive()).append(' ');
            DnsMessageUtil.appendRecordClass(stringBuilder, this.dnsClass()).append(' ').append(dnsRecordType.name());
        } else {
            stringBuilder.append("OPT flags:").append(this.timeToLive()).append(" udp:").append(this.dnsClass());
        }
        stringBuilder.append(' ').append(this.content().readableBytes()).append("B)");
        return stringBuilder.toString();
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
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

