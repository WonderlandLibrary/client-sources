package net.minecraft.network;

import io.netty.channel.*;

static final class NetworkManager$4 extends ChannelInitializer {
    final /* synthetic */ NetworkManager val$var1;
    
    protected void initChannel(final Channel p_initChannel_1_) {
        p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)this.val$var1);
    }
}