/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.xml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

public class XmlFrameDecoder
extends ByteToMessageDecoder {
    private final int maxFrameLength;

    public XmlFrameDecoder(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("maxFrameLength must be a positive int");
        }
        this.maxFrameLength = n;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int n;
        int n2;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        long l = 0L;
        int n3 = 0;
        int n4 = 0;
        int n5 = byteBuf.writerIndex();
        if (n5 > this.maxFrameLength) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            this.fail(n5);
            return;
        }
        block0: for (n2 = byteBuf.readerIndex(); n2 < n5; ++n2) {
            byte by;
            n = byteBuf.getByte(n2);
            if (!bl && Character.isWhitespace(n)) {
                ++n4;
                continue;
            }
            if (!bl && n != 60) {
                XmlFrameDecoder.fail(channelHandlerContext);
                byteBuf.skipBytes(byteBuf.readableBytes());
                return;
            }
            if (!bl3 && n == 60) {
                bl = true;
                if (n2 >= n5 - 1) continue;
                by = byteBuf.getByte(n2 + 1);
                if (by == 47) {
                    for (int i = n2 + 2; i <= n5 - 1; ++i) {
                        if (byteBuf.getByte(i) != 62) continue;
                        --l;
                        continue block0;
                    }
                    continue;
                }
                if (XmlFrameDecoder.isValidStartCharForXmlElement(by)) {
                    bl2 = true;
                    ++l;
                    continue;
                }
                if (by == 33) {
                    if (XmlFrameDecoder.isCommentBlockStart(byteBuf, n2)) {
                        ++l;
                        continue;
                    }
                    if (!XmlFrameDecoder.isCDATABlockStart(byteBuf, n2)) continue;
                    ++l;
                    bl3 = true;
                    continue;
                }
                if (by != 63) continue;
                ++l;
                continue;
            }
            if (!bl3 && n == 47) {
                if (n2 >= n5 - 1 || byteBuf.getByte(n2 + 1) != 62) continue;
                --l;
                continue;
            }
            if (n != 62) continue;
            n3 = n2 + 1;
            if (n2 - 1 > -1) {
                by = byteBuf.getByte(n2 - 1);
                if (!bl3) {
                    if (by == 63) {
                        --l;
                    } else if (by == 45 && n2 - 2 > -1 && byteBuf.getByte(n2 - 2) == 45) {
                        --l;
                    }
                } else if (by == 93 && n2 - 2 > -1 && byteBuf.getByte(n2 - 2) == 93) {
                    --l;
                    bl3 = false;
                }
            }
            if (bl2 && l == 0L) break;
        }
        n2 = byteBuf.readerIndex();
        n = n3 - n2;
        if (l == 0L && n > 0) {
            if (n2 + n >= n5) {
                n = byteBuf.readableBytes();
            }
            ByteBuf byteBuf2 = XmlFrameDecoder.extractFrame(byteBuf, n2 + n4, n - n4);
            byteBuf.skipBytes(n);
            list.add(byteBuf2);
        }
    }

    private void fail(long l) {
        if (l > 0L) {
            throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + l + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
    }

    private static void fail(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.fireExceptionCaught(new CorruptedFrameException("frame contains content before the xml starts"));
    }

    private static ByteBuf extractFrame(ByteBuf byteBuf, int n, int n2) {
        return byteBuf.copy(n, n2);
    }

    private static boolean isValidStartCharForXmlElement(byte by) {
        return by >= 97 && by <= 122 || by >= 65 && by <= 90 || by == 58 || by == 95;
    }

    private static boolean isCommentBlockStart(ByteBuf byteBuf, int n) {
        return n < byteBuf.writerIndex() - 3 && byteBuf.getByte(n + 2) == 45 && byteBuf.getByte(n + 3) == 45;
    }

    private static boolean isCDATABlockStart(ByteBuf byteBuf, int n) {
        return n < byteBuf.writerIndex() - 8 && byteBuf.getByte(n + 2) == 91 && byteBuf.getByte(n + 3) == 67 && byteBuf.getByte(n + 4) == 68 && byteBuf.getByte(n + 5) == 65 && byteBuf.getByte(n + 6) == 84 && byteBuf.getByte(n + 7) == 65 && byteBuf.getByte(n + 8) == 91;
    }
}

