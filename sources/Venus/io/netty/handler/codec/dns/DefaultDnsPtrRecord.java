/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.AbstractDnsRecord;
import io.netty.handler.codec.dns.DnsMessageUtil;
import io.netty.handler.codec.dns.DnsPtrRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

public class DefaultDnsPtrRecord
extends AbstractDnsRecord
implements DnsPtrRecord {
    private final String hostname;

    public DefaultDnsPtrRecord(String string, int n, long l, String string2) {
        super(string, DnsRecordType.PTR, n, l);
        this.hostname = ObjectUtil.checkNotNull(string2, "hostname");
    }

    @Override
    public String hostname() {
        return this.hostname;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('(');
        DnsRecordType dnsRecordType = this.type();
        stringBuilder.append(this.name().isEmpty() ? "<root>" : this.name()).append(' ').append(this.timeToLive()).append(' ');
        DnsMessageUtil.appendRecordClass(stringBuilder, this.dnsClass()).append(' ').append(dnsRecordType.name());
        stringBuilder.append(' ').append(this.hostname);
        return stringBuilder.toString();
    }
}

