/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.sponge.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.handlers.ChannelHandlerContextWrapper;
import com.viaversion.viaversion.handlers.ViaCodecHandler;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.reflect.InvocationTargetException;

public class SpongeEncodeHandler
extends MessageToByteEncoder<Object>
implements ViaCodecHandler {
    private final UserConnection info;
    private final MessageToByteEncoder<?> minecraftEncoder;

    public SpongeEncodeHandler(UserConnection userConnection, MessageToByteEncoder<?> messageToByteEncoder) {
        this.info = userConnection;
        this.minecraftEncoder = messageToByteEncoder;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected void encode(ChannelHandlerContext var1_1, Object var2_2, ByteBuf var3_3) throws Exception {
        if (!(var2_2 instanceof ByteBuf)) {
            try {
                PipelineUtil.callEncode(this.minecraftEncoder, new ChannelHandlerContextWrapper(var1_1, this), var2_2, var3_3);
            } catch (InvocationTargetException var4_4) {
                if (var4_4.getCause() instanceof Exception) {
                    throw (Exception)var4_4.getCause();
                }
                if (!(var4_4.getCause() instanceof Error)) ** GOTO lbl12
                throw (Error)var4_4.getCause();
            }
        } else {
            var3_3.writeBytes((ByteBuf)var2_2);
        }
lbl12:
        // 3 sources

        this.transform(var3_3);
    }

    @Override
    public void transform(ByteBuf byteBuf) throws Exception {
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            return;
        }
        this.info.transformClientbound(byteBuf, CancelEncoderException::generate);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (throwable instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, throwable);
    }
}

