/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ProtobufVarint32LengthFieldPrepender
extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes();
        int n2 = ProtobufVarint32LengthFieldPrepender.computeRawVarint32Size(n);
        byteBuf2.ensureWritable(n2 + n);
        ProtobufVarint32LengthFieldPrepender.writeRawVarint32(byteBuf2, n);
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), n);
    }

    static void writeRawVarint32(ByteBuf byteBuf, int n) {
        while (true) {
            if ((n & 0xFFFFFF80) == 0) {
                byteBuf.writeByte(n);
                return;
            }
            byteBuf.writeByte(n & 0x7F | 0x80);
            n >>>= 7;
        }
    }

    static int computeRawVarint32Size(int n) {
        if ((n & 0xFFFFFF80) == 0) {
            return 0;
        }
        if ((n & 0xFFFFC000) == 0) {
            return 1;
        }
        if ((n & 0xFFE00000) == 0) {
            return 0;
        }
        if ((n & 0xF0000000) == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

