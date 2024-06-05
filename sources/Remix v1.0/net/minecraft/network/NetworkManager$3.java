package net.minecraft.network;

import io.netty.handler.timeout.*;
import io.netty.channel.*;
import net.minecraft.util.*;

static final class NetworkManager$3 extends ChannelInitializer {
    final /* synthetic */ NetworkManager val$var2;
    
    protected void initChannel(final Channel p_initChannel_1_) {
        try {
            p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
        }
        catch (ChannelException ex) {}
        try {
            p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
        }
        catch (ChannelException ex2) {}
        p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(20)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)this.val$var2);
    }
}