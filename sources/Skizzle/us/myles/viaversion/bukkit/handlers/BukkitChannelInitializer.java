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
package us.myles.ViaVersion.bukkit.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.reflect.Method;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.bukkit.classgenerator.ClassGenerator;
import us.myles.ViaVersion.bukkit.classgenerator.HandlerConstructor;
import us.myles.ViaVersion.bukkit.handlers.BukkitPacketHandler;

public class BukkitChannelInitializer
extends ChannelInitializer<SocketChannel> {
    private final ChannelInitializer<SocketChannel> original;
    private Method method;

    public BukkitChannelInitializer(ChannelInitializer<SocketChannel> oldInit) {
        this.original = oldInit;
        try {
            this.method = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            this.method.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public ChannelInitializer<SocketChannel> getOriginal() {
        return this.original;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        UserConnection info = new UserConnection((Channel)socketChannel);
        new ProtocolPipeline(info);
        this.method.invoke(this.original, new Object[]{socketChannel});
        HandlerConstructor constructor = ClassGenerator.getConstructor();
        MessageToByteEncoder encoder = constructor.newEncodeHandler(info, (MessageToByteEncoder)socketChannel.pipeline().get("encoder"));
        ByteToMessageDecoder decoder = constructor.newDecodeHandler(info, (ByteToMessageDecoder)socketChannel.pipeline().get("decoder"));
        BukkitPacketHandler chunkHandler = new BukkitPacketHandler(info);
        socketChannel.pipeline().replace("encoder", "encoder", (ChannelHandler)encoder);
        socketChannel.pipeline().replace("decoder", "decoder", (ChannelHandler)decoder);
        socketChannel.pipeline().addAfter("packet_handler", "viaversion_packet_handler", (ChannelHandler)chunkHandler);
    }
}

