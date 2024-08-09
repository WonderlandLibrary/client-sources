/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.util.BungeePipelineUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
public class BungeeEncodeHandler
extends MessageToMessageEncoder<ByteBuf> {
    private final UserConnection info;
    private boolean handledCompression;

    public BungeeEncodeHandler(UserConnection userConnection) {
        this.info = userConnection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!channelHandlerContext.channel().isActive()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        try {
            boolean bl = this.handleCompressionOrder(channelHandlerContext, byteBuf2);
            this.info.transformClientbound(byteBuf2, CancelEncoderException::generate);
            if (bl) {
                this.recompress(channelHandlerContext, byteBuf2);
            }
            list.add(byteBuf2.retain());
        } finally {
            byteBuf2.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean handleCompressionOrder(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        boolean bl = false;
        if (!this.handledCompression && channelHandlerContext.pipeline().names().indexOf("compress") > channelHandlerContext.pipeline().names().indexOf("via-encoder")) {
            ByteBuf byteBuf2 = BungeePipelineUtil.decompress(channelHandlerContext, byteBuf);
            if (byteBuf != byteBuf2) {
                try {
                    byteBuf.clear().writeBytes(byteBuf2);
                } finally {
                    byteBuf2.release();
                }
            }
            ChannelHandler channelHandler = channelHandlerContext.pipeline().get("via-decoder");
            ChannelHandler channelHandler2 = channelHandlerContext.pipeline().get("via-encoder");
            channelHandlerContext.pipeline().remove(channelHandler);
            channelHandlerContext.pipeline().remove(channelHandler2);
            channelHandlerContext.pipeline().addAfter("decompress", "via-decoder", channelHandler);
            channelHandlerContext.pipeline().addAfter("compress", "via-encoder", channelHandler2);
            bl = true;
            this.handledCompression = true;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void recompress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        ByteBuf byteBuf2 = BungeePipelineUtil.compress(channelHandlerContext, byteBuf);
        try {
            byteBuf.clear().writeBytes(byteBuf2);
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
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

