/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.netty.handler;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@ChannelHandler.Sharable
public class VLBViaDecodeHandler
extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection user;

    public VLBViaDecodeHandler(UserConnection user) {
        this.user = user;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!this.user.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.user.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            this.user.transformIncoming(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
        } finally {
            transformedBuf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (PipelineUtil.containsCause(cause, CancelCodecException.class)) {
            return;
        }
        if (PipelineUtil.containsCause(cause, InformativeException.class) && this.user.getProtocolInfo().getState() != State.HANDSHAKE || Via.getManager().debugHandler().enabled()) {
            cause.printStackTrace();
        }
    }
}

