/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToByteEncoder<I>
extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;
    private final boolean preferDirect;

    protected MessageToByteEncoder() {
        this(true);
    }

    protected MessageToByteEncoder(Class<? extends I> clazz) {
        this(clazz, true);
    }

    protected MessageToByteEncoder(boolean bl) {
        this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
        this.preferDirect = bl;
    }

    protected MessageToByteEncoder(Class<? extends I> clazz, boolean bl) {
        this.matcher = TypeParameterMatcher.get(clazz);
        this.preferDirect = bl;
    }

    public boolean acceptOutboundMessage(Object object) throws Exception {
        return this.matcher.match(object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        block14: {
            ReferenceCounted referenceCounted = null;
            try {
                if (this.acceptOutboundMessage(object)) {
                    Object object2 = object;
                    referenceCounted = this.allocateBuffer(channelHandlerContext, object2, this.preferDirect);
                    try {
                        this.encode(channelHandlerContext, object2, (ByteBuf)referenceCounted);
                    } finally {
                        ReferenceCountUtil.release(object2);
                    }
                    if (((ByteBuf)referenceCounted).isReadable()) {
                        channelHandlerContext.write(referenceCounted, channelPromise);
                    } else {
                        referenceCounted.release();
                        channelHandlerContext.write(Unpooled.EMPTY_BUFFER, channelPromise);
                    }
                    referenceCounted = null;
                    break block14;
                }
                channelHandlerContext.write(object, channelPromise);
            } catch (EncoderException encoderException) {
                throw encoderException;
            } catch (Throwable throwable) {
                throw new EncoderException(throwable);
            } finally {
                if (referenceCounted != null) {
                    referenceCounted.release();
                }
            }
        }
    }

    protected ByteBuf allocateBuffer(ChannelHandlerContext channelHandlerContext, I i, boolean bl) throws Exception {
        if (bl) {
            return channelHandlerContext.alloc().ioBuffer();
        }
        return channelHandlerContext.alloc().heapBuffer();
    }

    protected abstract void encode(ChannelHandlerContext var1, I var2, ByteBuf var3) throws Exception;

    protected boolean isPreferDirect() {
        return this.preferDirect;
    }
}

