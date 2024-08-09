/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.compression.FastLz;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class FastLzFrameEncoder
extends MessageToByteEncoder<ByteBuf> {
    private final int level;
    private final Checksum checksum;

    public FastLzFrameEncoder() {
        this(0, null);
    }

    public FastLzFrameEncoder(int n) {
        this(n, null);
    }

    public FastLzFrameEncoder(boolean bl) {
        this(0, bl ? new Adler32() : null);
    }

    public FastLzFrameEncoder(int n, Checksum checksum) {
        super(false);
        if (n != 0 && n != 1 && n != 2) {
            throw new IllegalArgumentException(String.format("level: %d (expected: %d or %d or %d)", n, 0, 1, 2));
        }
        this.level = n;
        this.checksum = checksum;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        Checksum checksum = this.checksum;
        while (byteBuf.isReadable()) {
            int n;
            int n2;
            byte[] byArray;
            int n3;
            int n4 = byteBuf.readerIndex();
            int n5 = Math.min(byteBuf.readableBytes(), 65535);
            int n6 = byteBuf2.writerIndex();
            byteBuf2.setMedium(n6, 4607066);
            int n7 = n6 + 4 + (checksum != null ? 4 : 0);
            if (n5 < 32) {
                n3 = 0;
                byteBuf2.ensureWritable(n7 + 2 + n5);
                byArray = byteBuf2.array();
                n2 = byteBuf2.arrayOffset() + n7 + 2;
                if (checksum != null) {
                    int n8;
                    byte[] byArray2;
                    if (byteBuf.hasArray()) {
                        byArray2 = byteBuf.array();
                        n8 = byteBuf.arrayOffset() + n4;
                    } else {
                        byArray2 = new byte[n5];
                        byteBuf.getBytes(n4, byArray2);
                        n8 = 0;
                    }
                    checksum.reset();
                    checksum.update(byArray2, n8, n5);
                    byteBuf2.setInt(n6 + 4, (int)checksum.getValue());
                    System.arraycopy(byArray2, n8, byArray, n2, n5);
                } else {
                    byteBuf.getBytes(n4, byArray, n2, n5);
                }
                n = n5;
            } else {
                if (byteBuf.hasArray()) {
                    byArray = byteBuf.array();
                    n2 = byteBuf.arrayOffset() + n4;
                } else {
                    byArray = new byte[n5];
                    byteBuf.getBytes(n4, byArray);
                    n2 = 0;
                }
                if (checksum != null) {
                    checksum.reset();
                    checksum.update(byArray, n2, n5);
                    byteBuf2.setInt(n6 + 4, (int)checksum.getValue());
                }
                int n9 = FastLz.calculateOutputBufferLength(n5);
                byteBuf2.ensureWritable(n7 + 4 + n9);
                byte[] byArray3 = byteBuf2.array();
                int n10 = byteBuf2.arrayOffset() + n7 + 4;
                int n11 = FastLz.compress(byArray, n2, n5, byArray3, n10, this.level);
                if (n11 < n5) {
                    n3 = 1;
                    n = n11;
                    byteBuf2.setShort(n7, n);
                    n7 += 2;
                } else {
                    n3 = 0;
                    System.arraycopy(byArray, n2, byArray3, n10 - 2, n5);
                    n = n5;
                }
            }
            byteBuf2.setShort(n7, n5);
            byteBuf2.setByte(n6 + 3, n3 | (checksum != null ? 16 : 0));
            byteBuf2.writerIndex(n7 + 2 + n);
            byteBuf.skipBytes(n5);
        }
        return;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

