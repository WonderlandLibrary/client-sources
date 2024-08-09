/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  lzma.sdk.lzma.Encoder
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.InputStream;
import java.io.OutputStream;
import lzma.sdk.lzma.Encoder;

public class LzmaFrameEncoder
extends MessageToByteEncoder<ByteBuf> {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(LzmaFrameEncoder.class);
    private static final int MEDIUM_DICTIONARY_SIZE = 65536;
    private static final int MIN_FAST_BYTES = 5;
    private static final int MEDIUM_FAST_BYTES = 32;
    private static final int MAX_FAST_BYTES = 273;
    private static final int DEFAULT_MATCH_FINDER = 1;
    private static final int DEFAULT_LC = 3;
    private static final int DEFAULT_LP = 0;
    private static final int DEFAULT_PB = 2;
    private final Encoder encoder;
    private final byte properties;
    private final int littleEndianDictionarySize;
    private static boolean warningLogged;

    public LzmaFrameEncoder() {
        this(65536);
    }

    public LzmaFrameEncoder(int n, int n2, int n3) {
        this(n, n2, n3, 65536);
    }

    public LzmaFrameEncoder(int n) {
        this(3, 0, 2, n);
    }

    public LzmaFrameEncoder(int n, int n2, int n3, int n4) {
        this(n, n2, n3, n4, false, 32);
    }

    public LzmaFrameEncoder(int n, int n2, int n3, int n4, boolean bl, int n5) {
        if (n < 0 || n > 8) {
            throw new IllegalArgumentException("lc: " + n + " (expected: 0-8)");
        }
        if (n2 < 0 || n2 > 4) {
            throw new IllegalArgumentException("lp: " + n2 + " (expected: 0-4)");
        }
        if (n3 < 0 || n3 > 4) {
            throw new IllegalArgumentException("pb: " + n3 + " (expected: 0-4)");
        }
        if (n + n2 > 4 && !warningLogged) {
            logger.warn("The latest versions of LZMA libraries (for example, XZ Utils) has an additional requirement: lc + lp <= 4. Data which don't follow this requirement cannot be decompressed with this libraries.");
            warningLogged = true;
        }
        if (n4 < 0) {
            throw new IllegalArgumentException("dictionarySize: " + n4 + " (expected: 0+)");
        }
        if (n5 < 5 || n5 > 273) {
            throw new IllegalArgumentException(String.format("numFastBytes: %d (expected: %d-%d)", n5, 5, 273));
        }
        this.encoder = new Encoder();
        this.encoder.setDictionarySize(n4);
        this.encoder.setEndMarkerMode(bl);
        this.encoder.setMatchFinder(0);
        this.encoder.setNumFastBytes(n5);
        this.encoder.setLcLpPb(n, n2, n3);
        this.properties = (byte)((n3 * 5 + n2) * 9 + n);
        this.littleEndianDictionarySize = Integer.reverseBytes(n4);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int n = byteBuf.readableBytes();
        ByteBufInputStream byteBufInputStream = null;
        OutputStream outputStream = null;
        try {
            byteBufInputStream = new ByteBufInputStream(byteBuf);
            outputStream = new ByteBufOutputStream(byteBuf2);
            ((ByteBufOutputStream)outputStream).writeByte(this.properties);
            ((ByteBufOutputStream)outputStream).writeInt(this.littleEndianDictionarySize);
            ((ByteBufOutputStream)outputStream).writeLong(Long.reverseBytes(n));
            this.encoder.code((InputStream)byteBufInputStream, outputStream, -1L, -1L, null);
        } finally {
            if (byteBufInputStream != null) {
                ((InputStream)byteBufInputStream).close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    @Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, boolean bl) throws Exception {
        int n = byteBuf.readableBytes();
        int n2 = LzmaFrameEncoder.maxOutputBufferLength(n);
        return channelHandlerContext.alloc().ioBuffer(n2);
    }

    private static int maxOutputBufferLength(int n) {
        double d = n < 200 ? 1.5 : (n < 500 ? 1.2 : (n < 1000 ? 1.1 : (n < 10000 ? 1.05 : 1.02)));
        return 13 + (int)((double)n * d);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }

    @Override
    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, Object object, boolean bl) throws Exception {
        return this.allocateBuffer(channelHandlerContext, (ByteBuf)object, bl);
    }
}

