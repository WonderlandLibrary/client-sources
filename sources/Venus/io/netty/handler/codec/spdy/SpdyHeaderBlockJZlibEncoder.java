/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.jcraft.jzlib.Deflater
 *  com.jcraft.jzlib.JZlib
 */
package io.netty.handler.codec.spdy;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.JZlib;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.compression.CompressionException;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyHeaderBlockRawEncoder;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyVersion;
import io.netty.util.ReferenceCounted;

class SpdyHeaderBlockJZlibEncoder
extends SpdyHeaderBlockRawEncoder {
    private final Deflater z = new Deflater();
    private boolean finished;

    SpdyHeaderBlockJZlibEncoder(SpdyVersion spdyVersion, int n, int n2, int n3) {
        super(spdyVersion);
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        int n4 = this.z.deflateInit(n, n2, n3, JZlib.W_ZLIB);
        if (n4 != 0) {
            throw new CompressionException("failed to initialize an SPDY header block deflater: " + n4);
        }
        n4 = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
        if (n4 != 0) {
            throw new CompressionException("failed to set the SPDY dictionary: " + n4);
        }
    }

    private void setInput(ByteBuf byteBuf) {
        int n;
        byte[] byArray;
        int n2 = byteBuf.readableBytes();
        if (byteBuf.hasArray()) {
            byArray = byteBuf.array();
            n = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            byArray = new byte[n2];
            byteBuf.getBytes(byteBuf.readerIndex(), byArray);
            n = 0;
        }
        this.z.next_in = byArray;
        this.z.next_in_index = n;
        this.z.avail_in = n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ByteBuf encode(ByteBufAllocator byteBufAllocator) {
        boolean bl = true;
        ReferenceCounted referenceCounted = null;
        try {
            int n;
            int n2 = this.z.next_in_index;
            int n3 = this.z.next_out_index;
            int n4 = (int)Math.ceil((double)this.z.next_in.length * 1.001) + 12;
            referenceCounted = byteBufAllocator.heapBuffer(n4);
            this.z.next_out = ((ByteBuf)referenceCounted).array();
            this.z.next_out_index = ((ByteBuf)referenceCounted).arrayOffset() + ((ByteBuf)referenceCounted).writerIndex();
            this.z.avail_out = n4;
            try {
                n = this.z.deflate(2);
            } finally {
                ((ByteBuf)referenceCounted).skipBytes(this.z.next_in_index - n2);
            }
            if (n != 0) {
                throw new CompressionException("compression failure: " + n);
            }
            int n5 = this.z.next_out_index - n3;
            if (n5 > 0) {
                ((ByteBuf)referenceCounted).writerIndex(((ByteBuf)referenceCounted).writerIndex() + n5);
            }
            bl = false;
            ReferenceCounted referenceCounted2 = referenceCounted;
            return referenceCounted2;
        } finally {
            this.z.next_in = null;
            this.z.next_out = null;
            if (bl && referenceCounted != null) {
                referenceCounted.release();
            }
        }
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
            this.setInput(byteBuf);
            ByteBuf byteBuf3 = this.encode(byteBufAllocator);
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
        this.z.deflateEnd();
        this.z.next_in = null;
        this.z.next_out = null;
    }
}

