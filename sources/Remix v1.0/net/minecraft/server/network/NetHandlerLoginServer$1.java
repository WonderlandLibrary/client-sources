package net.minecraft.server.network;

import io.netty.channel.*;
import io.netty.util.concurrent.*;

class NetHandlerLoginServer$1 implements ChannelFutureListener {
    public void operationComplete(final ChannelFuture p_operationComplete_1_) {
        NetHandlerLoginServer.this.networkManager.setCompressionTreshold(NetHandlerLoginServer.access$000(NetHandlerLoginServer.this).getNetworkCompressionTreshold());
    }
}