/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.AbstractDnsRecord;
import io.netty.handler.codec.dns.DnsMessageUtil;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.util.internal.StringUtil;

public class DefaultDnsQuestion
extends AbstractDnsRecord
implements DnsQuestion {
    public DefaultDnsQuestion(String string, DnsRecordType dnsRecordType) {
        super(string, dnsRecordType, 0L);
    }

    public DefaultDnsQuestion(String string, DnsRecordType dnsRecordType, int n) {
        super(string, dnsRecordType, n, 0L);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append(StringUtil.simpleClassName(this)).append('(').append(this.name()).append(' ');
        DnsMessageUtil.appendRecordClass(stringBuilder, this.dnsClass()).append(' ').append(this.type().name()).append(')');
        return stringBuilder.toString();
    }
}

