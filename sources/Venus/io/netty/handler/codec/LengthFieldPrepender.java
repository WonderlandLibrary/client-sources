/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.ObjectUtil;
import java.nio.ByteOrder;
import java.util.List;

@ChannelHandler.Sharable
public class LengthFieldPrepender
extends MessageToMessageEncoder<ByteBuf> {
    private final ByteOrder byteOrder;
    private final int lengthFieldLength;
    private final boolean lengthIncludesLengthFieldLength;
    private final int lengthAdjustment;

    public LengthFieldPrepender(int n) {
        this(n, false);
    }

    public LengthFieldPrepender(int n, boolean bl) {
        this(n, 0, bl);
    }

    public LengthFieldPrepender(int n, int n2) {
        this(n, n2, false);
    }

    public LengthFieldPrepender(int n, int n2, boolean bl) {
        this(ByteOrder.BIG_ENDIAN, n, n2, bl);
    }

    public LengthFieldPrepender(ByteOrder byteOrder, int n, int n2, boolean bl) {
        if (n != 1 && n != 2 && n != 3 && n != 4 && n != 8) {
            throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + n);
        }
        ObjectUtil.checkNotNull(byteOrder, "byteOrder");
        this.byteOrder = byteOrder;
        this.lengthFieldLength = n;
        this.lengthIncludesLengthFieldLength = bl;
        this.lengthAdjustment = n2;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int n = byteBuf.readableBytes() + this.lengthAdjustment;
        if (this.lengthIncludesLengthFieldLength) {
            n += this.lengthFieldLength;
        }
        if (n < 0) {
            throw new IllegalArgumentException("Adjusted frame length (" + n + ") is less than zero");
        }
        switch (this.lengthFieldLength) {
            case 1: {
                if (n >= 256) {
                    throw new IllegalArgumentException("length does not fit into a byte: " + n);
                }
                list.add(channelHandlerContext.alloc().buffer(1).order(this.byteOrder).writeByte((byte)n));
                break;
            }
            case 2: {
                if (n >= 65536) {
                    throw new IllegalArgumentException("length does not fit into a short integer: " + n);
                }
                list.add(channelHandlerContext.alloc().buffer(2).order(this.byteOrder).writeShort((short)n));
                break;
            }
            case 3: {
                if (n >= 0x1000000) {
                    throw new IllegalArgumentException("length does not fit into a medium integer: " + n);
                }
                list.add(channelHandlerContext.alloc().buffer(3).order(this.byteOrder).writeMedium(n));
                break;
            }
            case 4: {
                list.add(channelHandlerContext.alloc().buffer(4).order(this.byteOrder).writeInt(n));
                break;
            }
            case 8: {
                list.add(channelHandlerContext.alloc().buffer(8).order(this.byteOrder).writeLong(n));
                break;
            }
            default: {
                throw new Error("should not reach here");
            }
        }
        list.add(byteBuf.retain());
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

