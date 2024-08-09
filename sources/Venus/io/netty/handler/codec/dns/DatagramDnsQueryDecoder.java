/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.dns.DatagramDnsQuery;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordDecoder;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@ChannelHandler.Sharable
public class DatagramDnsQueryDecoder
extends MessageToMessageDecoder<DatagramPacket> {
    private final DnsRecordDecoder recordDecoder;

    public DatagramDnsQueryDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    public DatagramDnsQueryDecoder(DnsRecordDecoder dnsRecordDecoder) {
        this.recordDecoder = ObjectUtil.checkNotNull(dnsRecordDecoder, "recordDecoder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
        DnsQuery dnsQuery = DatagramDnsQueryDecoder.newQuery(datagramPacket, byteBuf);
        boolean bl = false;
        try {
            int n = byteBuf.readUnsignedShort();
            int n2 = byteBuf.readUnsignedShort();
            int n3 = byteBuf.readUnsignedShort();
            int n4 = byteBuf.readUnsignedShort();
            this.decodeQuestions(dnsQuery, byteBuf, n);
            this.decodeRecords(dnsQuery, DnsSection.ANSWER, byteBuf, n2);
            this.decodeRecords(dnsQuery, DnsSection.AUTHORITY, byteBuf, n3);
            this.decodeRecords(dnsQuery, DnsSection.ADDITIONAL, byteBuf, n4);
            list.add(dnsQuery);
            bl = true;
        } finally {
            if (!bl) {
                dnsQuery.release();
            }
        }
    }

    private static DnsQuery newQuery(DatagramPacket datagramPacket, ByteBuf byteBuf) {
        int n = byteBuf.readUnsignedShort();
        int n2 = byteBuf.readUnsignedShort();
        if (n2 >> 15 == 1) {
            throw new CorruptedFrameException("not a query");
        }
        DatagramDnsQuery datagramDnsQuery = new DatagramDnsQuery((InetSocketAddress)datagramPacket.sender(), (InetSocketAddress)datagramPacket.recipient(), n, DnsOpCode.valueOf((byte)(n2 >> 11 & 0xF)));
        datagramDnsQuery.setRecursionDesired((n2 >> 8 & 1) == 1);
        datagramDnsQuery.setZ(n2 >> 4 & 7);
        return datagramDnsQuery;
    }

    private void decodeQuestions(DnsQuery dnsQuery, ByteBuf byteBuf, int n) throws Exception {
        for (int i = n; i > 0; --i) {
            dnsQuery.addRecord(DnsSection.QUESTION, this.recordDecoder.decodeQuestion(byteBuf));
        }
    }

    private void decodeRecords(DnsQuery dnsQuery, DnsSection dnsSection, ByteBuf byteBuf, int n) throws Exception {
        Object t;
        for (int i = n; i > 0 && (t = this.recordDecoder.decodeRecord(byteBuf)) != null; --i) {
            dnsQuery.addRecord(dnsSection, (DnsRecord)t);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (DatagramPacket)object, (List<Object>)list);
    }
}

