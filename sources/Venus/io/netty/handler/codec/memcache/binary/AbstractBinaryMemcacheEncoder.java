/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectEncoder;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;

public abstract class AbstractBinaryMemcacheEncoder<M extends BinaryMemcacheMessage>
extends AbstractMemcacheObjectEncoder<M> {
    private static final int MINIMUM_HEADER_SIZE = 24;

    @Override
    protected ByteBuf encodeMessage(ChannelHandlerContext channelHandlerContext, M m) {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer(24 + m.extrasLength() + m.keyLength());
        this.encodeHeader(byteBuf, m);
        AbstractBinaryMemcacheEncoder.encodeExtras(byteBuf, m.extras());
        AbstractBinaryMemcacheEncoder.encodeKey(byteBuf, m.key());
        return byteBuf;
    }

    private static void encodeExtras(ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf2 == null || !byteBuf2.isReadable()) {
            return;
        }
        byteBuf.writeBytes(byteBuf2);
    }

    private static void encodeKey(ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf2 == null || !byteBuf2.isReadable()) {
            return;
        }
        byteBuf.writeBytes(byteBuf2);
    }

    protected abstract void encodeHeader(ByteBuf var1, M var2);

    @Override
    protected ByteBuf encodeMessage(ChannelHandlerContext channelHandlerContext, MemcacheMessage memcacheMessage) {
        return this.encodeMessage(channelHandlerContext, (M)((BinaryMemcacheMessage)memcacheMessage));
    }
}

