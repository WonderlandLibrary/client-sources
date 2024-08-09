/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.json;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

public class JsonObjectDecoder
extends ByteToMessageDecoder {
    private static final int ST_CORRUPTED = -1;
    private static final int ST_INIT = 0;
    private static final int ST_DECODING_NORMAL = 1;
    private static final int ST_DECODING_ARRAY_STREAM = 2;
    private int openBraces;
    private int idx;
    private int lastReaderIndex;
    private int state;
    private boolean insideString;
    private final int maxObjectLength;
    private final boolean streamArrayElements;

    public JsonObjectDecoder() {
        this(0x100000);
    }

    public JsonObjectDecoder(int n) {
        this(n, false);
    }

    public JsonObjectDecoder(boolean bl) {
        this(0x100000, bl);
    }

    public JsonObjectDecoder(int n, boolean bl) {
        if (n < 1) {
            throw new IllegalArgumentException("maxObjectLength must be a positive int");
        }
        this.maxObjectLength = n;
        this.streamArrayElements = bl;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int n;
        int n2;
        if (this.state == -1) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        if (this.idx > byteBuf.readerIndex() && this.lastReaderIndex != byteBuf.readerIndex()) {
            this.idx = byteBuf.readerIndex() + (this.idx - this.lastReaderIndex);
        }
        if ((n2 = byteBuf.writerIndex()) > this.maxObjectLength) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            this.reset();
            throw new TooLongFrameException("object length exceeds " + this.maxObjectLength + ": " + n2 + " bytes discarded");
        }
        for (n = this.idx; n < n2; ++n) {
            byte by = byteBuf.getByte(n);
            if (this.state == 1) {
                this.decodeByte(by, byteBuf, n);
                if (this.openBraces != 0) continue;
                ByteBuf byteBuf2 = this.extractObject(channelHandlerContext, byteBuf, byteBuf.readerIndex(), n + 1 - byteBuf.readerIndex());
                if (byteBuf2 != null) {
                    list.add(byteBuf2);
                }
                byteBuf.readerIndex(n + 1);
                this.reset();
                continue;
            }
            if (this.state == 2) {
                this.decodeByte(by, byteBuf, n);
                if (this.insideString || (this.openBraces != 1 || by != 44) && (this.openBraces != 0 || by != 93)) continue;
                int n3 = byteBuf.readerIndex();
                while (Character.isWhitespace(byteBuf.getByte(n3))) {
                    byteBuf.skipBytes(1);
                    ++n3;
                }
                for (n3 = n - 1; n3 >= byteBuf.readerIndex() && Character.isWhitespace(byteBuf.getByte(n3)); --n3) {
                }
                ByteBuf byteBuf3 = this.extractObject(channelHandlerContext, byteBuf, byteBuf.readerIndex(), n3 + 1 - byteBuf.readerIndex());
                if (byteBuf3 != null) {
                    list.add(byteBuf3);
                }
                byteBuf.readerIndex(n + 1);
                if (by != 93) continue;
                this.reset();
                continue;
            }
            if (by == 123 || by == 91) {
                this.initDecoding(by);
                if (this.state != 2) continue;
                byteBuf.skipBytes(1);
                continue;
            }
            if (Character.isWhitespace(by)) {
                byteBuf.skipBytes(1);
                continue;
            }
            this.state = -1;
            throw new CorruptedFrameException("invalid JSON received at byte position " + n + ": " + ByteBufUtil.hexDump(byteBuf));
        }
        this.idx = byteBuf.readableBytes() == 0 ? 0 : n;
        this.lastReaderIndex = byteBuf.readerIndex();
    }

    protected ByteBuf extractObject(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int n, int n2) {
        return byteBuf.retainedSlice(n, n2);
    }

    private void decodeByte(byte by, ByteBuf byteBuf, int n) {
        if (!(by != 123 && by != 91 || this.insideString)) {
            ++this.openBraces;
        } else if (!(by != 125 && by != 93 || this.insideString)) {
            --this.openBraces;
        } else if (by == 34) {
            if (!this.insideString) {
                this.insideString = true;
            } else {
                int n2 = 0;
                --n;
                while (n >= 0 && byteBuf.getByte(n) == 92) {
                    ++n2;
                    --n;
                }
                if (n2 % 2 == 0) {
                    this.insideString = false;
                }
            }
        }
    }

    private void initDecoding(byte by) {
        this.openBraces = 1;
        this.state = by == 91 && this.streamArrayElements ? 2 : 1;
    }

    private void reset() {
        this.insideString = false;
        this.state = 0;
        this.openBraces = 0;
    }
}

