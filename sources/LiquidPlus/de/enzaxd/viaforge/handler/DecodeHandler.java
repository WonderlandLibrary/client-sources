/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandler$Sharable
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToMessageDecoder
 */
package de.enzaxd.viaforge.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import de.enzaxd.viaforge.handler.CommonTransformer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@ChannelHandler.Sharable
public class DecodeHandler
extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection info;
    private boolean handledCompression;
    private boolean skipDoubleTransform;

    public DecodeHandler(UserConnection info) {
        this.info = info;
    }

    public UserConnection getInfo() {
        return this.info;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (this.skipDoubleTransform) {
            this.skipDoubleTransform = false;
            out.add(bytebuf.retain());
            return;
        }
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            boolean needsCompress = this.handleCompressionOrder(ctx, transformedBuf);
            this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
            if (needsCompress) {
                CommonTransformer.compress(ctx, transformedBuf);
                this.skipDoubleTransform = true;
            }
            out.add(transformedBuf.retain());
        }
        finally {
            transformedBuf.release();
        }
    }

    private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
        if (this.handledCompression) {
            return false;
        }
        int decoderIndex = ctx.pipeline().names().indexOf("decompress");
        if (decoderIndex == -1) {
            return false;
        }
        this.handledCompression = true;
        if (decoderIndex > ctx.pipeline().names().indexOf("via-decoder")) {
            CommonTransformer.decompress(ctx, buf);
            ChannelHandler encoder = ctx.pipeline().get("via-encoder");
            ChannelHandler decoder = ctx.pipeline().get("via-decoder");
            ctx.pipeline().remove(encoder);
            ctx.pipeline().remove(decoder);
            ctx.pipeline().addAfter("compress", "via-encoder", encoder);
            ctx.pipeline().addAfter("decompress", "via-decoder", decoder);
            return true;
        }
        return false;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(ctx, cause);
    }
}

