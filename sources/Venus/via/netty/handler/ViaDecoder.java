/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.netty.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import via.netty.handler.CommonTransformer;

@ChannelHandler.Sharable
public class ViaDecoder
extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection user;
    private boolean handledCompression;
    private boolean skipDoubleTransform;

    public ViaDecoder(UserConnection userConnection) {
        this.user = userConnection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.skipDoubleTransform) {
            this.skipDoubleTransform = false;
            list.add(byteBuf.retain());
            return;
        }
        if (!this.user.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.user.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        try {
            boolean bl = this.handleCompressionOrder(channelHandlerContext, byteBuf2);
            this.user.transformIncoming(byteBuf2, CancelDecoderException::generate);
            if (bl) {
                CommonTransformer.compress(channelHandlerContext, byteBuf2);
                this.skipDoubleTransform = true;
            }
            list.add(byteBuf2.retain());
        } finally {
            byteBuf2.release();
        }
    }

    private boolean handleCompressionOrder(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws InvocationTargetException {
        if (this.handledCompression) {
            return true;
        }
        int n = channelHandlerContext.pipeline().names().indexOf("decompress");
        if (n == -1) {
            return true;
        }
        this.handledCompression = true;
        if (n > channelHandlerContext.pipeline().names().indexOf("via-decoder")) {
            CommonTransformer.decompress(channelHandlerContext, byteBuf);
            ChannelHandler channelHandler = channelHandlerContext.pipeline().get("via-encoder");
            ChannelHandler channelHandler2 = channelHandlerContext.pipeline().get("via-decoder");
            channelHandlerContext.pipeline().remove(channelHandler);
            channelHandlerContext.pipeline().remove(channelHandler2);
            channelHandlerContext.pipeline().addAfter("compress", "via-encoder", channelHandler);
            channelHandlerContext.pipeline().addAfter("decompress", "via-decoder", channelHandler2);
            return false;
        }
        return true;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (PipelineUtil.containsCause(throwable, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, throwable);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

