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
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordEncoder;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

@ChannelHandler.Sharable
public class DatagramDnsResponseEncoder
extends MessageToMessageEncoder<AddressedEnvelope<DnsResponse, InetSocketAddress>> {
    private final DnsRecordEncoder recordEncoder;

    public DatagramDnsResponseEncoder() {
        this(DnsRecordEncoder.DEFAULT);
    }

    public DatagramDnsResponseEncoder(DnsRecordEncoder dnsRecordEncoder) {
        this.recordEncoder = ObjectUtil.checkNotNull(dnsRecordEncoder, "recordEncoder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        InetSocketAddress inetSocketAddress = addressedEnvelope.recipient();
        DnsResponse dnsResponse = addressedEnvelope.content();
        ByteBuf byteBuf = this.allocateBuffer(channelHandlerContext, addressedEnvelope);
        boolean bl = false;
        try {
            DatagramDnsResponseEncoder.encodeHeader(dnsResponse, byteBuf);
            this.encodeQuestions(dnsResponse, byteBuf);
            this.encodeRecords(dnsResponse, DnsSection.ANSWER, byteBuf);
            this.encodeRecords(dnsResponse, DnsSection.AUTHORITY, byteBuf);
            this.encodeRecords(dnsResponse, DnsSection.ADDITIONAL, byteBuf);
            bl = true;
        } finally {
            if (!bl) {
                byteBuf.release();
            }
        }
        list.add(new DatagramPacket(byteBuf, inetSocketAddress, null));
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope) throws Exception {
        return channelHandlerContext.alloc().ioBuffer(1024);
    }

    private static void encodeHeader(DnsResponse dnsResponse, ByteBuf byteBuf) {
        byteBuf.writeShort(dnsResponse.id());
        int n = 32768;
        n |= (dnsResponse.opCode().byteValue() & 0xFF) << 11;
        if (dnsResponse.isAuthoritativeAnswer()) {
            n |= 0x400;
        }
        if (dnsResponse.isTruncated()) {
            n |= 0x200;
        }
        if (dnsResponse.isRecursionDesired()) {
            n |= 0x100;
        }
        if (dnsResponse.isRecursionAvailable()) {
            n |= 0x80;
        }
        n |= dnsResponse.z() << 4;
        byteBuf.writeShort(n |= dnsResponse.code().intValue());
        byteBuf.writeShort(dnsResponse.count(DnsSection.QUESTION));
        byteBuf.writeShort(dnsResponse.count(DnsSection.ANSWER));
        byteBuf.writeShort(dnsResponse.count(DnsSection.AUTHORITY));
        byteBuf.writeShort(dnsResponse.count(DnsSection.ADDITIONAL));
    }

    private void encodeQuestions(DnsResponse dnsResponse, ByteBuf byteBuf) throws Exception {
        int n = dnsResponse.count(DnsSection.QUESTION);
        for (int i = 0; i < n; ++i) {
            this.recordEncoder.encodeQuestion((DnsQuestion)dnsResponse.recordAt(DnsSection.QUESTION, i), byteBuf);
        }
    }

    private void encodeRecords(DnsResponse dnsResponse, DnsSection dnsSection, ByteBuf byteBuf) throws Exception {
        int n = dnsResponse.count(dnsSection);
        for (int i = 0; i < n; ++i) {
            this.recordEncoder.encodeRecord((DnsRecord)dnsResponse.recordAt(dnsSection, i), byteBuf);
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (AddressedEnvelope)object, (List<Object>)list);
    }
}

