/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
 *  io.netty.channel.socket.SocketChannel
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package us.myles.ViaVersion.sponge.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.reflect.Method;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.sponge.handlers.SpongeDecodeHandler;
import us.myles.ViaVersion.sponge.handlers.SpongeEncodeHandler;
import us.myles.ViaVersion.sponge.handlers.SpongePacketHandler;

public class SpongeChannelInitializer
extends ChannelInitializer<Channel> {
    private final ChannelInitializer<Channel> original;
    private Method method;

    public SpongeChannelInitializer(ChannelInitializer<Channel> oldInit) {
        this.original = oldInit;
        try {
            this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            this.method.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void initChannel(Channel channel) throws Exception {
        if (ProtocolRegistry.SERVER_PROTOCOL != -1 && channel instanceof SocketChannel) {
            UserConnection info = new UserConnection((Channel)((SocketChannel)channel));
            new ProtocolPipeline(info);
            this.method.invoke(this.original, new Object[]{channel});
            SpongeEncodeHandler encoder = new SpongeEncodeHandler(info, (MessageToByteEncoder)channel.pipeline().get("encoder"));
            SpongeDecodeHandler decoder = new SpongeDecodeHandler(info, (ByteToMessageDecoder)channel.pipeline().get("decoder"));
            SpongePacketHandler chunkHandler = new SpongePacketHandler(info);
            channel.pipeline().replace("encoder", "encoder", (ChannelHandler)encoder);
            channel.pipeline().replace("decoder", "decoder", (ChannelHandler)decoder);
            channel.pipeline().addAfter("packet_handler", "viaversion_packet_handler", (ChannelHandler)chunkHandler);
        } else {
            this.method.invoke(this.original, new Object[]{channel});
        }
    }

    public ChannelInitializer<Channel> getOriginal() {
        return this.original;
    }
}

