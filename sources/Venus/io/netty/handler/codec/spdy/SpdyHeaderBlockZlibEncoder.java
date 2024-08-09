/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyHeaderBlockRawEncoder;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyVersion;
import java.util.zip.Deflater;

class SpdyHeaderBlockZlibEncoder
extends SpdyHeaderBlockRawEncoder {
    private final Deflater compressor;
    private boolean finished;

    SpdyHeaderBlockZlibEncoder(SpdyVersion spdyVersion, int n) {
        super(spdyVersion);
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        this.compressor = new Deflater(n);
        this.compressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
    }

    private int setInput(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            this.compressor.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), n);
        } else {
            byte[] byArray = new byte[n];
            byteBuf.getBytes(byteBuf.readerIndex(), byArray);
            this.compressor.setInput(byArray, 0, byArray.length);
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ByteBuf encode(ByteBufAllocator byteBufAllocator, int n) {
        ByteBuf byteBuf = byteBufAllocator.heapBuffer(n);
        boolean bl = true;
        try {
            while (this.compressInto(byteBuf)) {
                byteBuf.ensureWritable(byteBuf.capacity() << 1);
            }
            bl = false;
            ByteBuf byteBuf2 = byteBuf;
            return byteBuf2;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    private boolean compressInto(ByteBuf byteBuf) {
        byte[] byArray = byteBuf.array();
        int n = byteBuf.arrayOffset() + byteBuf.writerIndex();
        int n2 = byteBuf.writableBytes();
        int n3 = this.compressor.deflate(byArray, n, n2, 2);
        byteBuf.writerIndex(byteBuf.writerIndex() + n3);
        return n3 == n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        if (spdyHeadersFrame == null) {
            throw new IllegalArgumentException("frame");
        }
        if (this.finished) {
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBuf byteBuf = super.encode(byteBufAllocator, spdyHeadersFrame);
        try {
            if (!byteBuf.isReadable()) {
                ByteBuf byteBuf2 = Unpooled.EMPTY_BUFFER;
                return byteBuf2;
            }
            int n = this.setInput(byteBuf);
            ByteBuf byteBuf3 = this.encode(byteBufAllocator, n);
            return byteBuf3;
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void end() {
        if (this.finished) {
            return;
        }
        this.finished = true;
        this.compressor.end();
        super.end();
    }
}

