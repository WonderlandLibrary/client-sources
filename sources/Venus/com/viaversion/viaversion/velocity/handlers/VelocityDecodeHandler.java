/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.velocity.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.velocity.handlers.VelocityChannelInitializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
public class VelocityDecodeHandler
extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection info;

    public VelocityDecodeHandler(UserConnection userConnection) {
        this.info = userConnection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        try {
            this.info.transformIncoming(byteBuf2, CancelDecoderException::generate);
            list.add(byteBuf2.retain());
        } finally {
            byteBuf2.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (throwable instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, throwable);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object != VelocityChannelInitializer.COMPRESSION_ENABLED_EVENT) {
            super.userEventTriggered(channelHandlerContext, object);
            return;
        }
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        ChannelHandler channelHandler = channelPipeline.get("via-encoder");
        channelPipeline.remove(channelHandler);
        channelPipeline.addBefore("minecraft-encoder", "via-encoder", channelHandler);
        ChannelHandler channelHandler2 = channelPipeline.get("via-decoder");
        channelPipeline.remove(channelHandler2);
        channelPipeline.addBefore("minecraft-decoder", "via-decoder", channelHandler2);
        super.userEventTriggered(channelHandlerContext, object);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

