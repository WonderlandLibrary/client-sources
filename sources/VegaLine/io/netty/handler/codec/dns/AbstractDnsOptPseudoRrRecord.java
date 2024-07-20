/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.handler.codec.dns.AbstractDnsRecord;
import io.netty.handler.codec.dns.DnsOptPseudoRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.util.internal.StringUtil;

public abstract class AbstractDnsOptPseudoRrRecord
extends AbstractDnsRecord
implements DnsOptPseudoRecord {
    protected AbstractDnsOptPseudoRrRecord(int maxPayloadSize, int extendedRcode, int version) {
        super("", DnsRecordType.OPT, maxPayloadSize, AbstractDnsOptPseudoRrRecord.packIntoLong(extendedRcode, version));
    }

    protected AbstractDnsOptPseudoRrRecord(int maxPayloadSize) {
        super("", DnsRecordType.OPT, maxPayloadSize, 0L);
    }

    private static long packIntoLong(int val, int val2) {
        return (long)((val & 0xFF) << 24 | (val2 & 0xFF) << 16 | 0 | 0) & 0xFFFFFFFFL;
    }

    @Override
    public int extendedRcode() {
        return (short)((int)this.timeToLive() >> 24 & 0xFF);
    }

    @Override
    public int version() {
        return (short)((int)this.timeToLive() >> 16 & 0xFF);
    }

    @Override
    public int flags() {
        return (short)((short)this.timeToLive() & 0xFF);
    }

    @Override
    public String toString() {
        return this.toStringBuilder().toString();
    }

    final StringBuilder toStringBuilder() {
        return new StringBuilder(64).append(StringUtil.simpleClassName(this)).append('(').append("OPT flags:").append(this.flags()).append(" version:").append(this.version()).append(" extendedRecode:").append(this.extendedRcode()).append(" udp:").append(this.dnsClass()).append(')');
    }
}

