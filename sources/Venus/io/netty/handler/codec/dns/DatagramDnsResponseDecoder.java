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
import io.netty.handler.codec.dns.DatagramDnsResponse;
import io.netty.handler.codec.dns.DnsOpCode;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordDecoder;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@ChannelHandler.Sharable
public class DatagramDnsResponseDecoder
extends MessageToMessageDecoder<DatagramPacket> {
    private final DnsRecordDecoder recordDecoder;

    public DatagramDnsResponseDecoder() {
        this(DnsRecordDecoder.DEFAULT);
    }

    public DatagramDnsResponseDecoder(DnsRecordDecoder dnsRecordDecoder) {
        this.recordDecoder = ObjectUtil.checkNotNull(dnsRecordDecoder, "recordDecoder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
        DnsResponse dnsResponse = DatagramDnsResponseDecoder.newResponse(datagramPacket, byteBuf);
        boolean bl = false;
        try {
            int n = byteBuf.readUnsignedShort();
            int n2 = byteBuf.readUnsignedShort();
            int n3 = byteBuf.readUnsignedShort();
            int n4 = byteBuf.readUnsignedShort();
            this.decodeQuestions(dnsResponse, byteBuf, n);
            this.decodeRecords(dnsResponse, DnsSection.ANSWER, byteBuf, n2);
            this.decodeRecords(dnsResponse, DnsSection.AUTHORITY, byteBuf, n3);
            this.decodeRecords(dnsResponse, DnsSection.ADDITIONAL, byteBuf, n4);
            list.add(dnsResponse);
            bl = true;
        } finally {
            if (!bl) {
                dnsResponse.release();
            }
        }
    }

    private static DnsResponse newResponse(DatagramPacket datagramPacket, ByteBuf byteBuf) {
        int n = byteBuf.readUnsignedShort();
        int n2 = byteBuf.readUnsignedShort();
        if (n2 >> 15 == 0) {
            throw new CorruptedFrameException("not a response");
        }
        DatagramDnsResponse datagramDnsResponse = new DatagramDnsResponse((InetSocketAddress)datagramPacket.sender(), (InetSocketAddress)datagramPacket.recipient(), n, DnsOpCode.valueOf((byte)(n2 >> 11 & 0xF)), DnsResponseCode.valueOf((byte)(n2 & 0xF)));
        datagramDnsResponse.setRecursionDesired((n2 >> 8 & 1) == 1);
        datagramDnsResponse.setAuthoritativeAnswer((n2 >> 10 & 1) == 1);
        datagramDnsResponse.setTruncated((n2 >> 9 & 1) == 1);
        datagramDnsResponse.setRecursionAvailable((n2 >> 7 & 1) == 1);
        datagramDnsResponse.setZ(n2 >> 4 & 7);
        return datagramDnsResponse;
    }

    private void decodeQuestions(DnsResponse dnsResponse, ByteBuf byteBuf, int n) throws Exception {
        for (int i = n; i > 0; --i) {
            dnsResponse.addRecord(DnsSection.QUESTION, this.recordDecoder.decodeQuestion(byteBuf));
        }
    }

    private void decodeRecords(DnsResponse dnsResponse, DnsSection dnsSection, ByteBuf byteBuf, int n) throws Exception {
        Object t;
        for (int i = n; i > 0 && (t = this.recordDecoder.decodeRecord(byteBuf)) != null; --i) {
            dnsResponse.addRecord(dnsSection, (DnsRecord)t);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (DatagramPacket)object, (List<Object>)list);
    }
}

