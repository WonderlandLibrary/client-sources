package ca.commencal.ware.utils.system;

import ca.commencal.ware.EventsHandler;
import ca.commencal.ware.utils.visual.ChatUtils;
import io.netty.channel.*;

public class Connection extends ChannelDuplexHandler {

    private EventsHandler eventHandler;

    public Connection(EventsHandler eventHandler) {
        this.eventHandler = eventHandler;
        try {
            ChannelPipeline pipeline = Wrapper.INSTANCE.mc().getConnection().getNetworkManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "PacketHandler", (ChannelHandler) this);
            ChatUtils.message("Connection: Attached");
        } catch (Exception exception) {
        	ChatUtils.error("Connection: Error on attaching");
            exception.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
        if (!eventHandler.onPacket(packet, Side.IN)) {
            return;
        }
        super.channelRead(ctx, packet);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (!eventHandler.onPacket(packet, Side.OUT)) {
            return;
        }
        super.write(ctx, packet, promise);
    }

    public static enum Side {
        IN,
        OUT;
    }
}
