package net.minecraft.network;

import net.minecraft.client.network.*;
import io.netty.channel.*;

class NetworkSystem$4 extends ChannelInitializer {
    protected void initChannel(final Channel p_initChannel_1_) {
        final NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
        var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$100(NetworkSystem.this), var2));
        NetworkSystem.access$000(NetworkSystem.this).add(var2);
        p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
    }
}