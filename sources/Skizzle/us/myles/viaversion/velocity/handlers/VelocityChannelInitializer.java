/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelInitializer
 */
package us.myles.ViaVersion.velocity.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Method;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.velocity.handlers.VelocityDecodeHandler;
import us.myles.ViaVersion.velocity.handlers.VelocityEncodeHandler;

public class VelocityChannelInitializer
extends ChannelInitializer<Channel> {
    private final ChannelInitializer<?> original;
    private final boolean clientSide;
    private static Method initChannel;

    public VelocityChannelInitializer(ChannelInitializer<?> original, boolean clientSide) {
        this.original = original;
        this.clientSide = clientSide;
    }

    protected void initChannel(Channel channel) throws Exception {
        initChannel.invoke(this.original, new Object[]{channel});
        UserConnection user = new UserConnection(channel, this.clientSide);
        new ProtocolPipeline(user);
        channel.pipeline().addBefore("minecraft-encoder", "via-encoder", (ChannelHandler)new VelocityEncodeHandler(user));
        channel.pipeline().addBefore("minecraft-decoder", "via-decoder", (ChannelHandler)new VelocityDecodeHandler(user));
    }

    static {
        try {
            initChannel = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            initChannel.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

