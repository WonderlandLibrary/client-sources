/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
public final class BukkitDecodeHandler
extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection connection;

    public BukkitDecodeHandler(UserConnection userConnection) {
        this.connection = userConnection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!this.connection.checkServerboundPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.connection.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        try {
            this.connection.transformIncoming(byteBuf2, CancelDecoderException::generate);
            list.add(byteBuf2.retain());
        } finally {
            byteBuf2.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (PipelineUtil.containsCause(throwable, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, throwable);
        if (NMSUtil.isDebugPropertySet()) {
            return;
        }
        InformativeException informativeException = PipelineUtil.getCause(throwable, InformativeException.class);
        if (informativeException != null && informativeException.shouldBePrinted()) {
            throwable.printStackTrace();
            informativeException.setShouldBePrinted(true);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT == null || object != BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT) {
            super.userEventTriggered(channelHandlerContext, object);
            return;
        }
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        channelPipeline.addAfter("compress", "via-encoder", channelPipeline.remove("via-encoder"));
        channelPipeline.addAfter("decompress", "via-decoder", channelPipeline.remove("via-decoder"));
        super.userEventTriggered(channelHandlerContext, object);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

