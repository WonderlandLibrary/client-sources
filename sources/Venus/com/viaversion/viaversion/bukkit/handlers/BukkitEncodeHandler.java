/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
public final class BukkitEncodeHandler
extends MessageToMessageEncoder<ByteBuf> {
    private final UserConnection connection;
    private boolean handledCompression = BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT != null;

    public BukkitEncodeHandler(UserConnection userConnection) {
        this.connection = userConnection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!this.connection.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.connection.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        try {
            boolean bl = !this.handledCompression && this.handleCompressionOrder(channelHandlerContext, byteBuf2);
            this.connection.transformClientbound(byteBuf2, CancelEncoderException::generate);
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
    private boolean handleCompressionOrder(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        List<String> list = channelPipeline.names();
        int n = list.indexOf("compress");
        if (n == -1) {
            return true;
        }
        this.handledCompression = true;
        if (n > list.indexOf("via-encoder")) {
            ByteBuf byteBuf2 = (ByteBuf)PipelineUtil.callDecode((ByteToMessageDecoder)channelPipeline.get("decompress"), channelHandlerContext, (Object)byteBuf).get(0);
            try {
                byteBuf.clear().writeBytes(byteBuf2);
            } finally {
                byteBuf2.release();
            }
            channelPipeline.addAfter("compress", "via-encoder", channelPipeline.remove("via-encoder"));
            channelPipeline.addAfter("decompress", "via-decoder", channelPipeline.remove("via-decoder"));
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void recompress(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer();
        try {
            PipelineUtil.callEncode((MessageToByteEncoder)channelHandlerContext.pipeline().get("compress"), channelHandlerContext, byteBuf, byteBuf2);
            byteBuf.clear().writeBytes(byteBuf2);
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

    public UserConnection connection() {
        return this.connection;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

