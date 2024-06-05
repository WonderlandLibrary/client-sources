package net.minecraft.network;

import io.netty.handler.timeout.*;
import io.netty.channel.*;
import net.minecraft.util.*;
import net.minecraft.server.network.*;

class NetworkSystem$3 extends ChannelInitializer {
    protected void initChannel(final Channel p_initChannel_1_) {
        try {
            p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
        }
        catch (ChannelException ex) {}
        try {
            p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)false);
        }
        catch (ChannelException ex2) {}
        p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
        final NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
        NetworkSystem.access$000(NetworkSystem.this).add(var2);
        p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
        var2.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.access$100(NetworkSystem.this), var2));
    }
}