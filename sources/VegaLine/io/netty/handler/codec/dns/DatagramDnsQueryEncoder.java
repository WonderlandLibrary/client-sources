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

    public DatagramDnsQueryEncoder(DnsRecordEncoder recordEncoder) {
        this.recordEncoder = ObjectUtil.checkNotNull(recordEncoder, "recordEncoder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<DnsQuery, InetSocketAddress> in, List<Object> out) throws Exception {
        InetSocketAddress recipient = in.recipient();
        DnsQuery query = in.content();
        ByteBuf buf = this.allocateBuffer(ctx, in);
        boolean success = false;
        try {
            DatagramDnsQueryEncoder.encodeHeader(query, buf);
            this.encodeQuestions(query, buf);
            this.encodeRecords(query, DnsSection.ADDITIONAL, buf);
            success = true;
        } finally {
            if (!success) {
                buf.release();
            }
        }
        out.add(new DatagramPacket(buf, recipient, null));
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, AddressedEnvelope<DnsQuery, InetSocketAddress> msg) throws Exception {
        return ctx.alloc().ioBuffer(1024);
    }

    private static void encodeHeader(DnsQuery query, ByteBuf buf) {
        buf.writeShort(query.id());
        int flags = 0;
        flags |= (query.opCode().byteValue() & 0xFF) << 14;
        if (query.isRecursionDesired()) {
            flags |= 0x100;
        }
        buf.writeShort(flags);
        buf.writeShort(query.count(DnsSection.QUESTION));
        buf.writeShort(0);
        buf.writeShort(0);
        buf.writeShort(query.count(DnsSection.ADDITIONAL));
    }

    private void encodeQuestions(DnsQuery query, ByteBuf buf) throws Exception {
        int count = query.count(DnsSection.QUESTION);
        for (int i = 0; i < count; ++i) {
            this.recordEncoder.encodeQuestion((DnsQuestion)query.recordAt(DnsSection.QUESTION, i), buf);
        }
    }

    private void encodeRecords(DnsQuery query, DnsSection section, ByteBuf buf) throws Exception {
        int count = query.count(section);
        for (int i = 0; i < count; ++i) {
            this.recordEncoder.encodeRecord((DnsRecord)query.recordAt(section, i), buf);
        }
    }
}

