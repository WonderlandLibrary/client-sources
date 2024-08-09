/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.DnsMessageUtil;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

public abstract class AbstractDnsRecord
implements DnsRecord {
    private final String name;
    private final DnsRecordType type;
    private final short dnsClass;
    private final long timeToLive;
    private int hashCode;

    protected AbstractDnsRecord(String string, DnsRecordType dnsRecordType, long l) {
        this(string, dnsRecordType, 1, l);
    }

    protected AbstractDnsRecord(String string, DnsRecordType dnsRecordType, int n, long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("timeToLive: " + l + " (expected: >= 0)");
        }
        this.name = AbstractDnsRecord.appendTrailingDot(IDN.toASCII(ObjectUtil.checkNotNull(string, "name")));
        this.type = ObjectUtil.checkNotNull(dnsRecordType, "type");
        this.dnsClass = (short)n;
        this.timeToLive = l;
    }

    private static String appendTrailingDot(String string) {
        if (string.length() > 0 && string.charAt(string.length() - 1) != '.') {
            return string + '.';
        }
        return string;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public DnsRecordType type() {
        return this.type;
    }

    @Override
    public int dnsClass() {
        return this.dnsClass & 0xFFFF;
    }

    @Override
    public long timeToLive() {
        return this.timeToLive;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof DnsRecord)) {
            return true;
        }
        DnsRecord dnsRecord = (DnsRecord)object;
        int n = this.hashCode;
        if (n != 0 && n != dnsRecord.hashCode()) {
            return true;
        }
        return this.type().intValue() == dnsRecord.type().intValue() && this.dnsClass() == dnsRecord.dnsClass() && this.name().equals(dnsRecord.name());
    }

    public int hashCode() {
        int n = this.hashCode;
        if (n != 0) {
            return n;
        }
        this.hashCode = this.name.hashCode() * 31 + this.type().intValue() * 31 + this.dnsClass();
        return this.hashCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append(StringUtil.simpleClassName(this)).append('(').append(this.name()).append(' ').append(this.timeToLive()).append(' ');
        DnsMessageUtil.appendRecordClass(stringBuilder, this.dnsClass()).append(' ').append(this.type().name()).append(')');
        return stringBuilder.toString();
    }
}

