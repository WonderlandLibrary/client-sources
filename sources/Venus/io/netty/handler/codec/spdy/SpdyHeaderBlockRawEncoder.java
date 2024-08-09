/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.spdy.SpdyHeaderBlockEncoder;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyVersion;
import java.util.Set;

public class SpdyHeaderBlockRawEncoder
extends SpdyHeaderBlockEncoder {
    private final int version;

    public SpdyHeaderBlockRawEncoder(SpdyVersion spdyVersion) {
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        this.version = spdyVersion.getVersion();
    }

    private static void setLengthField(ByteBuf byteBuf, int n, int n2) {
        byteBuf.setInt(n, n2);
    }

    private static void writeLengthField(ByteBuf byteBuf, int n) {
        byteBuf.writeInt(n);
    }

    @Override
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, SpdyHeadersFrame spdyHeadersFrame) throws Exception {
        Set set = spdyHeadersFrame.headers().names();
        int n = set.size();
        if (n == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        if (n > 65535) {
            throw new IllegalArgumentException("header block contains too many headers");
        }
        ByteBuf byteBuf = byteBufAllocator.heapBuffer();
        SpdyHeaderBlockRawEncoder.writeLengthField(byteBuf, n);
        for (CharSequence charSequence : set) {
            SpdyHeaderBlockRawEncoder.writeLengthField(byteBuf, charSequence.length());
            ByteBufUtil.writeAscii(byteBuf, charSequence);
            int n2 = byteBuf.writerIndex();
            int n3 = 0;
            SpdyHeaderBlockRawEncoder.writeLengthField(byteBuf, n3);
            for (CharSequence charSequence2 : spdyHeadersFrame.headers().getAll(charSequence)) {
                int n4 = charSequence2.length();
                if (n4 <= 0) continue;
                ByteBufUtil.writeAscii(byteBuf, charSequence2);
                byteBuf.writeByte(0);
                n3 += n4 + 1;
            }
            if (n3 != 0) {
                --n3;
            }
            if (n3 > 65535) {
                throw new IllegalArgumentException("header exceeds allowable length: " + charSequence);
            }
            if (n3 <= 0) continue;
            SpdyHeaderBlockRawEncoder.setLengthField(byteBuf, n2, n3);
            byteBuf.writerIndex(byteBuf.writerIndex() - 1);
        }
        return byteBuf;
    }

    @Override
    void end() {
    }
}

