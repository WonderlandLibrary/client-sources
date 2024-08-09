/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.dns.DnsOptEcsRecord;
import io.netty.handler.codec.dns.DnsOptPseudoRecord;
import io.netty.handler.codec.dns.DnsPtrRecord;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordEncoder;
import io.netty.util.internal.StringUtil;

public class DefaultDnsRecordEncoder
implements DnsRecordEncoder {
    private static final int PREFIX_MASK = 7;

    protected DefaultDnsRecordEncoder() {
    }

    @Override
    public final void encodeQuestion(DnsQuestion dnsQuestion, ByteBuf byteBuf) throws Exception {
        this.encodeName(dnsQuestion.name(), byteBuf);
        byteBuf.writeShort(dnsQuestion.type().intValue());
        byteBuf.writeShort(dnsQuestion.dnsClass());
    }

    @Override
    public void encodeRecord(DnsRecord dnsRecord, ByteBuf byteBuf) throws Exception {
        if (dnsRecord instanceof DnsQuestion) {
            this.encodeQuestion((DnsQuestion)dnsRecord, byteBuf);
        } else if (dnsRecord instanceof DnsPtrRecord) {
            this.encodePtrRecord((DnsPtrRecord)dnsRecord, byteBuf);
        } else if (dnsRecord instanceof DnsOptEcsRecord) {
            this.encodeOptEcsRecord((DnsOptEcsRecord)dnsRecord, byteBuf);
        } else if (dnsRecord instanceof DnsOptPseudoRecord) {
            this.encodeOptPseudoRecord((DnsOptPseudoRecord)dnsRecord, byteBuf);
        } else if (dnsRecord instanceof DnsRawRecord) {
            this.encodeRawRecord((DnsRawRecord)dnsRecord, byteBuf);
        } else {
            throw new UnsupportedMessageTypeException(StringUtil.simpleClassName(dnsRecord));
        }
    }

    private void encodeRecord0(DnsRecord dnsRecord, ByteBuf byteBuf) throws Exception {
        this.encodeName(dnsRecord.name(), byteBuf);
        byteBuf.writeShort(dnsRecord.type().intValue());
        byteBuf.writeShort(dnsRecord.dnsClass());
        byteBuf.writeInt((int)dnsRecord.timeToLive());
    }

    private void encodePtrRecord(DnsPtrRecord dnsPtrRecord, ByteBuf byteBuf) throws Exception {
        this.encodeRecord0(dnsPtrRecord, byteBuf);
        this.encodeName(dnsPtrRecord.hostname(), byteBuf);
    }

    private void encodeOptPseudoRecord(DnsOptPseudoRecord dnsOptPseudoRecord, ByteBuf byteBuf) throws Exception {
        this.encodeRecord0(dnsOptPseudoRecord, byteBuf);
        byteBuf.writeShort(0);
    }

    private void encodeOptEcsRecord(DnsOptEcsRecord dnsOptEcsRecord, ByteBuf byteBuf) throws Exception {
        this.encodeRecord0(dnsOptEcsRecord, byteBuf);
        int n = dnsOptEcsRecord.sourcePrefixLength();
        int n2 = dnsOptEcsRecord.scopePrefixLength();
        int n3 = n & 7;
        byte[] byArray = dnsOptEcsRecord.address();
        int n4 = byArray.length << 3;
        if (n4 < n || n < 0) {
            throw new IllegalArgumentException(n + ": " + n + " (expected: 0 >= " + n4 + ')');
        }
        short s = (short)(byArray.length == 4 ? InternetProtocolFamily.IPv4.addressNumber() : InternetProtocolFamily.IPv6.addressNumber());
        int n5 = DefaultDnsRecordEncoder.calculateEcsAddressLength(n, n3);
        int n6 = 8 + n5;
        byteBuf.writeShort(n6);
        byteBuf.writeShort(8);
        byteBuf.writeShort(n6 - 4);
        byteBuf.writeShort(s);
        byteBuf.writeByte(n);
        byteBuf.writeByte(n2);
        if (n3 > 0) {
            int n7 = n5 - 1;
            byteBuf.writeBytes(byArray, 0, n7);
            byteBuf.writeByte(DefaultDnsRecordEncoder.padWithZeros(byArray[n7], n3));
        } else {
            byteBuf.writeBytes(byArray, 0, n5);
        }
    }

    static int calculateEcsAddressLength(int n, int n2) {
        return (n >>> 3) + (n2 != 0 ? 1 : 0);
    }

    private void encodeRawRecord(DnsRawRecord dnsRawRecord, ByteBuf byteBuf) throws Exception {
        this.encodeRecord0(dnsRawRecord, byteBuf);
        ByteBuf byteBuf2 = dnsRawRecord.content();
        int n = byteBuf2.readableBytes();
        byteBuf.writeShort(n);
        byteBuf.writeBytes(byteBuf2, byteBuf2.readerIndex(), n);
    }

    protected void encodeName(String string, ByteBuf byteBuf) throws Exception {
        String string2;
        int n;
        String[] stringArray;
        if (".".equals(string)) {
            byteBuf.writeByte(0);
            return;
        }
        String[] stringArray2 = stringArray = string.split("\\.");
        int n2 = stringArray2.length;
        for (int i = 0; i < n2 && (n = (string2 = stringArray2[i]).length()) != 0; ++i) {
            byteBuf.writeByte(n);
            ByteBufUtil.writeAscii(byteBuf, (CharSequence)string2);
        }
        byteBuf.writeByte(0);
    }

    private static byte padWithZeros(byte by, int n) {
        switch (n) {
            case 0: {
                return 1;
            }
            case 1: {
                return (byte)(0x80 & by);
            }
            case 2: {
                return (byte)(0xC0 & by);
            }
            case 3: {
                return (byte)(0xE0 & by);
            }
            case 4: {
                return (byte)(0xF0 & by);
            }
            case 5: {
                return (byte)(0xF8 & by);
            }
            case 6: {
                return (byte)(0xFC & by);
            }
            case 7: {
                return (byte)(0xFE & by);
            }
            case 8: {
                return by;
            }
        }
        throw new IllegalArgumentException("lowOrderBitsToPreserve: " + n);
    }
}

