/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class ProtobufVarint32FrameDecoder
extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        int n = byteBuf.readerIndex();
        int n2 = ProtobufVarint32FrameDecoder.readRawVarint32(byteBuf);
        if (n == byteBuf.readerIndex()) {
            return;
        }
        if (n2 < 0) {
            throw new CorruptedFrameException("negative length: " + n2);
        }
        if (byteBuf.readableBytes() < n2) {
            byteBuf.resetReaderIndex();
        } else {
            list.add(byteBuf.readRetainedSlice(n2));
        }
    }

    private static int readRawVarint32(ByteBuf byteBuf) {
        if (!byteBuf.isReadable()) {
            return 1;
        }
        byteBuf.markReaderIndex();
        byte by = byteBuf.readByte();
        if (by >= 0) {
            return by;
        }
        int n = by & 0x7F;
        if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return 1;
        }
        by = byteBuf.readByte();
        if (by >= 0) {
            n |= by << 7;
        } else {
            n |= (by & 0x7F) << 7;
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return 1;
            }
            by = byteBuf.readByte();
            if (by >= 0) {
                n |= by << 14;
            } else {
                n |= (by & 0x7F) << 14;
                if (!byteBuf.isReadable()) {
                    byteBuf.resetReaderIndex();
                    return 1;
                }
                by = byteBuf.readByte();
                if (by >= 0) {
                    n |= by << 21;
                } else {
                    n |= (by & 0x7F) << 21;
                    if (!byteBuf.isReadable()) {
                        byteBuf.resetReaderIndex();
                        return 1;
                    }
                    by = byteBuf.readByte();
                    n |= by << 28;
                    if (by < 0) {
                        throw new CorruptedFrameException("malformed varint.");
                    }
                }
            }
        }
        return n;
    }
}

