/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.dns.AbstractDnsOptPseudoRrRecord;
import io.netty.handler.codec.dns.DnsOptEcsRecord;
import java.util.Arrays;

public final class DefaultDnsOptEcsRecord
extends AbstractDnsOptPseudoRrRecord
implements DnsOptEcsRecord {
    private final int srcPrefixLength;
    private final byte[] address;

    public DefaultDnsOptEcsRecord(int n, int n2, int n3, int n4, byte[] byArray) {
        super(n, n2, n3);
        this.srcPrefixLength = n4;
        this.address = (byte[])DefaultDnsOptEcsRecord.verifyAddress(byArray).clone();
    }

    public DefaultDnsOptEcsRecord(int n, int n2, byte[] byArray) {
        this(n, 0, 0, n2, byArray);
    }

    public DefaultDnsOptEcsRecord(int n, InternetProtocolFamily internetProtocolFamily) {
        this(n, 0, 0, 0, internetProtocolFamily.localhost().getAddress());
    }

    private static byte[] verifyAddress(byte[] byArray) {
        if (byArray.length == 4 || byArray.length == 16) {
            return byArray;
        }
        throw new IllegalArgumentException("bytes.length must either 4 or 16");
    }

    @Override
    public int sourcePrefixLength() {
        return this.srcPrefixLength;
    }

    @Override
    public int scopePrefixLength() {
        return 1;
    }

    @Override
    public byte[] address() {
        return (byte[])this.address.clone();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = this.toStringBuilder();
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.append(" address:").append(Arrays.toString(this.address)).append(" sourcePrefixLength:").append(this.sourcePrefixLength()).append(" scopePrefixLength:").append(this.scopePrefixLength()).append(')').toString();
    }
}

