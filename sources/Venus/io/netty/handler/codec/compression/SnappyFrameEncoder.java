/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.compression.CompressionException;
import io.netty.handler.codec.compression.Snappy;

public class SnappyFrameEncoder
extends MessageToByteEncoder<ByteBuf> {
    private static final int MIN_COMPRESSIBLE_LENGTH = 18;
    private static final byte[] STREAM_START = new byte[]{-1, 6, 0, 0, 115, 78, 97, 80, 112, 89};
    private final Snappy snappy = new Snappy();
    private boolean started;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        block6: {
            int n;
            if (!byteBuf.isReadable()) {
                return;
            }
            if (!this.started) {
                this.started = true;
                byteBuf2.writeBytes(STREAM_START);
            }
            if ((n = byteBuf.readableBytes()) > 18) {
                ByteBuf byteBuf3;
                int n2;
                while (true) {
                    n2 = byteBuf2.writerIndex() + 1;
                    if (n < 18) {
                        byteBuf3 = byteBuf.readSlice(n);
                        SnappyFrameEncoder.writeUnencodedChunk(byteBuf3, byteBuf2, n);
                        break block6;
                    }
                    byteBuf2.writeInt(0);
                    if (n <= Short.MAX_VALUE) break;
                    byteBuf3 = byteBuf.readSlice(Short.MAX_VALUE);
                    SnappyFrameEncoder.calculateAndWriteChecksum(byteBuf3, byteBuf2);
                    this.snappy.encode(byteBuf3, byteBuf2, Short.MAX_VALUE);
                    SnappyFrameEncoder.setChunkLength(byteBuf2, n2);
                    n -= Short.MAX_VALUE;
                }
                byteBuf3 = byteBuf.readSlice(n);
                SnappyFrameEncoder.calculateAndWriteChecksum(byteBuf3, byteBuf2);
                this.snappy.encode(byteBuf3, byteBuf2, n);
                SnappyFrameEncoder.setChunkLength(byteBuf2, n2);
            } else {
                SnappyFrameEncoder.writeUnencodedChunk(byteBuf, byteBuf2, n);
            }
        }
    }

    private static void writeUnencodedChunk(ByteBuf byteBuf, ByteBuf byteBuf2, int n) {
        byteBuf2.writeByte(1);
        SnappyFrameEncoder.writeChunkLength(byteBuf2, n + 4);
        SnappyFrameEncoder.calculateAndWriteChecksum(byteBuf, byteBuf2);
        byteBuf2.writeBytes(byteBuf, n);
    }

    private static void setChunkLength(ByteBuf byteBuf, int n) {
        int n2 = byteBuf.writerIndex() - n - 3;
        if (n2 >>> 24 != 0) {
            throw new CompressionException("compressed data too large: " + n2);
        }
        byteBuf.setMediumLE(n, n2);
    }

    private static void writeChunkLength(ByteBuf byteBuf, int n) {
        byteBuf.writeMediumLE(n);
    }

    private static void calculateAndWriteChecksum(ByteBuf byteBuf, ByteBuf byteBuf2) {
        byteBuf2.writeIntLE(Snappy.calculateChecksum(byteBuf));
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

