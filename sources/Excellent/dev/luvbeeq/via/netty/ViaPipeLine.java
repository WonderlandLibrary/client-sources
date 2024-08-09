package dev.luvbeeq.via.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class ViaPipeLine extends ChannelInboundHandlerAdapter {
    public final static String VIA_DECODER_HANDLER_NAME = "via-decoder";
    public final static String VIA_ENCODER_HANDLER_NAME = "via-encoder";

    private final UserConnection user;

    public ViaPipeLine(final UserConnection user) {
        this.user = user;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    public abstract String getDecoderHandlerName();

    public abstract String getEncoderHandlerName();

    public abstract String getDecompressionHandlerName();

    public abstract String getCompressionHandlerName();

    public UserConnection getUser() {
        return user;
    }
}
