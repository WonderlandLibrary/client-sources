/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.TooLongFrameException;
import java.nio.ByteOrder;
import java.util.List;

public class LengthFieldBasedFrameDecoder
extends ByteToMessageDecoder {
    private final ByteOrder byteOrder;
    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthFieldEndOffset;
    private final int lengthAdjustment;
    private final int initialBytesToStrip;
    private final boolean failFast;
    private boolean discardingTooLongFrame;
    private long tooLongFrameLength;
    private long bytesToDiscard;

    public LengthFieldBasedFrameDecoder(int n, int n2, int n3) {
        this(n, n2, n3, 0, 0);
    }

    public LengthFieldBasedFrameDecoder(int n, int n2, int n3, int n4, int n5) {
        this(n, n2, n3, n4, n5, true);
    }

    public LengthFieldBasedFrameDecoder(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this(ByteOrder.BIG_ENDIAN, n, n2, n3, n4, n5, bl);
    }

    public LengthFieldBasedFrameDecoder(ByteOrder byteOrder, int n, int n2, int n3, int n4, int n5, boolean bl) {
        if (byteOrder == null) {
            throw new NullPointerException("byteOrder");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + n2);
        }
        if (n5 < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + n5);
        }
        if (n2 > n - n3) {
            throw new IllegalArgumentException("maxFrameLength (" + n + ") must be equal to or greater than lengthFieldOffset (" + n2 + ") + lengthFieldLength (" + n3 + ").");
        }
        this.byteOrder = byteOrder;
        this.maxFrameLength = n;
        this.lengthFieldOffset = n2;
        this.lengthFieldLength = n3;
        this.lengthAdjustment = n4;
        this.lengthFieldEndOffset = n2 + n3;
        this.initialBytesToStrip = n5;
        this.failFast = bl;
    }

    @Override
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object object = this.decode(channelHandlerContext, byteBuf);
        if (object != null) {
            list.add(object);
        }
    }

    private void discardingTooLongFrame(ByteBuf byteBuf) {
        long l = this.bytesToDiscard;
        int n = (int)Math.min(l, (long)byteBuf.readableBytes());
        byteBuf.skipBytes(n);
        this.bytesToDiscard = l -= (long)n;
        this.failIfNecessary(false);
    }

    private static void failOnNegativeLengthField(ByteBuf byteBuf, long l, int n) {
        byteBuf.skipBytes(n);
        throw new CorruptedFrameException("negative pre-adjustment length field: " + l);
    }

    private static void failOnFrameLengthLessThanLengthFieldEndOffset(ByteBuf byteBuf, long l, int n) {
        byteBuf.skipBytes(n);
        throw new CorruptedFrameException("Adjusted frame length (" + l + ") is less than lengthFieldEndOffset: " + n);
    }

    private void exceededFrameLength(ByteBuf byteBuf, long l) {
        long l2 = l - (long)byteBuf.readableBytes();
        this.tooLongFrameLength = l;
        if (l2 < 0L) {
            byteBuf.skipBytes((int)l);
        } else {
            this.discardingTooLongFrame = true;
            this.bytesToDiscard = l2;
            byteBuf.skipBytes(byteBuf.readableBytes());
        }
        this.failIfNecessary(true);
    }

    private static void failOnFrameLengthLessThanInitialBytesToStrip(ByteBuf byteBuf, long l, int n) {
        byteBuf.skipBytes((int)l);
        throw new CorruptedFrameException("Adjusted frame length (" + l + ") is less than initialBytesToStrip: " + n);
    }

    protected Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        if (this.discardingTooLongFrame) {
            this.discardingTooLongFrame(byteBuf);
        }
        if (byteBuf.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        }
        int n = byteBuf.readerIndex() + this.lengthFieldOffset;
        long l = this.getUnadjustedFrameLength(byteBuf, n, this.lengthFieldLength, this.byteOrder);
        if (l < 0L) {
            LengthFieldBasedFrameDecoder.failOnNegativeLengthField(byteBuf, l, this.lengthFieldEndOffset);
        }
        if ((l += (long)(this.lengthAdjustment + this.lengthFieldEndOffset)) < (long)this.lengthFieldEndOffset) {
            LengthFieldBasedFrameDecoder.failOnFrameLengthLessThanLengthFieldEndOffset(byteBuf, l, this.lengthFieldEndOffset);
        }
        if (l > (long)this.maxFrameLength) {
            this.exceededFrameLength(byteBuf, l);
            return null;
        }
        int n2 = (int)l;
        if (byteBuf.readableBytes() < n2) {
            return null;
        }
        if (this.initialBytesToStrip > n2) {
            LengthFieldBasedFrameDecoder.failOnFrameLengthLessThanInitialBytesToStrip(byteBuf, l, this.initialBytesToStrip);
        }
        byteBuf.skipBytes(this.initialBytesToStrip);
        int n3 = byteBuf.readerIndex();
        int n4 = n2 - this.initialBytesToStrip;
        ByteBuf byteBuf2 = this.extractFrame(channelHandlerContext, byteBuf, n3, n4);
        byteBuf.readerIndex(n3 + n4);
        return byteBuf2;
    }

    protected long getUnadjustedFrameLength(ByteBuf byteBuf, int n, int n2, ByteOrder byteOrder) {
        long l;
        byteBuf = byteBuf.order(byteOrder);
        switch (n2) {
            case 1: {
                l = byteBuf.getUnsignedByte(n);
                break;
            }
            case 2: {
                l = byteBuf.getUnsignedShort(n);
                break;
            }
            case 3: {
                l = byteBuf.getUnsignedMedium(n);
                break;
            }
            case 4: {
                l = byteBuf.getUnsignedInt(n);
                break;
            }
            case 8: {
                l = byteBuf.getLong(n);
                break;
            }
            default: {
                throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
            }
        }
        return l;
    }

    private void failIfNecessary(boolean bl) {
        if (this.bytesToDiscard == 0L) {
            long l = this.tooLongFrameLength;
            this.tooLongFrameLength = 0L;
            this.discardingTooLongFrame = false;
            if (!this.failFast || bl) {
                this.fail(l);
            }
        } else if (this.failFast && bl) {
            this.fail(this.tooLongFrameLength);
        }
    }

    protected ByteBuf extractFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int n, int n2) {
        return byteBuf.retainedSlice(n, n2);
    }

    private void fail(long l) {
        if (l > 0L) {
            throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + l + " - discarded");
        }
        throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
    }
}

