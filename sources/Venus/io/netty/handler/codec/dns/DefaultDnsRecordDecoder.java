/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.dns.DefaultDnsPtrRecord;
import io.netty.handler.codec.dns.DefaultDnsQuestion;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordDecoder;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.util.CharsetUtil;

public class DefaultDnsRecordDecoder
implements DnsRecordDecoder {
    static final String ROOT = ".";

    protected DefaultDnsRecordDecoder() {
    }

    @Override
    public final DnsQuestion decodeQuestion(ByteBuf byteBuf) throws Exception {
        String string = DefaultDnsRecordDecoder.decodeName(byteBuf);
        DnsRecordType dnsRecordType = DnsRecordType.valueOf(byteBuf.readUnsignedShort());
        int n = byteBuf.readUnsignedShort();
        return new DefaultDnsQuestion(string, dnsRecordType, n);
    }

    @Override
    public final <T extends DnsRecord> T decodeRecord(ByteBuf byteBuf) throws Exception {
        int n = byteBuf.readerIndex();
        String string = DefaultDnsRecordDecoder.decodeName(byteBuf);
        int n2 = byteBuf.writerIndex();
        if (n2 - n < 10) {
            byteBuf.readerIndex(n);
            return null;
        }
        DnsRecordType dnsRecordType = DnsRecordType.valueOf(byteBuf.readUnsignedShort());
        int n3 = byteBuf.readUnsignedShort();
        long l = byteBuf.readUnsignedInt();
        int n4 = byteBuf.readUnsignedShort();
        int n5 = byteBuf.readerIndex();
        if (n2 - n5 < n4) {
            byteBuf.readerIndex(n);
            return null;
        }
        DnsRecord dnsRecord = this.decodeRecord(string, dnsRecordType, n3, l, byteBuf, n5, n4);
        byteBuf.readerIndex(n5 + n4);
        return (T)dnsRecord;
    }

    protected DnsRecord decodeRecord(String string, DnsRecordType dnsRecordType, int n, long l, ByteBuf byteBuf, int n2, int n3) throws Exception {
        if (dnsRecordType == DnsRecordType.PTR) {
            return new DefaultDnsPtrRecord(string, n, l, this.decodeName0(byteBuf.duplicate().setIndex(n2, n2 + n3)));
        }
        return new DefaultDnsRawRecord(string, dnsRecordType, n, l, byteBuf.retainedDuplicate().setIndex(n2, n2 + n3));
    }

    protected String decodeName0(ByteBuf byteBuf) {
        return DefaultDnsRecordDecoder.decodeName(byteBuf);
    }

    public static String decodeName(ByteBuf byteBuf) {
        int n = -1;
        int n2 = 0;
        int n3 = byteBuf.writerIndex();
        int n4 = byteBuf.readableBytes();
        if (n4 == 0) {
            return ROOT;
        }
        StringBuilder stringBuilder = new StringBuilder(n4 << 1);
        while (byteBuf.isReadable()) {
            boolean bl;
            short s = byteBuf.readUnsignedByte();
            boolean bl2 = bl = (s & 0xC0) == 192;
            if (bl) {
                if (n == -1) {
                    n = byteBuf.readerIndex() + 1;
                }
                if (!byteBuf.isReadable()) {
                    throw new CorruptedFrameException("truncated pointer in a name");
                }
                int n5 = (s & 0x3F) << 8 | byteBuf.readUnsignedByte();
                if (n5 >= n3) {
                    throw new CorruptedFrameException("name has an out-of-range pointer");
                }
                byteBuf.readerIndex(n5);
                if ((n2 += 2) < n3) continue;
                throw new CorruptedFrameException("name contains a loop.");
            }
            if (s == 0) break;
            if (!byteBuf.isReadable(s)) {
                throw new CorruptedFrameException("truncated label in a name");
            }
            stringBuilder.append(byteBuf.toString(byteBuf.readerIndex(), s, CharsetUtil.UTF_8)).append('.');
            byteBuf.skipBytes(s);
        }
        if (n != -1) {
            byteBuf.readerIndex(n);
        }
        if (stringBuilder.length() == 0) {
            return ROOT;
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) != '.') {
            stringBuilder.append('.');
        }
        return stringBuilder.toString();
    }
}

