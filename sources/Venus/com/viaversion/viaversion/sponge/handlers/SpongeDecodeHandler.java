/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.sponge.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCounted;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SpongeDecodeHandler
extends ByteToMessageDecoder {
    private final ByteToMessageDecoder minecraftDecoder;
    private final UserConnection info;

    public SpongeDecodeHandler(UserConnection userConnection, ByteToMessageDecoder byteToMessageDecoder) {
        this.info = userConnection;
        this.minecraftDecoder = byteToMessageDecoder;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!this.info.checkServerboundPacket()) {
            byteBuf.clear();
            throw CancelDecoderException.generate(null);
        }
        ReferenceCounted referenceCounted = null;
        try {
            if (this.info.shouldTransformPacket()) {
                referenceCounted = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
                this.info.transformServerbound((ByteBuf)referenceCounted, CancelDecoderException::generate);
            }
            try {
                list.addAll(PipelineUtil.callDecode(this.minecraftDecoder, channelHandlerContext, (Object)(referenceCounted == null ? byteBuf : referenceCounted)));
            } catch (InvocationTargetException invocationTargetException) {
                if (invocationTargetException.getCause() instanceof Exception) {
                    throw (Exception)invocationTargetException.getCause();
                }
                if (invocationTargetException.getCause() instanceof Error) {
                    throw (Error)invocationTargetException.getCause();
                }
            }
        } finally {
            if (referenceCounted != null) {
                referenceCounted.release();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (throwable instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, throwable);
    }
}

