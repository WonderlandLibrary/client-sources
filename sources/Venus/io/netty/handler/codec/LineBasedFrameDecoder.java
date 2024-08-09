/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ByteProcessor;
import java.util.List;

public class LineBasedFrameDecoder
extends ByteToMessageDecoder {
    private final int maxLength;
    private final boolean failFast;
    private final boolean stripDelimiter;
    private boolean discarding;
    private int discardedBytes;
    private int offset;

    public LineBasedFrameDecoder(int n) {
        this(n, true, false);
    }

    public LineBasedFrameDecoder(int n, boolean bl, boolean bl2) {
        this.maxLength = n;
        this.failFast = bl2;
        this.stripDelimiter = bl;
    }

    @Override
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object object = this.decode(channelHandlerContext, byteBuf);
        if (object != null) {
            list.add(object);
        }
    }

    protected Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int n = this.findEndOfLine(byteBuf);
        if (!this.discarding) {
            if (n >= 0) {
                ByteBuf byteBuf2;
                int n2;
                int n3 = n - byteBuf.readerIndex();
                int n4 = n2 = byteBuf.getByte(n) == 13 ? 2 : 1;
                if (n3 > this.maxLength) {
                    byteBuf.readerIndex(n + n2);
                    this.fail(channelHandlerContext, n3);
                    return null;
                }
                if (this.stripDelimiter) {
                    byteBuf2 = byteBuf.readRetainedSlice(n3);
                    byteBuf.skipBytes(n2);
                } else {
                    byteBuf2 = byteBuf.readRetainedSlice(n3 + n2);
                }
                return byteBuf2;
            }
            int n5 = byteBuf.readableBytes();
            if (n5 > this.maxLength) {
                this.discardedBytes = n5;
                byteBuf.readerIndex(byteBuf.writerIndex());
                this.discarding = true;
                this.offset = 0;
                if (this.failFast) {
                    this.fail(channelHandlerContext, "over " + this.discardedBytes);
                }
            }
            return null;
        }
        if (n >= 0) {
            int n6 = this.discardedBytes + n - byteBuf.readerIndex();
            int n7 = byteBuf.getByte(n) == 13 ? 2 : 1;
            byteBuf.readerIndex(n + n7);
            this.discardedBytes = 0;
            this.discarding = false;
            if (!this.failFast) {
                this.fail(channelHandlerContext, n6);
            }
        } else {
            this.discardedBytes += byteBuf.readableBytes();
            byteBuf.readerIndex(byteBuf.writerIndex());
        }
        return null;
    }

    private void fail(ChannelHandlerContext channelHandlerContext, int n) {
        this.fail(channelHandlerContext, String.valueOf(n));
    }

    private void fail(ChannelHandlerContext channelHandlerContext, String string) {
        channelHandlerContext.fireExceptionCaught(new TooLongFrameException("frame length (" + string + ") exceeds the allowed maximum (" + this.maxLength + ')'));
    }

    private int findEndOfLine(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        int n2 = byteBuf.forEachByte(byteBuf.readerIndex() + this.offset, n - this.offset, ByteProcessor.FIND_LF);
        if (n2 >= 0) {
            this.offset = 0;
            if (n2 > 0 && byteBuf.getByte(n2 - 1) == 13) {
                --n2;
            }
        } else {
            this.offset = n;
        }
        return n2;
    }
}

