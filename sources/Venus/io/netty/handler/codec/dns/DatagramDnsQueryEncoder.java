/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.dns;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordEncoder;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@ChannelHandler.Sharable
public class DatagramDnsQueryEncoder
extends MessageToMessageEncoder<AddressedEnvelope<DnsQuery, InetSocketAddress>> {
    private final DnsRecordEncoder recordEncoder;

    public DatagramDnsQueryEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    public DatagramDnsQueryEncoder(DnsRecordEncoder dnsRecordEncoder) {
        this.recordEncoder = ObjectUtil.checkNotNull(dnsRecordEncoder, "recordEncoder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsQuery, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        InetSocketAddress inetSocketAddress = addressedEnvelope.recipient();
        DnsQuery dnsQuery = addressedEnvelope.content();
        ByteBuf byteBuf = this.allocateBuffer(channelHandlerContext, addressedEnvelope);
        boolean bl = false;
        try {
            DatagramDnsQueryEncoder.encodeHeader(dnsQuery, byteBuf);
            this.encodeQuestions(dnsQuery, byteBuf);
            this.encodeRecords(dnsQuery, DnsSection.ADDITIONAL, byteBuf);
            bl = true;
        } finally {
            if (!bl) {
                byteBuf.release();
            }
        }
        list.add(new DatagramPacket(byteBuf, inetSocketAddress, null));
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsQuery, InetSocketAddress> addressedEnvelope) throws Exception {
        return channelHandlerContext.alloc().ioBuffer(1024);
    }

    private static void encodeHeader(DnsQuery dnsQuery, ByteBuf byteBuf) {
        byteBuf.writeShort(dnsQuery.id());
        int n = 0;
        n |= (dnsQuery.opCode().byteValue() & 0xFF) << 14;
        if (dnsQuery.isRecursionDesired()) {
            n |= 0x100;
        }
        byteBuf.writeShort(n);
        byteBuf.writeShort(dnsQuery.count(DnsSection.QUESTION));
        byteBuf.writeShort(0);
        byteBuf.writeShort(0);
        byteBuf.writeShort(dnsQuery.count(DnsSection.ADDITIONAL));
    }

    private void encodeQuestions(DnsQuery dnsQuery, ByteBuf byteBuf) throws Exception {
        int n = dnsQuery.count(DnsSection.QUESTION);
        for (int i = 0; i < n; ++i) {
            this.recordEncoder.encodeQuestion((DnsQuestion)dnsQuery.recordAt(DnsSection.QUESTION, i), byteBuf);
        }
    }

    private void encodeRecords(DnsQuery dnsQuery, DnsSection dnsSection, ByteBuf byteBuf) throws Exception {
        int n = dnsQuery.count(dnsSection);
        for (int i = 0; i < n; ++i) {
            this.recordEncoder.encodeRecord((DnsRecord)dnsQuery.recordAt(dnsSection, i), byteBuf);
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (AddressedEnvelope)object, (List<Object>)list);
    }
}

